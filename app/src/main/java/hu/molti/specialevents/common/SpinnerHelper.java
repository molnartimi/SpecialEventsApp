package hu.molti.specialevents.common;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import hu.molti.specialevents.activity.StartingActivity;

public class SpinnerHelper {

    public static <T> Spinner createSpinner(View view, int viewId, List<T> values) {
        Spinner spinner = view.findViewById(viewId);
        fillSpinner(spinner, values);
        return spinner;
    }

    public static <T> void fillSpinner(Spinner spinner, List<T> values) {
        ArrayAdapter<T> dataAdapter = new ArrayAdapter<>(StartingActivity.getContext(),
                android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(dataAdapter);
    }
}
