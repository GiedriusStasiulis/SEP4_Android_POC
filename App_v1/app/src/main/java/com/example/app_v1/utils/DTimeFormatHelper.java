package com.example.app_v1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DTimeFormatHelper
{
    public static String getTodayDateAsString()
    {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH).format(new Date());
    }

    public static String getDayBeforeTodayAsString()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String getTimeStringFromISO8601Timestamp(final String timestampISO8601) throws ParseException
    {
        String s = timestampISO8601.replace("Z","+02:00");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH).parse(s);
        DateFormat df = new SimpleDateFormat("HH:mm",Locale.ENGLISH);

        return df.format(date);
    }

    public static String getDateStringFromISO8601Timestamp(final String timestampISO8601) throws ParseException
    {
        String s = timestampISO8601.replace("Z","+02:00");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH).parse(s);
        DateFormat df = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);

        return df.format(date);
    }

    public static Date convertStringToDate(final String dateTimeString) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);

        return sdf.parse(dateTimeString);
    }

    public static Date convertISO8601stringToDate(final String timestampISO8601) throws ParseException
    {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(timestampISO8601);
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
}
