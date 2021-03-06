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

package com.github.vase4kin.teamcityapp.runbuild.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.agents.api.Agent;
import com.github.vase4kin.teamcityapp.agents.api.Agents;
import com.github.vase4kin.teamcityapp.api.Repository;
import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.navigation.api.BuildType;
import com.github.vase4kin.teamcityapp.properties.api.Properties;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Impl of {@link RunBuildInteractor}
 */
public class RunBuildInteractorImpl implements RunBuildInteractor {

    /**
     * Repository instance
     */
    private Repository mRepository;
    /**
     * Build type id
     */
    @NonNull
    private String mBuildTypeId;
    /**
     * To handle rx subscriptions
     */
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public RunBuildInteractorImpl(Repository repository, @NonNull String buildTypeId) {
        this.mRepository = repository;
        this.mBuildTypeId = buildTypeId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void queueBuild(String branchName,
                           @Nullable Agent agent,
                           boolean isPersonal,
                           boolean queueToTheTop,
                           boolean cleanAllFiles,
                           Properties properties,
                           final LoadingListenerWithForbiddenSupport<String> loadingListener) {
        Build build = new Build();
        build.setBranchName(branchName);
        build.setBuildTypeId(mBuildTypeId);
        build.setPersonal(isPersonal);
        build.setQueueAtTop(queueToTheTop);
        build.setCleanSources(cleanAllFiles);
        if (agent != null) {
            build.setAgent(agent);
        }
        if (!properties.getObjects().isEmpty()) {
            build.setProperties(properties);
        }
        queueBuild(build, loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void queueBuild(String branchName, Properties properties, final LoadingListenerWithForbiddenSupport<String> loadingListener) {
        Build build = new Build();
        build.setBuildTypeId(mBuildTypeId);
        build.setBranchName(branchName);
        build.setProperties(properties);
        queueBuild(build, loadingListener);
    }

    /**
     * Queue build
     *
     * @param build           - Build to queue
     * @param loadingListener - listener to receive callbacks on UI
     */
    private void queueBuild(Build build, final LoadingListenerWithForbiddenSupport<String> loadingListener) {
        Subscription queueBuildSubscription = mRepository.queueBuild(build)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Build>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            if (exception.code() == CODE_FORBIDDEN) {
                                loadingListener.onForbiddenError();
                            } else {
                                loadingListener.onFail(e.getMessage());
                            }
                        } else {
                            loadingListener.onFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Build build) {
                        loadingListener.onSuccess(build.getHref());
                    }
                });
        mSubscription.add(queueBuildSubscription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribe() {
        mSubscription.unsubscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAgents(final OnLoadingListener<List<Agent>> loadingListener) {
        Subscription queueBuildSubscription = mRepository.buildType(mBuildTypeId, false)
                .flatMap(new Func1<BuildType, Observable<Agents>>() {
                    @Override
                    public Observable<Agents> call(BuildType buildType) {
                        boolean isCompatibleAgentsSupported = buildType.getCompatibleAgents() != null;
                        if (isCompatibleAgentsSupported) {
                            // TODO: No hardcode
                            String locator = String.format("compatible:(buildType:(id:%s))", mBuildTypeId);
                            return mRepository.listAgents(null, null, locator, false);
                        } else {
                            return mRepository.listAgents(false, null, null, false);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Agents>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingListener.onFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Agents agents) {
                        loadingListener.onSuccess(agents.getObjects());
                    }
                });
        mSubscription.add(queueBuildSubscription);
    }
}
