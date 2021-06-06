package com.rhs.psw;

import android.util.Log;

import java.util.HashMap;

public class Calculator {
    public static final int INCREMENTAL = 10;
    public static final int FRACTIONAL = 11;

    public static final int VERY_STRONG = 1;
    public static final int STRONG = 2;
    public static final int MEDIUM = 3;
    public static final int OK = 4;
    public static final int WEAK = 5;
    public static final int EMPTY = 0;
    protected static float score;
    private int status = EMPTY;
    private float reducer = 0.5f;

    private static final String commonDigits =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private int veryStrongScore, strongScore, mediumScore, okScore;

    public void initScores(int okScore, int mediumScore, int strongScore, int veryStrongScore){
        this.veryStrongScore = veryStrongScore;
        this.strongScore = strongScore;
        this.mediumScore = mediumScore;
        this.okScore = okScore;
    }

    public int calculate(String str, int type){
        score=0;
        switch (type){
            case INCREMENTAL:
                return incremental(str);
            case FRACTIONAL:
                return fractional(str);
        }
        return 0;
    }

    public void setReducer(float reducer) {
        this.reducer = reducer;
    }

    private int fractional(String str) {
        HashMap<Character,Integer> hashMap = new HashMap<>();
        int length = str.length();
        score += length;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int occurrence;
            if (hashMap.containsKey(c)){
                occurrence = hashMap.get(c);
            }else{
                occurrence = 0;
                hashMap.put(c,1);
            }
            if (commonDigits.contains(String.valueOf(c))) {
                score += 2 - occurrence*reducer;
            } else {
                score += 3 - occurrence*reducer;
            }
        }
        Log.e("PSVScore",String.valueOf(score));
        return generateStatus();
    }


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

    public int getStatus() {
        return status;
    }

    public float getScore() {
        return score;
    }
}
