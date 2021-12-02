package com.rhs.psw.styles;

import android.graphics.Canvas;

import com.rhs.psw.PasswordStrengthView;

public abstract class PSVStyle {
    public static final int EMPTY = 0;
    public static final int WEAK = 1;
    public static final int OK = 2;
    public static final int MEDIUM = 3;
    public static final int STRONG = 4;
    public static final int VERY_STRONG = 5;

    boolean fixBackgroundColor;
    int backgroundColor;
    PasswordStrengthView psv;

    abstract public void draw(Canvas canvas, int status);

    abstract public int getDesiredWidth();
    abstract public int getDesiredHeight();
}
