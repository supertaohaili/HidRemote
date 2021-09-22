package com.android.base.hid.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;


public class BleUtils {


    public static boolean isBleSupported(@NonNull final Context context) {
        try {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == false) {
                return false;
            }

            final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

            final BluetoothAdapter bluetoothAdapter;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bluetoothAdapter = bluetoothManager.getAdapter();
            } else {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }

            if (bluetoothAdapter != null) {
                return true;
            }
        } catch (final Throwable ignored) {
            // ignore exception
        }
        return false;
    }


    @SuppressLint("NewApi")
    public static boolean isBlePeripheralSupported(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        final BluetoothAdapter bluetoothAdapter = ((BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();

        if (bluetoothAdapter == null) {
            return false;
        }

        return bluetoothAdapter.isMultipleAdvertisementSupported();
    }


    public static boolean isBluetoothEnabled(@NonNull final Context context) {
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        if (bluetoothManager == null) {
            return false;
        }

        final BluetoothAdapter bluetoothAdapter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (bluetoothAdapter == null) {
            return false;
        }

        return bluetoothAdapter.isEnabled();
    }

    public static final int REQUEST_CODE_BLUETOOTH_ENABLE = 0xb1e;

    public static void enableBluetooth(@NonNull final Activity activity) {
        activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_CODE_BLUETOOTH_ENABLE);
    }
}
