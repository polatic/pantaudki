
package com.polatic.pantaudki.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.ProgramModel;
import com.polatic.pantaudki.model.SKPDModel;
import com.polatic.pantaudki.model.UrusanModel;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity implements OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private JSONArray mJsonArray;
    private IntentFilter mNetworkStateChangedFilter;
    private BroadcastReceiver mNetworkStateIntentReceiver;
    private LinearLayout internetLayout;
    private boolean mAvailable = false;
    private ProgressBar mProgress;
    private Button mBtnReconnect;
    private TextView mSplashTextview;

    private List<SKPDModel> listSKPD;
    private List<UrusanModel> listUrusan;
    private List<ProgramModel> listProgram;

    private String YEAR = "2014";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_activity);
        internetLayout = (LinearLayout) findViewById(R.id.splash_layout_internet);
        mProgress = (ProgressBar) findViewById(R.id.splash_progressbar);
        mBtnReconnect = (Button) findViewById(R.id.splash_button_reconnect);
        mSplashTextview = (TextView) findViewById(R.id.splash_info_text);
        mSplashTextview.setText(Html.fromHtml(getString(R.string.splash_text)));

        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        mNetworkStateChangedFilter = new IntentFilter();
        mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        setupBroadcastReceiver();
        loadData();
        mBtnReconnect.setOnClickListener(this);
    }

    /**
     * Showing splashscreen while making network calls to download necessary
     * data before callAPIing the app Will use AsyncTask to make http call
     */
    private void loadData() {
        // TODO Auto-generated method stub
        String mCacheProgram = mSharedPreferences.getString(Constant.API_CACHE_PROGRAM, "");
        String mCacheUrusan = mSharedPreferences.getString(Constant.API_CACHE_URUSAN, "");
        String mCacheSKPD = mSharedPreferences.getString(Constant.API_CACHE_SKPD, "");

        if (mCacheProgram == null || mCacheProgram.equals("") ||
                mCacheUrusan == null || mCacheUrusan.equals("") ||
                mCacheSKPD == null || mCacheSKPD.equals("")) {
            callUrusanAPI();
        } else {
            mProgress.setVisibility(View.VISIBLE);
            mBtnReconnect.setVisibility(View.GONE);

            goToHome();
        }
    }

    /**
     * Go to home activity with animation
     */
    private void goToHome() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
        
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_right);

    }

    /**
     * call get urusan API
     */
    private void callUrusanAPI() {
        final String url = Constant.SERVER_URL + Constant.PARAM_URUSAN + YEAR
                + Constant.PARAM_API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("Calling API " + url);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                mProgress.setVisibility(View.VISIBLE);
                mBtnReconnect.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    mJsonArray = (JSONArray) response.get("result");

                    // Save into cache
                    mEditor = mSharedPreferences.edit();
                    mEditor.putString(Constant.API_CACHE_URUSAN, response.toString());
                    mEditor.commit();

                    // Load program API
                    callProgramAPI();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                mProgress.setVisibility(View.GONE);
                mBtnReconnect.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * call get program API
     */
    private void callProgramAPI() {
        final String url = Constant.SERVER_URL + Constant.PARAM_PROGRAM + YEAR
                + Constant.PARAM_API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("Calling API : " + url);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                mProgress.setVisibility(View.VISIBLE);
                mBtnReconnect.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    mJsonArray = (JSONArray) response.get("result");

                    // Save into cache
                    mEditor = mSharedPreferences.edit();
                    mEditor.putString(Constant.API_CACHE_PROGRAM, response.toString());
                    mEditor.commit();

                    callSKPDApi();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                mProgress.setVisibility(View.GONE);
                mBtnReconnect.setVisibility(View.VISIBLE);
            }

        });
    }

    /**
     * call get program API
     */
    private void callSKPDApi() {
        final String url = Constant.SERVER_URL + Constant.PARAM_SKPD + YEAR
                + Constant.PARAM_API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("Calling API : " + url);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                mProgress.setVisibility(View.VISIBLE);
                mBtnReconnect.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    mJsonArray = (JSONArray) response.get("result");

                    // Save into cache
                    mEditor = mSharedPreferences.edit();
                    mEditor.putString(Constant.API_CACHE_SKPD, response.toString());
                    mEditor.commit();
                    
                    goToHome();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                mProgress.setVisibility(View.GONE);
                mBtnReconnect.setVisibility(View.VISIBLE);
            }

        });
    }

    /**
     * Set broadcast receiver for network
     */
    private void setupBroadcastReceiver() {
        mNetworkStateIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo info = intent
                            .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    // ConnectivityManager connManager = (ConnectivityManager)
                    // context
                    // .getSystemService(Context.CONNECTIVITY_SERVICE);
                    // NetworkInfo info = connManager.getActiveNetworkInfo();
                    // if (info != null && info.isConnected()) {
                    // mAvailable = true;
                    // }
                    mAvailable = info.isAvailable();
                    updateScreen(false);

                } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    updateScreen(true);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
        unregisterReceiver(mNetworkStateIntentReceiver);
        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStateIntentReceiver);
    }

    /**
     * Show internet connection status on bottom fragment
     */
    private void updateScreen(Boolean isConnecting) {
        if (!mAvailable) {
            internetLayout.setVisibility(View.VISIBLE);

        } else {
            internetLayout.setVisibility(View.GONE);
            mBtnReconnect.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
            loadData();
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mBtnReconnect) {
            mBtnReconnect.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
            loadData();
        }
    }
}
