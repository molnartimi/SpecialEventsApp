package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.dao.EventDao;
import hu.molti.specialevents.database.EventDatabase;
import hu.molti.specialevents.entities.EventEntity;

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
        List<EventEntity> events = eventsInMonths.get(monthIdx);
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getDay() > entity.getDay()) {
                events.add(i, entity);
                emitListener(monthIdx);
                return;
            }
        }
        events.add(entity);
        emitListener(monthIdx);
    }

    @Override
    protected void entityRemoved(EventEntity entity) {
        dataList.remove(entity);
        int monthIdx = entity.getMonth() - 1;
        eventsInMonths.get(monthIdx).remove(entity);
        emitListener(monthIdx);
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
}
