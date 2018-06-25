package hu.molti.specialevents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.lists.PersonListAdapter;

public class PersonListActivity extends AppCompatActivity implements NewPersonDialogFragment.NewPersonDialogListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private PersonListAdapter personListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createFloatingActionBtn();
        initRecycleView();
    }

    private void createFloatingActionBtn() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewPersonDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(PersonEntity newPerson) {
        this.personListAdapter.addPerson(newPerson);
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.person_recycler_view);
        personListAdapter = new PersonListAdapter(new ArrayList<PersonEntity>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(personListAdapter);

        loadDatas();
    }

    private void loadDatas() {
        // TODO database
        personListAdapter.addPerson(new PersonEntity("Tomi"));
        personListAdapter.addPerson(new PersonEntity("Luca"));
    }
}
