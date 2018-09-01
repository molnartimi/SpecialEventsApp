package hu.molti.specialevents.activity;

import android.content.DialogInterface;
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

import hu.molti.specialevents.R;
import hu.molti.specialevents.listener.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.activity.dialog.ConfirmDialogFragment;
import hu.molti.specialevents.activity.dialog.SaveEventDialogFragment;
import hu.molti.specialevents.entity.EventEntity;
import hu.molti.specialevents.list.MonthListAdapter;
import hu.molti.specialevents.service.EventService;

public class EventListActivity extends AppCompatActivity implements EditEntityListener<EventEntity> {
    private EventService eventService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        createToolbar();
        createFloatingActionBtn();
        RecyclerViewHelper.initRecyclerView(findViewById(R.id.month_recycler_view), new MonthListAdapter(EventListActivity.this));
        eventService = EventService.getService();
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
                new SaveEventDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public void onEditBtnOnClicked(EventEntity event) {
        DialogFragment newEventDialog = new SaveEventDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", event.getId());
        newEventDialog.setArguments(bundle);
        newEventDialog.show(getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void onDeleteBtnOnClicked(final EventEntity eventEntity) {
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        dialog.setMessage(getString(R.string.confirm_delete_event_message));
        dialog.setOnOkListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eventService.remove(eventEntity);
            }
        });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
