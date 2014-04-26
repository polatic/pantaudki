
package com.polatic.pantaudki.urusan;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.UrusanModel;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UrusanFragment extends Fragment {
    private List<UrusanModel> mUrusanList = new ArrayList<UrusanModel>();
    private ListView itemListView;

    private UrusanAdapter mUrusanAdapter;
    private Context mContext;
    private ProgressBar mProgress;
    private TextView messageSystemTextView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private String jsonStr;
    private JSONObject mUrusanJSONObject;
    private String YEAR = "2014";
    private JSONArray mJsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        mContext = getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.urusan_fragment, container, false);

        // Set drawer menu title
        int i = getArguments().getInt(Constant.FRAGMENT_NUMBER);
        String menu_title = getResources().getStringArray(R.array.sidebar_menu_array)[i];
        getActivity().setTitle(menu_title);

        initView(rootView);
        loadData();

        return rootView;
    }

    /**
     * View initialization
     * 
     * @param rootView
     */
    private void initView(View rootView) {
        itemListView = (ListView) rootView.findViewById(R.id.urusan_listview);
        mProgress = (ProgressBar) rootView.findViewById(R.id.urusan_progress_bar);
        messageSystemTextView = (TextView) rootView.findViewById(R.id.urusan_message_system);

        mUrusanAdapter = new UrusanAdapter(mContext, mUrusanList);
        
        // Set order adapter
        itemListView.setAdapter(mUrusanAdapter);
        itemListView.setOnItemClickListener(listViewListener);
        
        mProgress.setVisibility(View.VISIBLE);
        
    }

    /**
     * Listener on click list of product Item
     */
    private OnItemClickListener listViewListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            goToDetail(mUrusanList, position);
        }
    };

    /**
     * Go to detail page / fragment
     * 
     * @param mUrusanList
     * @param position
     */
    public void goToDetail(List<UrusanModel> mUrusanList, int position) {
        // Generate bundle of product details contains title, description,
        // images
        
        LogManager.print("Get code " + mUrusanList.get(position).getCode());
        Bundle args = new Bundle();
        args.putString(Constant.JSON_URUSAN_CODE, mUrusanList.get(position).getCode());
        args.putString(Constant.JSON_URUSAN_NAME, mUrusanList.get(position).getName());

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Configuration configuration = getActivity().getResources().getConfiguration();

        // create a Fragment
        Fragment detailFragment = new ChildUrusanFragment();
        detailFragment.setArguments(args);

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popenter,
                R.anim.popexit);

        // Check if the device is landscape or portrait
        int ori = configuration.orientation;

        fragmentTransaction.replace(R.id.content_frame, detailFragment);
        if (ori == configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.addToBackStack(null);
        } else {
            fragmentTransaction.addToBackStack("tag");
        }

        fragmentTransaction.commit();
    }
    

    /**
     * Load data from cache or API if not found
     */
    private void loadData() {
        jsonStr = mSharedPreferences.getString(Constant.API_CACHE_URUSAN, null);

        if (jsonStr == null || jsonStr.equals("")) {
            callAPI();
        } else {
            try {
                mUrusanJSONObject = new JSONObject(jsonStr);
                parseData(mUrusanJSONObject);
                mProgress.setVisibility(View.GONE);
                itemListView.setVisibility(View.VISIBLE);
                
            } catch (JSONException e) {
                callAPI();
            }
        }
    }
    
    /**
     * call get urusan API
     */
    private void callAPI() {
        final String url = Constant.SERVER_URL + Constant.PARAM_URUSAN + YEAR  + Constant.PARAM_API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("Calling API " + url);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                mProgress.setVisibility(View.VISIBLE);
                itemListView.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    mJsonArray = (JSONArray) response.get("result");
                    // Load program API
                    parseData(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                    java.lang.String responseBody, java.lang.Throwable e) {
                mProgress.setVisibility(View.GONE);
                itemListView.setVisibility(View.VISIBLE);
            }
        });
    }
    
    /*
    * Parsing data from JSON cache or API
    * @param response
    */
   private void parseData(JSONObject response) {
       try {
           mJsonArray = (JSONArray) response.get("result");
           for (int i = 0; i < mJsonArray.length(); i++) {
               JSONObject obj = mJsonArray.getJSONObject(i);

               String code = obj.getString(Constant.JSON_URUSAN_CODE);
               String name = obj.getString(Constant.JSON_URUSAN_NAME);

               UrusanModel urusanModel = new UrusanModel(name, code);
               mUrusanList.add(urusanModel);
           }

           mUrusanAdapter.updateList(mUrusanList);
           mUrusanAdapter.notifyDataSetChanged();

           // Save json into cache
           mEditor = mSharedPreferences.edit();
           mEditor.putString(Constant.API_CACHE_URUSAN, response.toString());
           mEditor.commit();

       } catch (JSONException e) {
           e.printStackTrace();
       }
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

}
