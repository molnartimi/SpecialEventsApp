package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.service.EventService;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>
        implements DataModificationListener {
    private EventService eventService;
    private int monthIdx;
    private EditEntityListener<EventEntity> mListener;

    @Override
    public void changed() {
        notifyDataSetChanged();
    }

    public EventListAdapter(int monthIdx, EditEntityListener<EventEntity> listener) {
        this.monthIdx = monthIdx;
        eventService = EventService.getService();
        eventService.setDataModificationListener(this, monthIdx);
        mListener = listener;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public RecyclerView personsRecyclerView;
        public ImageView icon;
        public ImageView editBtn, deleteBtn;

        EventViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.event_list_row_date);
            personsRecyclerView = view.findViewById(R.id.event_list_row_persons_recycler_view);
            icon = view.findViewById(R.id.event_list_row_icon);
            editBtn = view.findViewById(R.id.event_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.event_list_row_delete_btn);
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
        final EventEntity event = eventService.getInMonth(monthIdx, position);
        holder.date.setText(event.getDay() + ".");
        RecyclerViewHelper.initRecyclerView(holder.personsRecyclerView,
                new EventListRowPersonsAdapter(event.getPersonIds()));

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
                mListener.onDeleteBtnOnClicked(event);
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditBtnOnClicked(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventService.getCountInMonth(monthIdx);
    }
}
