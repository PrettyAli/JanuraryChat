package com.ccy.janurarychat.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccy.janurarychat.R;
import com.ccy.janurarychat.db.Friend;
import com.ccy.janurarychat.server.SelectableRoundedImageView;
import com.ccy.janurarychat.server.pinyin.CharacterParser;
import com.ccy.janurarychat.server.pinyin.SideBar;
import com.ccy.janurarychat.ui.adapter.FriendListAdapter;

import java.util.List;

/**
 * Created by chanchaoyue on 2018/1/6.
 */

public class ContactsFragment extends Fragment implements View.OnClickListener {

    private SelectableRoundedImageView mSelectableRoundedImageView;
    private TextView mNameTextView, mNoFriends, mUnreadTextView, mDialogTextView;
    private View mHeadView;
    private EditText mSearchEditText;
    private SideBar mSidBar;
    private ListView mListView;
    /**
     * 中部展示的字母提示
     */
    private List<Friend> mFriendList;
    private List<Friend> mFilteredFriendList;
    /**
     * 好友列表的 mFriendListAdapter
     */
    private FriendListAdapter mFriendListAdapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */

    private String mId;
    private String mCacheName;

    private static final int CLICK_CONTACT_FRAGMENT_FRIEND = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contact_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSearchEditText = view.findViewById(R.id.search);
        mListView = view.findViewById(R.id.listView);
        mNoFriends = view.findViewById(R.id.show_no_friend);
        mSidBar = view.findViewById(R.id.sidrbar);
        mDialogTextView = view.findViewById(R.id.group_dialog);
        mSidBar.setTextView(mDialogTextView);
        LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header,
                null);
        mUnreadTextView = (TextView) mHeadView.findViewById(R.id.tv_unread);
        RelativeLayout newFriendsLayout = (RelativeLayout) mHeadView.findViewById(R.id.re_newfriends);
        RelativeLayout groupLayout = (RelativeLayout) mHeadView.findViewById(R.id.re_chatroom);
        RelativeLayout publicServiceLayout = (RelativeLayout) mHeadView.findViewById(R.id.publicservice);
        RelativeLayout selfLayout = (RelativeLayout) mHeadView.findViewById(R.id.contact_me_item);
        mSelectableRoundedImageView = (SelectableRoundedImageView) mHeadView.findViewById(R.id.contact_me_img);
        mNameTextView = (TextView) mHeadView.findViewById(R.id.contact_me_name);
        mListView.addHeaderView(mHeadView);
        mNoFriends.setVisibility(View.VISIBLE);

        selfLayout.setOnClickListener(this);
        groupLayout.setOnClickListener(this);
        newFriendsLayout.setOnClickListener(this);
        publicServiceLayout.setOnClickListener(this);
        //设置右侧触摸监听
        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mFriendListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
