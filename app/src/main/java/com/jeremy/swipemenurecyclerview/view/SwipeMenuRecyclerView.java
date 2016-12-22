package com.jeremy.swipemenurecyclerview.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jeremy.swipemenurecyclerview.SwipeMenuAdapter;

/**
 * Created by Jeremy on 2016/12/17.
 * 侧滑显示菜单RecyclerView
 */

public class SwipeMenuRecyclerView extends RecyclerView {

    private Rect mTouchFrame;

    public SwipeMenuRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMotionEventSplittingEnabled(false);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();

        MotionEventCompat.getActionMasked(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                SwipeMenuAdapter menuAdapter = (SwipeMenuAdapter) getAdapter();
                int itemOpenPosition = menuAdapter.getMenuOpenItemIndex();
                if (itemOpenPosition != -1) {
                    int pos = -1;
                    //通过点击的坐标计算当前的position
                    int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    Rect frame = mTouchFrame;
                    if (frame == null) {
                        mTouchFrame = new Rect();
                        frame = mTouchFrame;
                    }
                    final int count = getChildCount();
                    for (int i = count - 1; i >= 0; i--) {
                        final View child = getChildAt(i);
                        if (child.getVisibility() == View.VISIBLE) {
                            child.getHitRect(frame);
                            if (frame.contains(x, y)) {
                                pos = mFirstPosition + i;
                            }
                        }
                    }
                    if (pos != itemOpenPosition) {
                        menuAdapter.closeMenus();
                        return true;
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e);
    }
}
