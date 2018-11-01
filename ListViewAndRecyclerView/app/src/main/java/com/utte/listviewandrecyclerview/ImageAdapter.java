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

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 有问题
 */
public class ImageAdapter extends ArrayAdapter<String> {

    private ImageLruCache mCache = new ImageLruCache();

    public ImageAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String url = getItem(position);

        // 看要不要新加载View
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item, null);
        } else {
            view = convertView;
        }

        // 请求数据
        ImageView image = view.findViewById(R.id.im);
        image.setImageResource(R.drawable.ic_launcher_background);
        // 看缓存有没有
        BitmapDrawable drawable = mCache.getCache(url);
        if (drawable != null) {
            image.setImageDrawable(drawable);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(image);
            task.execute(url);
        }

        return view;

    }

    /**
     * 下载的AsyncTask
     */
    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        ImageView mImageView;

        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        // 会启动一个工作线程执行这个方法
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            String url = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = RequestUtil.downloadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
            // 放缓存
            mCache.addCache(url, drawable);
            return drawable;
        }

        // 执行完后调用 在UI Thread中执行
        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            if (mImageView != null && drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        }
    }

}
