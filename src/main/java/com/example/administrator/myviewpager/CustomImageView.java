package com.example.administrator.myviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/12/28.
 */
public class CustomImageView extends View {


    /**
     * 加载的图片
     */
    private Bitmap mImage;
    /**
     * 图片尺寸的显示
     */
    private int mImageScale;
    /**
     * 图片下方的标题
     */
    private String mTitle;
    /**
     * 标题颜色
     */
    private int mTextColor;
    /**
     * 标题字体大小
     */
    private  int  mTextSize;
    /**
     * 我测量得到的宽高
     */
    private int mWidth;
    private int mHeight;


    /**
     * 整个控件的区域
     */
    private Rect rect;

    /**
     * 字体的区域
     */
    private Rect mTextBound;

    /**
     * 我的画笔
     */
    private Paint mPaint;


    /**
     * 图片显示的比例类型适应图片大小
     */
    private final static int IMAGE_SCALE_FITXY=0;


    /**
     * 一定要重写三个构造函数
     * 最后带三个参数的构造函数需要调用父类带三个参数方法，并且在里面拿到各个自定义属性的值
     * @param context
     */
    public CustomImageView(Context context) {
        this(context, null);
    }
    public CustomImageView(Context context,AttributeSet attrs) {
        this(context, attrs, 0);
    }
    /**
     * 在里面拿到各个自定义属性的值
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomImageView, defStyleAttr, 0
        );

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColour:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                   mTextSize= a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;

            }
        }
        a.recycle();
        rect=new Rect();
        mTextBound=new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        //用画笔画出标题的内容�
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if(specMode==MeasureSpec.EXACTLY) {//精确模式
            Log.e("XXX", "EXACTLY");
            mWidth = specSize;
        }else {
            //图片需要的大小；无限模式
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            //标题需要的大小；无限模式
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (specMode == MeasureSpec.AT_MOST) {//至多模式
                int desire = Math.min(desireByImg, desireByTitle);
                mWidth = Math.min(desire, specSize);
                Log.e("XXX", "AT_MOST");

            }
        }
        /**
         * 获取控件高的信息
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//精确模式
            mHeight = specSize;

        }else {
            int desire = getPaddingBottom() + getPaddingTop() + mTextBound.height() + mImage.getHeight();//无限模式
            if (specMode == MeasureSpec.AT_MOST) {//至多模式
                mHeight = Math.min(desire, specSize);
            }
        }

        //最后将自己测量的值设置进测量的结果
        setMeasuredDimension(mWidth,mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         *画笔的设置
         * 并且画出边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //定位控件的上下左右
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight-getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        /**
         * 当标题显示的大小大于所测量的控件的大小时，显示XXX...
         */
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint,(float) mWidth - getPaddingRight() - getPaddingLeft()
            ,TextUtils.TruncateAt.END).toString();
            //画出标题
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        }else {
            //画出标题
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);

        }

        //减去用掉的标题区域，作为图片显示的区域
        rect.bottom -= mTextBound.height();

        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }else{
            //定位图片的上下左右�
            rect.left=mWidth/2-mImage.getWidth()/2;
            rect.right=mWidth/2+mImage.getWidth()/2;
            rect.top = mHeight / 2 - mTextBound.height() / 2 - mImage.getHeight() / 2;
            rect.bottom = mHeight / 2 - mTextBound.height() / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }


    }
}
