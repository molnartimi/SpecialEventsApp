package hu.molti.specialevents.common;

public interface EditEntityListener<Entity> {
    void onEditBtnOnClicked(Entity entity);
    void onDeleteBtnOnClicked(Entity entity);
}
