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
import com.github.vase4kin.teamcityapp.base.list.data.BaseListRxDataManager;
import com.github.vase4kin.teamcityapp.navigation.api.NavigationItem;

import java.util.List;

/**
 * Interactor to fetch favorite build types
 */
public interface FavoritesInteractor extends BaseListRxDataManager<FavoritesInteractorImpl.NavigationItemsList, NavigationItem> {

    /**
     * Load favorites
     *
     * @param update          - Force data update
     * @param loadingListener - Listener to receive server callbacks
     */
    void loadFavorites(@NonNull OnLoadingListener<List<NavigationItem>> loadingListener, boolean update);

    /**
     * @return the count of favorite build types
     */
    int getFavoritesCount();
}
