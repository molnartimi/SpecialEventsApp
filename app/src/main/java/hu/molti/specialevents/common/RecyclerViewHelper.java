package hu.molti.specialevents.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import hu.molti.specialevents.StartingActivity;

public class RecyclerViewHelper {

    public static void initRecyclerView(View recyclerView, RecyclerView.Adapter adapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StartingActivity.getContext());
        ((RecyclerView) recyclerView).setLayoutManager(mLayoutManager);
        ((RecyclerView) recyclerView).setAdapter(adapter);
    }
}
