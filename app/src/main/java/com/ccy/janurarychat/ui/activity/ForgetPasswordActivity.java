package com.ccy.janurarychat.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ccy.janurarychat.R;
import com.ccy.janurarychat.server.utils.AMUtils;
import com.ccy.janurarychat.server.utils.NToast;
import com.ccy.janurarychat.server.utils.downtime.DownTimer;
import com.ccy.janurarychat.server.utils.downtime.DownTimerListener;
import com.ccy.janurarychat.server.widget.ClearWriteEditText;
import com.ccy.janurarychat.server.widget.LoadDialog;
import com.ccy.janurarychat.ui.BaseActivity;

import java.io.DataOutputStream;

import io.rong.imkit.IExtensionClickListener;

/**
 * Created by chanchaoyue on 2018/1/5.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, DownTimerListener {

    private static final int CHECK_PHONE = 31;
    private static final int SEND_CODE = 32;
    private static final int CHANGE_PASSWORD = 33;
    private static final int VERIFY_CODE = 34;
    private static final int CHANGE_PASSWORD_BACK = 1002;
    private ClearWriteEditText mPhone, mCode, mPassword1, mPassword2;
    private Button mGetCode, mOK;
    private String phone, mCodeToken;
    private boolean available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        setHeadVisibility(View.GONE);
        setTitle(R.string.forget_pwd);
        initView();
    }

    private void initView() {
        mPhone = findViewById(R.id.forget_phone);
        mCode = findViewById(R.id.forget_code);
        mPassword1 = findViewById(R.id.forget_password);
        mPassword2 = findViewById(R.id.forget_password1);
        mGetCode = findViewById(R.id.forget_getcode);
        mOK = findViewById(R.id.forget_button);
        mGetCode.setOnClickListener(this);
        mOK.setOnClickListener(this);
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    if (AMUtils.isMobile(s.toString().trim())) {
                        phone = mPhone.getText().toString().trim();
                        request(CHECK_PHONE, true);
                        AMUtils.onInactive(mContext, mPhone);
                    } else {
                        Toast.makeText(mContext, R.string.Illegal_phone_number, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mGetCode.setClickable(false);
                    mGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    AMUtils.onInactive(mContext, mCode);
                    if (available) {
                        mOK.setClickable(true);
                        mOK.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_blue));
                    } else {
                        mOK.setClickable(false);
                        mOK.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_gray));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_getcode:
                if (TextUtils.isEmpty(mPhone.getText().toString().trim())) {
                    NToast.longToast(mContext, getString(R.string.phone_number_is_null));
                } else {
                    DownTimer downTimer = new DownTimer();
                    downTimer.setListener(this);
                    downTimer.startDown(60 * 1000);
                    request(SEND_CODE);
                }
                break;
            case R.id.forget_button:
                if (TextUtils.isEmpty(mPhone.getText().toString())) {
                    NToast.shortToast(mContext, getString(R.string.phone_number_is_null));
                    mPhone.setShakeAnimation();
                    return;
                }

                if (TextUtils.isEmpty(mCode.getText().toString())) {
                    NToast.shortToast(mContext, getString(R.string.code_is_null));
                    mCode.setShakeAnimation();
                    return;
                }

                if (TextUtils.isEmpty(mPassword1.getText().toString())) {
                    NToast.shortToast(mContext, getString(R.string.password_is_null));
                    mPassword1.setShakeAnimation();
                    return;
                }

                if (mPassword1.length() < 6 || mPassword1.length() > 16) {
                    NToast.shortToast(mContext, R.string.passwords_invalid);
                    return;
                }

                if (TextUtils.isEmpty(mPassword2.getText().toString())) {
                    NToast.shortToast(mContext, getString(R.string.confirm_password));
                    mPassword2.setShakeAnimation();
                    return;
                }

                if (!mPassword2.getText().toString().equals(mPassword1.getText().toString())) {
                    NToast.shortToast(mContext, getString(R.string.passwords_do_not_match));
                    return;
                }
                LoadDialog.show(mContext);
                request(VERIFY_CODE);
                break;
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mGetCode.setText("second:" + String.valueOf(millisUntilFinished / 1000));
        mGetCode.setClickable(false);
        mGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_gray));
    }

    @Override
    public void onFinish() {
        mGetCode.setText(R.string.get_code);
        mGetCode.setClickable(true);
        mGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_blue));
    }
}
