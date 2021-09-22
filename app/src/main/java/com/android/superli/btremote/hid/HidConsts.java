package com.android.superli.btremote.hid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class HidConsts {

    public final static String NAME = "app_remote";
    public final static String DESCRIPTION = "description";
    public final static String PROVIDER = "provider";
    public static BluetoothHidDevice HidDevice;
    public static BluetoothDevice BtDevice;
    public static byte ModifierByte = 0x00;
    public static byte KeyByte = 0x00;

    public final static byte[] Descriptor = {
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x02, (byte) 0xa1, (byte) 0x01, (byte) 0x09, (byte) 0x01, (byte) 0xa1, (byte) 0x00,
            (byte) 0x85, (byte) 0x01, (byte) 0x05, (byte) 0x09, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x03, (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x01,
            (byte) 0x95, (byte) 0x03, (byte) 0x75, (byte) 0x01, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x05, (byte) 0x81, (byte) 0x03,
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x30, (byte) 0x09, (byte) 0x31, (byte) 0x09, (byte) 0x38, (byte) 0x15, (byte) 0x81, (byte) 0x25, (byte) 0x7f,
            (byte) 0x75, (byte) 0x08, (byte) 0x95, (byte) 0x03, (byte) 0x81, (byte) 0x06, (byte) 0xc0, (byte) 0xc0, (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x06,
            (byte) 0xa1, (byte) 0x01, (byte) 0x85, (byte) 0x02, (byte) 0x05, (byte) 0x07, (byte) 0x19, (byte) 0xE0, (byte) 0x29, (byte) 0xE7, (byte) 0x15, (byte) 0x00,
            (byte) 0x25, (byte) 0x01, (byte) 0x75, (byte) 0x01, (byte) 0x95, (byte) 0x08, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x08,
            (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x65, (byte) 0x19, (byte) 0x00, (byte) 0x29, (byte) 0x65, (byte) 0x81, (byte) 0x00, (byte) 0x05, (byte) 0x08,
            (byte) 0x95, (byte) 0x05, (byte) 0x75, (byte) 0x01, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x05,
            (byte) 0x91, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x03, (byte) 0x91, (byte) 0x03,
            (byte) 0xc0
    };

    private static Handler handler;
    private static ExecutorService singleThreadExecutor;

    public static void reporttrans() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper()) {

                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        HidReport mHidReport = (HidReport) msg.obj;
                        postReport(mHidReport);
                    }
                };
                Looper.loop();
            }
        });
    }

    private static void postReport(HidReport report) {
        if ( Build.VERSION.SDK_INT<Build.VERSION_CODES.P) {
            return;
        }
        report.SendState = HidReport.State.Sending;
        Log.e("postReport", "ID:" + report.ReportId + "\t\tDATA:" + BytesUtils.toHexStringForLog(report.ReportData));
        boolean ret = HidDevice.sendReport(BtDevice, report.ReportId, report.ReportData);
        if (!ret) {
            report.SendState = HidReport.State.Failded;
        } else {
            report.SendState = HidReport.State.Sended;
        }
    }

    public static void exit() {
        if (handler != null) {
            handler.getLooper().quit();
            handler = null;
        }

        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown()) {
            singleThreadExecutor.shutdown();
            singleThreadExecutor = null;
        }
    }

    public static void CleanKbd() {
        SendKeyReport(new byte[]{0, 0});
    }

    protected static void addInputReport(final HidReport inputReport) {
        if (handler == null || singleThreadExecutor == null) {
            reporttrans();
        }
        if (inputReport != null && handler != null) {
            Message msg = new Message();
            msg.obj = inputReport;
            handler.sendMessage(msg);
        }
    }

    public static void SendMouseReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, reportData);
        addInputReport(report);
    }

    private static HidReport MouseReport = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, new byte[]{0, 0, 0, 0});

    public static void MouseMove(int dx, int dy, int wheel, final boolean leftButton, final boolean rightButton, final boolean middleButton) {

        if (MouseReport.SendState.equals(HidReport.State.Sending)) {
            return;
        }
        if (dx > 127) dx = 127;
        if (dx < -127) dx = -127;
        if (dy > 127) dy = 127;
        if (dy < -127) dy = -127;
        if (wheel > 127) wheel = 127;
        if (wheel < -127) wheel = -127;
        if (leftButton) {
            MouseReport.ReportData[0] |= 1;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~1));
        }
        if (rightButton) {
            MouseReport.ReportData[0] |= 2;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~2));
        }
        if (middleButton) {
            MouseReport.ReportData[0] |= 4;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~4));
        }
        MouseReport.ReportData[1] = (byte) dx;
        MouseReport.ReportData[2] = (byte) dy;
        MouseReport.ReportData[3] = (byte) wheel;

        addInputReport(MouseReport);
    }

    public static void LeftBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 1;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void LeftBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~1);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void LeftBtnClick() {
        LeftBtnDown();
        UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnUp();
            }
        }, 20, true);
    }

    public static TimerTask LeftBtnClickAsync(int delay) {
        return UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnClick();
            }
        }, delay, true);
    }

    public static void RightBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 2;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void RightBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~2);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void MidBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 4;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void MidBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~4);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static byte ModifierDown(byte UsageId) {
        synchronized (HidConsts.class) {
            ModifierByte |= UsageId;
        }
        return ModifierByte;
    }

    public static byte ModifierUp(byte UsageId) {
        UsageId = (byte) (~((byte) (UsageId)));
        synchronized (HidConsts.class) {
            ModifierByte = (byte) (ModifierByte & UsageId);
        }
        return ModifierByte;
    }

    public static void KbdKeyDown(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConsts.class) {
                    byte mod = ModifierDown((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                byte key = (byte) Integer.parseInt(usageStr);
                synchronized (HidConsts.class) {
                    KeyByte = key;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void KbdKeyUp(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConsts.class) {
                    byte mod = ModifierUp((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                synchronized (HidConsts.class) {
                    KeyByte = 0;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void SendKeyReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Keyboard, (byte) 0x02, reportData);
        addInputReport(report);
    }
}
