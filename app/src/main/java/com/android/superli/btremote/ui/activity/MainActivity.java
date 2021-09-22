package com.android.superli.btremote.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.base.ui.SupportFragment;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.HidEvent;
import com.android.superli.btremote.hid.HidUitls;
import com.android.superli.btremote.ui.fragment.SettingFragment;
import com.android.superli.btremote.ui.fragment.SimpleHomeFragmen;
import com.android.superli.btremote.ui.views.BottomBar;
import com.android.superli.btremote.ui.views.BottomBarTab;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends XActivity implements View.OnClickListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private BottomBar mBottomBar;

    private TextView tv_title;
    private TextView tv_state;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void bindUI(View rootView) {
        tv_title = findViewById(R.id.tv_title);
        tv_state = findViewById(R.id.tv_state);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        SupportFragment firstFragment = findFragment(SimpleHomeFragmen.class);
        if (firstFragment == null) {
            mFragments[FIRST] = SimpleHomeFragmen.newInstance();
            mFragments[SECOND] = SettingFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(SettingFragment.class);
        }

        tv_title.setText(getResources().getString(R.string.tab_home));
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar
                .addItem(new BottomBarTab(this, R.mipmap.icon_home, getResources().getString(R.string.tab_home)))
                .addItem(new BottomBarTab(this, R.mipmap.icon_me, getResources().getString(R.string.tab_us)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                if (position == 0) {
                    tv_title.setText(getResources().getString(R.string.tab_home));
                } else if (position == 1) {
                    tv_title.setText(getResources().getString(R.string.tab_us));
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }

    @Override
    public void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Boolean message) {
        if (message) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPreferencesUtil.putData("theme", 1);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPreferencesUtil.putData("theme", 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(HidEvent message) {
        if (message.mtcpType == HidEvent.tcpType.onDisConnected) {
            tv_state.setText("(已断开连接)");
            if (HidConsts.HidDevice != null) {
                HidUitls.reConnect(this);
                HidConsts.HidDevice = HidUitls.HidDevice;
                HidConsts.BtDevice = HidUitls.BtDevice;
            }
        } else if (message.mtcpType == HidEvent.tcpType.onConnecting) {
            tv_state.setText("(正在连接……)");
        } else {
            tv_state.setText("");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        if (theme == 0) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        HidConsts.exit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_menu) {

        }
    }
}
