package com.ccy.janurarychat.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccy.janurarychat.R;
import com.ccy.janurarychat.server.network.async.OnDataListener;
import com.ccy.janurarychat.server.network.http.HttpException;
import com.ccy.janurarychat.server.utils.downtime.DownTimerListener;

/**
 * Created by chanchaoyue on 2018/1/7.
 */

public class DiscoverFragment extends Fragment implements View.OnClickListener, OnDataListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom_list, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {

    }
}
