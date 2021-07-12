package com.rhs.psw.styles;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.rhs.psw.PasswordStrengthView;
import com.rhs.psw.R;

public class Discrete extends Style {

    private final PasswordStrengthView psv;
    private final RectF rect = new RectF();
    private int increment, indicatorWidth, indicatorHeight;
    private int width = -1, height, pStart, pEnd, pTop, pBottom;
    private Paint paint;
    private float indicatorRadius;
    private int disableAlpha;
    private final TypedArray tArr;
    private int veryStrongColor, strongColor, mediumColor, okColor, weakColor, emptyColor;
    private int spaceBetween;
    public boolean shouldRefresh;
    private boolean matchWidth, matchHeight;

    public Discrete(PasswordStrengthView psv, TypedArray tArr) {
        this.psv = psv;
        this.tArr = tArr;
        init();
        refresh();
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

        indicatorHeight = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorHeight, -1);
        indicatorWidth = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorWidth, -1);
        indicatorRadius = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorRadius, 20);
        spaceBetween = (int) tArr.getDimension(R.styleable.PasswordStrengthView_space_between, 10);

        matchWidth = indicatorWidth == -1;
        matchHeight = indicatorHeight == -1;

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

        if (matchWidth) {
            if (width != 0) {
                indicatorWidth = width - pStart - pEnd - spaceBetween*5;
                indicatorWidth = indicatorWidth / 10;
            } else {
                indicatorWidth = 20;
            }
        }
        increment = indicatorWidth * 2 + spaceBetween;

        if (matchHeight) {
            if (height != 0) {
                indicatorHeight = height - pTop - pBottom;
                indicatorHeight = indicatorHeight/2;
            } else {
                indicatorHeight = 20;
            }
        }
    }

    @Override
    public void draw(Canvas canvas, int status) {
        paint.setAlpha(255);
        if (width == 0 || shouldRefresh) refresh();

        switch (status) {
            case VERY_STRONG:
                paint.setColor(veryStrongColor);
                break;
            case STRONG:
                paint.setColor(strongColor);
                break;
            case MEDIUM:
                paint.setColor(mediumColor);
                break;
            case OK:
                paint.setColor(okColor);
                break;
            case WEAK:
                paint.setColor(weakColor);
                break;
            default:
                paint.setColor(emptyColor);
        }

        int x = width + pStart - pEnd - 4 * increment;
        x /= 2;
        int y = height + pTop - pBottom;
        y /= 2;


        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (status == WEAK) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (status == OK) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (status == MEDIUM) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (status == STRONG) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);
    }

    @Override
    public int getDesiredWidth() {
        return pStart + pEnd + indicatorWidth * 10 + spaceBetween * 4;
    }

    @Override
    public int getDesiredHeight() {
        return pTop + pBottom + indicatorHeight * 2;
    }

}
