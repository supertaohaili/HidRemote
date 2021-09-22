package com.android.base.ui;

public interface IPresent<V> {
    void attachV(V view);

    void detachV();

}
