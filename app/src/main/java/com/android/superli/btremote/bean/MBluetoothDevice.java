package com.android.superli.btremote.bean;

import android.bluetooth.BluetoothDevice;


public class MBluetoothDevice implements Comparable<MBluetoothDevice> {
    public int type;  //是否已经连接
    public BluetoothDevice mBluetoothDevice;

    @Override
    public int compareTo(MBluetoothDevice o) {
        return o.type - type;
    }
}
