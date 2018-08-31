package hu.molti.specialevents.lists;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonListAdapter extends BaseListAdapter<PersonEntity> {
    private OpenGiftsListener giftListener;
    private OpenPersonEventsListener personEventsListener;

    public interface OpenGiftsListener {
        void openGifts(String personId);
    }

    public interface OpenPersonEventsListener {
        void openPersonEvents(String personId);
    }

    public PersonListAdapter(EditEntityListener<PersonEntity> listener, OpenGiftsListener giftListener,
                             OpenPersonEventsListener personEventsListener) {
        super(listener);
        this.giftListener = giftListener;
        this.personEventsListener = personEventsListener;
        personService.setDataModificationListener(this, 0);
    }

    public class PersonViewHolder extends BaseListAdapter.ViewHolder {
        public TextView name;
        ImageButton giftBtn;

        PersonViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.person_list_row_name);
            editBtn = view.findViewById(R.id.person_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.person_list_row_delete_btn);
            giftBtn = view.findViewById(R.id.person_list_row_gift_btn);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setOnGiftBtnClick(final String personId) {
            this.giftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    giftListener.openGifts(personId);
                }
            });
        }

        public void setOnClick(final String id) {
            itemView.findViewById(R.id.person_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    personEventsListener.openPersonEvents(id);
                }
            });
        }
    }

    @Override
    protected ViewHolder getNewViewHolder(View itemView) {
        return new PersonViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.person_list_row;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.ViewHolder viewHolder, int position) {
        PersonEntity person = personService.get(position);
        PersonViewHolder personHolder = (PersonViewHolder) viewHolder;
        personHolder.setName(person.getName());
        personHolder.setOnEditBtnClick(person);
        personHolder.setOnDeleteBtnClick(person);
        personHolder.setOnGiftBtnClick(person.getId());
        personHolder.setOnClick(person.getId());
    }

    @Override
    public int getItemCount() {
        return personService.count();
    }

    @Override
    public void changed() {
        notifyDataSetChanged();
    }
}
