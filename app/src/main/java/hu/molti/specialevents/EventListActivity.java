package hu.molti.specialevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.lists.EventListAdapter;
import hu.molti.specialevents.lists.MonthListAdapter;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.EditEventBtnOnCliskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        createToolbar();
        createFloatingActionBtn();
        RecyclerViewHelper.initRecyclerView(findViewById(R.id.month_recycler_view), new MonthListAdapter(EventListActivity.this));
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

    @Override
    public void onEditEventOnClicked(EventEntity event) {
        DialogFragment newEventDialog = new NewEventDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", event.getId());
        newEventDialog.setArguments(bundle);
        newEventDialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
