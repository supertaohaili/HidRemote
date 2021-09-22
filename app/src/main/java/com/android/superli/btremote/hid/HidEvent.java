package com.android.superli.btremote.hid;

/**
 * @ClassName: SocketEvent
 * @Description: java类作用描述
 * @Author: taohaili
 * @CreateDate: 2021/3/10 16:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/3/10 16:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HidEvent {

    public enum tcpType {
        onConnecting, onConnected, onDisConnected
    }

    public tcpType mtcpType;

    public HidEvent(tcpType mtcpType) {
        this.mtcpType = mtcpType;
    }
}
