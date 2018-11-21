package com.example.jason.ichinese;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.ichinese.CustomCtrl.EditTextWithClearBtn;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;
import com.example.jason.ichinese.Translate.TranslateHelper;

public class TranslateFragment extends Fragment implements EditTextWithClearBtn.TextEditClickedListener{

    //View
    private TextView mTextViewTransRes;
    private EditTextWithClearBtn mSearchView;
    private TranslateHelper mTransHelper;
    private ImageView mDailyImageView;
    private TransApiJinshan.DailySentense mDailySentense;

    //translate callback
    private TranslateHelper.TranslateCallback mTransCallback = new TranslateHelper.TranslateCallback() {
        @Override
        public void call(String translateRes) {
            System.out.println(translateRes);
            mTextViewTransRes.setText(translateRes);
        }
    };

    //daily senetense callback
    private TranslateHelper.DailySentenseCallback mDayliSentenseCallback = new TranslateHelper.DailySentenseCallback() {
        @Override
        public void call(TransApiJinshan.DailySentense dailySentense) {
            System.out.println("mDayliSentenseCallback");
            mDailySentense = dailySentense;
            mDailyImageView.post(new Runnable() {
                @Override
                public void run() {
                    mDailyImageView.setImageBitmap(mDailySentense.getBitmap());
                }
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("translate activity on creat");
        View view;
        view = inflater.inflate(R.layout.fragment_translate, null);

        //init view
        mTextViewTransRes = view.findViewById(R.id.textViewTransRes);
        mSearchView = view.findViewById(R.id.userInput);
        mSearchView.setTextEditClickedListener(this);
        mDailyImageView = view.findViewById(R.id.dailyImageView);

        //初始化translate
        mTransHelper = new TranslateHelper();
        mTransHelper.setTranslateCallback(mTransCallback);
        mTransHelper.setDailySentenseCallback(mDayliSentenseCallback);
        mTransHelper.getDailySentense("2018-05-03");

        return view;
    }

    public void onTextEditClicked(){
        System.out.println("text edit has been clicked");
    }
}


