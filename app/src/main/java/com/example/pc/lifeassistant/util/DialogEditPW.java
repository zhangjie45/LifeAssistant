package com.example.pc.lifeassistant.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.pc.lifeassistant.R;

/**
 * Created by pc on 2019/3/17.
 */

public class DialogEditPW extends Dialog {
    public DialogEditPW(@NonNull Context context) {
        super(context);
    }

    public DialogEditPW(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private DialogEditPW dialogEditPW;
        private View contentView;
        private View layout;



        private View.OnClickListener cancelButtonClickListener;
        private View.OnClickListener okButtonClickListener;

        public Builder(Context context) {
            dialogEditPW = new DialogEditPW(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_edit_pw, null);
            dialogEditPW.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public DialogEditPW.Builder setMessage() {
            return this;
        }

        public DialogEditPW.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public DialogEditPW.Builder setCancelButton(View.OnClickListener listener) {
            this.cancelButtonClickListener = listener;
            return this;
        }

        public DialogEditPW.Builder setOkButton(View.OnClickListener listener) {
            this.okButtonClickListener = listener;
            return this;
        }
        public DialogEditPW ButtonDialog(){
            layout.findViewById(R.id.btn_dialog_editpw_cancel).setOnClickListener(cancelButtonClickListener);
            layout.findViewById(R.id.btn_dialog_editpw_ok).setOnClickListener(okButtonClickListener);
            return dialogEditPW;
        }
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(50, 0, 50, 0);
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.dialogAnimation);
    }
}
