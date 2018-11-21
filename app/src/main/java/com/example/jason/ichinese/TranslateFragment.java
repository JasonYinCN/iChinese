package com.example.jason.ichinese;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.ichinese.CustomCtrl.EditTextWithClearBtn;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;
import com.example.jason.ichinese.Translate.TranslateHelper;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TranslateFragment extends Fragment implements EditTextWithClearBtn.TextEditClickedListener{

    //View
    private TextView mTextViewTransRes;
    private EditTextWithClearBtn mSearchView;
    private TranslateHelper mTransHelper;
    private ImageView mDailyImageView;
    private TextView mTextViewContent;
    private TextView mTextViewNote;

    private Handler mHandler = new Handler();
    private TransApiJinshan.DailySentense mDailySentense;

    InputMethodManager manager;//输入法管理器

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

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextViewContent.setText(mDailySentense.getContent());
                    mTextViewNote.setText(mDailySentense.getNote());                }
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
        mTextViewContent = view.findViewById(R.id.content);
        mTextViewNote = view.findViewById(R.id.note);

        //初始化translate
        mTransHelper = new TranslateHelper();
        mTransHelper.setTranslateCallback(mTransCallback);
        mTransHelper.setDailySentenseCallback(mDayliSentenseCallback);
        mTransHelper.getDailySentense("2018-05-03");

        MainActivity activity = (MainActivity) this.getActivity();
        manager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        search();

        return view;
    }

    private void search() {
        mSearchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //hide keyboard
                    if (manager.isActive()) {
                        manager.hideSoftInputFromWindow(mSearchView.getApplicationWindowToken(), 0);
                    }
                    //translate
                    mTransHelper.translate(mSearchView.getText().toString());


                    System.out.println("XXXXXXXXXXXXXX");
                }
                //记得返回false
                return false;
            }
        });
    }

    public void onTextEditClicked(){
        System.out.println("text edit has been clicked");
    }
}


