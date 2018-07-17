package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;

import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.dao.EventDao;
import hu.molti.specialevents.database.EventDatabase;
import hu.molti.specialevents.entities.EventEntity;

public class EventService extends BaseService<EventDao, EventEntity> {
    private static EventService service;

    private EventService() {
        db = Room.databaseBuilder(StartingActivity.getContext(),
                EventDatabase.class, "event-db").build().EventDao();
    }

    public static EventService getService() {
        if (service == null) {
            service = new EventService();
        }
        return service;
    }
}
