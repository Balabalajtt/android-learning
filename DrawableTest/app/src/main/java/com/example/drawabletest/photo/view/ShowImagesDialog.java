package com.example.drawabletest.photo.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.drawabletest.R;
import com.example.drawabletest.photo.Config;
import com.example.drawabletest.photo.adapter.ShowImagesAdapter;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/12.
 */

public class ShowImagesDialog extends Dialog {
    private View mView ;
    private Context mContext;
    private ShowImagesViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mImgUrls;
    private List<String> mTitles;
    private List<View> mViews;
    private ShowImagesAdapter mAdapter;

    private int position;

    public ShowImagesDialog(@NonNull Context context, List<String> imgUrls, int position) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        this.position = position;
        initView();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = Config.EXACT_SCREEN_HEIGHT;
        wl.width = Config.EXACT_SCREEN_WIDTH;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }


    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mViewPager = mView.findViewById(R.id.vp_images);
        mIndexText = mView.findViewById(R.id.tv_image_index);
        mTitles = new ArrayList<>();
        mViews = new ArrayList<>();
    }


    @SuppressLint("SetTextI18n")
    private void initData() {
        OnPhotoTapListener listener = new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                dismiss();
            }
        };

        for (int i = 0; i < mImgUrls.size(); i++) {
            final PhotoView photoView = new PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnPhotoTapListener(listener);
            Glide.with(mContext)
                    .load(mImgUrls.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            photoView.setImageDrawable(resource);
                        }
                    });
            mViews.add(photoView);
            mTitles.add(i + "");
        }

        mAdapter = new ShowImagesAdapter(mViews, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mIndexText.setText((position + 1) + "/" + mImgUrls.size());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mIndexText.setText(position + 1 + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
