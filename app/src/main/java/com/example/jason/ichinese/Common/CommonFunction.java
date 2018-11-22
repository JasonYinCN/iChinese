package com.example.jason.ichinese.Common;

import java.util.Calendar;
import java.util.TimeZone;

public class CommonFunction {

    /**
     * default param: GMT+8:00 -> beijing time zone
     * */
    public static String getCurrentDate() {
        return getCurrentDate("GMT+8:00");
    }

    /**
     *  function: get loacl current date
     *  param: time zone
     *  return: format-> 2018-05-13
     * */
    public static String getCurrentDate(String timeZone) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH))+1;
        String day = String.valueOf(cal.get(Calendar.DATE));
        String date = year + "-" + month + "-" + day;

        return date;
    }
}
