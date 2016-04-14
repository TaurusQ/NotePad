package qiufeng.android.listener;

import java.util.List;

import qiufeng.android.model.NoteInfo;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface ISearchAdapter {
    /**
     * 获取笔记信息
     * @return
     */
    List<NoteInfo> get();
}
