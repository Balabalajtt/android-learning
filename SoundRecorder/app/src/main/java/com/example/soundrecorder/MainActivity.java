package com.example.soundrecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button button;
    private Button button2;
    private EditText editText;
    private TextView textView;

    private HashMap<String, String> mIatResults = new LinkedHashMap<>();

    private SpeechSynthesizer speechSynthesizer;
//    private SpeechRecognizer speechRecognizer;
    private RecognizerDialog recognizerDialog;

    private InitListener initListener = new InitListener() {
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Log.d(TAG, "onInit: " + "error init code" + i);
                MainActivity.this.finish();
            }
        }
    };

    private SynthesizerListener synthesizerListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

//    private RecognizerListener recognizerListener = new RecognizerListener() {
//        @Override
//        public void onVolumeChanged(int i, byte[] bytes) {
//
//        }
//
//        @Override
//        public void onBeginOfSpeech() {
//            Log.d(TAG, "onBeginOfSpeech: " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        }
//
//        @Override
//        public void onEndOfSpeech() {
//
//        }
//
//        @Override
//        public void onResult(RecognizerResult recognizerResult, boolean b) {
//            dealResult(recognizerResult);
//        }
//
//        @Override
//        public void onError(SpeechError speechError) {
//
//        }
//
//        @Override
//        public void onEvent(int i, int i1, int i2, Bundle bundle) {
//
//        }
//    };

private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        dealResult(recognizerResult);
    }

    @Override
    public void onError(SpeechError speechError) {

    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59f85a06");
        onCallPermission();
        initView();
        initSynthesizer();
//        initRecognizer();

    }

    private void initView() {
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        editText = findViewById(R.id.edit_text);
        editText.setText("曾帅智最喜欢江婷婷了");
        textView = findViewById(R.id.text_view);
    }

    private void initSynthesizer() {
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(this, initListener);
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaorong");
    }

    //    private void initRecognizer() {
//        speechRecognizer = SpeechRecognizer.createRecognizer(this, initListener);
//        speechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
//        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");
//    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            speechSynthesizer.startSpeaking(editText.getText().toString(), synthesizerListener);
        } else {
//            if (speechRecognizer.isListening()) {
//                speechRecognizer.stopListening();
//            } else {
//                speechRecognizer.startListening(recognizerListener);
//            }
            if (recognizerDialog == null) {
                // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
                recognizerDialog = new RecognizerDialog(this, initListener);
                //设置回调接口
                recognizerDialog.setListener(recognizerDialogListener);
            }
            //开始听写
            recognizerDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechSynthesizer.destroy();
//        speechRecognizer.destroy();
    }

    public void onCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果当前申请的权限没有授权
            if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                //请求权限
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }

    private void dealResult(RecognizerResult recognizerResult) {
        String result = recognizerResult.getResultString(); //为解析的

        String text = parseIatResult(result) ;//解析过后的

        String sn = null;
        // 读取json结果中的 sn字段
        try {
            JSONObject resultJson = new JSONObject(recognizerResult.getResultString()) ;
            sn = resultJson.optString("sn" );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text) ;

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults .get(key));
        }

        textView.setText(resultBuffer.toString());
    }

    public String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer() ;
        try {
            JSONTokener token = new JSONTokener(json) ;
            JSONObject joResult = new JSONObject(token) ;

            JSONArray words = joResult.getJSONArray("ws" );
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw" );
                JSONObject obj = items.getJSONObject(0 );
                ret.append(obj.getString("w" ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

}
