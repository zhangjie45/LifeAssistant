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
 * Created by pc on 2019/1/6.
 */

public class DialogDelChange extends Dialog {
    public DialogDelChange(@NonNull Context context) {
        super(context);
    }

    public DialogDelChange(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private View contentView;
        private DialogDelChange dialogDelChange;
        private View.OnClickListener changeButtonClickListener;
        private View.OnClickListener deleteButtonClickListener;
        private View layout;

        public Builder(Context context) {
            dialogDelChange = new DialogDelChange(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_delete_change, null);
            dialogDelChange.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public DialogDelChange.Builder setMessage() {
            return this;
        }

        public DialogDelChange.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public DialogDelChange.Builder setChangeButton(View.OnClickListener listener) {
            this.changeButtonClickListener = listener;
            return this;
        }

        public DialogDelChange.Builder setDeleteButton(View.OnClickListener listener) {
            this.deleteButtonClickListener = listener;
            return this;
        }

        public DialogDelChange DelChangeButtonDialog() {
            layout.findViewById(R.id.ll_dialogDelChange_dialog_change).setOnClickListener(changeButtonClickListener);
            layout.findViewById(R.id.ll_dialogDelChange_dialog_delete).setOnClickListener(deleteButtonClickListener);
            return dialogDelChange;
        }


    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 10);
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.dialogAnimation);

    }
}
