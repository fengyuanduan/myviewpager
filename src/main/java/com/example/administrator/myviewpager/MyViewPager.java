package com.example.administrator.myviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2015/12/28.
 */
public class MyViewPager extends ViewPager {

    private static final String TAG="myViewPager";
    //最大缩小比例
    private static final float SCALE_MAX=0.5f;
    //滑动时左,右的View
    private View mLeft;
    private View mRight;

    //保存position与View的对应关系
    private HashMap<Integer,View>mChildrenView=new LinkedHashMap<Integer,View>();

    private float mTrans;
    private float mScale;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {

        float effectOffset=isSmall(offset)?0:offset;
        mLeft = findViewFromObject(position);
        mRight = findViewFromObject(position + 1);

        animateStack(mLeft,mRight,effectOffset,offsetPixels);
        super.onPageScrolled(position, offset, offsetPixels);


    }

    public void setObjectforPositon(View view ,int position) {
        mChildrenView.put(position, view);
    }
    public View findViewFromObject(int position) {
        return mChildrenView.get(position);
    }

    private boolean isSmall(float positionOffset){
        return Math.abs(positionOffset)<0.001;
    }

    protected void animateStack(View left,View right,float effectOffset,
                                int positionOffsetPixels){
        if(right!=null){
            mScale=(1-SCALE_MAX)*effectOffset+SCALE_MAX;
            mTrans = -getWidth() - getPageMargin() + positionOffsetPixels;

           right.setScaleX(mScale);
            right.setScaleY(mScale);
            right.setTranslationX(mTrans);
        }
        if(left!=null){
            left.bringToFront();
        }
    }

}
