package com.android.base.ui;

import android.view.View;

public interface IView<P> {

    public int getLayoutId();

    public void bindUI(View rootView);

    public void initData();

    public P newP();
}
