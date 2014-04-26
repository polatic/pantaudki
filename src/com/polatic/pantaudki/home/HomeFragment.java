/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package com.polatic.pantaudki.home;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.GeneralNameValueModel;
import com.polatic.pantaudki.model.ProgramModel;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class HomeFragment extends Fragment {
    private Context mContext;
    private ProgressBar progressBar;
    private TextView messageSystemTextView;
    AsyncTask<Void, Void, Void> registerTask;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.home_fragment_parent, container, false);

        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

        // Set drawer menu title
        int i = getArguments().getInt(Constant.FRAGMENT_NUMBER);
        String menu_title = getResources().getStringArray(R.array.sidebar_menu_array)[i];
        getActivity().setTitle(menu_title);

        initView(rootView);
        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return null;
                case 0:
                    return new IncomeFragment();
                case 1:
                    return new ShoppingFragment();
                case 2:
                    return new OutcomeFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.home_label_menu_first).toUpperCase(l);
                case 1:
                    return getString(R.string.home_label_menu_second).toUpperCase(l);
                case 2:
                    return getString(R.string.home_label_menu_third).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * View initialization
     * 
     * @param rootView
     */
    private void initView(View rootView) {
        mContext = getActivity().getApplicationContext();
        progressBar = (ProgressBar)
                rootView.findViewById(R.id.home_progress_bar);
        messageSystemTextView = (TextView)
                rootView.findViewById(R.id.home_message_system);

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.home_view_pager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Listener on click list of product Item
     */
    private OnItemClickListener listViewListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };

    @Deprecated
    public static String encodeTobase64(Bitmap image) {
        Bitmap img = image;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }
}
