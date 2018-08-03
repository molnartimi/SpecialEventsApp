package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.common.RecyclerViewHelper;

public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.MonthViewHolder> {
    private int[] monthNameIds = {R.string.jan, R.string.febr, R.string.marc,
                                  R.string.apr, R.string.mai, R.string.jun,
                                  R.string.jul, R.string.aug, R.string.sept,
                                  R.string.oct, R.string.nov, R.string.dec};
    private EventListAdapter.EditEventBtnOnCliskListener mListener;

    public MonthListAdapter(EventListAdapter.EditEventBtnOnCliskListener listener) {
        this.mListener = listener;
    }

    public class MonthViewHolder extends RecyclerView.ViewHolder {
        public TextView month;
        public RecyclerView eventsRecyclerView;

        MonthViewHolder(View view) {
            super(view);
            month = view.findViewById(R.id.month_list_row_name);
            eventsRecyclerView = view.findViewById(R.id.month_list_row_events_recycler_view);
        }
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.month_list_row, parent, false);

        return new MonthViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.month.setText(StartingActivity.getContext().getResources().getString(monthNameIds[position]));
        RecyclerViewHelper.initRecyclerView(holder.eventsRecyclerView, new EventListAdapter(position, mListener));
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
