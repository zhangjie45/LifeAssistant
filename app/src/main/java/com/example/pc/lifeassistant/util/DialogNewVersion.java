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
 * Created by pc on 2019/4/21.
 */

public class DialogNewVersion extends Dialog {
    public DialogNewVersion(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public DialogNewVersion(@NonNull Context context) {
        super(context);
    }
    public static class Builder {
        private String content;
        private View contentView;
        private DialogNewVersion dialogNewVersion;
        private View.OnClickListener closeButtonClickListener;
        private View.OnClickListener okButtonClickListener;
        private View layout;
        public Builder(Context context) {
            dialogNewVersion = new DialogNewVersion(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_new_version, null);
            dialogNewVersion.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        public DialogNewVersion.Builder setMessage(String content) {
            this.content = content;
            return this;
        }

        public DialogNewVersion.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public DialogNewVersion.Builder closeChangeButton(View.OnClickListener listener) {
            this.closeButtonClickListener = listener;
            return this;
        }

        public DialogNewVersion.Builder okDeleteButton(View.OnClickListener listener) {
            this.okButtonClickListener = listener;
            return this;
        }
        public DialogNewVersion DialogNerVersionDialog() {
            layout.findViewById(R.id.iv_dialog_new_version_close).setOnClickListener(closeButtonClickListener);
            layout.findViewById(R.id.iv_dialog_new_version_ok).setOnClickListener(okButtonClickListener);
            create();
            return dialogNewVersion;
        }
        private void create() {
            if (content != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.tv_dialog_new_version_content)).setText(content);
            } else if (contentView != null) {       //如果使用Builder的setContentview()方法传入了布局，则使用传入的布局
                ((LinearLayout) layout.findViewById(R.id.dialog_new_version_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_new_version_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            dialogNewVersion.setContentView(layout);
            dialogNewVersion.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialogNewVersion.setCanceledOnTouchOutside(false);        //
        }

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(40, 0, 40, 0);
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.dialogAnimation);

    }

}
