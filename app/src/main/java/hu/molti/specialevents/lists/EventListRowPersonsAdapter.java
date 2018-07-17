package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.service.PersonService;

public class EventListRowPersonsAdapter extends RecyclerView.Adapter<EventListRowPersonsAdapter.PersonViewHolder> {
    private PersonService service;
    private List<String> personIds;

    public EventListRowPersonsAdapter(List<String> personIds) {
        service = PersonService.getService();
        this.personIds = personIds;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.event_list_persons_row_name);
        }
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_persons_row, parent, false);

        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        PersonEntity person = service.get(personIds.get(position));
        if (person != null) {
            holder.name.setText(person.getName());
        }

    }

    @Override
    public int getItemCount() {
        return personIds.size();
    }
}
