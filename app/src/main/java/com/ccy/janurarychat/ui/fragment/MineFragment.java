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

/**
 * Created by chanchaoyue on 2018/1/7.
 */

public class MineFragment extends Fragment implements View.OnClickListener,OnDataListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.seal_mine_fragment, container, false);
        return mView;
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
