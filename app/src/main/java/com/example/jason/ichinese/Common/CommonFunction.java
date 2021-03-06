package com.example.jason.ichinese.Common;

import android.graphics.Bitmap;

import com.example.jason.ichinese.Translate.HttpGet;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;

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

    static String mImageUrl;
    public static void getWebImage(String imageUrl){
        mImageUrl = imageUrl;
        new Thread(){
            public void run(){
                try {
                    Bitmap bitmap = HttpGet.getBitmap(mImageUrl);
                    mWebImageCallback.call(bitmap);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();
    }

    /**
     * web image callback
     * */
    public interface WebImageCallback{
        void call(Bitmap bitmap);
    }

    private static WebImageCallback mWebImageCallback;

    public static void setWebImageCallbackCallback(WebImageCallback callback){
        mWebImageCallback = callback;
    }
}
