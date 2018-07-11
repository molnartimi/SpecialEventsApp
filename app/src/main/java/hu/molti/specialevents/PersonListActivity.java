package hu.molti.specialevents;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import hu.molti.specialevents.dao.PersonDao;
import hu.molti.specialevents.database.PersonDatabase;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.lists.PersonListAdapter;

public class PersonListActivity extends AppCompatActivity
        implements NewPersonDialogFragment.NewPersonDialogListener, PersonListAdapter.PersonIsDeletedListener {
    private RecyclerView recyclerView;
    private PersonListAdapter personListAdapter;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        createToolbar();
        createFloatingActionBtn();
        personDao = Room.databaseBuilder(getApplicationContext(),
                PersonDatabase.class, "person-db").build().PersonDao();
        initRecycleView();
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

    @Override
    public void onDialogPositiveClick(final PersonEntity newPerson) {
        personListAdapter.addPerson(newPerson);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                personDao.insert(newPerson);
                return null;
            }
        }.execute();
    }

    public void initRecycleView() {
        final PersonListActivity listenerActivity = this;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                recyclerView = findViewById(R.id.person_recycler_view);
                personListAdapter = new PersonListAdapter(personDao.getAll(), listenerActivity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(personListAdapter);
                return null;
            }
        }.execute();
    }

    @Override
    public void deletePerson(final PersonEntity person) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                personDao.delete(person);
                return null;
            }
        }.execute();
    }
}
