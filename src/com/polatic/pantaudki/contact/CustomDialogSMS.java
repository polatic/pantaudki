package com.polatic.pantaudki.contact;

import com.polatic.pantaudki.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialogSMS extends DialogFragment implements OnClickListener {

    private Button mBtnSend;
    private Button mBtnCancel;
    private EditText mEditText;

    public static CustomDialogSMS newInstance() {
        CustomDialogSMS frag = new CustomDialogSMS();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_sms, null);

        mBtnSend = (Button) rootView.findViewById(R.id.btn_sms_send);
        mBtnCancel = (Button) rootView.findViewById(R.id.btn_sms_cancel);
        mEditText = (EditText) rootView.findViewById(R.id.txt_sms);

        mBtnSend.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSend) {
            Bundle mArgs = getArguments();
            String phoneNo = mArgs.getString("phone_number");
            String sms = mEditText.getText().toString();

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                Toast.makeText(getActivity(), "Pesan Terkirim", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Pesan Gagal", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            dismiss();
        } else if (v == mBtnCancel) {
            dismiss();
        }
    }

}
