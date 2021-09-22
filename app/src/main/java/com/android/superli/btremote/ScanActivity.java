package com.android.superli.btremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.base.router.Router;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.bean.MBluetoothDevice;
import com.android.superli.btremote.hid.HidEvent;
import com.android.superli.btremote.hid.HidUitls;
import com.android.superli.btremote.ui.activity.MainActivity;
import com.android.superli.btremote.ui.adapter.BleDeviceAdpter;
import com.android.superli.btremote.ui.views.dialog.AlertDialog;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScanActivity extends XActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;

    private RecyclerView mRecyclerview;
    private BleDeviceAdpter mBleDeviceAdpter;
    private List<MBluetoothDevice> datas;
    private LoadingDialog mLoadingDialog;

    //循环开启扫描功能
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startDiscovery();
        }
    };

    //显示无法连接
    private Handler handler2 = new Handler();
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
            showDisConnectDialog();
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public int getActivityTitle() {
        return R.string.scan_device;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);

        ImmersionBar.with(this).titleBar(R.id.llt_title)
                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        mLoadingDialog = new LoadingDialog(this);

        datas = new ArrayList<>();
        mRecyclerview = findViewById(R.id.recyclerview);
        mBleDeviceAdpter = new BleDeviceAdpter(this);
        mBleDeviceAdpter.setmOnClickListener(new BleDeviceAdpter.OnClickListener() {
            @Override
            public void onClick(MBluetoothDevice item) {

                if (mLoadingDialog != null) {
                    mLoadingDialog.show();
                }

                mBluetoothDevice = item.mBluetoothDevice;
                connect();
            }
        });
        mRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mBleDeviceAdpter.setData(datas);
        mRecyclerview.setAdapter(mBleDeviceAdpter);
    }

    private void connect() {
        if (mBluetoothDevice == null) {
            return;
        }

        handler2.removeCallbacks(runnable2);
        handler2.postDelayed(runnable2, 20000);

        String deviceAddress = mBluetoothDevice.getAddress();
        if (TextUtils.isEmpty(deviceAddress)) {
            boolean pair = HidUitls.Pair(deviceAddress);
            if (pair) {
                HidUitls.connect(mBluetoothDevice);
            }
            return;
        }

        HidUitls.SelectedDeviceMac = deviceAddress;
        boolean pair = HidUitls.Pair(deviceAddress);
        if (pair) {
            HidUitls.connect(deviceAddress);
        }
    }

    @Override
    public void initData() {
        int initDialog = (int) SharedPreferencesUtil.getData("initDialog", 0);
        if (initDialog == 0) {
            new AlertDialog(this).init()
                    .setTitle("声明")
                    .setCancelable(true)
                    .setMsg("由于本软件的蓝牙遥控功能是基于HID协议实现，但是部分手机厂家移除了hid模块，" +
                            "所以导致了不兼容情况的出现。如果你的" +
                            "手机出现了闪退等问题，大概率是该机型不被支持。\n\n" +
                            "更多好玩软件：www.wnkong.com")
                    .setPositiveButton("了解，不再提示！", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferencesUtil.putData("initDialog", 1);
                        }
                    }).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        registerReceiver(mReceiver, intentFilter);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        datas.clear();
        mBleDeviceAdpter.notifyDataSetChanged();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        List<BluetoothDevice> bluetoothDevices = Arrays.asList(pairedDevices.toArray(new BluetoothDevice[0]));
        for (BluetoothDevice device : bluetoothDevices) {
            MBluetoothDevice bean = new MBluetoothDevice();
            bean.mBluetoothDevice = device;
            bean.type = 1;
            addDevice(bean);
        }

        startDiscovery();
    }

    @Override
    protected void onStop() {
        super.onStop();


        unregisterReceiver(mReceiver);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        cancelDiscovery();

        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }

        if (alertDialog2 != null) {
            alertDialog2.dismiss();
        }

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (handler2 != null && runnable2 != null) {
            handler2.removeCallbacks(runnable2);
        }
    }


    private void startDiscovery() {
        handler.removeCallbacks(runnable);
        if (mBluetoothAdapter == null) {
            return;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            return;
        }
        mBluetoothAdapter.startDiscovery();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(HidEvent message) {
        if (message.mtcpType == HidEvent.tcpType.onConnected) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
            handler2.removeCallbacks(runnable2);
            ToastUtils.showShort("连接成功");

            Router.newIntent(this).to(MainActivity.class).launch();
            finish();
        } else if (message.mtcpType == HidEvent.tcpType.onDisConnected) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
            }
            ToastUtils.showShort("连接失败");
            handler2.removeCallbacks(runnable2);
            showDisConnectDialog();
        }
    }

    private void cancelDiscovery() {
        handler.removeCallbacks(runnable);
        if (mBluetoothAdapter == null) {
            return;
        }
        if (!mBluetoothAdapter.isDiscovering()) {
            return;
        }
        mBluetoothAdapter.cancelDiscovery();
        mBleDeviceAdpter = null;
    }

    //定义广播接收
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                MBluetoothDevice bean = new MBluetoothDevice();
                bean.mBluetoothDevice = device;
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {    //显示已配对设备
                    bean.type = 1;
                } else {
                    bean.type = 0;
                }
                addDevice(bean);
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                //重新扫描
                handler.postDelayed(runnable, 5000);

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Log.e("BlueToothTestActivity", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.e("BlueToothTestActivity", "完成配对");
                        connect();
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.e("BlueToothTestActivity", "取消配对");
                    default:
                        break;
                }
            }
        }
    };

    private void addDevice(MBluetoothDevice bean) {
        boolean deviceFound = false;
        for (MBluetoothDevice tmp : datas) {
            if (tmp.mBluetoothDevice.getAddress().equals(bean.mBluetoothDevice.getAddress())) {
                deviceFound = true;
            }
        }
        if (!deviceFound) {
            datas.add(bean);
            Collections.sort(datas);
            mBleDeviceAdpter.notifyDataSetChanged();
        }
    }

    private AlertDialog alertDialog2;

    private void showDisConnectDialog() {
        if (alertDialog2 == null) {
            alertDialog2 = new AlertDialog(this);
            alertDialog2
                    .init()
                    .setMsg("蓝牙似乎已经连接上了,但是APP接收不到系统反馈,你可以重启APP,或者点击重新连接")
                    .setPositiveButton("重启APP", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setNegativeButton("重新连接", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            connect();
                        }
                    });
        }
        alertDialog2.show();
    }


}