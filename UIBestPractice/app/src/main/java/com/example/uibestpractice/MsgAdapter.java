package com.example.uibestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/23.
 * 自定义适配器
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHoder> {


    private List<Msg> mMsgList;

    /**
     *泛型类型
     */
    public class ViewHoder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHoder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.right_layout);
            leftMsg = (TextView) itemView.findViewById(R.id.left_msg);
            rightMsg = (TextView) itemView.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<Msg> msgList)
    {
        mMsgList = msgList;
    }

    @Override
    public MsgAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.ViewHoder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED)
        {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftMsg.setText(msg.getContent());
        }
        else if (msg.getType() == Msg.TYPE_SENT)
        {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

}
