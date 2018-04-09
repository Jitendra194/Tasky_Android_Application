package com.example.android.taskreminder.ui.utilities;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by jiten on 1/29/2018.
 */

public abstract class SwipeToDelete extends ItemTouchHelper.SimpleCallback {


    public SwipeToDelete() {
        super(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        float itemHeight = itemView.getBottom() - itemView.getTop();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
