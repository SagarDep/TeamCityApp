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

package com.github.vase4kin.teamcityapp.bottomsheet_dialog.dagger;

import android.os.Bundle;
import android.view.View;

import com.github.vase4kin.teamcityapp.bottomsheet_dialog.menu_items.DefaultMenuItemsFactory;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.menu_items.MenuItemsFactory;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetDataModel;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetDataModelImpl;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetInteractor;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetInteractorImpl;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.view.BottomSheetView;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.view.BottomSheetViewImpl;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

/**
 * Bottom sheet dialog dependencies
 */
@Module
public class BottomSheetModule {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_BOTTOM_SHEET_TYPE = "arg_bottom_sheet_type";

    private final View view;
    private final Bundle bundle;

    public BottomSheetModule(View view, Bundle bundle) {
        this.view = view;
        this.bundle = bundle;
    }

    @Provides
    BottomSheetDataModel providesBottomSheetDataModel(Map<Integer, MenuItemsFactory> menuItemsFactories) {
        int type = bundle.getInt(ARG_BOTTOM_SHEET_TYPE);
        return new BottomSheetDataModelImpl(menuItemsFactories.get(type).createMenuItems());
    }

    @Provides
    BottomSheetInteractor providesInteractor(BottomSheetDataModel model) {
        String title = bundle.getString(ARG_TITLE);
        return new BottomSheetInteractorImpl(title, model);
    }

    @Provides
    BottomSheetView providesBottomSheetView() {
        return new BottomSheetViewImpl(view);
    }

    @IntoMap
    @IntKey(DefaultMenuItemsFactory.TYPE_DEFAULT)
    @Provides
    MenuItemsFactory providesDefaultMenu() {
        return new DefaultMenuItemsFactory(view.getContext());
    }
}
