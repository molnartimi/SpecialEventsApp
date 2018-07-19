package hu.molti.specialevents.common;

import android.view.View;
import android.widget.AdapterView;

public class EventSpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
    private IPlusMinusPersonLinkSetter listener;

    public EventSpinnerOnSelectedListener(IPlusMinusPersonLinkSetter listener) {
        this.listener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        listener.updatePlusMinusPersonLinks();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
