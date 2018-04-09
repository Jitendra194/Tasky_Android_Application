package com.example.android.taskreminder.ui.transitions;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by jiten on 3/6/2018.
 */

public class ExpandCard {

    public static AnimatorSet changeCardBounds(View view, ConstraintLayout layout) {
        TimeInterpolator FOSI = AnimationUtils.loadInterpolator(
                view.getContext(), android.R.interpolator.fast_out_slow_in);

        Rect rect1 = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        Rect rect2 = new Rect(layout.getLeft(), layout.getTop(), layout.getRight(), layout.getBottom());

        final int translationX = rect2.centerX() - rect1.centerX();
        final int translationY = rect2.centerY() - rect1.centerY();

        Path path = new Path();
        path.lineTo(translationX, translationY);

        ObjectAnimator translateX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, View.TRANSLATION_Y,
                path);
        translateX.setDuration(200L);
        ObjectAnimator translateZ = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, 6);


        translateX.setInterpolator(FOSI);

        float requiredX = getFloat(view.getWidth(), getWidthDps(view.getContext(), 350));
        float requiredY = getFloat(view.getHeight(), getWidthDps(view.getContext(), 300));

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1F, requiredX);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1F, requiredY);

        scaleX.setDuration(200L);
        scaleY.setDuration(600L);

        scaleX.setInterpolator(FOSI);
        scaleY.setInterpolator(FOSI);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, translateX);
        animatorSet.play(translateZ);
        animatorSet.start();
        return animatorSet;
    }

    private static int getWidthDps(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    private static float getFloat(int start, int end) {
        return ((end * 1F) / start);
    }
}
