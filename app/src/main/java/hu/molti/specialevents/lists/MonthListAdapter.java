package hu.molti.specialevents.lists;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.EventEntity;

public class MonthListAdapter extends BaseListAdapter<EventEntity> {
    private int[] monthNameIds = {R.string.jan, R.string.febr, R.string.marc,
                                  R.string.apr, R.string.mai, R.string.jun,
                                  R.string.jul, R.string.aug, R.string.sept,
                                  R.string.oct, R.string.nov, R.string.dec};

    public MonthListAdapter(EditEntityListener<EventEntity> listener) {
        super(listener);
    }

    @Override
    protected void setDataModificationListeners() {}

    public class MonthViewHolder extends BaseListAdapter.ViewHolder {
        public TextView month;
        public RecyclerView eventsRecyclerView;

        MonthViewHolder(View view) {
            super(view);
            month = view.findViewById(R.id.month_list_row_name);
            eventsRecyclerView = view.findViewById(R.id.month_list_row_events_recycler_view);
        }

        public void setMonthName(String monthName) {
            this.month.setText(monthName);
        }

        public void initRecyclerView(EventListAdapter eventListAdapter) {
            RecyclerViewHelper.initRecyclerView(eventsRecyclerView, eventListAdapter);
        }
    }

    @Override
    protected MonthViewHolder getNewViewHolder(View itemView) {
        return new MonthViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.month_list_row;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.ViewHolder viewHolder, int position) {
        MonthViewHolder monthHolder = (MonthViewHolder) viewHolder;

        Resources resources = StartingActivity.getContext().getResources();
        String monthName = resources.getString(monthNameIds[position]);

        monthHolder.setMonthName(monthName);
        monthHolder.initRecyclerView(new EventListAdapter(position, editEntityListener));
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
