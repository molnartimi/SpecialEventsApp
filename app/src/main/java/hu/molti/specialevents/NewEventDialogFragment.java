package hu.molti.specialevents;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hu.molti.specialevents.common.DateValidator;
import hu.molti.specialevents.common.EventTypeEnum;
import hu.molti.specialevents.common.EventSpinnerOnSelectedListener;
import hu.molti.specialevents.common.SpinnerHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.lists.PersonSelectorAdapter;
import hu.molti.specialevents.service.EventService;

public class NewEventDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private View dialogView;
    private Spinner typeSpinner;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private PersonSelectorAdapter personSelectorAdapter;
    private EventService eventService;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        eventService = EventService.getService();

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.new_event_dialog, null);
        initView();

        builder.setView(dialogView)
                .setTitle(R.string.save_event_dialog_title)
                .setPositiveButton(R.string.save_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eventService.add(createNewEvent());
                    }
                })
                .setNegativeButton(R.string.cancel_something, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }

    private EventEntity createNewEvent() {
        return new EventEntity(
                monthSpinner.getSelectedItemPosition() + 1,
                daySpinner.getSelectedItemPosition() + 1,
                EventTypeEnum.toEventType(typeSpinner.getSelectedItemPosition()),
                personSelectorAdapter.getPersonIds());
    }

    private void initView() {
        typeSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_type_spinner, EventTypeEnum.stringList());

        monthSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_month_spinner, getNumberListTo(12));
        monthSpinner.setOnItemSelectedListener(this);

        daySpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_day_spinner, getNumberListTo(31));

        initRecyclerView();

        TextView plusPersonLink = dialogView.findViewById(R.id.new_event_dialog_plus_person_btn);
        TextView minusPersonLink = dialogView.findViewById(R.id.new_event_dialog_minus_person_btn);

        typeSpinner.setOnItemSelectedListener(new EventSpinnerOnSelectedListener(personSelectorAdapter));
        personSelectorAdapter.setMinusPersonLink(minusPersonLink);

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

    private ArrayList<Integer> getNumberListTo(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = dialogView.findViewById(R.id.person_selector_recycler_view);
        personSelectorAdapter = new PersonSelectorAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(personSelectorAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int selectedMonth = (Integer) monthSpinner.getSelectedItem();
        int selectedDay = (Integer) daySpinner.getSelectedItem();
        int newMaxDay = DateValidator.getDayNum(selectedMonth);
        SpinnerHelper.fillSpinner(daySpinner, getNumberListTo(newMaxDay));
        daySpinner.setSelection(selectedDay <= newMaxDay ? selectedDay - 1 : newMaxDay - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
