package com.android.superli.btremote.ui.views.statelayout;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.superli.btremote.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;


public class StateManager {
    private static final String TAG = StateManager.class.getSimpleName();

    private StateLayout mStateLayout;

    private StateManager(Builder builder) {
        ViewGroup contentParent;
        Context context;
        if (builder.content instanceof Activity) {
            Activity activity = (Activity) builder.content;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (builder.content instanceof Fragment) {
            Fragment fragment = (Fragment) builder.content;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());

        } else if (builder.content instanceof View) {
            View view = (View) builder.content;
            contentParent = (ViewGroup) (view.getParent());
            if (contentParent == null) {
                throw new IllegalArgumentException("the view must already has a parent ");
            }
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the container's type must be Fragment or Activity or a view ");
        }

        int childCount = contentParent.getChildCount();
        int index = 0;
        View oldContent;
        if (builder.content instanceof View) {
            oldContent = (View) builder.content;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        StateLayout stateLayout = new StateLayout(context);
        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        stateLayout.setContentView(oldContent);
        contentParent.addView(stateLayout, index, lp);
        initStateLayout(stateLayout, builder);
    }

    private StateManager(StateLayout stateLayout, Builder builder) {
        initStateLayout(stateLayout, builder);
    }


    private void initStateLayout(StateLayout stateLayout, Builder builder) {
        stateLayout.setLoadingView(builder.loadingLayoutId)
                .setLoadingView(builder.loadingView)
                .setLoadingText(builder.loadingText);

        stateLayout.setEmptyView(builder.emptyLayoutId)
                .setEmptyView(builder.emptyView)
                .setEmptyImage(builder.emptyImageId)
                .setEmptyText(builder.emptyText);

        stateLayout.setErrorView(builder.errorLayoutId)
                .setErrorView(builder.errorView)
                .setErrorImage(builder.errorImageId)
                .setErrorText(builder.errorText);

        stateLayout.setNetErrorView(builder.netErrorLayoutId)
                .setNetErrorView(builder.netErrorView)
                .setNetErrorImage(builder.netErrorImageId)
                .setNetErrorText(builder.netErrorText);

        stateLayout.setEmptyOnClickListener(builder.emptyListener)
                .setErrorOnClickListener(builder.errorListener)
                .setNetErrorOnClickListener(builder.netErrorListener)
                .setConvertListener(builder.convertListener);
        mStateLayout = stateLayout;
    }

    public void showLoading() {
        mStateLayout.showLoading();
    }

    public void showError() {
        mStateLayout.showError();
    }

    public void showNetError() {
        mStateLayout.showNetError();
    }

    public void showContent() {
        mStateLayout.showContent();
    }

    public void showEmpty() {
        mStateLayout.showEmpty();
    }

    public void setStateLayout(StateLayout stateLayout) {
        this.mStateLayout = stateLayout;
    }

    public StateLayout getStateLayout() {
        return mStateLayout;
    }

    public static class Builder {
        private Context context;
        private Object content;
        private int loadingLayoutId = R.layout.state_loading;
        private View loadingView;
        private CharSequence loadingText;
        private int emptyLayoutId = R.layout.state_empty;
        private View emptyView;
        private int errorLayoutId = R.layout.state_error;
        private View errorView;
        private int netErrorLayoutId = R.layout.state_net_error;
        private View netErrorView;
        private int emptyImageId;
        private CharSequence emptyText;
        private int errorImageId;
        private CharSequence errorText;
        private int netErrorImageId;
        private CharSequence netErrorText;
        private StateListener.OnClickListener netErrorListener;
        private StateListener.OnClickListener errorListener;
        private StateListener.OnClickListener emptyListener;
        private StateListener.ConvertListener convertListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setLoadingView(@LayoutRes int loadingLayoutId) {
            this.loadingLayoutId = loadingLayoutId;
            this.loadingView = null;
            return this;
        }

        public Builder setLoadingView(@NonNull View view) {
            this.loadingLayoutId = 0;
            this.loadingView = view;
            return this;
        }

        public Builder setErrorView(@LayoutRes int errorLayoutId) {
            this.errorLayoutId = errorLayoutId;
            this.errorView = null;
            return this;
        }

        public Builder setErrorView(@NonNull View view) {
            this.errorLayoutId = 0;
            this.errorView = view;
            return this;
        }

        public Builder setEmptyView(@LayoutRes int emptyLayoutId) {
            this.emptyLayoutId = emptyLayoutId;
            this.emptyView = null;
            return this;
        }

        public Builder setEmptyView(@NonNull View view) {
            this.emptyLayoutId = 0;
            this.emptyView = view;
            return this;
        }

        public Builder setNetErrorView(@LayoutRes int netErrorLayoutId) {
            this.netErrorLayoutId = netErrorLayoutId;
            this.netErrorView = null;
            return this;
        }

        public Builder setNetErrorView(@NonNull View view) {
            this.netErrorLayoutId = 0;
            this.netErrorView = view;
            return this;
        }

        public Builder setContent(@NonNull Object content) {
            this.content = content;
            return this;
        }

        public Builder setEmptyImage(@DrawableRes int emptyImageId) {
            this.emptyImageId = emptyImageId;
            return this;
        }

        public Builder setEmptyText(@Nullable CharSequence emptyText) {
            this.emptyText = emptyText;
            return this;
        }

        public Builder setEmptyText(@StringRes int emptyTextId) {
            this.emptyText = context.getText(emptyTextId);
            return this;
        }

        public Builder setErrorImage(@DrawableRes int errorImageId) {
            this.errorImageId = errorImageId;
            return this;
        }

        public Builder setErrorText(@Nullable CharSequence errorText) {
            this.errorText = errorText;
            return this;
        }

        public Builder setErrorText(@StringRes int errorTextId) {
            this.errorText = context.getText(errorTextId);
            return this;
        }


        public Builder setNetErrorImage(@DrawableRes int netErrorImageId) {
            this.netErrorImageId = netErrorImageId;
            return this;
        }

        public Builder setNetErrorText(@Nullable CharSequence netErrorText) {
            this.netErrorText = netErrorText;
            return this;
        }

        public Builder setNetErrorText(@StringRes int netErrorTextId) {
            this.netErrorText = context.getText(netErrorTextId);
            return this;
        }

        public Builder setLoadingText(@Nullable CharSequence loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        public Builder setLoadingText(@StringRes int loadingText) {
            this.loadingText = context.getText(loadingText);
            return this;
        }

        public Builder setNetErrorOnClickListener(StateListener.OnClickListener listener) {
            this.netErrorListener = listener;
            return this;
        }

        public Builder setErrorOnClickListener(StateListener.OnClickListener listener) {
            this.errorListener = listener;
            return this;
        }

        public Builder setEmptyOnClickListener(StateListener.OnClickListener listener) {
            this.emptyListener = listener;
            return this;
        }

        public Builder setConvertListener(StateListener.ConvertListener listener) {
            this.convertListener = listener;
            return this;
        }

        public StateManager build() {
            return new StateManager(this);
        }

        public StateManager build(StateLayout mStateLayout) {
            return new StateManager(mStateLayout, this);
        }
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }
}
