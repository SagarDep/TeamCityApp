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

package com.github.vase4kin.teamcityapp.overview.data;

import com.github.vase4kin.teamcityapp.base.list.view.BaseDataModel;

/**
 * Data model to manage build details items
 */
public interface OverviewDataModel extends BaseDataModel {

    /**
     * @param position - Adapter position
     * @return Icon
     */
    String getIcon(int position);

    /**
     * @param position - Adapter position
     * @return Description for element
     */
    String getDescription(int position);

    /**
     * @param position - Adapter position
     * @return Title of element
     */
    String getHeaderName(int position);

    /**
     * @param position - Adapter position
     * @return {true} if card is branch one
     */
    boolean isBranchCard(int position);

    /**
     * @param position - Adapter position
     * @return {true} if card is build type one
     */
    boolean isBuildTypeCard(int position);

    /**
     * @param position - Adapter position
     * @return {true} if card is project one
     */
    boolean isProjectCard(int position);
}
