package com.ccy.janurarychat.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ccy.janurarychat.App;
import com.ccy.janurarychat.R;
import com.ccy.janurarychat.SealUserInfoManager;
import com.ccy.janurarychat.db.Friend;
import com.ccy.janurarychat.model.SealSearchConversationResult;
import com.ccy.janurarychat.server.SelectableRoundedImageView;
import com.ccy.janurarychat.ui.BaseActivity;

import java.util.List;

import io.rong.imageloader.core.ImageLoader;

/**
 * Created by chanchaoyue on 2018/1/6.
 */

public class FriendListAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;
    private List<Friend> list;

    public FriendListAdapter(Context context, List<Friend> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<Friend> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;
        if (position >= list.size())
            return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Friend mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.friend_item, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.mImageView = (SelectableRoundedImageView) convertView.findViewById(R.id.frienduri);
            viewHolder.tvUserId = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int section = getPositionForSection(position);
        if (position == getSectionForPosition(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            String letterFirst = mContent.getLetters();
            if (!TextUtils.isEmpty(letterFirst)) {
                letterFirst = String.valueOf(letterFirst.toUpperCase().charAt(0));
            }
            viewHolder.tvLetter.setText(letterFirst);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        if (mContent.isExitsDisplayName()) {
            viewHolder.tvTitle.setText(this.list.get(position).getDisplayName());
        } else {
            viewHolder.tvTitle.setText(this.list.get(position).getName());
        }
        String portraitUri = SealUserInfoManager.getInstance().getPortraitUri(list.get(position));
        ImageLoader.getInstance().displayImage(portraitUri, viewHolder.mImageView, App.getOptions());
        if (context.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("isDebug", false)) {
            viewHolder.tvUserId.setVisibility(View.VISIBLE);
            viewHolder.tvUserId.setText(list.get(position).getUserId());
        }
        return null;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetters();
            char firstChar = sortStr.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getLetters().charAt(0);
    }

    final static class ViewHolder {
        /**
         * 首字母
         */
        TextView tvLetter;
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        SelectableRoundedImageView mImageView;
        /**
         * userid
         */
        TextView tvUserId;
    }
}
