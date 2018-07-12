package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.List;

import hu.molti.specialevents.PersonListActivity;
import hu.molti.specialevents.common.DataInsertedListener;
import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.dao.PersonDao;
import hu.molti.specialevents.database.PersonDatabase;
import hu.molti.specialevents.entities.PersonEntity;

public class PersonService {
    private static PersonService service;
    private PersonDao db;
    private DataInsertedListener insertedListener;

    private PersonService() {
        db = Room.databaseBuilder(PersonListActivity.getContext(),
                PersonDatabase.class, "person-db").build().PersonDao();
    }

    public static PersonService getService() {
        if (service == null) {
            service = new PersonService();
        }
        return service;
    }

    public void setInsertedListener(DataInsertedListener listener) {
        insertedListener = listener;
    }

    public void startLoadAllPersons(final DataLoadedListener callbackListener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<PersonEntity> persons = db.getAll();
                callbackListener.dataIsLoaded(persons);
                return null;
            }
        }.execute();
    }


    public void addPerson(final PersonEntity newPerson) {
        insertedListener.dataInserted(newPerson);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.insert(newPerson);
                return null;
            }
        }.execute();
    }

    public void deletePerson(final PersonEntity person) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.delete(person);
                return null;
            }
        }.execute();
    }
}
