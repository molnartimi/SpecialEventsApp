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
public interface PersonDao {
    @Query("SELECT * FROM PersonEntity")
    List<PersonEntity> getAll();

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :name LIMIT 1")
    PersonEntity findByName(String name);

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :id")
    PersonEntity findById(String id);

    @Query("SELECT * FROM PersonEntity WHERE name LIKE :name")
    List<PersonEntity> findALLByName(String name);

    @Update
    void updatePerson(PersonEntity personEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PersonEntity... PersonEntitys);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonEntity personEntity);

    @Delete
    void delete(PersonEntity PersonEntity);
}
