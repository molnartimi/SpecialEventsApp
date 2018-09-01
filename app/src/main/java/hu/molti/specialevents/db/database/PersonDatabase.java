package hu.molti.specialevents.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.molti.specialevents.db.dao.PersonDao;
import hu.molti.specialevents.entity.PersonEntity;

@Database(entities = {PersonEntity.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao PersonDao();
}