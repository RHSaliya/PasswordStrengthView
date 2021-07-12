package com.rhs.psw.styles;

import android.graphics.Canvas;

public abstract class Style{
    public static final int EMPTY = 0;
    public static final int WEAK = 1;
    public static final int OK = 2;
    public static final int MEDIUM = 3;
    public static final int STRONG = 4;
    public static final int VERY_STRONG = 5;

    abstract public void draw(Canvas canvas, int status);

    abstract public int getDesiredWidth();
    abstract public int getDesiredHeight();
}
