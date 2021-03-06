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

package com.github.vase4kin.teamcityapp.bottomsheet_dialog.model;

import android.graphics.drawable.Drawable;

import com.github.vase4kin.teamcityapp.base.list.view.BaseDataModel;

/**
 * Model for handling botton sheet data
 */
public interface BottomSheetDataModel extends BaseDataModel {

    /**
     * Get title of menu item
     *
     * @param position - adapter position
     * @return title
     */
    String getTitle(int position);

    /**
     * Get description
     *
     * @param position - adapter position
     * @return description
     */
    String getDescription(int position);

    /**
     * Get icon for menu item
     *
     * @param position - adapter position
     * @return menu item icon
     */
    Drawable getIcon(int position);

    /**
     * Has copy action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasCopyAction(int position);

    /**
     * Has branch action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasBranchAction(int position);

    /**
     * Has build type action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasBuildTypeAction(int position);

    /**
     * Has project action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasProjectAction(int position);

    /**
     * Has artifact open action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasArtifactOpenAction(int position);

    /**
     * Has artifact download action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasArtifactDownloadAction(int position);

    /**
     * Has artifact open in browser action
     *
     * @param position - adapter position
     * @return action of menu item
     */
    boolean hasArtifactOpenInBrowserAction(int position);
}
