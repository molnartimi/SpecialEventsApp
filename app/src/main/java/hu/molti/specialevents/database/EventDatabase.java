package hu.molti.specialevents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.molti.specialevents.dao.EventDao;
import hu.molti.specialevents.entities.EventEntity;

@Database(entities = {EventEntity.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao EventDao();
}