package app.studyrewards.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Converts quantities in seconds or millisecons into time strings to display
 */
public class TimeConvert {

    public static String convertSecondsToTime(long seconds){
        NumberFormat f = new DecimalFormat("00");
        long hour = (seconds / 3600) % 24;
        long min = (seconds / 60) % 60;
        long sec = (seconds / 1) % 60;
        String timeStr = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec);
        return timeStr;
    }

    public static String convertMillisToTime(long milliseconds){
        NumberFormat f = new DecimalFormat("00");
        long hour = (milliseconds / 3600000) % 24;
        long min = (milliseconds / 60000) % 60;
        long sec = (milliseconds / 1000) % 60;
        String timeStr = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec);
        return timeStr;
    }

}
