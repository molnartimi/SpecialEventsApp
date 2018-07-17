package hu.molti.specialevents;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.common.DateValidator;
import hu.molti.specialevents.common.EventTypeEnum;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.entities.PersonEntity;
import hu.molti.specialevents.service.EventService;
import hu.molti.specialevents.service.PersonService;

public class NewEventDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private View dialogView;
    private Spinner typeSpinner;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner personSpinner;
    private PersonService personService;
    private EventService eventService;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        eventService = EventService.getService();
        personService = PersonService.getService();

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
        ArrayList<String> persons= new ArrayList<>();
        persons.add(personService.get(personSpinner.getSelectedItemPosition()).getId());
        return new EventEntity(
                monthSpinner.getSelectedItemPosition() + 1,
                daySpinner.getSelectedItemPosition() + 1,
                EventTypeEnum.toEventType(typeSpinner.getSelectedItemPosition()),
                persons);
    }

    private void initView() {
        typeSpinner = createSpinner(R.id.new_event_dialog_type_spinner, EventTypeEnum.stringList());

        monthSpinner = createSpinner(R.id.new_event_dialog_month_spinner, getNumberListTo(12));
        monthSpinner.setOnItemSelectedListener(this);

        daySpinner = createSpinner(R.id.new_event_dialog_day_spinner, getNumberListTo(31));

        personSpinner = createSpinner(R.id.new_event_dialog_person_spinner, personService.getAll());
    }

    private <T> Spinner createSpinner(int viewId, List<T> values) {
        Spinner spinner = dialogView.findViewById(viewId);
        fillSpinner(spinner, values);
        return spinner;
    }

    private <T> void fillSpinner(Spinner spinner, List<T> values) {
        ArrayAdapter<T> dataAdapter = new ArrayAdapter<>(StartingActivity.getContext(),
                android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(dataAdapter);
    }

    private ArrayList<Integer> getNumberListTo(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int selectedMonth = (Integer) monthSpinner.getSelectedItem();
        int selectedDay = (Integer) daySpinner.getSelectedItem();
        int newMaxDay = DateValidator.getDayNum(selectedMonth);
        fillSpinner(daySpinner, getNumberListTo(newMaxDay));
        daySpinner.setSelection(selectedDay <= newMaxDay ? selectedDay - 1 : newMaxDay - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
