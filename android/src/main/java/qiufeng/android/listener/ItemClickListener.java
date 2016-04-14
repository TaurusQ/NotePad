package qiufeng.android.listener;

import qiufeng.android.model.NoteInfo;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface ItemClickListener {
    /**
     * 某个笔记被点击
     * @param info 笔记信息
     */
    void onItemClick(NoteInfo info);
}
