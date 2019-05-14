package com.example.app_v1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeConverterHelper
{
    public static String convertDateTimeStringToISO8601String(final String dateTime)
    {
        SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);
        Date date = null;
        try {
            date = sdfIn.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
        return sdfOut.format(date);
    }

    public static String convertTimestampISO8601ToDateString(final String timestampISO8601)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(timestampISO8601);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
        return df.format(date);
    }

    public static String convertTimestampISO8601ToTimeString(final String timestampISO8601)
    {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(timestampISO8601);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        return df.format(date);
    }

    public static String convertTimePickerValuesToString(int hourOfDay, int minute)
    {
        String formattedTime,hours,minutes;

        if(hourOfDay < 10)
        {
            hours = String.format(Locale.ENGLISH,"0%d",hourOfDay);
        }
        else
        {
            hours = String.valueOf(hourOfDay);
        }

        if(minute < 10)
        {
            minutes = String.format(Locale.ENGLISH,"0%d",minute);
        }
        else
        {
            minutes = String.valueOf(minute);
        }

        formattedTime = String.format("%s:%s",hours,minutes);
        return formattedTime;
    }

    public static String convertDatePickerValuesToString(int year, int month, int day)
    {
        String formattedDate,fMonth,fDay;

        month = month + 1;

        if(month < 10)
        {
            fMonth = String.format(Locale.ENGLISH,"0%d",month);
        }
        else
        {
            fMonth = String.valueOf(month);
        }

        if(day < 10)
        {
            fDay = String.format(Locale.ENGLISH, "0%d",day);
        }
        else
        {
            fDay = String.valueOf(day);
        }

        formattedDate = String.format(Locale.ENGLISH,"%s/%s/%d",fMonth,fDay,year);
        return formattedDate;
    }

}