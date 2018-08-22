package hu.molti.specialevents.common;

import android.view.View;

public class EditOrDeleteBtnOnClickListener<Entity> implements View.OnClickListener {
    private EditEntityListener<Entity> listener;
    private Entity entity;
    private boolean isDeleteBtn;

    public EditOrDeleteBtnOnClickListener(Entity entity, EditEntityListener<Entity> listener, boolean isDeleteBtn) {
        this.listener = listener;
        this.entity = entity;
        this.isDeleteBtn = isDeleteBtn;
    }

    @Override
    public void onClick(View view) {
        if (isDeleteBtn) {
            listener.onDeleteBtnOnClicked(entity);
        } else {
            listener.onEditBtnOnClicked(entity);
        }
    }
}
