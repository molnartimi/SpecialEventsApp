package hu.molti.specialevents.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.common.EditOrDeleteBtnOnClickListener;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.service.EventService;
import hu.molti.specialevents.service.PersonService;

abstract public class BaseListAdapter<Entity> extends RecyclerView.Adapter<BaseListAdapter.ViewHolder>
        implements DataModificationListener {

    protected PersonService personService;
    protected EventService eventService;
    protected EditEntityListener<Entity> editEntityListener;

    public BaseListAdapter() {
        personService = PersonService.getService();
        eventService = EventService.getService();
        setDataModificationListeners();
    }

    public BaseListAdapter(EditEntityListener<Entity> listener) {
        this();
        editEntityListener = listener;
    }

    protected abstract void setDataModificationListeners();

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOnEditBtnClick(final Entity entity) {
            editBtn.setOnClickListener(new EditOrDeleteBtnOnClickListener<Entity>(entity, editEntityListener, false));
        }

        public void setOnDeleteBtnClick(final Entity entity) {
            deleteBtn.setOnClickListener(new EditOrDeleteBtnOnClickListener<Entity>(entity, editEntityListener, true));
        }
    }

    /**
     * Call the constructor of ViewHolder class
     */
    protected abstract <V extends ViewHolder> V getNewViewHolder(View itemView);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(getListRowLayoutId(), parent, false);

        return getNewViewHolder(itemView);
    }

    protected abstract int getListRowLayoutId();

    @Override
    public void changed() {
        notifyDataSetChanged();
    }

    @Override
    public abstract int getItemCount();
}
