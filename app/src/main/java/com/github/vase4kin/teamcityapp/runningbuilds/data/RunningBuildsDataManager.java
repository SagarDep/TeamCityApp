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

package com.github.vase4kin.teamcityapp.runningbuilds.data;

import android.support.annotation.NonNull;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.buildlist.data.BuildListDataManager;
import com.github.vase4kin.teamcityapp.overview.data.BuildDetails;

import java.util.List;

/**
 * Data manager handles running builds server operations
 */
public interface RunningBuildsDataManager extends BuildListDataManager {

    /**
     * Load running builds
     *
     * @param update          - Force cache update
     * @param loadingListener - Listener to receive server callbacks
     */
    void load(@NonNull OnLoadingListener<List<BuildDetails>> loadingListener, boolean update);

    /**
     * Load running builds count
     *
     * @param loadingListener - Listener to receive server callbacks
     */
    void loadCount(@NonNull OnLoadingListener<Integer> loadingListener);
}
