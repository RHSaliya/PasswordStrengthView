package com.rhs.psw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import static com.rhs.psw.Calculator.WEAK;
import static com.rhs.psw.Calculator.OK;
import static com.rhs.psw.Calculator.MEDIUM;
import static com.rhs.psw.Calculator.STRONG;
import static com.rhs.psw.Calculator.VERY_STRONG;


public class PasswordStrengthView extends View {
    private Paint paint;
    private int spaceBetween, indicatorHeight, indicatorWidth, indicatorRadius;
    private int veryStrongColor, strongColor, mediumColor, okColor, weakColor, emptyColor;
    int disableAlpha = 100;
    private Calculator calculator;
    private RectF rect;

    public PasswordStrengthView(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordStrengthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PasswordStrengthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int[] attrsArray = new int[]{
                android.R.attr.layout_width,    // 2
                android.R.attr.layout_height,   // 3
        };
        TypedArray taa = context.obtainStyledAttributes(attrs, attrsArray);


        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordStrengthView, 0, 0);

        veryStrongColor = typedArray.getColor(R.styleable.PasswordStrengthView_very_strong_color, 0xff00c754);
        strongColor = typedArray.getColor(R.styleable.PasswordStrengthView_strong_color, 0xff8BC34A);
        mediumColor = typedArray.getColor(R.styleable.PasswordStrengthView_medium_color, 0xffFFC107);
        okColor = typedArray.getColor(R.styleable.PasswordStrengthView_ok_color, 0xffFF9800);
        weakColor = typedArray.getColor(R.styleable.PasswordStrengthView_weak_color, 0xffFF5722);
        emptyColor = typedArray.getColor(R.styleable.PasswordStrengthView_empty_color, 0xffaaaaaa);

        indicatorHeight = (int) typedArray.getDimension(R.styleable.PasswordStrengthView_indicatorHeight, 20);
        indicatorWidth = (int) typedArray.getDimension(R.styleable.PasswordStrengthView_indicatorWidth, 20);
        indicatorRadius = (int) typedArray.getDimension(R.styleable.PasswordStrengthView_indicatorRadius, 20);
        spaceBetween = (int) typedArray.getDimension(R.styleable.PasswordStrengthView_space_between, 10);

        String scores = typedArray.getString(R.styleable.PasswordStrengthView_scores);
        calculator = new Calculator();
        if (scores == null || scores.split("-").length != 4) {
            calculator.initScores(10, 20, 30, 40);
        } else {
            String[] scoreArr = scores.split("-");
            calculator.initScores(Integer.parseInt(scoreArr[0]), Integer.parseInt(scoreArr[1]), Integer.parseInt(scoreArr[2]), Integer.parseInt(scoreArr[3]));
        }

        paint.setColor(emptyColor);
        rect = new RectF();
        taa.recycle();
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAlpha(255);

        int increment = indicatorWidth * 2 + spaceBetween;
        int x = getWidth() + getPaddingStart() - getPaddingEnd() - 4 * increment;
        x /= 2;
        int y = getHeight() + getPaddingTop() - getPaddingBottom();
        y /= 2;


        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (calculator.getStatus() == WEAK) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (calculator.getStatus() == OK) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (calculator.getStatus() == MEDIUM) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);

        if (calculator.getStatus() == STRONG) paint.setAlpha(disableAlpha);
        x += increment;
        rect.set(x - indicatorWidth, y - indicatorHeight, x + indicatorWidth, y + indicatorHeight);
        canvas.drawRoundRect(rect, indicatorRadius, indicatorRadius, paint);
//        canvas.drawCircle(x, y, size, paint);

        super.onDraw(canvas);
    }

    public void update(String str) {
        switch (calculator.calculate(str, Calculator.INCREMENTAL)) {
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
        invalidate();
    }

    public void attachEditText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                update(s.toString());
            }
        });
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getPaddingStart() + getPaddingEnd() + indicatorWidth * 10 + spaceBetween * 4;
        int desiredHeight = getPaddingTop() + getPaddingBottom() + indicatorHeight * 2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    public void setSpaceBetween(int spaceBetween) {
        this.spaceBetween = spaceBetween;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
    }

    public void setIndicatorRadius(int indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
    }

    public void setVeryStrongColor(int veryStrongColor) {
        this.veryStrongColor = veryStrongColor;
    }

    public void setStrongColor(int strongColor) {
        this.strongColor = strongColor;
    }

    public void setMediumColor(int mediumColor) {
        this.mediumColor = mediumColor;
    }

    public void setOkColor(int okColor) {
        this.okColor = okColor;
    }

    public void setWeakColor(int weakColor) {
        this.weakColor = weakColor;
    }

    public void setEmptyColor(int emptyColor) {
        this.emptyColor = emptyColor;
    }

    public void setDisableAlpha(int disableAlpha) {
        this.disableAlpha = disableAlpha;
    }

    public int getSpaceBetween() {
        return spaceBetween;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public int getIndicatorWidth() {
        return indicatorWidth;
    }

    public int getIndicatorRadius() {
        return indicatorRadius;
    }

    public int getVeryStrongColor() {
        return veryStrongColor;
    }

    public int getStrongColor() {
        return strongColor;
    }

    public int getMediumColor() {
        return mediumColor;
    }

    public int getOkColor() {
        return okColor;
    }

    public int getWeakColor() {
        return weakColor;
    }

    public int getEmptyColor() {
        return emptyColor;
    }

    public int getDisableAlpha() {
        return disableAlpha;
    }

    public int getStatus(){
        return calculator.getStatus();
    }

    public float getScore(){
        return calculator.getScore();
    }
}
