package com.android.superli.btremote.ui.activity.fqs;

import android.view.View;

import com.android.base.router.Router;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.ui.adapter.FqsAdpter;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FqsActivity extends XActivity implements View.OnClickListener {

    private RecyclerView mRecyclerview;
    private FqsAdpter mFqsAdpter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fqs;
    }

    @Override
    public int getActivityTitle() {
        return R.string.common_problem;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();


        mRecyclerview = findViewById(R.id.recyclerview);
        mFqsAdpter = new FqsAdpter(this);
        mFqsAdpter.setmOnClickListener(new FqsAdpter.OnClickListener() {
            @Override
            public void onClick(String item) {
                Router.newIntent(FqsActivity.this).to(FqsMsgActivity.class).launch();
            }
        });
        mRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mFqsAdpter);

        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        mFqsAdpter.addData(datas);

    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }
}