package hu.molti.specialevents.service;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.molti.specialevents.StartingActivity;
import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.dao.GiftDao;
import hu.molti.specialevents.database.GiftDatabase;
import hu.molti.specialevents.entities.GiftEntity;

public class GiftService extends BaseService<GiftDao, GiftEntity> {
    private static GiftService service;
    private String personId;

    private GiftService() {
        db = Room.databaseBuilder(StartingActivity.getContext(),
                GiftDatabase.class, "gift-db").build().GiftDao();
    }

    public static GiftService getService() {
        if (service == null) {
            service = new GiftService();
        }
        return service;
    }

    public void loadGiftsOfPerson(final String personId, final DataLoadedListener callbackListener) {
        this.personId = personId;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dataList = db.getAllOfPerson(personId);
                if (callbackListener != null) {
                    callbackListener.dataIsLoaded();
                }
                return null;
            }
        }.execute();
    }

    public String getPersonId() {
        return personId;
    }
}
