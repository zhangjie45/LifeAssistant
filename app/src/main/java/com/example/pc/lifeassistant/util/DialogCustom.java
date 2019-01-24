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
 * Created by pc on 2019/1/6.
 */

public class DialogCustom extends Dialog {
    public DialogCustom(@NonNull Context context) {
        super(context);
    }

    public DialogCustom(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private String title;
        private String date;
        private String remakes;
        private View contentView;
        private DialogCustom dialogCustom;
        private View.OnClickListener singleButtonClickListener;
        private View layout;

        public Builder(Context context) {
            dialogCustom = new DialogCustom(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_custom, null);
            dialogCustom.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setMessage(String title, String date, String remakes) {
            this.title = title;
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

        public DialogCustom createSingleButtonDialog() {
            layout.findViewById(R.id.img_dialog_custom_back).setOnClickListener(singleButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“返回”
//            if (singleButtonText != null) {
//                ((Button) layout.findViewById(R.id.singleButton)).setText(singleButtonText);
//            } else {
//                ((Button) layout.findViewById(R.id.singleButton)).setText("返回");
//            }
            create();
            return dialogCustom;

        }

        private void create() {
            if (title != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.tv_dialog_custom_title)).setText(title);
                ((TextView) layout.findViewById(R.id.tv_dialog_custom_date)).setText(date);
                ((TextView) layout.findViewById(R.id.tv_dialog_custom_remakes)).setText(remakes);

            } else if (contentView != null) {       //如果使用Builder的setContentview()方法传入了布局，则使用传入的布局
                ((LinearLayout) layout.findViewById(R.id.dialog_custom_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_custom_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialogCustom.setContentView(layout);
            dialogCustom.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialogCustom.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
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

    }
}
