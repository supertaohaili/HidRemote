package com.android.superli.btremote.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.base.router.Router;
import com.android.base.ui.XFragment;
import com.android.superli.btremote.R;
import com.android.superli.btremote.ui.activity.AboutUsActivity;
import com.android.superli.btremote.ui.activity.PrivacyPolicyActivity;
import com.android.superli.btremote.ui.views.SwitchButton;
import com.android.base.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.bigkoo.pickerview.builder.OptionsPickerBuilder;
import www.bigkoo.pickerview.listener.OnOptionsSelectListener;
import www.bigkoo.pickerview.view.OptionsPickerView;


public class SettingFragment extends XFragment implements View.OnClickListener {

    private SwitchButton switchButton;
    private TextView tv_vibration;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void bindUI(View rootView) {
        switchButton = rootView.findViewById(R.id.switch_button);
        tv_vibration = rootView.findViewById(R.id.tv_vibrate);
        rootView.findViewById(R.id.tv_privacy_policy).setOnClickListener(this);
        rootView.findViewById(R.id.tv_about_us).setOnClickListener(this);
        rootView.findViewById(R.id.tv_problem_feekback).setOnClickListener(this);
        rootView.findViewById(R.id.llt_tv_vibration).setOnClickListener(this);
        rootView.findViewById(R.id.tv_website).setOnClickListener(this);

        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        if (theme == 0) {
            switchButton.setChecked(false);
        } else {
            switchButton.setChecked(true);
        }
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                EventBus.getDefault().post(isChecked);
            }
        });

    }

    @Override
    public void initData() {
        int vibrate = (int) SharedPreferencesUtil.getData("vibrate", 50);
        if (vibrate == 200) {
            tv_vibration.setText("强");
        } else if (vibrate == 100) {
            tv_vibration.setText("中");
        } else if (vibrate == 50) {
            tv_vibration.setText("弱");
        } else if (vibrate == -1) {
            tv_vibration.setText("关闭");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llt_tv_vibration:
                List<String> datas = new ArrayList<>();
                datas.add("强");
                datas.add("中");
                datas.add("弱");
                datas.add("关闭");

                OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tv_vibration.setText(datas.get(options1));
                        if (options1 == 0) {
                            SharedPreferencesUtil.putData("vibrate", 200);
                        } else if (options1 == 1) {
                            SharedPreferencesUtil.putData("vibrate", 100);
                        } else if (options1 == 2) {
                            SharedPreferencesUtil.putData("vibrate", 50);
                        } else if (options1 == 3) {
                            SharedPreferencesUtil.putData("vibrate", -1);
                        }
                    }
                })
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setTitleText("请选择按键振动强度")
                        .setBgColor(getResources().getColor(R.color.bg_window))
                        .setTitleBgColor(getResources().getColor(R.color.bg_item))
                        .setTitleColor(getResources().getColor(R.color.main_text))
                        .setCancelColor(getResources().getColor(R.color.main_text))
                        .setSubmitColor(getResources().getColor(R.color.main_text))
                        .setTextColorCenter(getResources().getColor(R.color.main_text))
                        .build();

                pvNoLinkOptions.setNPicker(datas, null, null);
                pvNoLinkOptions.setSelectOptions(0, 0, 0);
                pvNoLinkOptions.show();
                break;
            case R.id.tv_privacy_policy:
                Router.newIntent(getActivity()).to(PrivacyPolicyActivity.class).launch();
                break;
            case R.id.tv_website:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.wnkong.com/");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.tv_problem_feekback:
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "1312398581@qq.com");
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                try {
                    Intent chooser = Intent.createChooser(sendIntent, "Send mail...");
                    context.startActivity(chooser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_about_us:
                Router.newIntent(getActivity()).to(AboutUsActivity.class).launch();
                break;
        }
    }
}
