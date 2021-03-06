package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.molti.specialevents.activity.StartingActivity;
import hu.molti.specialevents.common.EventComparator;
import hu.molti.specialevents.db.dao.EventDao;
import hu.molti.specialevents.db.database.EventDatabase;
import hu.molti.specialevents.entity.EventEntity;

public class EventService extends BaseService<EventDao, EventEntity> {
    private static EventService service;
    private List<List<EventEntity>> eventsInMonths;

    private EventService() {
        db = Room.databaseBuilder(StartingActivity.getContext(),
                EventDatabase.class, "event-db").build().EventDao();

        eventsInMonths = new ArrayList<>();
        int i = 0;
        while (i < 12) {
            eventsInMonths.add(new ArrayList<EventEntity>());
            i++;
        }
    }

    public static EventService getService() {
        if (service == null) {
            service = new EventService();
        }
        return service;
    }

    public List<EventEntity> getAll(String personId) {
        List<EventEntity> events = new ArrayList<>();
        for (EventEntity event: dataList) {
            if (event.getPersonIds().contains(personId)) {
                events.add(event);
            }
        }
        Collections.sort(events, new EventComparator());
        return events;
    }

    public EventEntity getInMonth(int monthIdx, int pos) {
        return eventsInMonths.get(monthIdx).get(pos);
    }

    public int getCountInMonth(int monthIdx) {
        return eventsInMonths.get(monthIdx).size();
    }

    @Override
    protected void afterDataLoaded() {
        for (EventEntity event: dataList) {
            eventsInMonths.get(event.getMonth() - 1).add(event);
        }
    }

    @Override
    protected void entityAdded(EventEntity entity) {
        dataList.add(entity);
        int monthIdx = entity.getMonth() - 1;
        insertEventToMonthList(entity, monthIdx);
        emitAllListeners();
    }

    private void insertEventToMonthList(EventEntity entity, int monthIdx) {
        List<EventEntity> events = eventsInMonths.get(monthIdx);
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getDay() > entity.getDay()) {
                events.add(i, entity);
                return;
            }
        }
        events.add(entity);
    }

    @Override
    protected void entityRemoved(EventEntity entity) {
        dataList.remove(entity);
        int monthIdx = entity.getMonth() - 1;
        eventsInMonths.get(monthIdx).remove(entity);
        emitAllListeners();
    }

    public void removePersonFromEvents(final String personId) {
        // TODO loading, lassú lehet...
        ArrayList<EventEntity> toRemove = new ArrayList<>();
        for (EventEntity event : dataList) {
            int personIdx = event.getPersonIdx(personId);
            if (personIdx >= 0) {
                if (event.getPersonIds().size() == 1) {
                    toRemove.add(event);
                } else {
                    event.getPersonIds().remove(personIdx);
                    update(event);
                }
            }
        }

        for (EventEntity event : toRemove) {
            remove(event);
        }
    }

    @Override
    protected void entityUpdated(EventEntity entity) {
        emitAllListeners();
    }

    public void update(EventEntity event, int originMonth) {
        if (originMonth != event.getMonth()) {
            insertEventToMonthList(event, event.getMonth() - 1);
            eventsInMonths.get(originMonth - 1).remove(event);
        }
        update(event);
    }
}
