package com.example.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String mUserName;
    private String mRepo;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData(){
        ButterKnife.bind(this);
        mUserName = getResources().getString(R.string.user_name);
        mRepo = getResources().getString(R.string.repo);
    }

    @OnClick({R.id.btn_retrofit_simple_contributors,
            R.id.btn_retrofit_converter_contributors,
    })
    public void onClickButton(View v){
        switch (v.getId()){
            //简单演示retrofit的使用
            case R.id.btn_retrofit_simple_contributors:
                requestGitHubContributorsSimple();
                break;
            //添加转换器
            case R.id.btn_retrofit_converter_contributors:
                requestGitHubContributorsByConverter();
                break;
        }
    }

    /**
     * 简单示例
     */
    private void requestGitHubContributorsSimple(){

        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        //调用API请求接口
        GitHubApi repo = retrofit.create(GitHubApi.class);
        Call<ResponseBody> call = repo.contributorsBySimpleGetCall(mUserName, mRepo);
        call.enqueue(new Callback<ResponseBody>() {
            //获得json数据 用gson解析 获得login contributors
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    ArrayList<Contributor> contributorsList = gson.fromJson(response.body().string(), new TypeToken<List<Contributor>>() {
                    }.getType());
                    for (Contributor contributor : contributorsList) {
                        Log.d("login", contributor.getLogin());
                        Log.d("contributions", contributor.getContributions() + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 转换器
     */
    private void requestGitHubContributorsByConverter(){
        //我们通过addConverterFactory指定一个factory来对响应反序列化
        // 这里converters被添加的顺序将是它们被Retrofit尝试的顺序
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubApi repo = retrofit.create(GitHubApi.class);
        Call<List<Contributor>> call = repo.contributorsByAddConverterGetCall(mUserName, mRepo);
        call.enqueue(new Callback<List<Contributor>>() {

            @Override
            public void onResponse(Call<List<Contributor>> call, retrofit2.Response<List<Contributor>> response) {
                List<Contributor> contributorList = response.body();
                for (Contributor contributor : contributorList){
                    Log.d("login", contributor.getLogin());
                    Log.d("contributions", contributor.getContributions() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }



}
