package qiufeng.android.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/4/11.
 */

@Table(name = "NoteInfos")
public class NoteInfo extends Model {
    /** 内容 */
    @Expose
    @Column(name = "content")
    private String content;

    /** 创建日期 */
    @Expose
    @Column(name = "create_date")
    private long create_date;

    /** 修改日期 */
    @Expose
    @Column(name = "update_date")
    private long update_date;

    public NoteInfo() {
    }

    //第一次创建时调用
    public NoteInfo(String content, long create_date) {
        this.content = content;
        this.create_date = create_date;
    }

    //修改时调用
    public NoteInfo(String content, long create_date, long update_date) {
        this.content = content;
        this.create_date = create_date;
        this.update_date = update_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public long getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(long update_date) {
        this.update_date = update_date;
    }

    //将NoteInfo对象转换为json
    public String toJson(NoteInfo noteInfo)
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(noteInfo);
    }

    //将json转换为NoteInfo对象
    public NoteInfo fromJson(String json)
    {
        return new Gson().fromJson(json,NoteInfo.class);
    }
}
