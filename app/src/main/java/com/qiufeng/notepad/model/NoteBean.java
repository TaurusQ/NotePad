package com.qiufeng.notepad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qiufeng.notepad.utils.LogUtils.LOGD;
import static com.qiufeng.notepad.utils.LogUtils.makeLogTag;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NoteBean {
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(NoteBean.class);
    public static final List<Note> ITEMS = new ArrayList<Note>();

    public static final Map<String, Note> ITEM_MAP = new HashMap<String, Note>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addNote(createNote(i));
        }
    }

    private static void addNote(Note item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Note createNote(int position) {
        return new Note(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
