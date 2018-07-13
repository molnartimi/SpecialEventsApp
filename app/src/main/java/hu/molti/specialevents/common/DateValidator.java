package hu.molti.specialevents.common;

public class DateValidator {
    public static int getDayNum(int month) {
        switch (month) {
            case 2: return 29;
            case 4:
            case 6:
            case 9:
            case 11: return 30;
            default: return 31;
        }
    }
}
