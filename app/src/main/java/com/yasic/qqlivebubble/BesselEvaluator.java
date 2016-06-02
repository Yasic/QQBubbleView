package com.yasic.qqlivebubble;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by Yasic on 2016/6/2.
 */
public class BesselEvaluator implements TypeEvaluator<PointF> {
    private PointF point1, point2;

    public BesselEvaluator(PointF point1, PointF point2){
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF pointF = new PointF();
        pointF.x = startValue.x * (1 - fraction) * (1 - fraction) * (1 - fraction)
                + point1.x * 3 * fraction * (1 - fraction) * (1 - fraction)
                + point2.x * 3 * (1 - fraction) * fraction * fraction
                + endValue.x * fraction * fraction * fraction;
        pointF.y = startValue.y * (1 - fraction) * (1 - fraction) * (1 - fraction)
                + point1.y * 3 * fraction * (1 - fraction) * (1 - fraction)
                + point2.y * 3 * (1 - fraction) * fraction * fraction
                + endValue.y * fraction * fraction * fraction;
        return pointF;
    }
}
