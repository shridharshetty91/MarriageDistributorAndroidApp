package com.example.shridhar.distributorapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Shridhar on 11/5/16.
 */

public class AdminEmailHolder extends LinearLayout {

    private TextView mEmailIdTextView;
    private Button mDeleteButton;

    private String mEmailId;
    private OnClickListener mDeletetButtonClickListner;

    public void setDeleteButtonClickListner(OnClickListener listner) {
        mDeletetButtonClickListner = listner;
        if (mDeleteButton != null) {
            mDeleteButton.setOnClickListener(mDeletetButtonClickListner);
        }
    }

    public void setEmailId(String emailId) {
        mEmailId = emailId;
        if (mEmailIdTextView != null) {
            mEmailIdTextView.setText(mEmailId);
        }
    }

    public AdminEmailHolder(Context context) {
        super(context);
    }

    public AdminEmailHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdminEmailHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdminEmailHolder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initializeAdminEmailHolderView();
    }

    private void initializeAdminEmailHolderView() {
        mEmailIdTextView = (TextView) findViewById(R.id.email_textview);
        mEmailIdTextView.setText(mEmailId);
        mDeleteButton = (Button) findViewById(R.id.delete_email_id_button);
        mDeleteButton.setOnClickListener(mDeletetButtonClickListner);
    }
}
