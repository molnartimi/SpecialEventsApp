package hu.molti.specialevents.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.molti.specialevents.db.dao.GiftDao;
import hu.molti.specialevents.entity.GiftEntity;

@Database(entities = {GiftEntity.class}, version = 1)
public abstract class GiftDatabase extends RoomDatabase {
    public abstract GiftDao GiftDao();
}
