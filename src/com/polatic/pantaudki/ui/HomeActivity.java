/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package com.polatic.pantaudki.ui;

import com.polatic.pantaudki.R;
import com.polatic.pantaudki.utils.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

public class HomeActivity extends Activity {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Initialize sharedprefernces and context
        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mContext = this;

        // Redirect to pantau activity if user already pass guideline

        Intent i = new Intent(this, PantauActivity.class);
        startActivity(i);
        finish();

        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);

    }

}
