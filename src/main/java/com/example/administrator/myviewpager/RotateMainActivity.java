package com.example.administrator.myviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class RotateMainActivity extends Activity {

    private int[] mImgIds= new int[]{R.drawable.mainpage_tab_mycircle_selected,
            R.drawable.mainpage_tab_discovery_selected,R.drawable.mainpage_tab_message_selected,
    R.drawable.ic_leftmenu,R.drawable.mainpage_tab_setting_selected};
    private ViewPager viewPager;
    private List<ImageView>mImageViews=new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rotate);
        initData();//≥ı ºªØImageView
        viewPager = (ViewPager) findViewById(R.id.id_rotate_pager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(mImageViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });

        viewPager.setPageTransformer(true,new RotatePageTransform());

    }

    private void initData() {

        for(int mImgId:mImgIds ) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(mImgId);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews.add(imageView);
        }

    }
}
