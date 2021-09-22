package com.android.superli.btremote;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.android.base.SharedPreferencesUtil;
import com.android.base.router.Router;
import com.android.base.ui.SupportActivity;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.ui.views.dialog.AlertDialog;
import com.android.superli.btremote.utils.ScreenUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import androidx.annotation.Nullable;
import www.hjq.permissions.OnPermissionCallback;
import www.hjq.permissions.Permission;
import www.hjq.permissions.XXPermissions;


public class SplashActivity extends SupportActivity {

    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private BluetoothListenerReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenUtils.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initReceiver();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            showNoDialog();
            return;
        }

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            showNoDialog();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            showBleTipDialog();
            return;
        }

        rpermissions();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        receiver = new BluetoothListenerReceiver();
        receiver.setmBluetoothStateListener(new BluetoothListenerReceiver.BluetoothStateListener() {
            @Override
            public void stateTurningOn() {

            }

            @Override
            public void stateOn() {
                rpermissions();
            }

            @Override
            public void stateTurningOff() {

            }

            @Override
            public void stateOff() {

            }
        });
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    private void rpermissions() {
        if (XXPermissions.isGrantedPermission(this, Permission.ACCESS_FINE_LOCATION)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                askGpsDialog();
            } else {
                initDoing();
            }
        } else {
            applyXXPermission();
        }
    }

    private void applyXXPermission() {
        new AlertDialog(this)
                .init()
                .setMsg("注:手机需要获取定位权限,才可以扫描出附近的蓝牙设备. 是否同意获取权限?")
                .setNegativeButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setPositiveButton("授权", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPermission();
                    }
                }).show();
    }

    private void getPermission() {
        XXPermissions.with(SplashActivity.this)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        //获取成功
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                            askGpsDialog();
                        } else {
                            initDoing();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        //获取失败，never 永久拒绝
                        String msg = "很抱歉，由于没有获取到定位权限，所以无法发现附近蓝牙设备, APP功能无法正常使用。";
                        if (never) {
                            msg = msg + "(你已经永久拒绝权限，激活需要跳转到应用权限系统设置页面，手动授权)";
                        }
                        AlertDialog alertDialog = new AlertDialog(SplashActivity.this)
                                .init()
                                .setMsg(msg)
                                .setPositiveButton("已知悉,退出APP", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                });
                        if (never) {
                            alertDialog.setNegativeButton("手动授权", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    XXPermissions.startPermissionActivity(SplashActivity.this, permissions);
                                }
                            });
                        }
                        alertDialog.show();
                    }
                });
    }

    private void initDoing() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Router.newIntent(SplashActivity.this)
                        .to(ScanActivity.class)
                        .launch();
                finish();
            }
        }, 500);
    }

    private void askGpsDialog() {
        new AlertDialog(SplashActivity.this).init()
                .setMsg("当前还未开启定位服务,点击开启")
                .setCancelable(false)
                .setNegativeButton("开启", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                initDoing();
            } else {
                new AlertDialog(this).init()
                        .setMsg("获取定位服务失败,APP正常提供服务,如需重新授权,请重启APP")
                        .setCancelable(false)
                        .setNegativeButton("了解，退出app", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
            }
        }
    }


    private void showBleTipDialog() {
        new AlertDialog(this)
                .init()
                .setMsg("你的手机还未开启蓝牙，是否开启蓝牙?")
                .setPositiveButton("开启蓝牙", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    }
                }).show();
    }

    private void showNoDialog() {
        new AlertDialog(this)
                .init()
                .setMsg("当前设备不支持蓝牙HID功能!!")
                .setPositiveButton("好吧", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}