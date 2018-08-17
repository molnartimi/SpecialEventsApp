package hu.molti.specialevents;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import hu.molti.specialevents.common.DataLoadedListener;
import hu.molti.specialevents.common.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.lists.PersonListAdapter;
import hu.molti.specialevents.service.GiftService;
import hu.molti.specialevents.service.PersonService;

public class PersonListActivity extends AppCompatActivity implements EditEntityListener<PersonEntity>,
        PersonListAdapter.OpenGiftsListener, DataLoadedListener {
    private PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        createToolbar();
        createFloatingActionBtn();
        RecyclerViewHelper.initRecyclerView(findViewById(R.id.person_recycler_view),
                new PersonListAdapter(PersonListActivity.this, PersonListActivity.this));
        personService = PersonService.getService();
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
                new SavePersonDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public void onEditBtnOnClicked(PersonEntity personEntity) {
        SavePersonDialogFragment dialog = new SavePersonDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", personEntity.getId());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void onDeleteBtnOnClicked(final PersonEntity personEntity) {
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        dialog.setMessage(getString(R.string.confirm_delete_person_message));
        dialog.setOnOkListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        personService.remove(personEntity);
                    }
                });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void openGifts(String personId) {
        GiftService.getService().loadGiftsOfPerson(personId, PersonListActivity.this);
    }

    @Override
    public void dataIsLoaded() {
        startActivity(new Intent(this, GiftListActivity.class));
    }
}
