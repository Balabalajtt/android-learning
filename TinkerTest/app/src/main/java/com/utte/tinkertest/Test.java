package com.utte.tinkertest;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test {
    private static final String TAG = "Test";
    private static final String RSA_ALGORITHM = "RSA";
    public static final String CHARSET = "UTF-8";

    public static void main(String[] args) {
        System.out.println(getPubKey("AIUn+lCcIYzZqXvcL4wxyf4cZCt0uZuCWpfN1Q6C90299OSKdiQsaYE+m74+X+qZIE+Vp7\\/D6MaNrAGbzwtJjT2j8uGPIQvcjo36apGOBvol71lEhxvDD2dPEMAxN4RobPanMrzbjHoUJkN86TxRhDdi3QbWKBD2u66Xs8lr\\/O+l"));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=1539509678717&_=1539509678488").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String url = response.body().string();
                    System.out.println(url);
                    String publicKey = url.substring(30, url.length() - 2);
                    String data = "waxi1998JTT.";
                    System.out.println(publicKey);
//                    System.out.println(getPubKey(publicKey));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void aaa() {
        Log.d(TAG, "aaa: " + getPubKey("AIUn+lCcIYzZqXvcL4wxyf4cZCt0uZuCWpfN1Q6C90299OSKdiQsaYE+m74+X+qZIE+Vp7\\/D6MaNrAGbzwtJjT2j8uGPIQvcjo36apGOBvol71lEhxvDD2dPEMAxN4RobPanMrzbjHoUJkN86TxRhDdi3QbWKBD2u66Xs8lr\\/O+l"));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=1539509678717&_=1539509678488").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String url = response.body().string();
                    System.out.println(url);
                    String publicKey = url.substring(30, url.length() - 2);
                    String data = "waxi1998JTT.";
                    System.out.println(publicKey);
//                    System.out.println(getPubKey(publicKey));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static PublicKey getPubKey(String pubKeyStr) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKeyStr.getBytes(), Base64.DEFAULT));
            // RSA对称加密算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
