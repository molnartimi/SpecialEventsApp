package hu.molti.specialevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.common.EventTypeEnum;
import hu.molti.specialevents.lists.EventListAdapter;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        createToolbar();
        createFloatingActionBtn();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.event_recycler_view);
        EventListAdapter adapter = new EventListAdapter();
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

    private void createFloatingActionBtn() {
        FloatingActionButton fab = findViewById(R.id.add_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewEventDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }
}
