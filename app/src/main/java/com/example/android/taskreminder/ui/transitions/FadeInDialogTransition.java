package com.example.android.taskreminder.ui.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.android.taskreminder.ui.activities.TaskDialogActivity;

/**
 * Created by jiten on 3/12/2018.
 */

public class FadeInDialogTransition extends Transition {

    private static final String PROP_BOUNDS = "PROP_BOUNDS";
    private String[] TRANSITION_PROPERTIES = {
            PROP_BOUNDS
    };

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final Rect startBounds = (Rect) startValues.values.get(PROP_BOUNDS);
        final Rect endBounds = (Rect) endValues.values.get(PROP_BOUNDS);

        TimeInterpolator FOSI = AnimationUtils.loadInterpolator(
                sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in);

        boolean fromCard = endBounds.width() > startBounds.width();
        final View view = endValues.view;

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 0F, 1F);
        fadeIn.setDuration(200L);
        fadeIn.setInterpolator(FOSI);
        return fadeIn;
    }

    private void captureValues(TransitionValues transitionValues) {

        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;
        transitionValues.values.put(PROP_BOUNDS,
                new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
    }

    public static boolean setUp(TaskDialogActivity taskDialogActivity, CardView target) {
        final FadeInDialogTransition sharedEnter = new FadeInDialogTransition();
        sharedEnter.addTarget(target);
        taskDialogActivity.getWindow().setSharedElementEnterTransition(sharedEnter);
        return true;
    }
}
