package hu.molti.specialevents.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.molti.specialevents.entities.EventEntity;

@Dao
public interface EventDao extends IDao<EventEntity> {
    @Override
    @Query("SELECT * FROM EventEntity")
    List<EventEntity> getAll();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventEntity entity);

    @Override
    @Delete
    void delete(EventEntity entity);
}
