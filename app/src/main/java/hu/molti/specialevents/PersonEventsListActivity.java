package hu.molti.specialevents;

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

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.lists.MonthListAdapter;
import hu.molti.specialevents.lists.PersonEventsListAdapter;
import hu.molti.specialevents.lists.PersonListAdapter;
import hu.molti.specialevents.service.EventService;
import hu.molti.specialevents.service.GiftService;
import hu.molti.specialevents.service.PersonService;

public class PersonEventsListActivity extends AppCompatActivity implements
        EditEntityListener<EventEntity>, DataLoadedListener {
    private EventService eventService;
    private String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_events_list);

        personId = getIntent().getStringExtra("personId");

        createToolbar();
        createFloatingActionBtn();

        RecyclerViewHelper.initRecyclerView(findViewById(R.id.person_events_recycler_view),
                new PersonEventsListAdapter(personId, PersonEventsListActivity.this));
        eventService = EventService.getService();
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.person_events_list_toolbar);
        toolbar.setTitle(PersonService.getService().get(personId).getName() +
                getString(R.string.person_events_activity_title_last_part));
        findViewById(R.id.gift_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GiftService.getService().loadGiftsOfPerson(personId, PersonEventsListActivity.this);
            }
        });
    }

    private void createFloatingActionBtn() {
        FloatingActionButton fab = findViewById(R.id.add_event_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO az ember itt csak ő lehessen + még aki kell
                new SaveEventDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public void onEditBtnOnClicked(EventEntity event) {
        // TODO emberek közül őt ne lehessen törölni
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

    @Override
    public void dataIsLoaded() {
        startActivity(new Intent(this, GiftListActivity.class));
    }

    // TODO itt ne lehessen törölni, módosítani a személyt
}
