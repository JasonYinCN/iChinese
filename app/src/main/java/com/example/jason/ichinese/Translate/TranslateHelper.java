package com.example.jason.ichinese.Translate;
import com.example.jason.ichinese.Translate.Baidu.TransApi;

import org.json.JSONObject;

public class TranslateHelper {

    private static final String APP_ID = "20181107000231104";
    private static final String SECURITY_KEY = "Z0U8MqOMuNLhaxLdRFNZ";

    private TransApi mTransApi;
    private String mInputText;
    private String mFromLanguage;
    private String mToLanguage;
    private String mTranslateRes;

    public TranslateHelper(){
        mTransApi = new TransApi(APP_ID, SECURITY_KEY);
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
                    mTranslateRes = mTransApi.getTransResult(mInputText, mFromLanguage, mToLanguage);
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
}
