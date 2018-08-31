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

public class EventListRowPersonsAdapter extends BaseListAdapter {
    private List<String> personIds;

    public EventListRowPersonsAdapter(List<String> personIds) {
        super();
        this.personIds = personIds;
    }

    public class PersonViewHolder extends BaseListAdapter.ViewHolder {
        public TextView name;

        PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.event_list_persons_row_name);
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }

    @Override
    protected PersonViewHolder getNewViewHolder(View itemView) {
        return new PersonViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.event_list_persons_row;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        PersonEntity person = personService.get(personIds.get(position));
        PersonViewHolder personHolder = (PersonViewHolder) viewHolder;

        if (person != null) {
            personHolder.setName(person.getName());
        }
    }

    @Override
    public int getItemCount() {
        return personIds.size();
    }
}
