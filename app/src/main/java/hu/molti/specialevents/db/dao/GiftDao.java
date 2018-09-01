package hu.molti.specialevents.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


import hu.molti.specialevents.entity.GiftEntity;

@Dao
public interface GiftDao extends IDao<GiftEntity> {
    @Override
    @Query("SELECT * FROM GiftEntity")
    List<GiftEntity> getAll();

    @Query("SELECT * FROM GiftEntity WHERE personId like :personId")
    List<GiftEntity> getAllOfPerson(String personId);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GiftEntity entity);

    @Override
    @Delete
    void delete(GiftEntity entity);

    @Override
    @Update
    void update(GiftEntity entity);
}
