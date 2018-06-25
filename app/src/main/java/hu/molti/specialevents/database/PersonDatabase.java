package hu.molti.specialevents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.molti.specialevents.dao.PersonDao;
import hu.molti.specialevents.entities.PersonEntity;

@Database(entities = {PersonEntity.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao PersonDao();
}