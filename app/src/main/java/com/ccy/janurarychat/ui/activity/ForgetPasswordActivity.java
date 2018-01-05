package com.ccy.janurarychat.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ccy.janurarychat.R;
import com.ccy.janurarychat.server.utils.downtime.DownTimerListener;
import com.ccy.janurarychat.ui.BaseActivity;

import java.io.DataOutputStream;

/**
 * Created by chanchaoyue on 2018/1/5.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, DownTimerListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }
}
