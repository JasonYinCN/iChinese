package com.example.jason.ichinese;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.jason.ichinese.CustomCtrl.EditTextWithClearBtn;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;
import com.example.jason.ichinese.Translate.TranslateHelper;

import java.util.Objects;

public class TranslateActivity extends AppCompatActivity {

    private TextView mTextViewTransRes;
    private EditTextWithClearBtn mSearchView;
    private TranslateHelper mTransHelper;

    InputMethodManager manager;//输入法管理器
    private Handler mHandler = new Handler();

    //translate callback
    private TranslateHelper.TranslateCallback mTransCallback = new TranslateHelper.TranslateCallback() {
        @Override
        public void call(String translateRes) {
            System.out.println(translateRes);
            //analyze json
            TransApiJinshan.WordInfo wordInfo = mTransHelper.analyzeJinshanTransResult(translateRes);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //update translate result to ui
                    //Todo update translate result to ui
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        //init view
        mTextViewTransRes = findViewById(R.id.textViewTransRes);
        mSearchView = findViewById(R.id.userInputTrans);

        //初始化translate
        mTransHelper = new TranslateHelper();
        mTransHelper.setTranslateCallback(mTransCallback);

        manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        userInputEditClicked();
        search();
    }

    private void userInputEditClicked() {
        mSearchView.setTextEditClickedListener(new EditTextWithClearBtn.TextEditClickedListener() {
            @Override
            public void onTextEditClicked() {
                System.out.println("onTextEditClicked");
            }
        });
    }

    private void search() {
        mSearchView.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if(!Objects.requireNonNull(mSearchView.getText()).toString().isEmpty()){
                        //hide keyboard
                        if (manager.isActive()) {
                            manager.hideSoftInputFromWindow(mSearchView.getApplicationWindowToken(), 0);
                        }

                        //translate
                        mTransHelper.translate(mSearchView.getText().toString());
                    }
                }
                //记得返回false
                return false;
            }
        });
    }

    public void btnCancelClicked(View view) {
        Intent intent = new Intent(TranslateActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
