package com.example.jason.ichinese.Translate.Jinshan;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.JsonReader;

import com.example.jason.ichinese.Translate.HttpGet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class TransApiJinshan {
    private String mUserUrl;
    private static String mBasicUrl = "http://dict-co.iciba.com/api/dictionary.php?w=*&type=json&key=#";
    private static String mDailySentenseUrl = "http://open.iciba.com/dsapi/?date=*";

    public TransApiJinshan(String userKey){

        mUserUrl = mBasicUrl.replace("#", userKey);
        System.out.println(mUserUrl);
    }

    public String  getTransResult(String input){
        String sendUrl = mUserUrl.replace("*", input);
        return HttpGet.get(sendUrl);
    }

    public String getDailySentense(String date){
        String sendUrl = mDailySentenseUrl.replace("*", date);
        return HttpGet.get(sendUrl);
    }

    private void readTransResJson(String jsonData){
        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonReader jsReader = new JsonReader(new InputStreamReader(is));
        try {
            /*开始解析json为object对象*/
            jsReader.beginObject();
            while(jsReader.hasNext()){
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "word_name":
                        System.out.println("name:" + jsReader.nextString());
                        break;
                    case "symbols":
                        readSymbol(jsReader);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
            jsReader.close(); // 关闭数据流
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    //read datas in symbols
    private void readSymbol(JsonReader jsReader) throws IOException {
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "word_symbol":
                        String word_symbol = jsReader.nextString();
                        System.out.println("word_symbol:" + word_symbol);
                        break;
                    case "symbol_mp3":
                        String symbol_mp3 = jsReader.nextString();
                        System.out.println("symbol_mp3:" + symbol_mp3);
                        break;
                    case "parts":
                        readParts(jsReader);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    //read datas in parts array
    private void readParts(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "part_name":
                        String part_name = jsReader.nextString();
                        System.out.println("part_name:" + part_name);
                        break;
                    case "means":
                        readMeans(jsReader);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    //read datas in means array
    private void readMeans(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                if (tagName.equals("word_mean")) {
                    String word_mean = jsReader.nextString();
                    System.out.println("word_mean:"+ word_mean);
                }else {
                    //跳过当前值
                    jsReader.skipValue();
                    System.out.println("skip======>");
                }
            }
            jsReader.endObject();
        }
        jsReader.endArray();
    }

    public class DailySentense{
        private String content;
        private String note;
        private String picture2Url;
        private Bitmap bitmap;

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public String getContent() {
            return content;
        }

        public String getNote() {
            return note;
        }

        public String getPicture2Url() {
            return picture2Url;
        }


    }

    public DailySentense readDailySentenseJson(String jsonData){
        DailySentense sentense = new DailySentense();
        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonReader jsReader = new JsonReader(new InputStreamReader(is));
        try {
            /*开始解析json为object对象*/
            jsReader.beginObject();
            while(jsReader.hasNext()){
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "content":
                        sentense.content = jsReader.nextString();
                        System.out.println("content:" + sentense.content);
                        break;
                    case "note":
                        sentense.note = jsReader.nextString();
                        System.out.println("note:" + sentense.note);
                        break;
                    case "picture2":
                        sentense.picture2Url = jsReader.nextString();
                        System.out.println("picture2Url:" + sentense.picture2Url);
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
            jsReader.close(); // 关闭数据流
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return sentense;
    }
}
