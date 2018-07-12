package hu.molti.specialevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import hu.molti.specialevents.database.PersonDatabase;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.enums.EventTypeEnum;
import hu.molti.specialevents.lists.EventListAdapter;
import hu.molti.specialevents.lists.PersonListAdapter;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.EventIsDeletedListener {
    private RecyclerView recyclerView;
    private EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        createToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.event_recycler_view);

        ArrayList<PersonEntity> persons = new ArrayList<>();
        persons.add(new PersonEntity("Test1"));
        ArrayList<EventEntity> events = new ArrayList<>();
        events.add(new EventEntity(1, 1, EventTypeEnum.BIRTHDAY, persons));
        events.add(new EventEntity(2,2,EventTypeEnum.NAMEDAY, persons));

        adapter = new EventListAdapter(events, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_persons:
                startActivity(new Intent(this, PersonListActivity.class));
                break;
            default:
                break;
        }

        return true;
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.event_list_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void deleteEvent(EventEntity event) {

    }
}
