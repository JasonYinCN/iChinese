package com.example.jason.ichinese.Translate.Jinshan;

import android.util.JsonReader;

import com.example.jason.ichinese.Translate.HttpGet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TransApiJinshan {
    private String mUserUrl;
    public TransApiJinshan(String userKey){
        String mBasicUrl = "http://dict-co.iciba.com/api/dictionary.php?w=*&type=json&key=#";
        mUserUrl = mBasicUrl.replace("#", userKey);
        System.out.println(mUserUrl);
    }

    public void getTransResult(String input){
        String sendUrl = mUserUrl.replace("*", input);
        String res = HttpGet.get(sendUrl);
    }

    private void readJson(String jsonData){
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
    private static void readSymbol(JsonReader jsReader) throws IOException {
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
    private static void readParts(JsonReader jsReader) throws IOException{
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
    private static void readMeans(JsonReader jsReader) throws IOException{
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
}
