package com.utte.listviewandrecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通过setTag()解决
 */
public class ImageAdapter1 extends ArrayAdapter<String> {

    private ImageLruCache mCache = new ImageLruCache();
    private ListView mListView;

    public ImageAdapter1(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String url = getItem(position);

        if (mListView == null) {
            mListView = (ListView) parent;
        }

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item, null);
        } else {
            view = convertView;
        }

        ImageView image = view.findViewById(R.id.im);
        // 如果不设置这个占位图，就还是会变一下，因为在没有加载之前是用之前加载过的图的
        image.setImageResource(R.drawable.ic_launcher_background);
        // 给控件设置url的TAG
        image.setTag(url);
        // 看缓存有没有
        BitmapDrawable drawable = mCache.getCache(url);
        if (drawable != null) {
            image.setImageDrawable(drawable);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(url);
        }

        return view;

    }

    /**
     * 下载的AsyncTask
     */
    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        private String mImgUrl;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            mImgUrl = params[0];
            Bitmap bitmap = RequestUtil.downloadBitmap(mImgUrl);
            BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
            mCache.addCache(mImgUrl, drawable);
            return drawable;
        }

        // 执行完后调用 在UI Thread中执行
        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            // 根据TAG找到相应的ImageView
            ImageView imageView = mListView.findViewWithTag(mImgUrl);
            // 找到再设置 设置了新的TAG的老ImageView就不再会被设置图片了
            if (imageView != null && drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        }


    }


}
