package com.udacity.findaflight.utils;

import android.content.Intent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {
    private static final DateFormat originalFormat = new SimpleDateFormat("d M yyyy", Locale.ENGLISH);

    public static String getDateInDayMonthFormat(int day, int month, int year) {
        DateFormat targetFormat = new SimpleDateFormat("dd MMM,EEEE");
        String originalDate = day + " " + month + " " + year;
        Date date = null;
        try {
            date = originalFormat.parse(originalDate);
            String formattedDate = targetFormat.format(date).toUpperCase();
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateInDayMonthYearFormat(int day, int month, int year) {
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        String originalDate = day + " " + month + " " + year;
        Date date = null;
        try {
            date = originalFormat.parse(originalDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromString(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    }

    public static Date getDateTimeFromString(String dateTimeString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTimeString);
    }

    public static Date getDateTimeFromUnixTime(Long unixTime) {
        Date date = new Date(unixTime * 1000L);
        return date;
    }

    public static String getDateTimeString(Date date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd|h:mm a");
        return dateFormat.format(date);
    }

    public static String getHoursMinutesFromSeconds(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds / 60) % 60;

        String hoursString = Long.toString(hours);
        String minutesString = String.valueOf(minutes);
        return hoursString + "h " + minutesString + "m";
    }

    public static String getDayOfWeekMonthDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d");
        return dateFormat.format(date);
    }

    public static String getDateMonthYear(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        return dateFormat.format(date);
    }
}
