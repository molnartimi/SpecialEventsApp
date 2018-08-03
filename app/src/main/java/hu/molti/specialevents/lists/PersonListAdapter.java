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
import hu.molti.specialevents.common.EditBtnOnClickListener;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.service.PersonService;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>
        implements DataModificationListener {
    private PersonService service;
    private EditBtnOnClickListener<PersonEntity> mListener;

    public PersonListAdapter(EditBtnOnClickListener<PersonEntity> listener) {
        service = PersonService.getService();
        service.setDataModificationListener(this, 0);
        mListener = listener;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ImageView editBtn, deleteBtn;

        PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.person_list_row_name);
            editBtn = view.findViewById(R.id.person_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.person_list_row_delete_btn);
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
        final PersonEntity person = service.get(position);
        String name = person.getName();
        holder.name.setText(name);
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditBtnOnClicked(person);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.remove(person);
            }
        });
    }

    @Override
    public int getItemCount() {
        return service.count();
    }

    @Override
    public void changed() {
        notifyDataSetChanged();
    }
}
