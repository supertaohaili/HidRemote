package com.android.superli.btremote.hid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.text.TextUtils;

import com.android.superli.btremote.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

public class HidUitls {
    public static String SelectedDeviceMac = "";
    public static boolean _connected = false;
    public static boolean IsRegisted = false;

    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothProfile bluetoothProfile;
    public static BluetoothDevice BtDevice;
    public static BluetoothHidDevice HidDevice;

    public static void RegistApp(Context context) {
        try {
            if (IsRegisted) {

            } else {
                BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, mProfileServiceListener, BluetoothProfile.HID_DEVICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort("当前系统不支持蓝牙遥控!");
        }
    }

    public static boolean Pair(String deviceAddress) {
        if (BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
            try {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (BtDevice == null) {
                    BtDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
                }
                if (BtDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    BtDevice.createBond();
                    return false;
                } else if (BtDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    return true;
                } else if (BtDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static boolean IsConnected() {
        try {
            return HidUitls._connected;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static void IsConnected(boolean _connected) {
        HidUitls._connected = _connected;
    }

    public static boolean connect(String deviceAddress) {
        if (TextUtils.isEmpty(deviceAddress)) {
            ToastUtils.showShort("获取mac地址失败");
            return false;
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtils.showShort("当前设备不支持蓝牙HID");
            return false;
        }
        if (BtDevice == null) {
            BtDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
        }
        boolean ret = HidDevice.connect(BtDevice);
        HidConsts.BtDevice = BtDevice;
        HidConsts.HidDevice = HidDevice;
        return ret;
    }

    public static boolean connect(BluetoothDevice device) {
        boolean ret = HidDevice.connect(device);
        HidConsts.BtDevice = device;
        HidConsts.HidDevice = HidDevice;
        return ret;
    }

    public static void reConnect( Activity context) {
        if (TextUtils.isEmpty(SelectedDeviceMac)) {
            return;
        }
        try {
            if (HidUitls.HidDevice != null) {
                if (HidUitls.BtDevice == null) {
                    HidUitls.BtDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(HidUitls.SelectedDeviceMac);
                }
                int state = HidUitls.HidDevice.getConnectionState(HidUitls.BtDevice);
                if (state == BluetoothProfile.STATE_DISCONNECTED) {
                    if (TextUtils.isEmpty(HidUitls.SelectedDeviceMac)) {
                    } else {
                        if (HidUitls.Pair(HidUitls.SelectedDeviceMac)) {
                            HidUitls.RegistApp(context.getApplicationContext());
                            UtilCls.DelayTask(new Runnable() {
                                @Override
                                public void run() {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HidUitls.connect(HidUitls.SelectedDeviceMac);
                                        }
                                    });
                                }
                            }, 500, true);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public static BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {
        }

        @SuppressLint("NewApi")
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            bluetoothProfile = proxy;
            if (profile == BluetoothProfile.HID_DEVICE) {
                HidDevice = (BluetoothHidDevice) proxy;
                HidConsts.HidDevice = HidDevice;
                BluetoothHidDeviceAppSdpSettings sdp = new BluetoothHidDeviceAppSdpSettings(HidConsts.NAME, HidConsts.DESCRIPTION, HidConsts.PROVIDER, BluetoothHidDevice.SUBCLASS1_COMBO, HidConsts.Descriptor);
                HidDevice.registerApp(sdp, null, null, Executors.newCachedThreadPool(), mCallback);
            }
        }
    };

    public static final BluetoothHidDevice.Callback mCallback = new BluetoothHidDevice.Callback() {
        @Override
        public void onAppStatusChanged(BluetoothDevice pluggedDevice, boolean registered) {
            IsRegisted = registered;
        }

        @Override
        public void onConnectionStateChanged(BluetoothDevice device, int state) {
            if (state == BluetoothProfile.STATE_DISCONNECTED) {
                HidUitls.IsConnected(false);
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onDisConnected));
            } else if (state == BluetoothProfile.STATE_CONNECTED) {
                HidUitls.IsConnected(true);
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onConnected));
            } else if (state == BluetoothProfile.STATE_CONNECTING) {
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onConnecting));
            }
        }
    };
}
