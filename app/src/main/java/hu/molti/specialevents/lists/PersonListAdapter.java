package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.service.PersonService;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>
        implements DataModificationListener {
    private PersonService service;

    public PersonListAdapter() {
        service = PersonService.getService();
        service.setDataModificationListener(this);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ImageButton deleteBtn;

        PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.person_list_row_name);
            deleteBtn = view.findViewById(R.id.delete_person_btn);
        }
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_row, parent, false);

        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = service.get(position).getName();
        holder.name.setText(name);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return service.count();
    }

    @Override
    public void inserted() {
        notifyDataSetChanged();
    }

    @Override
    public void deleted() {
        notifyDataSetChanged();
    }
}
