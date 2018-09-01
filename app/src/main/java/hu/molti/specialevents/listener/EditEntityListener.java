package hu.molti.specialevents.listener;

public interface EditEntityListener<Entity> {
    void onEditBtnOnClicked(Entity entity);
    void onDeleteBtnOnClicked(Entity entity);
}
