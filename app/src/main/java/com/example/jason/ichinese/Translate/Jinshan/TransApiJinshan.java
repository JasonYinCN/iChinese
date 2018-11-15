package com.example.jason.ichinese.Translate.Jinshan;

import com.example.jason.ichinese.Translate.HttpGet;

public class TransApiJinshan {
    private String mUserUrl;
    public TransApiJinshan(String userKey){
        String mBasicUrl = "http://dict-co.iciba.com/api/dictionary.php?w=*&type=json&key=#";
        mUserUrl = mBasicUrl.replace("#", userKey);
        System.out.println(mUserUrl);
    }

    public void getTransResult(String input){
        String sendUrl = mUserUrl.replace("*", input);
        HttpGet.get(sendUrl);
    }
}
