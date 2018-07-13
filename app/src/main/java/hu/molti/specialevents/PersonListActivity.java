package hu.molti.specialevents;

import android.content.Context;
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

import java.util.List;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.lists.PersonListAdapter;
import hu.molti.specialevents.service.PersonService;

public class PersonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        createToolbar();
        createFloatingActionBtn();
        initRecyclerView();
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
            case R.id.action_events:
                startActivity(new Intent(this, EventListActivity.class));
                break;
            default:
                break;
        }

        return true;
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.person_list_toolbar);
        setSupportActionBar(toolbar);
    }

    private void createFloatingActionBtn() {
        FloatingActionButton fab = findViewById(R.id.add_person_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewPersonDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.person_recycler_view);
        PersonListAdapter personListAdapter = new PersonListAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(personListAdapter);
    }
}
