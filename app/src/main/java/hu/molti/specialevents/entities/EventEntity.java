package hu.molti.specialevents.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hu.molti.specialevents.common.EventType;
import hu.molti.specialevents.common.MyTypeConverter;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EventEntity implements IEntity<EventEntity> {
    @PrimaryKey
    @NonNull
    private String id;
    private int month;
    private int day;

    @TypeConverters(EventType.class)
    private EventType type;

    @TypeConverters(MyTypeConverter.class)
    private List<String> personIds;

    public EventEntity(int month, int day, EventType type, List<String> personIds) {
        this.month = month;
        this.day = day;
        this.type = type;
        this.personIds = personIds;
        this.id = UUID.randomUUID().toString();
    }

    public String getDateString() {
        return (month < 10 ? "0" : "") + Integer.toString(month) + "." +
                (day < 10 ? "0" : "") + Integer.toString(day) + ".";
    }

    public int getPersonIdx(String personId) {
        for (int i = 0; i < personIds.size(); i++) {
            if (personIds.get(i).equals(personId)) {
                return i;
            }
        }
        return -1;
    }
}
