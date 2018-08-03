package hu.molti.specialevents.service;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.dao.IDao;
import hu.molti.specialevents.entities.IEntity;

public abstract class BaseService<Dao extends IDao<Entity>, Entity extends IEntity<Entity>> {
    protected HashMap<Integer, DataModificationListener> dataModificationListeners = new HashMap<>();
    protected Dao db;
    protected List<Entity> dataList;

    public void setDataModificationListener(DataModificationListener listener, int id) {
        dataModificationListeners.put(id, listener);
    }

    public void loadData(final DataLoadedListener callbackListener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dataList = db.getAll();
                afterDataLoaded();
                callbackListener.dataIsLoaded();
                return null;
            }
        }.execute();
    }

    public List<Entity> getAll() {
        return dataList;
    }

    public Entity get(int pos) {
        return dataList.get(pos);
    }

    public Entity get(String id) {
        for (Entity entity: dataList) {
            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    public int count() {
        return dataList.size();
    }

    public void add(final Entity entity) {
        entityAdded(entity);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.insert(entity);
                return null;
            }
        }.execute();
    }

    public void remove(final Entity entity) {
        entityRemoved(entity);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.delete(entity);
                return null;
            }
        }.execute();
    }

    public void update(final Entity entity) {
        entityUpdated(entity);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.update(entity);
                return null;
            }
        }.execute();
    }

    protected void emitListener(int id) {
        dataModificationListeners.get(id).changed();
    }

    protected void emitAllListeners() {
        for (DataModificationListener listener: dataModificationListeners.values()) {
            listener.changed();
        }
    }

    protected void afterDataLoaded() {}

    protected void entityAdded(Entity entity) {
        dataList.add(entity);
        emitListener(0);
    }

    protected void entityRemoved(Entity entity) {
        dataList.remove(entity);
        emitListener(0);
    }

    protected void entityUpdated(Entity entity) {
        emitListener(0);
    }

}
