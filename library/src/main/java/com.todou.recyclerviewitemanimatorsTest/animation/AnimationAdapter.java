package com.todou.recyclerviewitemanimatorsTest.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class AnimationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "AnimationAdapter";

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;

    private boolean isFirstOnly = true;
    private RecyclerView mRecyclerView;
    private ArrayList<Integer> runningPosition;

    public AnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mAdapter = adapter;
        runningPosition = new ArrayList<>();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        mAdapter.onBindViewHolder(holder, position);
        if (!isFirstOnly || position > mLastPosition) {
            holder.itemView.setVisibility(View.GONE);
            runningPosition.add(new Integer(position));
            Log.e(TAG, position + "");
            for (Animator anim : getAnimators(holder.itemView)) {
                anim.setDuration(mDuration);
                anim.setStartDelay(mDuration / 5 * (runningPosition.size() - 1));
                anim.start();
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        holder.itemView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        holder.itemView.setVisibility(View.VISIBLE);
                        runningPosition.remove(new Integer(position));
                    }
                });
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }
    }

    private int calculateAnimationDelay(final int position) {
        int delay;
        delay = mDuration / 5;
        int lastVisiblePosition = 0;
        int firstVisiblePosition = 0;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)mRecyclerView.getLayoutManager();
            lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        }

        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - mLastPosition;

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {

            /*if (mListViewWrapper.getListView() instanceof GridView && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                int numColumns = ((GridView) mListViewWrapper.getListView()).getNumColumns();
                delay += mAnimationDelayMillis * (position % numColumns);
            }*/
        } else {
           //int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
            //delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
        }
        return delay;
    }

    @Override public int getItemCount() {
        return mAdapter.getItemCount();
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setStartPosition(int start) {
        mLastPosition = start;
    }

    protected abstract Animator[] getAnimators(View view);

    public void setFirstOnly(boolean firstOnly) {
        isFirstOnly = firstOnly;
    }

    @Override public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getWrappedAdapter() {
        return mAdapter;
    }
}
