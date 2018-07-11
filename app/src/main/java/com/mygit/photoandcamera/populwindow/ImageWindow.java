package com.mygit.photoandcamera.populwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.bumptech.glide.Glide;
import com.mygit.photoandcamera.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/30.
 */

public class ImageWindow extends PopupWindow {

    private ViewPager mViewPager;
    private Activity mContext;

    public ImageWindow(Context context) {

        mContext = (Activity) context;

        View view = View.inflate(mContext,
                R.layout.view_image_popuwindow, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewFlipper1);

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(view);
    }

    public void showFile(View parent, List<File> shopFaceList, int position) {
        if (parent == null) {
            return;
        }
        List<View> list = new ArrayList<View>();

        for (int i = 0; i < shopFaceList.size(); i++) {
            ImageView img = new ImageView(mContext);
            img.setBackgroundColor(img.getResources().getColor(R.color.black));
//            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Bitmap bitmap = BitmapFactory.decodeFile(shopFaceList.get(i).getAbsolutePath());
            img.setImageBitmap(bitmap);
            list.add(img);
        }
        ViewPagerGuideAdapter adapter = new ViewPagerGuideAdapter(list);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void showStr(View parent, List<String> shopFaceList, int position) {
        if (parent == null) {
            return;
        }
        List<View> list = new ArrayList<View>();

        for (int i = 0; i < shopFaceList.size(); i++) {
            ImageView img = new ImageView(mContext);
            img.setBackgroundColor(img.getResources().getColor(R.color.black));
//            img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext)
                    .load(shopFaceList.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(img);
            list.add(img);
        }
        ViewPagerGuideAdapter adapter = new ViewPagerGuideAdapter(list);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    class ViewPagerGuideAdapter extends PagerAdapter {

        // 界面列表
        private List<View> views;

        public ViewPagerGuideAdapter(List<View> views) {
            this.views = views;
        }

        // 销毁position位置的界面
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        // 初始化position位置的界面
        @Override
        public Object instantiateItem(View view, int position) {

            ((ViewPager) view).addView(views.get(position), 0);

            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }

}
