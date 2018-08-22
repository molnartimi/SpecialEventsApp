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

    public interface OpenGiftsListener {
        void openGifts(String personId);
    }

    public PersonListAdapter(EditEntityListener<PersonEntity> listener, OpenGiftsListener giftListener) {
        super(listener);
        this.giftListener = giftListener;
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
    }

    @Override
    protected void setDataModificationListeners() {
        personService.setDataModificationListener(this, 0);
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
        final PersonEntity person = personService.get(position);
        PersonViewHolder personHolder = (PersonViewHolder) viewHolder;
        personHolder.setName(person.getName());
        personHolder.setOnEditBtnClick(person);
        personHolder.setOnDeleteBtnClick(person);
        personHolder.setOnGiftBtnClick(person.getId());
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
