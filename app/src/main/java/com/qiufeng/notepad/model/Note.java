package com.qiufeng.notepad.model;

import java.io.Serializable;
import java.util.Date;

import static com.qiufeng.notepad.utils.LogUtils.LOGD;

/**
 * Created by Administrator on 2016/2/28.
 */

public class Note implements Serializable {
    public String id;
    public String title;
    public String content;
    private Date createdAt;
    private Date updatedAt;

    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("Note [id=").append(id)
                .append(", title=").append(title)
                .append(", content=").append(content)
                .append(", createdAt=").append(createdAt)
                .append(", updatedAt=").append(updatedAt)
                .append("]").toString();
    }

}