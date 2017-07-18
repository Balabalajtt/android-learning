package com.example.anlnsec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/18.
 */

public class PhoneBookAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    //private String[] mNames = {"jiangtingting","zengshuaizhi"};
    private List<UserInfo> mUserInfos= new ArrayList<>();

    public PhoneBookAdapter(Context context,List<UserInfo> userInfos)
    {
        mContext = context;
        mUserInfos = userInfos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mUserInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view == null) {
            view = mLayoutInflater.inflate(R.layout.item_phone_book_friend, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            viewHolder.nameTextView = (TextView)view.findViewById(R.id.name_text_view);
            viewHolder.AvatarImageView = (ImageView)view.findViewById(R.id.head_photo);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.nameTextView.setText(mUserInfos.get(i).getUserName());
        viewHolder.AvatarImageView.setImageResource(R.drawable.hema);
        return view;
    }

    public void refreshData(List<UserInfo> userInfos)
    {
        mUserInfos = userInfos;
        notifyDataSetChanged();
    }

    class ViewHolder
    {
        TextView nameTextView;
        ImageView AvatarImageView;
    }

}
