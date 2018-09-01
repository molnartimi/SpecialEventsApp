package hu.molti.specialevents.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import hu.molti.specialevents.R;
import hu.molti.specialevents.listener.EditEntityListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.activity.dialog.ConfirmDialogFragment;
import hu.molti.specialevents.activity.dialog.SaveGiftDialogFragment;
import hu.molti.specialevents.entity.GiftEntity;
import hu.molti.specialevents.list.GiftListAdapter;
import hu.molti.specialevents.service.GiftService;
import hu.molti.specialevents.service.PersonService;

public class GiftListActivity extends AppCompatActivity implements EditEntityListener<GiftEntity>{
    private GiftService giftService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gift_list);
        giftService = GiftService.getService();
        createToolbar();
        createFloatingActionBtn();
        RecyclerViewHelper.initRecyclerView(findViewById(R.id.gift_recycler_view),
                new GiftListAdapter(GiftListActivity.this));
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.gift_list_toolbar);
        toolbar.setTitle(PersonService.getService().get(giftService.getPersonId()).getName() +
                getString(R.string.gift_activity_title_last_part));
    }

    private void createFloatingActionBtn() {
        FloatingActionButton fab = findViewById(R.id.add_gift_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveGiftDialogFragment().show(getSupportFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    public void onEditBtnOnClicked(GiftEntity giftEntity) {
        SaveGiftDialogFragment dialog = new SaveGiftDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", giftEntity.getId());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void onDeleteBtnOnClicked(final GiftEntity giftEntity) {
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        dialog.setMessage(getString(R.string.confirm_delete_gift_message));
        dialog.setOnOkListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giftService.remove(giftEntity);
                    }
                });
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }
}
