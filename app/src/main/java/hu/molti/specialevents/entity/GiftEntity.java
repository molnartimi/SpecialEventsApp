package hu.molti.specialevents.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GiftEntity implements IEntity<GiftEntity> {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private boolean done;
    private String personId;

    public GiftEntity(String personId, String name) {
        this.personId = personId;
        this.name = name;
        this.done = false;
        this.id = UUID.randomUUID().toString();
    }

    public void click() {
        done = !done;
    }
}
