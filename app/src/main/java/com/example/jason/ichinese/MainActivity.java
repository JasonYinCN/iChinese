package com.example.jason.ichinese;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jason.ichinese.CustomCtrl.HorizontalScrollBarStrip;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.util.DisplayMetrics;
import android.app.Service;
import android.view.WindowManager;

import com.example.jason.ichinese.Translate.TranslateHelper;
import com.example.jason.ichinese.Translate.TranslateHelper.TranslateCallback;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HorizontalScrollBarStrip.TagChangeListener{
    private TranslateHelper mTransHelper;

    //View
    private EditText mUserInputText;
    private TextView mTextViewTransRes;
    private Spinner mSpinerLangFrom;
    private Spinner mSpinerLangTo;

    //scroll bar
    private HorizontalScrollBarStrip id_horizontal_view;
    //    private View id_line;
    private LinearLayout.LayoutParams lineLp;

    private List<String> mScrollBarTitiles = Arrays.asList("翻译", "生词本", "发现", "我的");
    private int mLineLocation_X = 0;

    //translate callback
    private TranslateCallback mTransCallback = new TranslateCallback() {
        @Override
        public void call(String translateRes) {
            System.out.println(translateRes);
            mTextViewTransRes.setText(translateRes);
        }
    };

    private List<String> mTitiles = Arrays.asList("en", "zh", "jp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化view
        initView();
        //初始化translate
        mTransHelper = new TranslateHelper();
        mTransHelper.setTranslateCallback(mTransCallback);
    }

    private void initView() {
        id_horizontal_view = findViewById(R.id.id_horizontal_view);
        initLineParams();
//        id_line = findViewById(R.id.id_line);
//        id_line.setLayoutParams(lineLp);
        id_horizontal_view.setTags(mScrollBarTitiles);
        id_horizontal_view.setOnTagChangeListener(this);

        mUserInputText = findViewById(R.id.userInput);
        mTextViewTransRes = findViewById(R.id.textViewTransRes);
        mSpinerLangFrom = findViewById(R.id.language_from);
        mSpinerLangTo = findViewById(R.id.language_to);
    }

    public void changeLine(int location_x, boolean isClick) {
        TranslateAnimation animation = new TranslateAnimation(mLineLocation_X, location_x, 0f, 0f);
        animation.setInterpolator(new LinearInterpolator());
        int duration = 0;
        if (isClick) {
            duration = 200 * (Math.abs(location_x - mLineLocation_X) / 100);
            duration = duration > 400 ? 400 : duration;
        } else {
            duration = 0;
        }
        animation.setDuration(duration);
        animation.setFillAfter(true);
//        id_line.startAnimation(animation);
        mLineLocation_X = location_x;
    }

    private void initLineParams() {
        lineLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lineLp.weight = 0f;
        WindowManager wm = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        lineLp.width = (int) (width / (id_horizontal_view.mDefaultShowTagCount + 0.7));
        lineLp.height = (int) (getResources().getDisplayMetrics().density * 1 + 0.5f);
    }

    public void buttonTranslateClicked(View v){
        String uersInputStr = mUserInputText.getText().toString();
        System.out.println("User input:" + uersInputStr);

        try {
            //get language parameters
            String strLangFrom, strLangTo;
            //src language
            int nLangFrom = (int)mSpinerLangFrom.getSelectedItemId();
            if(0 == nLangFrom){
                strLangFrom = "auto";
            }else{
                strLangFrom = mTitiles.get(nLangFrom - 1);
            }

            //target language
            strLangTo = mTitiles.get((int)mSpinerLangTo.getSelectedItemId());

            System.out.println("Language from:" + strLangFrom);
            System.out.println("Language to:" + strLangTo);

            //开始翻译
            mTransHelper.translate(uersInputStr, strLangFrom, strLangTo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
