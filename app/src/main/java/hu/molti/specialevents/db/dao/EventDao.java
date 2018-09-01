package hu.molti.specialevents.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.molti.specialevents.entity.EventEntity;

@Dao
public interface EventDao extends IDao<EventEntity> {
    @Override
    @Query("SELECT * FROM EventEntity ORDER BY month, day")
    List<EventEntity> getAll();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventEntity entity);

    @Override
    @Delete
    void delete(EventEntity entity);

    @Override
    @Update
    void update(EventEntity entity);
}
