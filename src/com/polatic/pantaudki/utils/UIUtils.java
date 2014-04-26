/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.polatic.pantaudki.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class UIUtils {
	
	/**
	 * Hide keyboard
	 * @param mContext
	 * @param activity
	 */
	public static void hideKeyboard(Context mContext, Window window) {
		InputMethodManager inputManager = 
		        (InputMethodManager) mContext.
		            getSystemService(Context.INPUT_METHOD_SERVICE);
		
		inputManager.hideSoftInputFromWindow(
				window.getCurrentFocus().getWindowToken(),
		        InputMethodManager.HIDE_NOT_ALWAYS); 
	}
	
}
