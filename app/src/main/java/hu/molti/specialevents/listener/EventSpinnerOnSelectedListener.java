package hu.molti.specialevents.listener;

import android.view.View;
import android.widget.AdapterView;

import hu.molti.specialevents.common.EventType;
import hu.molti.specialevents.list.PersonSelectorAdapter;

public class EventSpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
    private PersonSelectorAdapter adapter;

    public EventSpinnerOnSelectedListener(PersonSelectorAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        EventType type = EventType.toEventType(i);
        int personCount = adapter.getItemCount();
        if (type == EventType.ANNIVERSARY && personCount == 1) {
                adapter.addOne();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
