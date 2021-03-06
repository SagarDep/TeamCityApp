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

package com.github.vase4kin.teamcityapp.drawer.router;

import android.support.v7.app.AppCompatActivity;

import com.github.vase4kin.teamcityapp.about.AboutActivity;
import com.github.vase4kin.teamcityapp.account.manage.view.AccountListActivity;
import com.github.vase4kin.teamcityapp.agenttabs.view.AgentTabsActivity;
import com.github.vase4kin.teamcityapp.favorites.view.FavoritesActivity;
import com.github.vase4kin.teamcityapp.queue.view.BuildQueueActivity;
import com.github.vase4kin.teamcityapp.root.view.RootProjectsActivity;
import com.github.vase4kin.teamcityapp.runningbuilds.view.RunningBuildsListActivity;

/**
 * Impl of {@link DrawerRouter}
 */
public class DrawerRouterImpl implements DrawerRouter {

    protected AppCompatActivity mActivity;

    public DrawerRouterImpl(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startRootProjectsActivity() {
        RootProjectsActivity.startWhenNavigateToRootFromDrawer(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startRootProjectsActivityWhenSwitchingAccounts() {
        RootProjectsActivity.startWhenSwitchingAccountsFromDrawer(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startAccountListActivity() {
        AccountListActivity.start(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startAgentActivity() {
        AgentTabsActivity.start(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startBuildRunningActivity() {
        RunningBuildsListActivity.start(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startQueuedBuildsActivity() {
        BuildQueueActivity.start(mActivity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startAboutActivity() {
        AboutActivity.start(mActivity);
    }

    @Override
    public void startFavoritesActivity() {
        FavoritesActivity.start(mActivity);
    }
}
