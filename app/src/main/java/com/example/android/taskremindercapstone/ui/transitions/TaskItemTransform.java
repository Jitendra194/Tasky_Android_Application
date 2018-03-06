package com.example.android.taskremindercapstone.ui.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.transition.ArcMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * Created by jiten on 2/28/2018.
 */

public class TaskItemTransform extends Transition {

    private static final String EXTRA_CARD_COLOR = "EXTRA_CARD_COLOR";
    private static final String EXTRA_CARD_TEXT_TITLE = "EXTRA_CARD_TEXT_TITLE";
    private static final String EXTRA_CARD_TEXT_DESCRIPTION = "EXTRA_CARD_TEXT_DESCRIPTION";

    private static final String TAG = TaskItemTransform.class.getSimpleName();

    private static final String PROP_BOUNDS = "PROP_BOUNDS";
    private static View view1;
    private static View view2;
    private String[] TRANSITION_PROPERTIES = {
            PROP_BOUNDS
    };

    private final int color;
    private final String title;
    private final String description;

    public static void addExtras(@NonNull Intent intent, @ColorInt int cardColor,
                                 String title, String description) {
        intent.putExtra(EXTRA_CARD_COLOR, cardColor);
        intent.putExtra(EXTRA_CARD_TEXT_TITLE, title);
        intent.putExtra(EXTRA_CARD_TEXT_DESCRIPTION, description);
    }


    public static boolean setUp(Activity activity, View target, View target2) {
        view1 = target;
        view2 = target2;
        final Intent intent = activity.getIntent();
        if (!intent.hasExtra(EXTRA_CARD_COLOR) || !intent.hasExtra(EXTRA_CARD_TEXT_TITLE) ||
                !intent.hasExtra(EXTRA_CARD_TEXT_DESCRIPTION)) {
            return false;
        }
        final int color = intent.getIntExtra(EXTRA_CARD_COLOR, Color.TRANSPARENT);
        final String title = intent.getStringExtra(EXTRA_CARD_TEXT_TITLE);
        final String description = intent.getStringExtra(EXTRA_CARD_TEXT_DESCRIPTION);
        final TaskItemTransform sharedEnter = new TaskItemTransform(color, title, description);
        sharedEnter.addTarget(target);
        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        return true;
    }

    public TaskItemTransform(int color, String title, String description) {
        this.color = color;
        this.title = title;
        this.description = description;
        setPathMotion(new ArcMotion());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {

        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;
        transitionValues.values.put(PROP_BOUNDS,
                new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final Rect startBounds = (Rect) startValues.values.get(PROP_BOUNDS);
        final Rect endBounds = (Rect) endValues.values.get(PROP_BOUNDS);

        TimeInterpolator FOSI = AnimationUtils.loadInterpolator(
                sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in);

        boolean fromCard = endBounds.width() > startBounds.width();
        final View view = endValues.view;

        view.measure(makeMeasureSpec(startBounds.width(), View.MeasureSpec.UNSPECIFIED),
                makeMeasureSpec(startBounds.height(), View.MeasureSpec.UNSPECIFIED));
        view.layout(startBounds.left, startBounds.top, startBounds.right, startBounds.bottom);

        final int translationX = endBounds.centerX() - startBounds.centerX();
        final int translationY = endBounds.centerY() - startBounds.centerY();

        float requiredX = getFloatX(view.getWidth(), endBounds.width());
        float requiredY = getFloatY(view.getHeight(), endBounds.height());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, fromCard ? requiredX : 1F);
        scaleX.setDuration(100L);
        setInterpolator(FOSI);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, fromCard ? requiredY : 1F);
        scaleX.setDuration(300L);
        setInterpolator(FOSI);

        final Animator translate = ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_X,
                View.TRANSLATION_Y,
                fromCard ? getPathMotion().getPath(0, 0, translationX, translationY) :
                        getPathMotion().getPath(-translationX, -translationY, 0, 0));
        translate.setDuration(300L);
        translate.setInterpolator(FOSI);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY, translate);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (fromCard) {
                    view.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return set;
    }

    private float getFloatX(int startWidth, int endWidth) {
        return ((endWidth * 1F) / startWidth);
    }

    private float getFloatY(int startHeight, int endHeight) {
        return ((endHeight * 1F) / startHeight);
    }
}
