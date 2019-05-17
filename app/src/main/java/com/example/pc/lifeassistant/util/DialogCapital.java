package com.example.pc.lifeassistant.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.lifeassistant.R;

/**
 * Created by pc on 2019/5/16.
 */

public class DialogCapital extends Dialog {
    public DialogCapital(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private String type;
        private String date;
        private String remakes;
        private View contentView;
        private DialogCapital dialogCapital;
        private View.OnClickListener singleButtonClickListener;
        private View layout;

        public Builder(Context context) {
            dialogCapital = new DialogCapital(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_capital, null);
            dialogCapital.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        public Builder setMessage(String type, String date, String remakes) {
            this.type = type;
            this.date = date;
            this.remakes = remakes;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public Builder setSingleButton(View.OnClickListener listener) {
            this.singleButtonClickListener = listener;
            return this;
        }

        public DialogCapital createSingleButtonDialog() {
            layout.findViewById(R.id.img_dialog_capital_back).setOnClickListener(singleButtonClickListener);
            create();
            return dialogCapital;

        }

        private void create() {
            if (type != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.tv_dialog_capital_type)).setText(type);
                ((TextView) layout.findViewById(R.id.tv_dialog_capital_date)).setText(date);
                ((TextView) layout.findViewById(R.id.tv_dialog_capital_remakes)).setText(remakes);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.dialog_capital_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_capital_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialogCapital.setContentView(layout);
            dialogCapital.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialogCapital.setCanceledOnTouchOutside(true);        //用户不能通过点击对话框之外的地方取消对话框显示
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
        getWindow().getDecorView().setPadding(140, 0, 140, 0);
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.dialogAnimation);
    }
}
