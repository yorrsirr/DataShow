package com.xq.datashow;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class GradeArcView extends View {

    //根据数据显示的圆弧Paint
    private Paint mArcPaint;
    //文字描述的paint
    private Paint mTextPaint;
    //圆弧开始的角度
    private float startAngle=135;
    //圆弧结束的角度
    private float endAngle=45;
    //圆弧背景的开始和结束间的夹角大小
    private float mAngle=270;
    //当前进度夹角大小
    private float mIncludedAngle=0;
    //圆弧的画笔的宽度
    private float mStrokeWith=10;
    //中心的文字描述
    private String mDes="";
    //动画效果的数据及最大/小值
    private float mAnimatorValue = (float)0.0,mMinValue,mMaxValue;
    //中心点的XY坐标
    private float centerX,centerY;

    public GradeArcView(Context context) {
        this(context,null);
    }

    public GradeArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GradeArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init();
    }

    private void initPaint() {
        //圆弧的paint
        mArcPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(Color.parseColor("#85FFDD"));
        //设置透明度（数值为0-255）
        mArcPaint.setAlpha(100);
        //设置画笔的画出的形状
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔类型
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(dp2px(mStrokeWith));

        //中心文字的paint
        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        //设置文本的对齐方式
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp_12));
        mTextPaint.setTextSize(dp2px(25));

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX=getWidth()/2;
        centerY=getHeight()/2;
        //初始化paint
        initPaint();
        //绘制弧度
        drawArc(canvas);
        //绘制文本
        drawText(canvas);
    }

    /**
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Rect mRect=new Rect();
        String mValue=String.valueOf(mAnimatorValue);
        //绘制中心的数值
        mTextPaint.getTextBounds(mValue,0,mValue.length(),mRect);
        canvas.drawText(String.valueOf(mAnimatorValue),centerX,centerY+mRect.height()/2,mTextPaint);

        //绘制中心文字描述
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setTextSize(dp2px(12));
        mTextPaint.getTextBounds(mDes,0,mDes.length(),mRect);
        canvas.drawText(mDes,centerX,centerY+2*mRect.height()+dp2px(30),mTextPaint);
    }

    /**
     * 绘制当前的圆弧
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        //绘制圆弧背景
        RectF mRectF=new RectF(mStrokeWith+dp2px(5),mStrokeWith+dp2px(5),getWidth()-mStrokeWith-dp2px(5),getHeight()-mStrokeWith);
        canvas.drawArc(mRectF,startAngle,mAngle,false,mArcPaint);
        //绘制当前数值对应的圆弧
        mArcPaint.setColor(Color.parseColor("#85FFDD"));
        //根据当前数据绘制对应的圆弧
        canvas.drawArc(mRectF,startAngle,mIncludedAngle,false,mArcPaint);
    }

    /**
     * 为绘制弧度及数据设置动画
     *
     * @param startAngle 开始的弧度
     * @param currentAngle 需要绘制的弧度
     * @param currentValue 需要绘制的数据
     * @param time 动画执行的时长
     */
    private void setAnimation(float startAngle, float currentAngle,float currentValue, int time) {
        //绘制当前数据对应的圆弧的动画效果
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(startAngle, currentAngle);
        progressAnimator.setDuration(time);
        progressAnimator.setTarget(mIncludedAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIncludedAngle = (float) animation.getAnimatedValue();
                //重新绘制，不然不会出现效果
                postInvalidate();
            }
        });
        //开始执行动画
        progressAnimator.start();

        //中心数据的动画效果
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mAnimatorValue, currentValue);
        valueAnimator.setDuration(25);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置数据
     * @param minValue 最小值
     * @param maxValue 最大值
//     * @param currentValue 当前绘制的值
     * @param des 描述信息
     */
    public void setValues(int minValue,int maxValue, String currentValueString,String des) {
        float currentValue = Float.valueOf(currentValueString);
        mDes=des;
        mMaxValue=maxValue;
        mMinValue=minValue;
        //完全覆盖
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        //计算弧度比重
        float scale = (float)0.0;
        scale = (float) currentValue / maxValue;
        //计算弧度
        float currentAngle = (float)0.0;
        currentAngle = scale * mAngle;
        //开始执行动画
        setAnimation((float)0.0, currentAngle, currentValue,2500);
    }

    public float dp2px(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }

}
