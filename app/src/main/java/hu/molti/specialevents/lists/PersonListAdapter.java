package hu.molti.specialevents.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>  {
    private final List<PersonEntity> persons;

    public PersonListAdapter(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.person_list_row_name);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_row, parent, false);

        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        String name = persons.get(position).getName();
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void addPerson(PersonEntity newPerson) {
        persons.add(newPerson);
        notifyDataSetChanged();
    }
}
