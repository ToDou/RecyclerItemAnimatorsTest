package com.todou.recyclerviewitemanimatorsTest;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by loopeer on 2015/8/24.
 */
public class ScaleInItemAnimator extends BaseItemAnimator2 {

    private float DEFAULT_SCALE_INITIAL_END = 1f;
    private float DEFAULT_SCALE_INITIAL = 0.6f;

    private float mInitialScaleX = DEFAULT_SCALE_INITIAL;
    private float mInitialScaleY = DEFAULT_SCALE_INITIAL;

    private float mEndScaleX = DEFAULT_SCALE_INITIAL_END;
    private float mEndScaleY = DEFAULT_SCALE_INITIAL_END;

    private float mOriginalScaleX;
    private float mOriginalScaleY;

    public ScaleInItemAnimator(RecyclerView recyclerView) {
        super(recyclerView);

        setAddDuration(750);
        setRemoveDuration(750);
        setMoveDuration(750);
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                scaleX(mEndScaleX).scaleY(mEndScaleY).
                translationX(+mRecyclerView.getWidth()).setListener(new BaseItemAnimator.VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setScaleX(view, mEndScaleX);
                ViewCompat.setScaleY(view, mEndScaleY);
                ViewCompat.setTranslationX(view, +mRecyclerView.getWidth());
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                scaleX(mOriginalScaleX).scaleY(mOriginalScaleY).
                translationX(+mRecyclerView.getWidth()).setListener(new BaseItemAnimator.VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setScaleX(view, mEndScaleX);
                ViewCompat.setScaleY(view, mEndScaleY);
                ViewCompat.setTranslationX(view, +mRecyclerView.getWidth());
                dispatchMoveFinished(holder);
                mMoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mMoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        retrieveOriginalScale(holder);
        ViewCompat.setScaleX(holder.itemView, mInitialScaleX);
        ViewCompat.setScaleY(holder.itemView, mInitialScaleY);

        ViewCompat.setTranslationX(holder.itemView, +mRecyclerView.getWidth());
    }



    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).scaleX(mOriginalScaleX).scaleY(mOriginalScaleY).translationX(0)
                .setDuration(getAddDuration()).
                setListener(new BaseItemAnimator.VpaListenerAdapter() {
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationX(view, 0);
                        ViewCompat.setScaleX(view, mOriginalScaleX);
                        ViewCompat.setScaleY(view, mOriginalScaleY);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }

    public void setInitialScale(float scaleXY){
        setInitialScale(scaleXY, scaleXY);
    }

    public void setInitialScale(float scaleX, float scaleY){
        mInitialScaleX = scaleX;
        mInitialScaleY = scaleY;

        mEndScaleX = scaleX;
        mEndScaleY = scaleY;
    }

    public void setEndScale(float scaleXY){
        setEndScale(scaleXY, scaleXY);
    }

    public void setEndScale(float scaleX, float scaleY){
        mEndScaleX = scaleX;
        mEndScaleY = scaleY;
    }

    private void retrieveOriginalScale(RecyclerView.ViewHolder holder) {
        mOriginalScaleX = holder.itemView.getScaleX();
        mOriginalScaleY = holder.itemView.getScaleY();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, final RecyclerView.ViewHolder viewHolder1, int i, int i1, int i2, int i3) {
        final View view = viewHolder1.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                scaleX(mOriginalScaleX).scaleY(mOriginalScaleY).
                translationX(+mRecyclerView.getWidth()).setListener(new BaseItemAnimator.VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setScaleX(view, mEndScaleX);
                ViewCompat.setScaleY(view, mEndScaleY);
                ViewCompat.setTranslationX(view, +mRecyclerView.getWidth());
                dispatchMoveFinished(viewHolder1);
                mMoveAnimations.remove(viewHolder1);
                dispatchFinishedWhenDone();
            }
        }).start();
        mMoveAnimations.add(viewHolder1);
        return false;
    }


}
