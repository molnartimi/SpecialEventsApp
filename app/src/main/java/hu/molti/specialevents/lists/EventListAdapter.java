package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.entities.PersonEntity;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>  {
    private List<EventEntity> events;
    private EventIsDeletedListener mListener;

    public interface EventIsDeletedListener {
        public void deleteEvent(EventEntity event);
    }

    public EventListAdapter(List<EventEntity> events, EventIsDeletedListener listener) {
        this.events = events;
        mListener = listener;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView name;
        public ImageView icon;
        ImageButton deleteBtn;

        EventViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.event_list_row_date);
            name = view.findViewById(R.id.event_list_row_name);
            icon = view.findViewById(R.id.event_list_row_icon);
            deleteBtn = view.findViewById(R.id.delete_event_btn);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        EventEntity event = events.get(position);
        holder.date.setText(event.getDateString());
        holder.name.setText(event.getPersons().get(0).getName()); // TODO miafranclegyen a sok emberrel
        int iconId = 0;
        switch (event.getType()) {
            case BIRTHDAY:
                iconId = R.drawable.ic_cake_white_24dp;
                break;
            case NAMEDAY:
                iconId = R.drawable.ic_flower_white_24dp;
                break;
            case ANNIVERSARY:
                iconId = R.drawable.ic_heart_white_24dp;
        }
        holder.icon.setImageResource(iconId);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeEvent(events.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void addEvent(EventEntity newEvent) {
        events.add(newEvent);
        notifyDataSetChanged();
    }

    private void removeEvent(EventEntity event) {
        mListener.deleteEvent(event);
        events.remove(event);
        notifyDataSetChanged();
    }
}
