package com.example.jason.ichinese;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.jason.ichinese.CustomCtrl.ImageTextAdapter;

public class BookShelvesFragment extends Fragment {
    //Todo 见下
    /**
    * 1、服务器后台维护 一个json表，用来存储句子分类的地址
    * 2、语句分类内容：
     *      1、封面
     *      2、语句Json列表
    * 3、语句Json内容：语句中文，语句英文、发音url、场景图片url（后期增加）
    * */
    private GridView mGridView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("translate activity on creat");
        View view;
        view = inflater.inflate(R.layout.fragment_bookshelves, null);

        mContext = view.getContext();
        mGridView = view.findViewById(R.id.gridViewCommonSentence);
        mGridView.setAdapter(new ImageTextAdapter(mContext));

        return view;
    }
}
