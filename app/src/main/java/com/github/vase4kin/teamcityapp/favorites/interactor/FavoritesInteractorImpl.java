/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.favorites.interactor;

import android.support.annotation.NonNull;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.api.Repository;
import com.github.vase4kin.teamcityapp.api.interfaces.Collectible;
import com.github.vase4kin.teamcityapp.base.list.data.BaseListRxDataManagerImpl;
import com.github.vase4kin.teamcityapp.navigation.api.BuildType;
import com.github.vase4kin.teamcityapp.navigation.api.NavigationItem;
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Impl of {@link FavoritesInteractor}
 */
public class FavoritesInteractorImpl extends BaseListRxDataManagerImpl<FavoritesInteractorImpl.NavigationItemsList, NavigationItem> implements FavoritesInteractor {

    private final Repository repository;
    private final SharedUserStorage storage;

    public FavoritesInteractorImpl(Repository repository, SharedUserStorage storage) {
        this.repository = repository;
        this.storage = storage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFavorites(@NonNull OnLoadingListener<List<NavigationItem>> loadingListener, final boolean update) {
        List<String> ids = storage.getFavoriteBuildTypeIds();
        Observable<NavigationItemsList> favoritesObservable = Observable.from(ids)
                .flatMap(new Func1<String, Observable<BuildType>>() {
                    @Override
                    public Observable<BuildType> call(String id) {
                        return repository.buildType(id, update)
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends BuildType>>() {
                                    @Override
                                    public Observable<? extends BuildType> call(Throwable throwable) {
                                        return Observable.empty();
                                    }
                                })
                                .flatMap(new Func1<BuildType, Observable<BuildType>>() {
                                    @Override
                                    public Observable<BuildType> call(BuildType buildType) {
                                        return Observable.just(buildType);
                                    }
                                });
                    }
                })
                .toSortedList(new Func2<BuildType, BuildType, Integer>() {
                    @Override
                    public Integer call(BuildType buildType1, BuildType buildType2) {
                        // sort by project ids
                        return buildType1.getProjectId().compareToIgnoreCase((buildType2.getProjectId()));
                    }
                })
                .flatMap(new Func1<List<BuildType>, Observable<List<NavigationItem>>>() {
                    @Override
                    public Observable<List<NavigationItem>> call(List<BuildType> buildTypes) {
                        return Observable.from(buildTypes).cast(NavigationItem.class).toList();
                    }
                })
                .flatMap(new Func1<List<NavigationItem>, Observable<NavigationItemsList>>() {
                    @Override
                    public Observable<NavigationItemsList> call(List<NavigationItem> navigationItems) {
                        return Observable.just(new NavigationItemsList(navigationItems));
                    }
                });
        load(favoritesObservable, loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFavoritesCount() {
        return storage.getFavoriteBuildTypeIds().size();
    }

    static class NavigationItemsList implements Collectible<NavigationItem> {

        private final List<NavigationItem> items;

        NavigationItemsList(List<NavigationItem> items) {
            this.items = items;
        }

        @Override
        public List<NavigationItem> getObjects() {
            return items;
        }
    }
}
