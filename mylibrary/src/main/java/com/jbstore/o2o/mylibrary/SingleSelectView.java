package com.jbstore.o2o.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gwy on 2017/6/22.
 */

/**
 * Created by gwy on 2017/6/22.
 */

public class SingleSelectView<T extends SelectString> extends View {

    private int visibleCount;
    private int textSize;
    private int selectSize;
    private int textColor;
    private int selectColor;
    private Paint normalPaint;
    private Paint selectPaint;
    private Rect rect = new Rect();
    private int anInt;
    private int width;
    private int height;
    private boolean isFirst = true;
    private int n;
    private List<T> str = new ArrayList<>();
    private float downY;
    float offInt;
    int textWidth;
    Paint RectPaint;


    public SingleSelectView(Context context) {
        this(context, null);
    }

    public SingleSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        setClickable(true);
        initPaint();
    }

    public int getSelect() {
        return n;
    }


    private void initPaint() {
        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPaint.setColor(textColor);
        normalPaint.setTextSize(textSize);
        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setColor(selectColor);
        selectPaint.setTextSize(selectSize);
        RectPaint = new Paint();
        RectPaint.setColor(Color.GRAY);
        RectPaint.setStyle(Paint.Style.STROKE);
        RectPaint.setStrokeWidth(2);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.SelectView);
        visibleCount = typedArray.getInteger(R.styleable.SelectView_visibleCount, 5);
        if (visibleCount % 2 == 0) {
            visibleCount -= 1;
        }
        textSize = typedArray.getDimensionPixelSize(R.styleable.SelectView_textSize, 17);
        textColor = typedArray.getColor(R.styleable.SelectView_textColor, R.color.colorPrimaryDark);
        selectColor = typedArray.getColor(R.styleable.SelectView_selectColor, R.color.colorAccent);
        selectSize = typedArray.getDimensionPixelSize(R.styleable.SelectView_selectSize, 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            width = getWidth();
            height = getHeight();
            anInt = height / visibleCount;
            isFirst = false;
        }
        if (n >= 0 && n < str.size()) {
            String s = str.get(n).getString();
            selectPaint.getTextBounds(s, 0, s.length(), rect);
            int selWidth = rect.width();
            int selHeight = rect.height();
            canvas.drawText(s, width / 2 - selWidth / 2, height / 2 + selHeight / 2 + offInt, selectPaint);
            canvas.drawRect(10, height / 2 + anInt / 2, width - 10, height / 2 - anInt / 2, RectPaint);
//            canvas.drawRect(rect,RectPaint);
            for (int i = 0; i < str.size(); i++) {
                if (n > 0 && n < str.size() - 1) {
                    normalPaint.getTextBounds(str.get(n - 1).getString(), 0, str.get(n - 1).getString().length(), rect);
                    int mHeight = rect.height();
                    normalPaint.getTextBounds(str.get(n + 1).getString(), 0, str.get(n + 1).getString().length(), rect);
                    int mHeight1 = rect.height();
                    textWidth = (mHeight + mHeight1) / 2;

                }
                if (i != n) {
                    normalPaint.getTextBounds(str.get(i).getString(), 0, str.get(i).getString().length(), rect);
                    int selWidth1 = rect.width();
                    int selHeight1 = rect.height();
                    canvas.drawText(str.get(i).getString(), width / 2 - selWidth1 / 2, (height / 2 + (i - n) * anInt + textWidth / 2 + offInt), normalPaint);
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float scrollY = event.getY();
                if (n != 0 && n != str.size() - 1) {
                    offInt = scrollY - downY;
                } else {
                    offInt = (float) ((scrollY - downY) * 0.5);
                }
                if (scrollY > downY) {
                    if (scrollY - downY >= (anInt * 2) / 3) {
                        if (n > 0) {
                            n -= 1;
                            downY = scrollY;
                            Log.e("gao", "onTouchEvent: ");
                            offInt = 0;
                        }

                    }
                } else {
                    if (downY - scrollY >= (anInt * 2) / 3) {
                        if (n < str.size() - 1) {
                            n += 1;
                            Log.e("gao", "onTouchEvent1111: ");
                            downY = scrollY;
                            offInt = 0;
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                offInt = 0;
                invalidate();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setData(List<T> list) {
        str.addAll(list);
        n = str.size() / 2;
        invalidate();
    }

}


