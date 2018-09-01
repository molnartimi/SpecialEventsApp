package hu.molti.specialevents.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.listener.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entity.EventEntity;

public class PersonEventsListAdapter extends BaseListAdapter<EventEntity> {
    private String personId;
    private List<EventEntity> events;
    public static final String ID = "PEA";

    public PersonEventsListAdapter(String personId, EditEntityListener<EventEntity> listener) {
        super(listener);
        this.personId = personId;
        this.events = eventService.getAll(personId);
        eventService.setDataModificationListener(this, ID);
    }

    public class EventViewHolder extends BaseListAdapter.ViewHolder {
        private TextView date;
        private RecyclerView otherPersonsRecyclerView;
        private ImageView icon;

        public EventViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.person_events_list_row_date);
            otherPersonsRecyclerView = view.findViewById(R.id.person_events_list_row_persons_recycler_view);
            icon = view.findViewById(R.id.person_events_list_row_icon);
            editBtn = view.findViewById(R.id.person_events_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.person_events_list_row_delete_btn);
        }

        public void setDate(String date) {
            this.date.setText(date);
        }

        public void initRecyclerView(EventListRowPersonsAdapter eventListRowPersonsAdapter) {
            RecyclerViewHelper.initRecyclerView(otherPersonsRecyclerView, eventListRowPersonsAdapter);
        }

        public void setIcon(int iconId) {
            this.icon.setImageResource(iconId);
        }
    }

    @Override
    protected EventViewHolder getNewViewHolder(View itemView) {
        return new EventViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.person_events_list_row;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.ViewHolder viewHolder, int position) {
        EventEntity event = events.get(position);
        EventViewHolder eventHolder = (EventViewHolder) viewHolder;
        eventHolder.setDate(event.getDateString());

        List<String> otherPersonIds = new ArrayList<>();
        for (String personId: event.getPersonIds()) {
            if (!personId.equals(this.personId)) {
                otherPersonIds.add(personId);
            }
        }

        eventHolder.initRecyclerView(new EventListRowPersonsAdapter(otherPersonIds));
        eventHolder.setIcon(event.getType().getIconId());
        eventHolder.setOnEditBtnClick(event);
        eventHolder.setOnDeleteBtnClick(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public void changed() {
        this.events = eventService.getAll(personId);
        notifyDataSetChanged();
    }
}
