package com.android.superli.btremote.utils;

import android.app.Service;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.android.base.SharedPreferencesUtil;

/**
 * 震动
 */
public class VibrateUtil {
    private static Vibrator vibrator;

    /**
     * 震动milliseconds毫秒
     */

    public static void vibrate() {
        int vibrate = (int) SharedPreferencesUtil.getData("vibrate", 50);
        if (vibrate == -1) {
            return;
        }
        vibrate(Utils.getContext(), vibrate/5, vibrate);
    }

    private static void vibrate(Context context, long milliseconds, int vibrate) {
        if (null == vibrator) {
            vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        }
        boolean b = vibrator.hasVibrator();
        if (!b) {
            return;
        }
        VibrationEffect vibrationEffect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int defaultAmplitude = VibrationEffect.DEFAULT_AMPLITUDE;
            if (vibrator.hasAmplitudeControl()) {
                defaultAmplitude = vibrate;
            }
            vibrationEffect = VibrationEffect.createOneShot(milliseconds, defaultAmplitude);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(milliseconds);
        }
    }

}
