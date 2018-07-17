package hu.molti.specialevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.service.EventService;
import hu.molti.specialevents.service.PersonService;

public class StartingActivity extends Activity implements DataLoadedListener {
    private static Context mContext;
    private int dbsLoadedCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_starting);
        PersonService.getService().loadData(this);
        EventService.getService().loadData(this);
    }

    @Override
    public void dataIsLoaded() {
        if (++dbsLoadedCnt == 2) {
            startActivity(new Intent(this, EventListActivity.class));
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
