package com.rhs.psw;

import static com.rhs.psw.PasswordStrengthView.EMPTY;
import static com.rhs.psw.PasswordStrengthView.MEDIUM;
import static com.rhs.psw.PasswordStrengthView.OK;
import static com.rhs.psw.PasswordStrengthView.STRONG;
import static com.rhs.psw.PasswordStrengthView.VERY_STRONG;
import static com.rhs.psw.PasswordStrengthView.WEAK;

import android.util.Log;

import java.util.HashMap;

/**
 * This class is used to calculate the strength of the password
 *
 * @author Rahul Saliya
 */
public class Calculator {
    public static final int INCREMENTAL = 10;
    public static final int FRACTIONAL = 11;

    protected static float score;
    private int status = EMPTY;
    private float reducer = 0.5f;

    private static final String commonDigits =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private int veryStrongScore, strongScore, mediumScore, okScore;

    /**
     * Initialize the scores for the password strength
     *
     * @param okScore         value for OK status
     * @param mediumScore     value for MEDIUM status
     * @param strongScore     value for STRONG status
     * @param veryStrongScore value for VERY_STRONG status
     */
    public void initScores(int okScore, int mediumScore, int strongScore, int veryStrongScore) {
        this.veryStrongScore = veryStrongScore;
        this.strongScore = strongScore;
        this.mediumScore = mediumScore;
        this.okScore = okScore;
    }

    /**
     * Calculate the strength of the password
     *
     * @param str  the password
     * @param type the type of the calculator
     * @return the score of the password
     */
    public int calculate(String str, int type) {
        score = 0;
        switch (type) {
            case INCREMENTAL:
                return incremental(str);
            case FRACTIONAL:
                return fractional(str);
        }
        return 0;
    }

    /**
     * Set the reducer for the calculator, the reducer is used to reduce the score of the repeated characters in the password string<br><br>
     * <b>For example,</b> if the reducer is 0.5, then the score of the repeated characters will be reduced by 0.5*total occurrences if the calculate method is fractional
     *
     * @param reducer the reducer
     */
    public void setReducer(float reducer) {
        this.reducer = reducer;
    }

    /**
     * This function will calculate password strength using fractional method
     *
     * @param str the password
     * @return the score of the password
     */
    private int fractional(String str) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        int length = str.length();
        score += length;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int occurrence;
            if (hashMap.containsKey(c)) {
                occurrence = hashMap.get(c);
            } else {
                occurrence = 0;
                hashMap.put(c, 1);
            }
            if (commonDigits.contains(String.valueOf(c))) {
                score += Math.max(2 - occurrence * reducer, 0);
            } else {
                score += Math.max(3 - occurrence * reducer, 0);
            }
        }
        Log.e("PSVScore", String.valueOf(score));
        return generateStatus();
    }


    /**
     * This function will calculate password strength using incremental method
     *
     * @param str the password
     * @return the score of the password
     */
    private int incremental(String str) {
        int length = str.length();
        score += length;

        for (int i = 0; i < length; i++) {
            boolean b = str.indexOf(str.charAt(i), i) != -1;
            if (commonDigits.contains(String.valueOf(str.charAt(i)))) {
                score += b ? 1 : 2;
            } else {
                score += b ? 2 : 3;
            }
        }

        return generateStatus();
    }


    /**
     * This function will generate the status of the password based on the score
     *
     * @return the status of the password
     */
    public int generateStatus() {
        if (score >= veryStrongScore) {
            status = VERY_STRONG;
        } else if (score >= strongScore) {
            status = STRONG;
        } else if (score >= mediumScore) {
            status = MEDIUM;
        } else if (score >= okScore) {
            status = OK;
        } else if (score >= 1) {
            status = WEAK;
        } else {
            status = EMPTY;
        }
        return status;
    }

    /**
     * Get the status of the password
     *
     * @return the status of the password
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get the score of the password
     *
     * @return the score of the password
     */
    public float getScore() {
        return score;
    }
}
