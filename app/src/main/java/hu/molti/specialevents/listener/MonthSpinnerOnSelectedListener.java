package hu.molti.specialevents.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import hu.molti.specialevents.common.DateValidator;
import hu.molti.specialevents.common.SpinnerHelper;

public class MonthSpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
    private Spinner monthSpinner;
    private Spinner daySpinner;

    public MonthSpinnerOnSelectedListener(Spinner monthSpinner, Spinner daySpinner) {
        this.monthSpinner = monthSpinner;
        this.daySpinner = daySpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int selectedMonth = (Integer) monthSpinner.getSelectedItem();
        int selectedDay = (Integer) daySpinner.getSelectedItem();
        int oldMaxDay = daySpinner.getCount();
        int newMaxDay = DateValidator.getDayNum(selectedMonth);
        if (oldMaxDay != newMaxDay) {
            SpinnerHelper.fillSpinner(daySpinner, getNumberListTo(newMaxDay));
            daySpinner.setSelection(selectedDay <= newMaxDay ? selectedDay - 1 : newMaxDay - 1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private ArrayList<Integer> getNumberListTo(int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            numbers.add(i);
        }
        return numbers;
    }
}
