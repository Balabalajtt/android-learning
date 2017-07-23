package com.example.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> mMsgList = new ArrayList<>();
    private EditText mEditText;
    private Button mButton;
    private MsgAdapter mMsgAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsgs();

        mEditText = (EditText) findViewById(R.id.input_text);
        mButton = (Button) findViewById(R.id.button);
        mRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mMsgAdapter = new MsgAdapter(mMsgList);
        mRecyclerView.setAdapter(mMsgAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEditText.getText().toString();
                if(!content.equals(""))
                {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    mMsgList.add(msg);

                    mMsgAdapter.notifyItemInserted(mMsgList.size()-1);
                    mRecyclerView.scrollToPosition(mMsgList.size()-1);
                    mEditText.setText("");
                }
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void initMsgs()
    {
        Msg msg1 = new Msg("唉，ListView和RecyclerView真让人头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！头疼！", Msg.TYPE_RECEIVED);
        Msg msg2 = new Msg("唉，是啊，好烦，好乱！", Msg.TYPE_SENT);
        Msg msg3 = new Msg("我能怎么办，我也很无奈啊，可能多敲敲能好一点吧！", Msg.TYPE_RECEIVED);
        mMsgList.add(msg1);
        mMsgList.add(msg2);
        mMsgList.add(msg3);
    }
}
