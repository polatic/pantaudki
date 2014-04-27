
package com.polatic.pantaudki.apbd;

import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.APBDModel;
import com.polatic.pantaudki.urusan.ChildUrusanFragment;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class APBDFragment extends Fragment {
    private Context mContext;
    private ProgressBar mProgress;
    private TextView messageSystemTextView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView titleActivity;
    private TextView skdpTextview;
    private TextView urusanTextview;
    private TextView programTextview;
    private TextView yearTextView;
    private TextView priceTextView;
    private TextView percentTextView;
    private ShareActionProvider mShareActionProvider;

    APBDModel mAPBDModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.apbd_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mAPBDModel = bundle.getParcelable(ChildUrusanFragment.PARCELABLE_APDB_KEY);
        }
        
        // set title for actionbar drawer
        getActivity().setTitle(mAPBDModel.getKegiatanName());

        // Show back arrow in action bar - MUST
        ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(mAPBDModel.getKegiatanName());
        

        mContext = getActivity().getApplicationContext();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate menu resource file.
        inflater.inflate(R.menu.share_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    /**
     * View initialization
     * 
     * @param rootView
     */
    private void initView(View rootView) {

        titleActivity = (TextView) rootView.findViewById(R.id.apbd_detail_activity);
        titleActivity.setText(mAPBDModel.getKegiatanName());

        programTextview = (TextView) rootView.findViewById(R.id.apbd_detail_program);
        programTextview.setText(mAPBDModel.getProgramName().toUpperCase());

        skdpTextview = (TextView) rootView.findViewById(R.id.apbd_detail_skdp);
        skdpTextview.setText(mAPBDModel.getSKPDName());

        LogManager.print(mAPBDModel.getKegiatanName() + ", " + mAPBDModel.getSKPDName()
                + ", " + mAPBDModel.getProgramName()
                + ", " + mAPBDModel.getUrusanName()
                + ", " + mAPBDModel.getPrice()
                );

        priceTextView = (TextView) rootView.findViewById(R.id.apbd_detail_price);
        priceTextView.setText("Rp." + mAPBDModel.getPrice());

        yearTextView = (TextView) rootView.findViewById(R.id.apbd_detail_year);
        yearTextView.setText("Tahun " + mAPBDModel.getYear());

        urusanTextview = (TextView) rootView.findViewById(R.id.apbd_detail_urusan);
        urusanTextview.setText(mAPBDModel.getUrusanName());

        mProgress = (ProgressBar) rootView.findViewById(R.id.urusan_progress_bar);
        messageSystemTextView = (TextView) rootView.findViewById(R.id.urusan_message_system);

        LinearLayout apdbPercent = (LinearLayout) rootView
                .findViewById(R.id.apbd_detail_percent_layout);
        if (!mAPBDModel.getPercentRealization().equals("-")) {
            apdbPercent.setVisibility(View.VISIBLE);
            percentTextView = (TextView) rootView
                    .findViewById(R.id.apbd_detail_percent_realization);
            percentTextView.setText(mAPBDModel.getPercentRealization());
        }

    }

}
