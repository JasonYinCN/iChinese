package com.example.jason.ichinese;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jason.ichinese.Common.CommonFunction;
import com.example.jason.ichinese.CustomCtrl.EditTextWithClearBtn;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;
import com.example.jason.ichinese.Translate.TranslateHelper;

public class TranslateFragment extends Fragment{

    private TranslateHelper mTransHelper;
    //View
    private EditTextWithClearBtn mSearchView;
    private ImageView mDailyImageView;

    private Handler mHandler = new Handler();
    private TransApiJinshan.DailySentense mDailySentense;

    //daily senetense callback
    private TranslateHelper.DailySentenseCallback mDayliSentenseCallback = new TranslateHelper.DailySentenseCallback() {
        @Override
        public void call(TransApiJinshan.DailySentense dailySentense) {
            mDailySentense = dailySentense;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = mDailySentense.getBitmap();
                    int cropHeight = bitmap.getHeight() - 400;
                    Bitmap cropBitmap =  Bitmap.createBitmap(bitmap, 0, 110, bitmap.getWidth(), cropHeight, null, false);
                    mDailyImageView.setImageBitmap(cropBitmap);
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
        mSearchView = view.findViewById(R.id.userInput);
        mDailyImageView = view.findViewById(R.id.dailyImageView);

        //daily sentense
        mTransHelper = new TranslateHelper();
        mTransHelper.setDailySentenseCallback(mDayliSentenseCallback);
        mTransHelper.getDailySentense(CommonFunction.getCurrentDate());

        //edit text clicked callback
        userInputEditClicked();

        return view;
    }

    private void userInputEditClicked() {
        mSearchView.setTextEditClickedListener(new EditTextWithClearBtn.TextEditClickedListener() {
            @Override
            public void onTextEditClicked() {
                Intent intent = new Intent(getActivity(), TranslateActivity.class);
                startActivity(intent);
            }
        });
    }
}


