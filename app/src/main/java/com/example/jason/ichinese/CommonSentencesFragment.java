package com.example.jason.ichinese;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonSentencesFragment extends Fragment {
    //Todo 见下
    /**
    * 1、服务器后台维护 一个json表，用来存储句子分类的地址
    * 2、语句分类内容：
     *      1、封面
     *      2、语句Json列表
    * 3、语句Json内容：语句中文，语句英文、发音url、场景图片url（后期增加）
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("translate activity on creat");
        View view;
        view = inflater.inflate(R.layout.fragment_commonsentences, null);

        return view;
    }
}
