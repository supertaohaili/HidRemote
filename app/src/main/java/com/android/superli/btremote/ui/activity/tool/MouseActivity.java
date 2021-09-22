package com.android.superli.btremote.ui.activity.tool;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.HidUitls;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.VibrateUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.Date;
import java.util.TimerTask;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class MouseActivity extends XActivity implements View.OnClickListener {

    private float Xpad, Ypad, Xmus, Ymus;
    private int maxPointerCount;
    private long actionDownTime_Pad = 0;
    private float rate = 1f;

    private boolean leftbtnUped = true;//左键是否抬起
    private boolean leftUped = true;//pad双击模拟左键是否抬起
    private boolean rightbtnUped = true;//右键是否抬起
    private boolean midbtnUped = true;//中键是否抬起

    private TimerTask virtureClickTask;

    private RelativeLayout rlt_mid;
    private Button tv_left;
    private Button tv_rigth;

    @Override
    public int getLayoutId() {
        return R.layout.activity_control_mouse;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);

        ImmersionBar.with(this)
                .titleBar(R.id.llt_content)
                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        findViewById(R.id.llt_pad).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                //region
                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                    case ACTION_POINTER_DOWN:
                        long now = new Date().getTime();
                        long dis = now - actionDownTime_Pad;
                        if (dis >= 50 && dis <= 150 && leftbtnUped) {
                            if (virtureClickTask != null) {
                                virtureClickTask.cancel();
                            }
                            HidConsts.LeftBtnDown();
                            leftUped = false;
                        }
                        actionDownTime_Pad = now;
                        maxPointerCount = motionEvent.getPointerCount();
                        Xpad = motionEvent.getX();
                        Ypad = motionEvent.getY();
                        return true;
                    case ACTION_MOVE:
                        maxPointerCount = Math.max(maxPointerCount, motionEvent.getPointerCount());
                        if (HidUitls.IsConnected()) {
                            int deltaX = (int) ((motionEvent.getX() - Xpad) * rate);
                            int deltay = (int) ((motionEvent.getY() - Ypad) * rate);
                            HidConsts.MouseMove(deltaX, deltay, 0, !leftbtnUped || !leftUped, !rightbtnUped, !midbtnUped);
                        }
                        Xpad = motionEvent.getX();
                        Ypad = motionEvent.getY();
                        return true;
                    case ACTION_UP:
                    case ACTION_POINTER_UP:
                        Xpad = motionEvent.getX();
                        Ypad = motionEvent.getY();
                        now = new Date().getTime();
                        dis = now - actionDownTime_Pad;
                        actionDownTime_Pad = now;
                        if (HidUitls.IsConnected()) {
                            if (maxPointerCount == 1) {
                                if (dis >= 50 && dis <= 150 && leftUped) {
                                    virtureClickTask = HidConsts.LeftBtnClickAsync(150);
                                } else if (dis >= 50 && dis <= 150 && !leftUped) {
                                    HidConsts.LeftBtnUp();
                                    leftUped = true;//模拟左键抬起
                                    HidConsts.LeftBtnClickAsync(20);
                                } else {
                                    HidConsts.LeftBtnUp();
                                    leftUped = true;//模拟左键抬起
                                    virtureClickTask = null;
                                }
                            }
                        }
                        //UtilCls.SLog(TAG,"ACTION_UP:"+dis);
                        return true;
                }
                //endregion
                return false;
            }
        });
        //endregion

        //region 鼠标中键
        rlt_mid = findViewById(R.id.rlt_mid);
        rlt_mid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                    case ACTION_POINTER_DOWN:
                        HidConsts.MidBtnDown();
                        midbtnUped = false;
                        maxPointerCount = motionEvent.getPointerCount();
                        Ymus = motionEvent.getY();
                        midbtnUped = false;
                        rlt_mid.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                        return true;
                    case ACTION_MOVE:
                        maxPointerCount = Math.max(maxPointerCount, motionEvent.getPointerCount());
                        if (HidUitls.IsConnected()) {
                            if (!midbtnUped) {
                                HidConsts.MidBtnUp();
                                midbtnUped = true;
                            }
                            int deltay = -(int) ((motionEvent.getY() - Ymus));
                            HidConsts.MouseMove(0, 0, deltay, !leftbtnUped, !rightbtnUped, !midbtnUped);
                        }
                        Ymus = motionEvent.getY();
                        return true;
                    case ACTION_UP:
                    case ACTION_POINTER_UP:
                        if (!midbtnUped) {
                            HidConsts.MidBtnUp();
                            midbtnUped = true;
                        }
                        rlt_mid.setBackgroundResource(R.drawable.shape_key_sel_c5);
                        return true;
                }

                return false;
            }
        });
        //endregion

        //region鼠标左右键
        tv_left = findViewById(R.id.tv_left);
        tv_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    HidConsts.LeftBtnUp();
                    leftbtnUped = true;
                    tv_left.setBackgroundResource(R.drawable.shape_key_sel_c5);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    HidConsts.LeftBtnDown();
                    leftbtnUped = false;
                    tv_left.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                }
                return true;
            }
        });

        tv_rigth = findViewById(R.id.tv_rigth);
        tv_rigth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    HidConsts.RightBtnUp();
                    rightbtnUped = true;
                    tv_rigth.setBackgroundResource(R.drawable.shape_key_sel_c5);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    HidConsts.RightBtnDown();
                    rightbtnUped = false;
                    tv_rigth.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                }
                return true;
            }
        });

    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }
}