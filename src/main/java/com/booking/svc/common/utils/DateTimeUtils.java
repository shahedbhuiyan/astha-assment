package com.booking.svc.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public abstract class DateTimeUtils {

    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static Optional<Date> convertDateTime(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_DATE_TIME_FORMAT, Locale.ENGLISH);
        try {
            Date parsedDate = formatter.parse(dateTime);
            return Optional.ofNullable(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Date> convertDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_DATE_FORMAT, Locale.ENGLISH);
        try {
            Date parsedDate = formatter.parse(date);
            return Optional.ofNullable(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static String convertDateTime(Date dateTime) {
        DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_TIME_FORMAT);
        return dateFormat.format(dateTime);
    }

    public static String convertDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String concatenateDateAndTimeInISOFormat(String date, String time) {
        return date.concat("T").concat(time);
    }

}
