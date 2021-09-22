package com.android.base.ui;

import java.lang.ref.WeakReference;

public class XPresent<V extends IView> implements IPresent<V> {
    private WeakReference<V> v;

    @Override
    public void attachV(V view) {
        v = new WeakReference<V>(view);
    }

    @Override
    public void detachV() {
        if (v == null) {
            return;
        }
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    protected V getV() {
        if (v == null) {
            return null;
        }
        return v.get();
    }
}
