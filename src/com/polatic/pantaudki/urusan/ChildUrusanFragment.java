
package com.polatic.pantaudki.urusan;

import com.jafarkhq.views.EndlessListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.apbd.APBDFragment;
import com.polatic.pantaudki.model.APBDModel;
import com.polatic.pantaudki.model.UrusanModel;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChildUrusanFragment extends Fragment {
    private List<APBDModel> mAPDBList = new ArrayList<APBDModel>();
    private EndlessListView itemListView;

    private APBDAdapter mAPBDAdapter;
    private Context mContext;
    private ProgressBar mProgress;
    private TextView messageSystemTextView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private String jsonStr;
    private JSONObject mUrusanJSONObject;
    private String YEAR = "2014";
    private JSONArray mJsonArray;
    
    private String mParamName;
    private String mParamCode;
    
    private int mPage = 1;
    private int mTotal = 0;
    private boolean mHaveMoreDataToLoad = true;
    
    public static final String PARCELABLE_APDB_KEY = "com.polatic.pantaudki.APBD";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        mContext = getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.urusan_children_fragment, container, false);

        // Set drawer menu title
        int i = getArguments().getInt(Constant.FRAGMENT_NUMBER);
        String menu_title = getResources().getStringArray(R.array.sidebar_menu_array)[i];
        getActivity().setTitle(menu_title);

        extraBundle();
        initView(rootView);

        return rootView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().supportInvalidateOptionsMenu();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().supportInvalidateOptionsMenu();
        setHasOptionsMenu(true);
    }
    
    /**
     * Load bundle passed from home fragment
     */
    private void extraBundle() {
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }

        mParamName = bundle.getString(Constant.JSON_URUSAN_NAME);
        mParamCode = bundle.getString(Constant.JSON_URUSAN_CODE);
    }

    /**
     * View initialization
     * 
     * @param rootView
     */
    private void initView(View rootView) {
        itemListView = (EndlessListView) rootView.findViewById(R.id.urusan_children_listview);
        mProgress = (ProgressBar) rootView.findViewById(R.id.urusan_progress_bar);
        messageSystemTextView = (TextView) rootView.findViewById(R.id.urusan_message_system);

        mAPBDAdapter = new APBDAdapter(mContext, mAPDBList);
        itemListView.setAdapter(mAPBDAdapter);
        itemListView.setOnLoadMoreListener(loadMoreListener);
        
        // Set order adapter
        itemListView.setOnItemClickListener(listViewListener);
    }

    private void loadMoreData() {
        callAPI(mPage);
    }

    private EndlessListView.OnLoadMoreListener loadMoreListener = new EndlessListView.OnLoadMoreListener() {

        @Override
        public boolean onLoadMore() {
            if (true == mHaveMoreDataToLoad) {
                loadMoreData();
            } else {
                Toast.makeText(getActivity(), getString(R.string.listview_end),
                        Toast.LENGTH_SHORT).show();
            }

            return mHaveMoreDataToLoad;
        }
    };
    
    /**
     * Listener on click list of product Item
     */
    private OnItemClickListener listViewListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            goToDetail(mAPDBList, position);
        }
    };

    /**
     * Go to detail page / fragment
     * 
     * @param mAPDBList
     * @param position
     */
    public void goToDetail(List<APBDModel> mAPDBList, int position) {
        // Generate bundle of product details contains title, description,
        // images
        Bundle args = new Bundle();
        args.putParcelable(PARCELABLE_APDB_KEY, mAPDBList.get(position));
        
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Configuration configuration = getActivity().getResources().getConfiguration();

        // create a Fragment
        Fragment detailFragment = new APBDFragment();
        detailFragment.setArguments(args);

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popenter,
                R.anim.popexit);

        // Check if the device is landscape or portrait
        int ori = configuration.orientation;

        fragmentTransaction.replace(R.id.content_frame, detailFragment);
        if (ori == configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
    
    /**
     * call get urusan API
     */
    private void callAPI(int page) {
        final String url = Constant.SERVER_URL  + Constant.PARAM_API_KEY
        + Constant.PARAM_URUSAN_WITH_AND + mParamCode + "&year=" + YEAR
        + Constant.PARAM_PAGE + page + Constant.PARAM_PER_PAGE;
               
        AsyncHttpClient client = new AsyncHttpClient();

        LogManager.print("Calling API " + url);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() { 
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {   }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    mJsonArray = (JSONArray) response.get("result");
                    mTotal = (Integer) response.get("total");
                    
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
               String inputSKPD = obj.getString(Constant.JSON_SKPDNAMA);
               String inputYear = obj.getString(Constant.JSON_YEAR);
               String inputProgramName = obj.getString(Constant.JSON_PROGRAM_NAME);
               String inputProgramCode = obj.getString(Constant.JSON_PROGRAM_CODE);
               String inputKegiatanName = obj.getString(Constant.JSON_KEGIATAN_NAME);
               String inputKegiatanID = obj.getString(Constant.JSON_KEGIATAN_ID);
               String inputPrice = obj.getString(Constant.JSON_PRICE);
               String inputIsPhysic = obj.getString(Constant.JSON_IS_PYHSIC);
               String inputRealization = obj.getString(Constant.JSON_REALIZATION);
               String inputPercentRealization = obj.getString(Constant.JSON_PERCENT_REALIZATION);
               String inputID = obj.getString(Constant.JSON_ID);
               String inputUnit = obj.getString(Constant.JSON_UNIT);
               String inputUrusanName = obj.getString(Constant.JSON_URUSAN_NAME);
               String inputUrusanCode = obj.getString(Constant.JSON_URUSAN_CODE);

               APBDModel product = new APBDModel(inputID,
                       inputYear, 
                       inputUnit, 
                       inputSKPD, 
                       inputUrusanCode, 
                       inputUrusanName,
                       inputProgramCode, 
                       inputProgramName,
                       inputKegiatanID, 
                       inputKegiatanName, 
                       inputPrice, 
                       inputRealization, 
                       inputPercentRealization, inputIsPhysic);
               mAPDBList.add(product);
               
               mPage++;
               if(mTotal < mPage * Constant.LIMIT_PAGE) {
                   mHaveMoreDataToLoad = false;
               }
               
               
           }

           mAPBDAdapter.addList(mAPDBList);
           itemListView.loadMoreCompleat();
           mProgress.setVisibility(View.GONE);
           itemListView.setVisibility(View.VISIBLE);

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
