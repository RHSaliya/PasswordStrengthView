package com.rhs.psw.styles;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.rhs.psw.PasswordStrengthView;
import com.rhs.psw.R;

/**
 * Continuous style for the PasswordStrengthView library, this style will show the strength of the password in a continuous line
 *
 * @author Rahul Saliya
 */
public class Continuous extends PSVStyle {

    private final RectF rect = new RectF();
    private final TypedArray tArr;
    private int increment, indicatorWidth, indicatorHeight;
    private int veryStrongColor, strongColor, mediumColor, okColor, weakColor;
    private int width = -1, height, pStart, pEnd, pTop, pBottom;
    private int currentColor;
    private Paint paint;
    private float indicatorRadius;
    private int disableAlpha;
    public boolean shouldRefresh;

    /**
     * Constructor for the Continuous style
     *
     * @param passwordStrengthView the PasswordStrengthView
     * @param tArr                 the TypedArray
     */
    public Continuous(PasswordStrengthView passwordStrengthView, TypedArray tArr) {
        this.psv = passwordStrengthView;
        this.tArr = tArr;
        init();
    }

    /**
     * Initialize the style
     */
    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        veryStrongColor = tArr.getColor(R.styleable.PasswordStrengthView_very_strong_color, 0xff00c754);
        strongColor = tArr.getColor(R.styleable.PasswordStrengthView_strong_color, 0xff8BC34A);
        mediumColor = tArr.getColor(R.styleable.PasswordStrengthView_medium_color, 0xffFFC107);
        okColor = tArr.getColor(R.styleable.PasswordStrengthView_ok_color, 0xffFF9800);
        weakColor = tArr.getColor(R.styleable.PasswordStrengthView_weak_color, 0xffFF5722);
        backgroundColor = tArr.getColor(R.styleable.PasswordStrengthView_background_color, 0xffaaaaaa);

        indicatorWidth = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorWidth, 20);
        indicatorHeight = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorHeight, 20);
        indicatorRadius = (int) tArr.getDimension(R.styleable.PasswordStrengthView_indicatorRadius, 20);

        fixBackgroundColor = tArr.getBoolean(R.styleable.PasswordStrengthView_fixBackgroundColor, false);

        tArr.recycle();
    }

    /**
     * Refresh the style after the view is changed
     */
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

        int y = (height + pTop - pBottom)/2;

        int right = width - pEnd;
        switch (status) {
            case VERY_STRONG:
                currentColor = veryStrongColor;
                break;
            case STRONG:
                right -= increment;
                currentColor = strongColor;
                break;
            case MEDIUM:
                right -= increment * 2;
                currentColor = mediumColor;
                break;
            case OK:
                right -= increment * 3;
                currentColor = okColor;
                break;
            case WEAK:
                right -= increment * 4;
                currentColor = weakColor;
                break;
            default:
                currentColor = backgroundColor;
        }

        if (fixBackgroundColor) {
            paint.setColor(backgroundColor);
        } else {
            paint.setColor(currentColor);
            paint.setAlpha(disableAlpha);
        }
        rect.set(pStart, y - indicatorHeight, width - pEnd, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (status != EMPTY) paint.setAlpha(255);

        if (fixBackgroundColor) paint.setColor(currentColor);


        rect.set(pStart, y - indicatorHeight, right, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);
    }

    @Override
    public int getDesiredWidth() {
        return pStart + pEnd + indicatorWidth * 5;
    }

    @Override
    public int getDesiredHeight() {
        return pTop + pBottom + indicatorHeight * 2;
    }

    /**
     * Get the current color of the style
     *
     * @return the current color
     */
    public int getCurrentColor() {
        return currentColor;
    }
}
