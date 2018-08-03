package hu.molti.specialevents;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.common.EventType;
import hu.molti.specialevents.common.EventSpinnerOnSelectedListener;
import hu.molti.specialevents.common.MonthSpinnerOnSelectedListener;
import hu.molti.specialevents.common.RecyclerViewHelper;
import hu.molti.specialevents.common.SpinnerHelper;
import hu.molti.specialevents.entities.EventEntity;
import hu.molti.specialevents.lists.PersonSelectorAdapter;
import hu.molti.specialevents.service.EventService;

public class NewEventDialogFragment extends DialogFragment {
    private View dialogView;
    private Spinner typeSpinner, monthSpinner, daySpinner;
    private PersonSelectorAdapter personSelectorAdapter;
    private EventEntity event;
    private EventService eventService;

    public NewEventDialogFragment() {
        eventService = EventService.getService();
    }

    @Override
    public void setArguments(Bundle bundle) {
        event = eventService.get(bundle.getString("id"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        dialogView = getActivity().getLayoutInflater().inflate(R.layout.new_event_dialog, null);

        initView();

        builder.setView(dialogView)
                .setTitle((event == null) ? R.string.save_event_dialog_title : R.string.edit_event_dialog_title)
                .setPositiveButton(R.string.save_something, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (event == null) {
                            eventService.add(getEvent());
                        } else {
                            eventService.update(getEvent());
                        }

                    }
                })
                .setNegativeButton(R.string.cancel_something, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }

    private EventEntity getEvent() {
        int month = monthSpinner.getSelectedItemPosition() + 1;
        int day = daySpinner.getSelectedItemPosition() + 1;
        EventType type = EventType.toEventType(typeSpinner.getSelectedItemPosition());
        List<String> personIds = personSelectorAdapter.getPersonIds();

        if (event == null) {
            event = new EventEntity(month, day, type, personIds);
        } else {
            event.setMonth(month);
            event.setDay(day);
            event.setType(type);
            event.setPersonIds(personIds);
        }

        return event;
    }

    private void initView() {
        initSpinners();
        initPlusMinusPersonButtons();
    }

    private void initPlusMinusPersonButtons() {
        ImageView plusPersonBtn = dialogView.findViewById(R.id.new_event_dialog_plus_person_btn);
        ImageView minusPersonBtn = dialogView.findViewById(R.id.new_event_dialog_minus_person_btn);

        plusPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personSelectorAdapter.addOne();
            }
        });
        minusPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventType type = EventType.toEventType(typeSpinner.getSelectedItemPosition());
                int personsCount = personSelectorAdapter.getItemCount();
                if ((type == EventType.ANNIVERSARY && personsCount > 2) ||
                    (type != EventType.ANNIVERSARY && personsCount > 1)) {
                    personSelectorAdapter.removeOne();
                }
            }
        });
    }

    private void initSpinners() {
        typeSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_type_spinner, EventType.stringList());
        monthSpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_month_spinner, getNumberListTo(12));
        daySpinner = SpinnerHelper.createSpinner(dialogView,
                R.id.new_event_dialog_day_spinner, getNumberListTo(31));

        personSelectorAdapter = new PersonSelectorAdapter();
        RecyclerViewHelper.initRecyclerView(dialogView.findViewById(R.id.person_selector_recycler_view),
                personSelectorAdapter);

        typeSpinner.setOnItemSelectedListener(new EventSpinnerOnSelectedListener(personSelectorAdapter));
        monthSpinner.setOnItemSelectedListener(new MonthSpinnerOnSelectedListener(monthSpinner, daySpinner));

        if (event != null) {
            typeSpinner.setSelection(EventType.toInt(event.getType()));
            monthSpinner.setSelection(event.getMonth() - 1);
            daySpinner.setSelection(event.getDay() - 1);
            personSelectorAdapter.setPersons(event.getPersonIds());
        }
    }

    private ArrayList<Integer> getNumberListTo(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            numbers.add(i);
        }
        return numbers;
    }
}
