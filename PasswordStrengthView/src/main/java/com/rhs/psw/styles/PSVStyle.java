package com.rhs.psw.styles;

import android.graphics.Canvas;

import com.rhs.psw.PasswordStrengthView;

/**
 * Abstract class for the PasswordStrengthView library, you can create your own style by extending this class
 *
 * @author Rahul Saliya
 */
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

    /**
     * Draw the style on the canvas
     *
     * @param canvas the canvas
     * @param status the status of the password
     */
    abstract public void draw(Canvas canvas, int status);

    /**
     * Get the desired width of the style
     *
     * @return the desired width
     */
    abstract public int getDesiredWidth();

    /**
     * Get the desired height of the style
     *
     * @return the desired height
     */
    abstract public int getDesiredHeight();
}
