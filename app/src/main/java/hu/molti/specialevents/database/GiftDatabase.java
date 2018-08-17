package hu.molti.specialevents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.molti.specialevents.dao.GiftDao;
import hu.molti.specialevents.entities.GiftEntity;

@Database(entities = {GiftEntity.class}, version = 1)
public abstract class GiftDatabase extends RoomDatabase {
    public abstract GiftDao GiftDao();
}
