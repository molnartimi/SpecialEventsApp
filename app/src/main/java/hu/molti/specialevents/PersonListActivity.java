package hu.molti.specialevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.lists.PersonListAdapter;

public class PersonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        createToolbar();
        createFloatingActionBtn();
        RecyclerViewHelper.initRecyclerView(findViewById(R.id.person_recycler_view), new PersonListAdapter());
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
}
