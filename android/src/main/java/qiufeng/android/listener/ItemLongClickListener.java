package qiufeng.android.listener;

import qiufeng.android.model.NoteInfo;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface ItemLongClickListener {
    /**
     * 某个信息长按点击事件
     * @param info 笔记信息
     */
    void onItemLongClick(NoteInfo info);
}
