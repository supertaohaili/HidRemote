package com.android.superli.btremote.ui.views.statelayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.superli.btremote.R;


import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


public class StateLayout extends FrameLayout {
    public static final String TAG = StateLayout.class.getSimpleName();
    private View mContentView, mLoadingView, mEmptyView, mErrorView, mNetErrorView;
    int loadingLayoutId, emptyLayoutId, errorLayoutId, netErrorLayoutId;
    private StateListener.ConvertListener mConvertListener;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        try {
            loadingLayoutId = a.getResourceId(R.styleable.StateLayout_loadingLayout, R.layout.state_loading);
            emptyLayoutId = a.getResourceId(R.styleable.StateLayout_emptyLayout, R.layout.state_empty);
            errorLayoutId = a.getResourceId(R.styleable.StateLayout_errorLayout, R.layout.state_error);
            netErrorLayoutId = a.getResourceId(R.styleable.StateLayout_netErrorLayout, R.layout.state_net_error);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("StateLayout can host only 1 direct child");
        }
        mContentView = this.getChildAt(0);
        setLoadingView(loadingLayoutId);
        setEmptyView(emptyLayoutId);
        setErrorView(errorLayoutId);
        setNetErrorView(netErrorLayoutId);
    }

    public StateLayout setContentView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return setContentView(inflate(getContext(), layoutId, null));
    }

    public StateLayout setLoadingView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return setLoadingView(inflate(getContext(), layoutId, null));
    }

    public StateLayout setEmptyView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return setEmptyView(inflate(getContext(), layoutId, null));
    }

    public StateLayout setErrorView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            return this;
        }
        return setErrorView(inflate(getContext(), layoutId, null));
    }

    public StateLayout setNetErrorView(@LayoutRes int layoutId) {
        if (layoutId == 0) {
            return null;
        }
        return setNetErrorView(inflate(getContext(), layoutId, null));
    }

    public StateLayout setLoadingView(@Nullable View view) {
        if (view == null) {
            return this;
        }
        if (mLoadingView != null) {
            removeView(mLoadingView);
            Log.w(TAG, "you have already set a loading view and would be instead of this new one.");
        }
        addView(view);
        mLoadingView = view;
        mLoadingView.setVisibility(GONE);
        return this;
    }

    public StateLayout setEmptyView(@Nullable View view) {
        if (view == null) {
            return this;
        }
        if (mEmptyView != null) {
            removeView(mEmptyView);
            Log.w(TAG, "you have already set a empty view and would be instead of this new one.");
        }
        addView(view);
        mEmptyView = view;
        mEmptyView.setVisibility(GONE);
        return this;
    }

    public StateLayout setErrorView(@Nullable View view) {
        if (view == null) {
            return this;
        }
        if (mErrorView != null) {
            removeView(mErrorView);
            Log.w(TAG, "you have already set a error view and would be instead of this new one.");
        }
        addView(view);
        mErrorView = view;
        mErrorView.setVisibility(GONE);
        return this;
    }

    public StateLayout setNetErrorView(@Nullable View view) {
        if (view == null) {
            return this;
        }
        if (mNetErrorView != null) {
            removeView(mNetErrorView);
            Log.w(TAG, "you have already set a net error view and would be instead of this new one.");
        }
        addView(view);
        mNetErrorView = view;
        mNetErrorView.setVisibility(GONE);
        return this;
    }

    public StateLayout setContentView(@Nullable View view) {
        if (view == null) {
            return this;
        }
        if (mContentView != null) {
            removeView(mContentView);
            Log.w(TAG, "you have already set a content view and would be instead of this new one.");
        }
        addView(view);
        mContentView = view;
        return this;
    }

    public StateLayout setEmptyImage(@DrawableRes int imageId) {
        if (imageId == 0 || mEmptyView == null) {
            return this;
        }

        View view = mEmptyView.findViewById(R.id.iv_empty_state);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }

        return this;
    }

    public StateLayout setEmptyText(@Nullable CharSequence emptyText) {
        if (TextUtils.isEmpty(emptyText) || mEmptyView == null) {
            return this;
        }

        View view = mEmptyView.findViewById(R.id.tv_empty_state);
        if (view instanceof TextView) {
            ((TextView) view).setText(emptyText);
        }

        return this;
    }

    public StateLayout setErrorImage(@DrawableRes int imageId) {
        if (imageId == 0 || mErrorView == null) {
            return this;
        }

        View view = mErrorView.findViewById(R.id.iv_error_state);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }
        return this;
    }

    public StateLayout setErrorText(@Nullable CharSequence errorText) {
        if (TextUtils.isEmpty(errorText) || mErrorView == null) {
            return this;
        }
        View view = mErrorView.findViewById(R.id.tv_error_state);
        if (view instanceof TextView) {
            ((TextView) view).setText(errorText);
        }
        return this;
    }


    public StateLayout setNetErrorImage(@DrawableRes int imageId) {
        if (imageId == 0 || mNetErrorView == null) {
            return this;
        }

        View view = mNetErrorView.findViewById(R.id.iv_net_error_state);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }

        return this;
    }

    public StateLayout setNetErrorText(@Nullable CharSequence netErrorText) {
        if (TextUtils.isEmpty(netErrorText) || mNetErrorView == null) {
            return this;
        }

        View view = mNetErrorView.findViewById(R.id.tv_net_error_state);
        if (view instanceof TextView) {
            ((TextView) view).setText(netErrorText);
        }
        return this;
    }

    public StateLayout setLoadingText(@Nullable CharSequence loadingText) {
        if (TextUtils.isEmpty(loadingText) || mLoadingView == null) {
            return this;
        }

        View view = mLoadingView.findViewById(R.id.tv_loading_state);
        if (view instanceof TextView) {
            ((TextView) view).setText(loadingText);
        }
        return this;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getNetErrorView() {
        return mNetErrorView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public boolean isLoadingShowing() {
        return mLoadingView != null && mLoadingView.getVisibility() == VISIBLE;
    }

    public boolean isEmptyShowing() {
        return mEmptyView != null && mEmptyView.getVisibility() == VISIBLE;
    }

    public boolean isContentShowing() {
        return mContentView != null && mContentView.getVisibility() == VISIBLE;
    }

    public boolean isErrorShowing() {
        return mErrorView != null && mErrorView.getVisibility() == VISIBLE;
    }

    public boolean isNetErrorShowing() {
        return mNetErrorView != null && mNetErrorView.getVisibility() == VISIBLE;
    }

    public void showEmpty() {
        if (isEmptyShowing()) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child == mEmptyView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showError() {
        if (isErrorShowing()) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child == mErrorView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showNetError() {
        if (isNetErrorShowing()) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child == mNetErrorView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showLoading() {
        if (isLoadingShowing()) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child == mLoadingView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showContent() {
        if (isContentShowing()) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (child == mContentView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public StateLayout setNetErrorOnClickListener(final StateListener.OnClickListener listener) {
        if (mNetErrorView == null) {
            return this;
        }
        mNetErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onClick(v);

                } else if (!isNetWorkAvailable(v.getContext())) {
                    showDialog();
                }
            }
        });
        return this;
    }

    public StateLayout setErrorOnClickListener(final StateListener.OnClickListener listener) {
        if (listener == null || mErrorView == null) {
            return this;
        }
        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public StateLayout setEmptyOnClickListener(final StateListener.OnClickListener listener) {
        if (listener == null || mEmptyView == null) {
            return this;

        }
        mEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public StateLayout setConvertListener(final StateListener.ConvertListener listener) {
        mConvertListener = listener;
        if (mConvertListener != null) {
            mConvertListener.convert(new BaseViewHolder(this), this);
        }
        return this;
    }

    /**
     * 判断是否有网络。
     *
     * @param context 上下文
     * @return
     */
    private static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                return false;
            } else {
                if (info.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置。
     */
    private void showDialog() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("提示")
                .setMessage("当前网络无效，请问是否去设置更换网络？")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到系统的网络设置界面
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        StateLayout.this.getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                }).setNegativeButton("知道了", null)
                .show();
    }
}
