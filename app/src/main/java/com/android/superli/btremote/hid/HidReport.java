package com.android.superli.btremote.hid;

public class HidReport {
    public byte ReportId;
    public DeviceType deviceType;
    public static State SendState = State.None;
    public byte[] ReportData;

    public HidReport(DeviceType deviceType, byte reportId, byte[] data){
        this.deviceType = deviceType;
        this.ReportId = reportId;
        this.ReportData = data;
    }

    public enum DeviceType{
        None,Mouse,Keyboard
    }

    public enum State{
        None,Sending,Sended,Failded
    }
}
