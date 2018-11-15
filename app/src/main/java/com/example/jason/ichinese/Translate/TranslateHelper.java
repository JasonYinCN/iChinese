package com.example.jason.ichinese.Translate;
import com.example.jason.ichinese.Translate.Baidu.TransApiBaidu;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;

import org.json.JSONObject;

public class TranslateHelper {

    private static final String APP_ID_BAIDU = "20181107000231104";
    private static final String SECURITY_KEY_BAIDU = "Z0U8MqOMuNLhaxLdRFNZ";
    private static final String SECURITY_KEY_JINSHAN = "826800DC7754063D928103455B282CD1";

    private TransApiBaidu mTransApiBaidu;
    private TransApiJinshan mTransApiJinshan;
    private String mInputText;
    private String mFromLanguage;
    private String mToLanguage;
    private String mTranslateRes;

    public TranslateHelper(){
        mTransApiBaidu = new TransApiBaidu(APP_ID_BAIDU, SECURITY_KEY_BAIDU);
        mTransApiJinshan = new TransApiJinshan(SECURITY_KEY_JINSHAN);
    }

    public static interface TranslateCallback{
        public void call(String translateRes);
    }

    private TranslateCallback mCallback;

    public void setTranslateCallback(TranslateCallback callback){
        mCallback = callback;
    }

    //在线程中翻译
    public void translate(String input, String form, String to ){
        mInputText = input;
        mFromLanguage = form;
        mToLanguage = to;

        new Thread(){
            public void run(){
                try {
                    mTranslateRes = mTransApiBaidu.getTransResult(mInputText, mFromLanguage, mToLanguage);
                    System.out.println(mTranslateRes);
                    JSONObject jsonObj = new JSONObject(mTranslateRes);
                    JSONObject transResult= jsonObj.getJSONArray("trans_result").getJSONObject(0);
                    String dst = transResult.getString("dst");
                    mCallback.call(dst);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();
    }

    public void translate(String input){
        mInputText = input;

        new Thread(){
            public void run(){
                try {
                    mTransApiJinshan.getTransResult(mInputText);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();
    }
}
