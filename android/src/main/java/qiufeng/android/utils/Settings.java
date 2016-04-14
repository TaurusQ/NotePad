package qiufeng.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import qiufeng.android.R;
import qiufeng.android.model.NoteInfo;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/4/13.
 */
public class Settings {
    public static final String TAG = makeLogTag(Settings.class);

    private Context context;
    private SharedPreferences settings;

    public Settings(Context context)
    {
        this.context = context;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //排序设置
    public List<NoteInfo> getAllNote()
    {
        String str =settings.getString(Constans.NOTE_ORDER,"1");
        String [] strs =context.getResources().getStringArray(R.array.pref_settings_list_values);
        if (TextUtils.equals(str,strs[0])){
            LOGI(TAG,"按创建日期排序");
            return DBHelper.getAllNoteOrderByCreate();
        }else {
            LOGI(TAG,"按修改日期排序");
            return DBHelper.getAllNoteOrderByUpdate();
        }
    }

    //显示设置
    public boolean isToShowCreate()
    {
        LOGI(TAG,"是否显示创建日期："+settings.getBoolean(Constans.SHOW_NOTE_CREATE,true));
        return settings.getBoolean(Constans.SHOW_NOTE_CREATE,true);
    }

    public boolean isToShowUpdate()
    {
        LOGI(TAG,"是否显示修改日期："+settings.getBoolean(Constans.SHOW_NOTE_UPDATE,true));
        return settings.getBoolean(Constans.SHOW_NOTE_UPDATE,true);
    }

    public boolean isToShowAllContent()
    {
        LOGI(TAG,"是否显示全部内容："+settings.getBoolean(Constans.SHOW_ALL_CONTENT,true));
        return settings.getBoolean(Constans.SHOW_ALL_CONTENT,true);
    }
}
