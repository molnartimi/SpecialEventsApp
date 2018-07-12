package hu.molti.specialevents.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import hu.molti.specialevents.enums.EventTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EventEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private int month;
    private int day;
    private EventTypeEnum type;
    private List<PersonEntity> persons;

    public EventEntity(int month, int day, EventTypeEnum type, List<PersonEntity> persons) {
        this.month = month;
        this.day = day;
        this.type = type;
        this.persons = persons;
        this.id = UUID.randomUUID().toString();
    }

    public String getDateString() {
        return (month < 10 ? "0" : "") + Integer.toString(month) + "." +
                (day < 10 ? "0" : "") + Integer.toString(day) + ".";
    }
}
