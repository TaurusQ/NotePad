package qiufeng.android.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.query.Select;

import java.util.List;

import qiufeng.android.model.NoteInfo;

/**
 * Created by Administrator on 2016/4/12.
 */
public class DBHelper {

    /**
     * 获取所有笔记
     * @return 所有笔记
     * */
    public static List<NoteInfo> getAllNoteOrderByUpdate()
    {
        return new Select().from(NoteInfo.class).orderBy("update_date DESC").execute();
    }

    public static List<NoteInfo> getAllNoteOrderByCreate()
    {
        return new Select().from(NoteInfo.class).orderBy("create_date DESC").execute();
    }

    /**
     * 搜索包含关键字的笔记
     * @param required 关键字
     * @return 查到的笔记
     * */
    public static List<NoteInfo> searchNote(String required)
    {
        return new Select().from(NoteInfo.class).where("content = ?", required).execute();
    }

    /**
     * 删除指定的笔记
     * @param
     * */
    public static void deleteNote(NoteInfo noteInfo)
    {
        noteInfo.delete(NoteInfo.class,1);
    }

}
