package com.android.superli.btremote;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {

    private Dialog dialog;

    public LoadingDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_loading, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.AlertDialogStyle);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
    }


    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCancelable(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
    }
}
