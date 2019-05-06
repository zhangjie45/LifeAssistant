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
 * Created by pc on 2019/5/6.
 */

public class DialogForgetPw extends Dialog {
    public DialogForgetPw(@NonNull Context context) {
        super(context);
    }

    public DialogForgetPw(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private DialogForgetPw dialogForgetPw;
        private View contentView;
        private View layout;
        private View.OnClickListener cancelButtonClickListener;
        private View.OnClickListener okButtonClickListener;

        public Builder(Context context) {
            dialogForgetPw = new DialogForgetPw(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_forget_pw, null);
            dialogForgetPw.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        public DialogForgetPw.Builder setMessage() {
            return this;
        }

        public DialogForgetPw.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public DialogForgetPw.Builder setCancelButton(View.OnClickListener listener) {
            this.cancelButtonClickListener = listener;
            return this;
        }

        public DialogForgetPw.Builder setOkButton(View.OnClickListener listener) {
            this.okButtonClickListener = listener;
            return this;
        }

        public DialogForgetPw ButtonDialog() {
            layout.findViewById(R.id.iv_dialog_forgetPw_close).setOnClickListener(cancelButtonClickListener);
            layout.findViewById(R.id.iv_send_forgetPw).setOnClickListener(okButtonClickListener);
            return dialogForgetPw;
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
