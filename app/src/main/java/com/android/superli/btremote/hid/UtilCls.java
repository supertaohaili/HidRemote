package com.android.superli.btremote.hid;

import java.util.Timer;
import java.util.TimerTask;

public class UtilCls {
    /**
     * 延时执行任务
     * @param runnable
     * @param delay
     * @param runonce
     */
    public static TimerTask DelayTask(final Runnable runnable, int delay, final boolean runonce){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                runnable.run();
                if(runonce){
                    this.cancel();
                }
            }
        };
        timer.schedule(task, delay);
        return task;
    }
}