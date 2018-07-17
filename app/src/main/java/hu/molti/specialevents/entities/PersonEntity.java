package hu.molti.specialevents.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonEntity implements IEntity<PersonEntity>{
    @PrimaryKey
    @NonNull
    private String id;
    private String name;

    public PersonEntity(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return name;
    }
}
