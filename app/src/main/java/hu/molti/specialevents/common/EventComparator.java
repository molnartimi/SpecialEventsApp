package hu.molti.specialevents.common;

import java.util.Comparator;

import hu.molti.specialevents.entity.EventEntity;

public class EventComparator implements Comparator<EventEntity> {
    @Override
    public int compare(EventEntity e1, EventEntity e2) {
        if (e1.getMonth() == e2.getMonth()) {
            return e1.getDay() - e2.getDay();
        } else {
            return e1.getMonth() - e2.getMonth();
        }
    }
}
