package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;

import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.dao.PersonDao;
import hu.molti.specialevents.database.PersonDatabase;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonService extends BaseService<PersonDao, PersonEntity> {
    private static PersonService service;
    private EventService eventService;

    private PersonService() {
        db = Room.databaseBuilder(StartingActivity.getContext(),
                PersonDatabase.class, "person-db").build().PersonDao();
        eventService = EventService.getService();
    }

    public static PersonService getService() {
        if (service == null) {
            service = new PersonService();
        }
        return service;
    }

    @Override
    protected void entityAdded(PersonEntity person) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getName().compareTo(person.getName()) > 0) {
                dataList.add(i, person);
                emitListener(0);
                return;
            }
        }
        dataList.add(person);
        emitListener(0);
    }

    @Override
    protected void entityRemoved(PersonEntity entity) {
        dataList.remove(entity);
        emitListener(0);
        eventService.removePersonFromEvents(entity.getId());

    }

    public int getIdx(String id) {
        for (PersonEntity person: dataList) {
            if (person.getId().equals(id)) {
                return dataList.indexOf(person);
            }
        }
        return -1;
    }
}
