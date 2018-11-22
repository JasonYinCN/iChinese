package com.example.jason.ichinese.Translate.Jinshan;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.Image;
import android.util.JsonReader;

import com.example.jason.ichinese.Translate.HttpGet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransApiJinshan {
    private String mUserUrl;
    private static String mBasicUrl;
    private static String mDailySentenseUrl;

    static {
        mBasicUrl = "http://dict-co.iciba.com/api/dictionary.php?w=*&type=json&key=#";
        mDailySentenseUrl = "http://open.iciba.com/dsapi/?date=*";
    }

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

    public class Part{
        private String partName;
        private List<String> meanList;


        public String getPartName() {
            return partName;
        }

        public List<String> getMeanList() {
            return meanList;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }

        public void setMeanList(List<String> meanList) {
            this.meanList = meanList;
        }
    }

    public class Symbol{
        private String wordSymbol;
        private String symbolMp3Url;

        private List<Part> partList;

        public String getWordSymbol() {
            return wordSymbol;
        }

        public void setWordSymbol(String wordSymbol) {
            this.wordSymbol = wordSymbol;
        }

        public String getSymbolMp3Url() {
            return symbolMp3Url;
        }

        public void setSymbolMp3Url(String symbolMp3Url) {
            this.symbolMp3Url = symbolMp3Url;
        }

        public List<Part> getPartList() {
            return partList;
        }

        public void setPartList(List<Part> partList) {
            this.partList = partList;
        }
    }

    public class WordInfo{
        private String wordName;
        private List<Symbol> symbolList;

        public String getWordName() {
            return wordName;
        }

        public void setWordName(String wordName) {
            this.wordName = wordName;
        }

        public List<Symbol> getSymbolList() {
            return symbolList;
        }

        public void setSymbolList(List<Symbol> symbolList) {
            this.symbolList = symbolList;
        }
    }

    public WordInfo analyzeJinshanTransJson(String jsonData){
        WordInfo wordInfo = new WordInfo();
        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonReader jsReader = new JsonReader(new InputStreamReader(is));
        try {
            /*开始解析json为object对象*/
            jsReader.beginObject();
            while(jsReader.hasNext()){
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "word_name":
                        wordInfo.setWordName(jsReader.nextString());
                        System.out.println("name:" + wordInfo.getWordName());
                        break;
                    case "symbols":
                        wordInfo.setSymbolList(readSymbol(jsReader));
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

        return wordInfo;
    }

    //read datas in symbols
    private List<Symbol> readSymbol(JsonReader jsReader) throws IOException {
        jsReader.beginArray();
        List<Symbol> symbolList = new ArrayList<>();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            Symbol symbol = new Symbol();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "word_symbol":
                        symbol.setWordSymbol(jsReader.nextString());
                        System.out.println("word_symbol:" + symbol.getWordSymbol());
                        break;
                    case "symbol_mp3":
                        symbol.setSymbolMp3Url(jsReader.nextString());
                        System.out.println("symbol_mp3:" + symbol.getSymbolMp3Url());
                        break;
                    case "parts":
                        symbol.setPartList(readParts(jsReader));
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            symbolList.add(symbol);
            jsReader.endObject();
        }
        jsReader.endArray();
        return symbolList;
    }

    //read datas in parts array
    private List<Part> readParts(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        List<Part> partList = new ArrayList<>();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            Part part = new Part();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                switch (tagName) {
                    case "part_name":
                        part.setPartName(jsReader.nextString());
                        System.out.println("part_name:" + part.getPartName());
                        break;
                    case "means":
                        part.setMeanList(readMeans(jsReader));
                        break;
                    default:
                        //跳过当前值
                        jsReader.skipValue();
                        System.out.println("skip======>");
                        break;
                }
            }
            jsReader.endObject();
            partList.add(part);
        }
        jsReader.endArray();

        return partList;
    }

    //read datas in means array
    private List<String> readMeans(JsonReader jsReader) throws IOException{
        jsReader.beginArray();
        List<String> meanList = new ArrayList<>();
        while (jsReader.hasNext()) {
            jsReader.beginObject();
            while (jsReader.hasNext()) {
                String tagName = jsReader.nextName();
                if (tagName.equals("word_mean")) {
                    String word_mean = jsReader.nextString();
                    meanList.add(word_mean);
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

        return meanList;
    }

    public class DailySentense{
        private String content;
        private String note;
        private String picture2Url;
        private Bitmap bitmap;
        private String fenxiangImgUrl;

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

        public String getFenxiangImgUrl() {
            return fenxiangImgUrl;
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
                    case "fenxiang_img":
                        sentense.fenxiangImgUrl = jsReader.nextString();
                        System.out.println("fenxiangImgUrl:" + sentense.fenxiangImgUrl);
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
