package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.service.EventService;
import hu.molti.specialevents.service.PersonService;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>
        implements DataModificationListener {
    private EventService eventService;
    private PersonService personService;

    @Override
    public void changed() {
        notifyDataSetChanged();
    }

    public EventListAdapter() {
        eventService = EventService.getService();
        personService = PersonService.getService();
        eventService.setDataModificationListener(this);
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
        EventEntity event = eventService.get(position);
        holder.date.setText(event.getDateString());
        holder.name.setText(personService.get(event.getPersonIds().get(0)).getName()); // TODO miafranclegyen a sok emberrel
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
                eventService.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventService.count();
    }
}
