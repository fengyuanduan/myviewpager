package com.example.administrator.myviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private int[] mImg;
    private MyViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImg = new int[]{R.drawable.ic_leftmenu,R.drawable.mainpage_tab_discovery_selected,
                R.drawable.mainpage_tab_message_selected,R.drawable.mainpage_tab_mycircle_selected};
        mViewPager =(MyViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImg.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView((View)object);

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(mImg[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mViewPager.setObjectforPositon(imageView,position);
                return imageView;
            }
        });


    }



}
