package com.example.jason.ichinese.CustomCtrl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import com.example.jason.ichinese.R;

/**
 * 自定义编辑框
 *
 * @author Jason Yin
 */
@SuppressLint("HandlerLeak")
public class MultiLineEditText extends AppCompatMultiAutoCompleteTextView{
    private Paint mPaint;
    private int mLineColor;

    public MultiLineEditText(Context context) {
        super(context);
    }

    public MultiLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }



    public MultiLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs){
        TypedArray attrArrays = context.obtainStyledAttributes(attrs, R.styleable.MultiLineEditText);

        mPaint = new Paint();
        int lenght = attrArrays.getIndexCount();
        for(int i = 0 ; i < lenght; i ++){
            int index = attrArrays.getIndex(i);
            switch (index){
                case R.styleable.MultiLineEditText_lineColorEt:
                    mLineColor = attrArrays.getColor(index,0xFFF);
                break;
            }
        }

        attrArrays.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);

        canvas.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1, mPaint);
    }
}
