package com.rhs.psw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.rhs.psw.styles.Continuous;
import com.rhs.psw.styles.Discrete;
import com.rhs.psw.styles.PSVStyle;

import static com.rhs.psw.R.styleable;


public class PasswordStrengthView extends View {
    public static final int EMPTY = 0;
    public static final int WEAK = 1;
    public static final int OK = 2;
    public static final int MEDIUM = 3;
    public static final int STRONG = 4;
    public static final int VERY_STRONG = 5;
    public static final int DISCRETE = 0;
    public static final int CONTINUOUS = 1;

    int disableAlpha = 100;
    private Calculator calculator;
    private int status;
    private boolean isManualMode;
    private PSVStyle psvStyle;

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
        TypedArray tArr = context.obtainStyledAttributes(attrs, styleable.PasswordStrengthView, 0, 0);


        status = tArr.getInt(styleable.PasswordStrengthView_status, EMPTY);
        isManualMode = tArr.getBoolean(styleable.PasswordStrengthView_manual_mode, false);

        String scores = tArr.getString(styleable.PasswordStrengthView_scores);
        calculator = new Calculator();
        if (scores == null || scores.split("-").length != 4) {
            calculator.initScores(10, 20, 30, 40);
        } else {
            String[] scoreArr = scores.split("-");
            calculator.initScores(Integer.parseInt(scoreArr[0]), Integer.parseInt(scoreArr[1]), Integer.parseInt(scoreArr[2]), Integer.parseInt(scoreArr[3]));
        }

        int type = tArr.getInt(styleable.PasswordStrengthView_type, DISCRETE);
        switch (type) {
            case CONTINUOUS: {
                psvStyle = new Continuous(this, tArr);
                break;
            }
            default: {
                psvStyle = new Discrete(this, tArr);
            }
        }

        taa.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int currentStatus = (isManualMode) ? status : calculator.getStatus();
        psvStyle.draw(canvas, currentStatus);
        super.onDraw(canvas);
    }

    public void update(String str) {
        calculator.calculate(str, Calculator.INCREMENTAL);
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
            width = Math.min(psvStyle.getDesiredWidth(), widthSize);
        } else {
            //Be whatever you want
            width = psvStyle.getDesiredWidth();
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(psvStyle.getDesiredHeight(), heightSize);
        } else {
            //Be whatever you want
            height = psvStyle.getDesiredHeight();
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    public void setDisableAlpha(int disableAlpha) {
        this.disableAlpha = disableAlpha;
    }


    public int getDisableAlpha() {
        return disableAlpha;
    }

    public int getStatus() {
        if (isManualMode) {
            return status;
        }
        return calculator.getStatus();
    }

    public void setManualStatus(int status) {
        this.status = status;
    }

    public void isManualMode(boolean manualMode) {
        isManualMode = manualMode;
    }

    public float getScore() {
        return calculator.getScore();
    }

}
