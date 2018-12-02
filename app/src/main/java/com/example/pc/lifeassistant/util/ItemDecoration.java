package com.example.pc.lifeassistant.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.pc.lifeassistant.R;

/**
 * Created by pc on 2018/12/2.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    Context context;

    public ItemDecoration(Context context) {
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3); //时间轴线的宽度。
        mPaint.setColor(Color.BLACK); //时间轴线的颜色。
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float left = dip2px(context, 14 + 10);
            float bottom = view.getBottom();
            c.drawLine(left, dip2px(context, (50 - 10) / 2), left, bottom, mPaint);
        }
    }


    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
