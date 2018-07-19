package hu.molti.specialevents;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hu.molti.specialevents.common.EventTypeEnum;
import hu.molti.specialevents.common.EventSpinnerOnSelectedListener;
import hu.molti.specialevents.common.IPlusMinusPersonLinkSetter;
import hu.molti.specialevents.common.MonthSpinnerOnSelectedListener;
import hu.molti.specialevents.common.SpinnerHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.lists.PersonSelectorAdapter;
import hu.molti.specialevents.service.EventService;

public class NewEventDialogFragment extends DialogFragment implements IPlusMinusPersonLinkSetter {
    private View dialogView;
    private Spinner typeSpinner, monthSpinner, daySpinner;
    private PersonSelectorAdapter personSelectorAdapter;
    private TextView plusPersonLink, minusPersonLink;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        dialogView = getActivity().getLayoutInflater().inflate(R.layout.new_event_dialog, null);

        initView();

        builder.setView(dialogView)
                .setTitle(R.string.save_event_dialog_title)
                .setPositiveButton(R.string.save_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventService.getService().add(createNewEvent());
                    }
                })
                .setNegativeButton(R.string.cancel_something, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }

    @Override
    public void updatePlusMinusPersonLinks() {
        EventTypeEnum type = EventTypeEnum.toEventType(typeSpinner.getSelectedItemPosition());
        int personCount = personSelectorAdapter.getItemCount();
        if (type == EventTypeEnum.ANNIVERSARY) {
            if (personCount == 1) {
                personSelectorAdapter.addOne();
            } else {
                showMinusPersonLink(personCount > 2);
            }
        } else {
            if (personCount == 1) {
                showMinusPersonLink(false);
            } else {
                showMinusPersonLink(true);
            }
        }
    }

    private EventEntity createNewEvent() {
        return new EventEntity(
                monthSpinner.getSelectedItemPosition() + 1,
                daySpinner.getSelectedItemPosition() + 1,
                EventTypeEnum.toEventType(typeSpinner.getSelectedItemPosition()),
                personSelectorAdapter.getPersonIds());
    }

    private void initView() {
        initSpinners();
        initPlusMinusPersonLinks();
    }

    private void initPlusMinusPersonLinks() {
        plusPersonLink = dialogView.findViewById(R.id.new_event_dialog_plus_person_btn);
        minusPersonLink = dialogView.findViewById(R.id.new_event_dialog_minus_person_btn);

        plusPersonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personSelectorAdapter.addOne();
            }
        });
        minusPersonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personSelectorAdapter.removeOne();
            }
        });
    }

    private void initSpinners() {
        typeSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_type_spinner, EventTypeEnum.stringList());
        monthSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_month_spinner, getNumberListTo(12));
        daySpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_day_spinner, getNumberListTo(31));
        initPersonSelectorRecyclerView();

        typeSpinner.setOnItemSelectedListener(new EventSpinnerOnSelectedListener(this));
        monthSpinner.setOnItemSelectedListener(new MonthSpinnerOnSelectedListener(monthSpinner, daySpinner));
    }

    private ArrayList<Integer> getNumberListTo(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    private void initPersonSelectorRecyclerView() {
        RecyclerView recyclerView = dialogView.findViewById(R.id.person_selector_recycler_view);
        personSelectorAdapter = new PersonSelectorAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(personSelectorAdapter);
    }

    private void showMinusPersonLink(boolean shouldShow) {
        minusPersonLink.setVisibility(shouldShow ? View.VISIBLE : View.INVISIBLE);
    }
}
