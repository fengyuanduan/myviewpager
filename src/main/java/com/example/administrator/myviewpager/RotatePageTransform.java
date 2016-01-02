package com.example.administrator.myviewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2015/12/28.
 */
public class RotatePageTransform implements ViewPager.PageTransformer {

    private static float ROTATE_MAX=30;
    private float mRotate;
    @Override
    public void transformPage(View page, float position) {

        //由于只能看到[-1,1]范围，所以只旋转[-1,1]，其范围之外旋转0度
        if(position<-1){
            page.setRotation(0);
        }else if(position<=1) {
            mRotate = ROTATE_MAX * position;
            page.setPivotX(page.getMeasuredWidth()*0.5f);
            page.setPivotY(page.getMeasuredHeight());
            page.setRotation(mRotate);

        }else{
            page.setRotation(0);
        }
    }
}
