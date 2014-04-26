/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package com.polatic.pantaudki.ui;

import com.polatic.pantaudki.R;
import com.polatic.pantaudki.guideline.GuidelineCallFragment;
import com.polatic.pantaudki.guideline.GuidelineHomeFragment;
import com.polatic.pantaudki.guideline.GuidelineOrderFragment;
import com.polatic.pantaudki.utils.Constant;
import com.viewpagerindicator.PageIndicator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class GuidelineActivity extends FragmentActivity {

    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private PageIndicator mPageIndicator;
    private Button mBtnPrev;
    private Button mBtnNext;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guideline_mainlayout);
        
        initView();
    }

    /**
     * View initialization
     */
    private void initView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.mainlayout_guideline);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPageIndicator = (PageIndicator) findViewById(R.id.guideline_indicator);
        mBtnPrev = (Button) findViewById(R.id.guideline_btnprev);
        mBtnNext = (Button) findViewById(R.id.guideline_btnnext);

        mViewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager()));
        mPageIndicator.setViewPager(mViewPager);
        mBtnPrev.setText(R.string.guideline_button_skip);
        mBtnNext.setText(R.string.guideline_button_next);

        mPageIndicator.setOnPageChangeListener(fragmentChangeListener);

        // Set button previous listener
        mBtnPrev.setOnClickListener(previousButtonListener);
        
        // Set button previous listener
        mBtnNext.setOnClickListener(nextButtonListener);

    }
    
    /**
     * Change fragment listener
     */
    private OnPageChangeListener fragmentChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mBtnPrev.setText(R.string.guideline_button_skip);
                    mBtnNext.setText(R.string.guideline_button_next);
                    break;
                case 1:
                    mBtnPrev.setText(R.string.guideline_button_prev);
                    mBtnNext.setText(R.string.guideline_button_next);
                    break;
                case 2:
                    mBtnPrev.setText(R.string.guideline_button_prev);
                    mBtnNext.setText(R.string.guideline_button_finish);
                    break;
                    
                default:
                    mBtnPrev.setText(R.string.guideline_button_skip);
                    mBtnNext.setText(R.string.guideline_button_next);
                    break;
            }
        };

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    
    /**
     * Set previous button listener
     */
    private OnClickListener previousButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // If button value is skip or finish then go to home page
            if (mBtnPrev.getText().toString().equals(getString(R.string.guideline_button_skip))) {
                goToHome();
                
            } else if (mBtnPrev.getText().toString()
                    .equals(getString(R.string.guideline_button_prev))) {
                // If button value is previous then go to previous fragment
                int currentPage = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(currentPage - 1, true);
            }
        }
    };
    
    /**
     * Set next button listener
     */
    private OnClickListener nextButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // If button value is next then go to next page
            if (mBtnNext.getText().toString().equals(getString(R.string.guideline_button_next))) {
                int currentPage = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(currentPage + 1, true);
            } else if (mBtnNext.getText().toString()
                    .equals(getString(R.string.guideline_button_finish))) {
                // If button value is finish then go to home page
               goToHome();
            }
        }
    };
    
    /**
     * Go to home activity with animation
     */
    private void goToHome() {
        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(Constant.IS_FIRSTTIME, true);
        mEditor.commit();
        
        // Start activity with animation
        Intent intent = new Intent(GuidelineActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        
    }
    
    private class ViewpagerAdapter extends FragmentPagerAdapter {

        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return GuidelineHomeFragment.newInstance();
                case 1:
                    return GuidelineOrderFragment.newInstance();
                case 2:
                    return GuidelineCallFragment.newInstance();
                default:
                    return GuidelineHomeFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}
