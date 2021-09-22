package com.android.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {
    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean showSoftInput(Activity activity) {
        boolean isShowSoftInput = false;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        if (imm != null) {
            isShowSoftInput =   imm.showSoftInput(view, 2);
        }
        return isShowSoftInput;
    }

    public static void showSoftInput(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInput(view, 2);
        }
    }

    public static boolean hideSoftInput(Activity activity) {
        boolean isShowSoftInput = false;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        if (imm != null) {
            isShowSoftInput =  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return isShowSoftInput;
    }

    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
