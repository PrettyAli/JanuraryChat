package com.ccy.janurarychat.ui.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.janurarychat.R;
import com.ccy.janurarychat.server.broadcast.BroadcastManager;
import com.ccy.janurarychat.ui.BaseActivity;
import com.ccy.janurarychat.ui.fragment.ContactsFragment;
import com.ccy.janurarychat.ui.widget.DragPointView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.manager.IUnReadMessageObserver;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, DragPointView.OnDragListencer, IUnReadMessageObserver {

    public static ViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();
    private RelativeLayout chatRLayout, contactRLayout, foundRLayout, mineRLayout;
    private ImageView mImageChats, mImageContact, mImageMe, mImageFind;
    private TextView mTextChats, mTextContact, mTextFind, mTextMe;
    private ImageView mMineRed, moreImage, mSearchImageView;
    private boolean isDebug;
    private DragPointView mUnreadNumView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeadVisibility(View.GONE);
        isDebug = getSharedPreferences("config", MODE_PRIVATE).getBoolean("isDebug", false);
//        changeTextTabColor();
//        changeSelectedTabState(0);
        initMainViewPager();
        initViews();
    }

    private void initMainViewPager() {
        Fragment conversationList=initConversationList();
        mViewPager=findViewById(R.id.main_viewpager);
        mUnreadNumView=findViewById(R.id.seal_num);
        mUnreadNumView.setOnClickListener(this);
        mUnreadNumView.setDragListencer(this);
        mFragment.add(conversationList);
        mFragment.add(new ContactsFragment());

    }

    private Fragment initConversationList() {
        return null;
    }

    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextChats.setTextColor(Color.parseColor("#0099ff"));
                mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_chat_hover));
                break;
            case 1:
                mTextContact.setTextColor(Color.parseColor("#0099ff"));
                mImageContact.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_contacts_hover));
                break;
            case 2:
                mTextFind.setTextColor(Color.parseColor("#0099ff"));
                mImageFind.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_found_hover));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#0099ff"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_me_hover));
                break;
        }
    }

    private void changeTextTabColor() {
        mImageChats.setBackgroundResource(R.drawable.tab_chat);
        mImageContact.setBackgroundResource(R.drawable.tab_contacts);
        mImageFind.setBackgroundResource(R.drawable.tab_found);
        mImageMe.setBackgroundResource(R.drawable.tab_me);
        mTextChats.setTextColor(Color.parseColor("#abadbb"));
        mTextContact.setTextColor(Color.parseColor("#abadbb"));
        mTextFind.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
    }

    private void initViews() {
        chatRLayout = findViewById(R.id.seal_chat);
        contactRLayout = findViewById(R.id.seal_contact_list);
        foundRLayout = findViewById(R.id.seal_find);
        mineRLayout = findViewById(R.id.seal_me);
        mImageChats = findViewById(R.id.tab_img_chats);
        mImageContact = findViewById(R.id.tab_img_contact);
        mImageFind = findViewById(R.id.tab_img_find);
        mImageMe = findViewById(R.id.tab_img_me);
        mTextChats = findViewById(R.id.tab_text_chats);
        mTextContact = findViewById(R.id.tab_text_contact);
        mTextFind = findViewById(R.id.tab_text_find);
        mTextMe = findViewById(R.id.tab_text_me);
        mMineRed = findViewById(R.id.mine_red);
        moreImage = findViewById(R.id.seal_more);
        mSearchImageView = findViewById(R.id.ac_iv_search);

        chatRLayout.setOnClickListener(this);
        contactRLayout.setOnClickListener(this);
        foundRLayout.setOnClickListener(this);
        mineRLayout.setOnClickListener(this);
        moreImage.setOnClickListener(this);
        mSearchImageView.setOnClickListener(this);
//        BroadcastManager.getInstance(mContext).addAction(MineFragment.SHOW_RED, new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                mMineRed.setVisibility(View.VISIBLE);
//            }
//        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDragOut() {

    }

    @Override
    public void onCountChanged(int i) {

    }
}
