package com.todou.recycleritemanimatorstest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.todou.recycleritemanimatorstest.ui.adapter.TestRecyclerAdapter;
import com.todou.recyclerviewitemanimatorsTest.animation.FadeInAnimator;
import com.todou.recyclerviewitemanimatorsTest.animation.ScaleInAnimationAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int index = 1;
    private RecyclerView mRecyclerView;
    private TestRecyclerAdapter mAdapter;
    ScaleInAnimationAdapter scaleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setUpRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setData(0);
            }
        }, 1000);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestRecyclerAdapter(this);

        //mRecyclerView.setItemAnimator(new FadeInAnimator());
        scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        //        scaleAdapter.setFirstOnly(false);
        //        scaleAdapter.setInterpolator(new OvershootInterpolator());
        mRecyclerView.setAdapter(scaleAdapter);
    }

    private void setData(int a) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            result.add("This is the item :  " + i);
        }

        for (int j = 0; j < a; j++) {
            result.add(j, "Insert the item: " + a);
        }

        mAdapter.updateData(result);
        scaleAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            setData(index++);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
