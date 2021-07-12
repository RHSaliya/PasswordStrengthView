package com.rhs.psw.styles;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.rhs.psw.PasswordStrengthView;
import com.rhs.psw.R;

public class Continuous extends Style {

    private final PasswordStrengthView psv;
    private final RectF rect = new RectF();
    private final TypedArray tArr;
    private int increment, indicatorWidth, indicatorHeight;
    private int veryStrongColor, strongColor, mediumColor, okColor, weakColor, emptyColor;
    private int width = -1, height, pStart, pEnd, pTop, pBottom;
    private Paint paint;
    private float indicatorRadius;
    private int disableAlpha;
    public boolean shouldRefresh;


    public Continuous(PasswordStrengthView passwordStrengthView, TypedArray tArr) {
        this.psv = passwordStrengthView;
        this.tArr = tArr;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        veryStrongColor = tArr.getColor(R.styleable.PasswordStrengthView_very_strong_color, 0xff00c754);
        strongColor = tArr.getColor(R.styleable.PasswordStrengthView_strong_color, 0xff8BC34A);
        mediumColor = tArr.getColor(R.styleable.PasswordStrengthView_medium_color, 0xffFFC107);
        okColor = tArr.getColor(R.styleable.PasswordStrengthView_ok_color, 0xffFF9800);
        weakColor = tArr.getColor(R.styleable.PasswordStrengthView_weak_color, 0xffFF5722);
        emptyColor = tArr.getColor(R.styleable.PasswordStrengthView_empty_color, 0xffaaaaaa);

        indicatorWidth = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorWidth, 20);
        indicatorHeight = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorHeight, 20);
        indicatorRadius = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorRadius, 20);

        tArr.recycle();
    }

    public void refresh() {
        shouldRefresh = false;
        width = psv.getWidth();
        height = psv.getHeight();
        pStart = psv.getPaddingStart();
        pEnd = psv.getPaddingEnd();
        pTop = psv.getPaddingTop();
        pBottom = psv.getPaddingBottom();
        disableAlpha = psv.getDisableAlpha();
        increment = width / 5;
    }

    @Override
    public void draw(Canvas canvas, int status) {
        if (width != psv.getWidth() || shouldRefresh) refresh();

        int y = height + pTop - pBottom;
        y /= 2;

        int right = width - pEnd;
        switch (status) {
            case VERY_STRONG:
                paint.setColor(veryStrongColor);
                break;
            case STRONG:
                right -= increment;
                paint.setColor(strongColor);
                break;
            case MEDIUM:
                right -= increment * 2;
                paint.setColor(mediumColor);
                break;
            case OK:
                right -= increment * 3;
                paint.setColor(okColor);
                break;
            case WEAK:
                right -= increment * 4;
                paint.setColor(weakColor);
                break;
            default:
                paint.setColor(emptyColor);
        }

        paint.setAlpha(disableAlpha);
        rect.set(pStart, y - indicatorHeight, width - pEnd, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);


        if (status != EMPTY) {
            paint.setAlpha(255);
            rect.set(pStart, y - indicatorHeight, right, y + indicatorHeight);
            canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);
        }
    }

    @Override
    public int getDesiredWidth() {
        return pStart + pEnd + indicatorWidth * 5;
    }

    @Override
    public int getDesiredHeight() {
        return pTop + pBottom + indicatorHeight * 2;
    }
}
