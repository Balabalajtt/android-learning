package com.utte.listviewandrecyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.lang.ref.WeakReference;

public class ImageAdapter2 extends ArrayAdapter<String> {

    private Bitmap mLoadingBitmap;
    private ImageLruCache mCache = new ImageLruCache();

    public ImageAdapter2(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mLoadingBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item, null);
        } else {
            view = convertView;
        }
        ImageView imageView = view.findViewById(R.id.im);

        BitmapDrawable drawable = mCache.getCache(url);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else if (cancelPotentialWork(url, imageView)) { // 调用看这个imageView绑定的任务下在的url和传入现在准备下载的url是否相同
            // 给task绑定imageView
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            // bitmap绑定task
            AsyncDrawable asyncDrawable = new AsyncDrawable(getContext().getResources(), mLoadingBitmap, task);
            // imageView绑定bitmap
            imageView.setImageDrawable(asyncDrawable);
            task.execute(url);
        }

        return view;

    }

    /**
     * bitmap有task的引用
     */
    class AsyncDrawable extends BitmapDrawable {

        private WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }

    }

    /**
     * 从imageView中取出与其绑定的bitmap
     */
    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * 判断取消逻辑
     */
    public boolean cancelPotentialWork(String url, ImageView imageView) {
        BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            String imageUrl = bitmapWorkerTask.imageUrl;
            // 如果为空或正在请求别的url，就取消这个任务，返回true让去请求新的
            if (imageUrl == null || !imageUrl.equals(url)) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }



    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        String imageUrl;

        private WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = RequestUtil.downloadBitmap(imageUrl);
            BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            mCache.addCache(imageUrl, drawable);
            ImageView imageView = getAttachedImageView();
            if (imageView != null && drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        }

        /**
         * 先获取task引用的imageView，再获取到imageView的task，看是否一致，不一致说明已经被重新赋值了，就不要更新了
         */
        private ImageView getAttachedImageView() {
            ImageView imageView = imageViewReference.get();
            BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask) {
                return imageView;
            }
            return null;
        }


    }

}