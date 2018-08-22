package hu.molti.specialevents.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.common.EventType;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.EventEntity;

public class EventListAdapter extends BaseListAdapter<EventEntity> {
    private int monthIdx;

    public EventListAdapter(int monthIdx, EditEntityListener<EventEntity> listener) {
        super(listener);
        this.monthIdx = monthIdx;
    }

    @Override
    protected void setDataModificationListeners() {
        eventService.setDataModificationListener(this, monthIdx);
    }

    public class EventViewHolder extends BaseListAdapter.ViewHolder {
        private TextView date;
        private RecyclerView personsRecyclerView;
        private ImageView icon;

        EventViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.event_list_row_date);
            personsRecyclerView = view.findViewById(R.id.event_list_row_persons_recycler_view);
            icon = view.findViewById(R.id.event_list_row_icon);
            editBtn = view.findViewById(R.id.event_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.event_list_row_delete_btn);
        }

        public void setDate(String date) {
            this.date.setText(date);
        }

        public void initRecyclerView(EventListRowPersonsAdapter eventListRowPersonsAdapter) {
            RecyclerViewHelper.initRecyclerView(personsRecyclerView, eventListRowPersonsAdapter);
        }

        public void setIcon(int iconId) {
            this.icon.setImageResource(iconId);
        }
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.event_list_row;
    }

    @Override
    protected EventViewHolder getNewViewHolder(View itemView) {
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.ViewHolder viewHolder, int position) {
        final EventEntity event = eventService.getInMonth(monthIdx, position);
        EventViewHolder eventHolder = (EventViewHolder) viewHolder;
        eventHolder.setDate(event.getDay() + ".");
        eventHolder.initRecyclerView(new EventListRowPersonsAdapter(event.getPersonIds()));
        eventHolder.setIcon(getEventIconId(event.getType()));
        eventHolder.setOnEditBtnClick(event);
        eventHolder.setOnDeleteBtnClick(event);
    }

    @Override
    public int getItemCount() {
        return eventService.getCountInMonth(monthIdx);
    }

    private int getEventIconId(EventType eventType) {
        switch (eventType) {
            case BIRTHDAY:      return R.drawable.ic_cake_white_24dp;
            case NAMEDAY:       return R.drawable.ic_flower_white_24dp;
            case ANNIVERSARY:   return R.drawable.ic_heart_white_24dp;
            default:            return -1;
        }
    }
}
