package com.example.jason.ichinese;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jason.ichinese.Common.CommonFunction;
import com.example.jason.ichinese.Translate.Jinshan.TransApiJinshan;
import com.example.jason.ichinese.Translate.TranslateHelper;

public class StartActivity extends AppCompatActivity {
    private TranslateHelper mTransHelper;
    private ImageView mStartWebImage;
    private Button mBtnSkip;
    private Bitmap WebImage;

    private Handler mHandler = new Handler();
    private TransApiJinshan.DailySentense mDailySentense;

    private boolean mInoMainActivity = false;

    //daily senetense callback
    private TranslateHelper.DailySentenseCallback mDayliSentenseCallback = new TranslateHelper.DailySentenseCallback() {
        @Override
        public void call(TransApiJinshan.DailySentense dailySentense) {
            mDailySentense = dailySentense;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
//                    mStartWebView.loadUrl(mDailySentense.getFenxiangImgUrl());
//                    mStartWebImage.setImageBitmap(mDailySentense.getBitmap());
                }
            });
        }
    };

    private CommonFunction.WebImageCallback mWebImageCallback = new CommonFunction.WebImageCallback() {
        @Override
        public void call(Bitmap bitmap) {
            WebImage = bitmap;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mStartWebImage.setImageBitmap(WebImage);
                }
            });
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //init view
        mStartWebImage = findViewById(R.id.startWebImage);
        mBtnSkip = findViewById(R.id.btnSkip);

        //初始化translate
        mTransHelper = new TranslateHelper();
        mTransHelper.setDailySentenseCallback(mDayliSentenseCallback);
        mTransHelper.getDailySentense(CommonFunction.getCurrentDate());

        CommonFunction.setWebImageCallbackCallback(mWebImageCallback);
        CommonFunction.getWebImage("http://39.96.51.230/test/merry1.jpeg");

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished/1000;
                String text = getResources().getString(R.string.skip) + "  " + String.valueOf(sec);
                mBtnSkip.setText(text);
            }

            @Override
            public void onFinish() {
                skipToMainActivity();

            }
        }.start();
    }

    public void btnSkipClicked(View view){
        skipToMainActivity();
    }

    private void skipToMainActivity(){
        if(!mInoMainActivity){
            mInoMainActivity = true;
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            try {
                finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}



/**
 * webview
 * */
//        WebView mStartWebView;
//        mStartWebView.addJavascriptInterface(new JsCallJavaObj() {
//            @JavascriptInterface
//            @Override
//            public void openUrl(String url){
//                Intent intent = new Intent();
//                intent.setData(Uri.parse("https://www.baidu.com"));//Url 就是你要打开的网址
//                intent.setAction(Intent.ACTION_VIEW);
//                startActivity(intent); //启动浏览器
//            }
//
//        },"jsCallJavaObj");
//
//        mStartWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                setWebImageClick(view);
//            }
//        });

    /**
     * 設置網頁中圖片的點擊事件
     * @param view
     */
//    private  void setWebImageClick(WebView view) {
//        String jsCode="javascript:(function(){" +
//                "var imgs=document.getElementsByTagName(\"img\");" +
//                "for(var i=0;i<imgs.length;i++){" +
//                "imgs[i].onclick=function(){" +
//                "window.jsCallJavaObj.openUrl(this.src);" +
//                "}}})()";
//        mStartWebView.loadUrl(jsCode);
//    }

/**
 * Js調用Java接口
 */
//private interface JsCallJavaObj{
//    void openUrl(String url);
//}

