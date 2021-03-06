
package com.polatic.pantaudki.contact;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ContactFragment extends Fragment implements OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView mDescription;
    private Button mBtnCall;
    private Button mBtnSms;
    private Button mBtnEmail;
    private String mJSONString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.contact_fragment, container, false);
        initView(rootView);

        // Set drawer menu title
        int i = getArguments().getInt(Constant.FRAGMENT_NUMBER);
        String menu_title = getResources().getStringArray(R.array.sidebar_menu_array)[i];
        getActivity().setTitle(menu_title);
        
        // Show back arrow in action bar - MUST
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(menu_title);
        
        mBtnCall.setOnClickListener(this);
        mBtnSms.setOnClickListener(this);
        mBtnEmail.setOnClickListener(this);

        return rootView;
    }

    private void initView(View rootView) {
        // TODO Auto-generated method stub
        mBtnCall = (Button) rootView.findViewById(R.id.btn_call);
        mBtnSms = (Button) rootView.findViewById(R.id.btn_sms);
        mBtnEmail = (Button) rootView.findViewById(R.id.btn_send_email);
        mDescription = (TextView) rootView.findViewById(R.id.contact_description);
        mDescription.setText(Html.fromHtml(getString(R.string.contact_description)));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mBtnCall) {
            String number = getString(R.string.contact_phone);
            String phone = getResources().getString(R.string.tel)
                    + number;
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phone));
            startActivity(callIntent);
            
        } else if (v == mBtnEmail) {
            String[] TO = {
                "dki@jakarta.go.id"
            };
            
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Judul Surat");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Kepada Pemerintah DKI Jakarta, \n\n");

            LogManager.print("Send email now");
            
            try {
                startActivity(Intent.createChooser(emailIntent, "Mengirimkan mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(),
                        "Tidak ada email client yang terinstall.", Toast.LENGTH_LONG).show();
            }
            
        } else {
            Bundle args = new Bundle();

            args.putString(getResources().getString(R.string.phone_number),
                    getString(R.string.contact_sms));

            CustomDialogSMS newFragment = CustomDialogSMS.newInstance();
            newFragment.setArguments(args);
            newFragment.show(getFragmentManager(), getResources().getString(R.string.dialog));
        }
    }

}
