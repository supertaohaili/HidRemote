package com.android.superli.btremote;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothListenerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.e("BluetoothListener", "onReceive---------蓝牙正在打开中");
                        if (mBluetoothStateListener!=null){
                            mBluetoothStateListener.stateTurningOn();
                        }
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.e("BluetoothListener", "onReceive---------蓝牙已经打开");
                        if (mBluetoothStateListener!=null){
                            mBluetoothStateListener.stateOn();
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.e("BluetoothListener", "onReceive---------蓝牙正在关闭中");
                        if (mBluetoothStateListener!=null){
                            mBluetoothStateListener.stateTurningOff();
                        }
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        Log.e("BluetoothListener", "onReceive---------蓝牙已经关闭");
                        if (mBluetoothStateListener!=null){
                            mBluetoothStateListener.stateOff();
                        }
                        break;
                }
                break;
        }
    }

    private BluetoothStateListener mBluetoothStateListener;

    public void setmBluetoothStateListener(BluetoothStateListener mBluetoothStateListener) {
        this.mBluetoothStateListener = mBluetoothStateListener;
    }

    public interface BluetoothStateListener{
        void stateTurningOn();
        void stateOn();
        void stateTurningOff();
        void stateOff();
    }
}
