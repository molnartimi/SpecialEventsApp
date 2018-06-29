package hu.molti.specialevents.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>  {
    private List<PersonEntity> persons;
    private PersonIsDeletedListener mListener;

    public interface PersonIsDeletedListener {
        public void deletePerson(PersonEntity person);
    }

    public PersonListAdapter(List<PersonEntity> persons, PersonIsDeletedListener listener) {
        this.persons = persons;
        mListener = listener;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button deleteBtn;

        public PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.person_list_row_name);
            deleteBtn = view.findViewById(R.id.delete_person_btn);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_row, parent, false);

        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {
        String name = persons.get(position).getName();
        holder.name.setText(name);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePerson(persons.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void addPerson(PersonEntity newPerson) {
        persons.add(newPerson);
        notifyDataSetChanged();
    }

    public void removePerson(PersonEntity person) {
        mListener.deletePerson(person);
        persons.remove(person);
        notifyDataSetChanged();
    }
}
