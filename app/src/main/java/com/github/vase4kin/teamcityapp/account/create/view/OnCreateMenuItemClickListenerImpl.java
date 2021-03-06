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

package com.github.vase4kin.teamcityapp.account.create.view;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.github.vase4kin.teamcityapp.R;

public class OnCreateMenuItemClickListenerImpl implements Toolbar.OnMenuItemClickListener {

    private final OnValidateListener onValidateListener;
    private final TextView serverUrl;
    private final TextView userName;
    private final TextView password;
    private final Switch guestUserSwitch;
    private final Switch disableSslSwitch;

    OnCreateMenuItemClickListenerImpl(OnValidateListener onValidateListener,
                                      TextView serverUrl,
                                      TextView userName,
                                      TextView password,
                                      Switch guestUserSwitch,
                                      Switch disableSslSwitch) {
        this.onValidateListener = onValidateListener;
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.password = password;
        this.guestUserSwitch = guestUserSwitch;
        this.disableSslSwitch = disableSslSwitch;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
            if (guestUserSwitch.isChecked()) {
                onValidateListener.validateGuestUserData(
                        serverUrl.getText().toString().trim(),
                        disableSslSwitch.isChecked());
            } else {
                onValidateListener.validateUserData(
                        serverUrl.getText().toString().trim(),
                        userName.getText().toString().trim(),
                        password.getText().toString().trim(),
                        disableSslSwitch.isChecked());
            }
            return true;
        }
        return false;
    }
}
