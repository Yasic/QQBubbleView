package com.yasic.qqlivebubble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Yasic on 2016/6/1.
 */
public class BubbleView extends RelativeLayout{
    private List<Drawable> drawableList = new ArrayList<>();

    private int parentWidth = -1, parentHeight = -1;
    private int viewWidth = 64, viewHeight = 64;

    private int maxHeartNum = 8;
    private int minHeartNum = 2;

    private int riseDuration = 4000;

    private int bottomPadding = 200;
    private int originMargin = 60;

    public BubbleView(Context context) {
        super(context);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDrawableList(List<Drawable> drawableList){
        this.drawableList = drawableList;
    }

    public void setRiseDuration(int riseDuration){
        this.riseDuration = riseDuration;
    }

    public void setBottomPadding(int dp){
        this.bottomPadding = dp2pix(dp);
    }

    public void setOriginMargin(int dp){
        this.originMargin = dp2pix(dp);
    }

    public void setGiftBoxImaeg(Drawable drawable, int positionX, int positionY){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawable);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageView.getWidth(), imageView.getHeight());
        this.addView(imageView, layoutParams);
        imageView.setX(positionX);
        imageView.setY(positionY);
    }

    public void startAnimation(final int rankWidth, final int rankHeight, int delay, int count){
        Observable.timer(delay, TimeUnit.MILLISECONDS)
                .repeat(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        bubbleAnimation(rankWidth, rankHeight);
                    }
                });
    }

    private void bubbleAnimation(int rankWidth, int rankHeight){
        this.parentWidth = rankWidth;
        this.parentHeight = rankHeight;
        parentHeight -= bottomPadding;
        int seed = (int)(Math.random() * 3);
        switch (seed){
            case 0:
                parentWidth -= originMargin;
                break;
            case 1:
                parentWidth += originMargin;
                break;
            case 2:
                parentHeight -= originMargin;
                break;
        }

        int heartDrawableIndex;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewWidth, viewHeight);
        heartDrawableIndex = (int)(drawableList.size() * Math.random());
        ImageView tempImageView = new ImageView(getContext());
        tempImageView.setImageDrawable(drawableList.get(heartDrawableIndex));
        addView(tempImageView, layoutParams);

        ObjectAnimator riseAlphaAnimator = ObjectAnimator.ofFloat(tempImageView, "alpha", 1.0f, 0.0f);
        riseAlphaAnimator.setDuration(riseDuration);
        ValueAnimator valueAnimator = getBesselAnimator(tempImageView, parentWidth, parentHeight);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(riseAlphaAnimator);
        animatorSet.start();
    }

    private ValueAnimator getBesselAnimator(final ImageView imageView, int parentWidth, int parentHeight){
        final PointF pointF0 = new PointF(parentWidth/2, parentHeight);
        PointF pointF1 = new PointF();
        pointF1.x = (float) ((double)(parentWidth) * (0.10)) + (float) (Math.random() * (float)(parentWidth) * (0.8));
        pointF1.y = (float) (parentHeight - Math.random() * parentHeight * (0.5));
        PointF pointF2 = new PointF();
        pointF2.x = (float) (Math.random() * parentWidth);
        pointF2.y = (float) (Math.random() * parentHeight * (0.5));
        PointF pointF3 = new PointF();
        pointF3.x = (float) (Math.random() * parentWidth);
        pointF3.y = 0;

        BesselEvaluator besselEvaluator = new BesselEvaluator(pointF1, pointF2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(besselEvaluator, pointF0, pointF3);
        valueAnimator.setDuration(riseDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF value = (PointF)animation.getAnimatedValue();
                imageView.setTranslationX(value.x);
                imageView.setTranslationY(value.y);
            }
        });
        return valueAnimator;
    }

    private int dp2pix(int dp){
        float scale = getResources().getDisplayMetrics().density;
        int pix = (int) (dp * scale + 0.5f);
        return pix;
    }

    private int pix2dp(int pix){
        float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (pix/scale + 0.5f);
        return dp;
    }
}
