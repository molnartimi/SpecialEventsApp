package hu.molti.specialevents.common;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public class MyTypeConverter {
    @TypeConverter
    public static String convertToString(List<String> list) {
        return TextUtils.join(",", list);
    }

    @TypeConverter
    public static List<String> convertToList(String joined) {
        return Arrays.asList(joined.split(","));
    }
}
