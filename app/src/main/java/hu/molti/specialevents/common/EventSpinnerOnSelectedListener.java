package hu.molti.specialevents.common;

import android.view.View;
import android.widget.AdapterView;

import hu.molti.specialevents.lists.PersonSelectorAdapter;

public class EventSpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
    private PersonSelectorAdapter personAdapter;

    public EventSpinnerOnSelectedListener(PersonSelectorAdapter adapter) {
        personAdapter = adapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2 && personAdapter.getItemCount() == 1) {
            personAdapter.addOne();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
