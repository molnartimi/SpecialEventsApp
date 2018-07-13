package hu.molti.specialevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.service.PersonService;

public class StartingActivity extends Activity implements DataLoadedListener {
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_starting);
        PersonService.getService().startLoadAllPersons(this);
    }

    @Override
    public void dataIsLoaded() {
        startActivity(new Intent(this, EventListActivity.class));
    }

    public static Context getContext() {
        return mContext;
    }
}
