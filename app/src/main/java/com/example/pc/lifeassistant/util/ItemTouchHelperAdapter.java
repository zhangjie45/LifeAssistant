package com.example.pc.lifeassistant.util;

/**
 * Created by pc on 2018/11/24.
 */

public interface ItemTouchHelperAdapter {
    //移动item
    public void onItemMove(int fromPosition, int toPosition);

    //删除item
    public void onItemDelete(int position);
}
