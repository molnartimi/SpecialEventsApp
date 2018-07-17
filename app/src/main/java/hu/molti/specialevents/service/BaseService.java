package hu.molti.specialevents.service;

import android.os.AsyncTask;

import java.util.List;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.dao.IDao;
import hu.molti.specialevents.entities.IEntity;

public abstract class BaseService<Dao extends IDao<Entity>, Entity extends IEntity<Entity>> {
    protected DataModificationListener dataModificationListener;
    protected Dao db;
    protected List<Entity> dataList;

    public void setDataModificationListener(DataModificationListener listener) {
        dataModificationListener = listener;
    }

    public void loadData(final DataLoadedListener callbackListener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dataList = db.getAll();
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
        dataList.add(entity);
        dataModificationListener.changed();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.insert(entity);
                return null;
            }
        }.execute();
    }

    public void remove(final int pos) {
        final Entity deleted = dataList.remove(pos);
        dataModificationListener.changed();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.delete(deleted);
                return null;
            }
        }.execute();
    }

}
