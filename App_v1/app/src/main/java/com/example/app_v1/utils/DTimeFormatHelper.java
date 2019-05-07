package com.example.app_v1.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DTimeFormatHelper
{
    public static String getTimeFromISO8601Timestamp(final String timestampISO8601) throws ParseException
    {
        String s = timestampISO8601.replace("Z","+02:00");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH).parse(s);
        DateFormat df = new SimpleDateFormat("HH:mm",Locale.ENGLISH);

        return df.format(date);
    }

    public static String getDateFromISO8601Timestamp(final String timestampISO8601) throws ParseException
    {
        String s = timestampISO8601.replace("Z","+02:00");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH).parse(s);
        DateFormat df = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);

        return df.format(date);
    }

    public static Date convertISO8601stringToDate(final String timestampISO8601) throws ParseException
    {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH);

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(timestampISO8601);
    }
}
