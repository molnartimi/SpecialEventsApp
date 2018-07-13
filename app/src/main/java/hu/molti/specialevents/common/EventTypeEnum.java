package hu.molti.specialevents.common;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.StartingActivity;

public enum EventTypeEnum {
    BIRTHDAY, NAMEDAY, ANNIVERSARY;

    @Override
    public String toString() {
        Resources resources = StartingActivity.getContext().getResources();
        int id = 0;
        switch (this) {
            case BIRTHDAY: id = R.string.birthday; break;
            case NAMEDAY: id = R.string.nameday; break;
            case ANNIVERSARY: id = R.string.anniversary; break;
        }
        return StartingActivity.getContext().getResources().getString(id);
    }

    public static List<String> stringList() {
        List<String> strings = new ArrayList<>();
        for (EventTypeEnum type: values()) {
            strings.add(type.toString());
        }
        return strings;
    }
}
