package hu.molti.specialevents.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.molti.specialevents.entities.PersonEntity;

@Dao
public interface PersonDao extends IDao<PersonEntity> {
    @Override
    @Query("SELECT * FROM PersonEntity ORDER BY name")
    List<PersonEntity> getAll();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonEntity personEntity);

    @Override
    @Delete
    void delete(PersonEntity PersonEntity);

    @Override
    @Update
    void update(PersonEntity personEntity);

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :name LIMIT 1")
    PersonEntity findByName(String name);

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :id")
    PersonEntity findById(String id);

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :name")
    List<PersonEntity> findALLByName(String name);
}
