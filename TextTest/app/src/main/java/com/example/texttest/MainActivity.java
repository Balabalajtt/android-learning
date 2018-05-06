package com.example.texttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyTextView myTextView = findViewById(R.id.myTextView);
//        EditText textView = findViewById(R.id.mTextView);
//        AlignTextView editText = findViewById(R.id.mEditText);
        ReadingGlassView readingGlassView = new ReadingGlassView(this, MainActivity.this);
        String s = "作为观众，我多少有点无奈。陪伴了80、90后走过整个青春的奶茶，配上前任、北漂的元素，本身就是一个卖座他拼餐。非要加上不漂亮的商业运作，反而很倒胃口。作为观众，我多少有点无奈。陪伴了80、90后走过整个青春的奶茶，配上前任、北漂的元素，本身就是一个卖座他拼餐。非要加上不漂亮的商业运作，反而很倒胃口。作为观众，我多少有点无奈。陪伴了80、90后走过整个青春的奶茶，配上前任、北漂的元素，本身就是一个卖座他拼餐。非要加上不漂亮的商业运作，反而很倒胃口。作为观众，我多少有点无奈。陪伴了80、90后走过整个青春的奶茶，配上前任、北漂的元素，本身就是一个卖座他拼餐。非要加上不漂亮的商业运作，反而很倒胃口。作为观众，我多少有点无奈。陪伴了80、90后走过整个青春的奶茶，配上前任、北漂的元素，本身就是一个卖座他拼餐。非要加上不漂亮的商业运作，反而很倒胃口。";

        TextView textView = findViewById(R.id.mTextView);
        textView.setText(s);

        SelectableTextHelper mSelectableTextHelper = new SelectableTextHelper.Builder(textView, readingGlassView)
                .setSelectedColor(getResources().getColor(R.color.colorHighLight))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.colorAccent))
                .build();

//        myTextView.setText(s);
//        textView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            public void onDestroyActionMode(ActionMode mode) {
//            }
//
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                //This will prevent opening the menu
//                return false;
//            }
//
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                return false;
//            }
//        });
//        textView.setText(s);
//        editText.setText(s);

    }
}
