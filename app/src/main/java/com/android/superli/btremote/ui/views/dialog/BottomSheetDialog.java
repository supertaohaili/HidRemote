package com.android.superli.btremote.ui.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.android.superli.btremote.R;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetDialog {
    private Context mContext;
    private Dialog dialog;
    private TextView tvTitle;
    private TextView tvCancel;
    private LinearLayout content;
    private ScrollView scrollView;
    private List<SheetItem> sheetItemList;
    private Display display;


    public BottomSheetDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();


    }

    public BottomSheetDialog init() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_actionsheet, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        scrollView = (ScrollView) view.findViewById(R.id.sLayout_content);
        content = (LinearLayout) view.findViewById(R.id.lLayout_content);
        tvTitle = (TextView) view.findViewById(R.id.txt_title);
        tvCancel = (TextView) view.findViewById(R.id.txt_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public BottomSheetDialog setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        return this;
    }

    public BottomSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public BottomSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public BottomSheetDialog addSheetItem(SheetItem sheetItem) {
        addSheetItem2List(sheetItem);
        return this;
    }


    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollView
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            scrollView.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.itemName;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;

            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(sheetItem.textStyle.textSize);
            textView.setGravity(Gravity.CENTER);

            textView.setBackgroundResource(R.drawable.item_sel_c0);
            // 字体颜色
            textView.setTextColor(sheetItem.textStyle.textColor);

            // 高度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.topMargin = (int) (1 * scale + 0.5f);
            textView.setLayoutParams(params);

            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            content.addView(textView);
        }
    }

    private void addSheetItem2List(SheetItem sheetItem) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(sheetItem);
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }


    public static class SheetItem {

        String itemName;
        SheetItemTextStyle textStyle;
        OnSheetItemClickListener itemClickListener;

        public SheetItem(String itemName, SheetItemTextStyle textStyle, OnSheetItemClickListener itemClickListener) {
            this.itemName = itemName;
            this.textStyle = textStyle;
            this.itemClickListener = itemClickListener;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public SheetItemTextStyle getTextStyle() {
            return textStyle;
        }

        public void setTextStyle(SheetItemTextStyle textStyle) {
            this.textStyle = textStyle;
        }

        public OnSheetItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(OnSheetItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    public static class SheetItemTextStyle {


        int textColor;
        int textSize;
        Typeface typeface;   //加粗，倾斜，等


        public SheetItemTextStyle(String textColor, int textSize, Typeface typeface) {
            this(Color.parseColor(textColor), textSize, typeface);
        }

        public SheetItemTextStyle(int textColor, int textSize) {
            this(textColor, textSize, Typeface.defaultFromStyle(Typeface.NORMAL));
        }


        public SheetItemTextStyle(int textColor, int textSize, Typeface typeface) {
            this.textColor = textColor;
            this.textSize = textSize;
            this.typeface = typeface;
        }

        public Typeface getTypeface() {
            return typeface;
        }

        public void setTypeface(Typeface typeface) {
            this.typeface = typeface;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }
    }
}