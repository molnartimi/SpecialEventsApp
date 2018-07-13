package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.List;

import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.common.DataModificationListener;
import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.dao.PersonDao;
import hu.molti.specialevents.database.PersonDatabase;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonService {
    private static PersonService service;
    private PersonDao db;
    private List<PersonEntity> persons;
    private DataModificationListener dataModificationListener;

    private PersonService() {
        db = Room.databaseBuilder(StartingActivity.getContext(),
                PersonDatabase.class, "person-db").build().PersonDao();
    }

    public static PersonService getService() {
        if (service == null) {
            service = new PersonService();
        }
        return service;
    }

    public void setDataModificationListener(DataModificationListener listener) {
        dataModificationListener = listener;
    }

    public void startLoadAllPersons(final DataLoadedListener callbackListener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                persons = db.getAll();
                callbackListener.dataIsLoaded();
                return null;
            }
        }.execute();
    }

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public PersonEntity get(int pos) {
        return persons.get(pos);
    }

    public int count() {
        return persons.size();
    }

    public void add(final PersonEntity newPerson) {
        persons.add(newPerson);
        dataModificationListener.inserted();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.insert(newPerson);
                return null;
            }
        }.execute();
    }

    public void remove(final int pos) {
        final PersonEntity deleted = persons.remove(pos);
        dataModificationListener.deleted();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.delete(deleted);
                return null;
            }
        }.execute();
    }
}
