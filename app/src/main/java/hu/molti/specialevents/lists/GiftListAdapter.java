package hu.molti.specialevents.lists;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import hu.molti.specialevents.R;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.entities.GiftEntity;
import hu.molti.specialevents.service.GiftService;

public class GiftListAdapter extends BaseListAdapter<GiftEntity> {
    private GiftService giftService;

    public GiftListAdapter(EditEntityListener<GiftEntity> listener) {
        super(listener);
        giftService = GiftService.getService();
        giftService.setDataModificationListener(this, 0);
    }

    public class GiftViewHolder extends BaseListAdapter.ViewHolder {
        public CheckBox doneCheckBox;
        public TextView name;

        GiftViewHolder(View view) {
            super(view);
            doneCheckBox = view.findViewById(R.id.gift_list_row_checkbox);
            name = view.findViewById(R.id.gift_list_row_name);
            editBtn = view.findViewById(R.id.gift_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.gift_list_row_delete_btn);
        }

        public void setCheckBox(boolean done) {
            this.doneCheckBox.setChecked(done);
        }

        public void setOnCheckBoxClick(final GiftEntity gift) {
            this.doneCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gift.click();
                    giftService.update(gift);
                }
            });
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }

    @Override
    protected GiftViewHolder getNewViewHolder(View itemView) {
        return new GiftViewHolder(itemView);
    }

    @Override
    protected int getListRowLayoutId() {
        return R.layout.gift_list_row;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.ViewHolder viewHolder, int position) {
        GiftEntity gift = giftService.get(position);
        GiftViewHolder giftHolder = (GiftViewHolder) viewHolder;

        giftHolder.setCheckBox(gift.isDone());
        giftHolder.setOnCheckBoxClick(gift);
        giftHolder.setName(gift.getName());
        giftHolder.setOnEditBtnClick(gift);
        giftHolder.setOnDeleteBtnClick(gift);
    }

    @Override
    public int getItemCount() {
        return giftService.count();
    }
}
