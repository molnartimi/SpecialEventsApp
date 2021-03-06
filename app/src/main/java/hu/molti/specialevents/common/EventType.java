package hu.molti.specialevents.common;

import android.arch.persistence.room.TypeConverter;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import hu.molti.specialevents.R;
import hu.molti.specialevents.activity.StartingActivity;

public enum EventType {
    BIRTHDAY, NAMEDAY, ANNIVERSARY;

    private static Resources resources = StartingActivity.getContext().getResources();

    @Override
    public String toString() {
        int id = 0;
        switch (this) {
            case BIRTHDAY: id = R.string.birthday; break;
            case NAMEDAY: id = R.string.nameday; break;
            case ANNIVERSARY: id = R.string.anniversary; break;
        }
        return resources.getString(id);
    }

    public int getIconId() {
        switch (this) {
            case BIRTHDAY:      return R.drawable.ic_cake_white_24dp;
            case NAMEDAY:       return R.drawable.ic_flower_white_24dp;
            case ANNIVERSARY:   return R.drawable.ic_heart_white_24dp;
            default:            return -1;
        }
    }

    public static List<String> stringList() {
        List<String> strings = new ArrayList<>();
        for (EventType type: values()) {
            strings.add(type.toString());
        }
        return strings;
    }

    @TypeConverter
    public static EventType toEventType(int code) {
        return values()[code];
    }

    @TypeConverter
    public static int toInt(EventType obj) {
        EventType[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (obj.equals(values[i])) {
                return i;
            }
        }
        return 0;
    }
}
