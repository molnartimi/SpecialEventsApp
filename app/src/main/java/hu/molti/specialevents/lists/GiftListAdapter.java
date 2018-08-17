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

public class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.GiftViewHolder>
        implements DataModificationListener {
    private GiftService giftService;
    private EditEntityListener<GiftEntity> mListener;

    public GiftListAdapter(EditEntityListener<GiftEntity> listener) {
        giftService = GiftService.getService();
        giftService.setDataModificationListener(this, 0);
        mListener = listener;
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder {
        public CheckBox doneCheckBox;
        public TextView name;
        public ImageView editBtn, deleteBtn;

        GiftViewHolder(View view) {
            super(view);
            doneCheckBox = view.findViewById(R.id.gift_list_row_checkbox);
            name = view.findViewById(R.id.gift_list_row_name);
            editBtn = view.findViewById(R.id.gift_list_row_edit_btn);
            deleteBtn = view.findViewById(R.id.gift_list_row_delete_btn);
        }
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gift_list_row, parent, false);

        return new GiftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final GiftEntity gift = giftService.get(position);
        holder.doneCheckBox.setChecked(gift.isDone());
        holder.doneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gift.click();
                giftService.update(gift);
            }
        });
        String name = gift.getName();
        holder.name.setText(name);
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditBtnOnClicked(gift);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeleteBtnOnClicked(gift);
            }
        });
    }

    @Override
    public int getItemCount() {
        return giftService.count();
    }

    @Override
    public void changed() {
        notifyDataSetChanged();
    }
}
