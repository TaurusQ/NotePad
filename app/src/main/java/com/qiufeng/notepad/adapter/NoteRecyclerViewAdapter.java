package com.qiufeng.notepad.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiufeng.notepad.R;
import com.qiufeng.notepad.model.Note;
import com.qiufeng.notepad.model.NoteBean;

import java.util.List;

import static com.qiufeng.notepad.utils.LogUtils.LOGD;
import static com.qiufeng.notepad.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class NoteRecyclerViewAdapter
        extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>{
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(NoteRecyclerViewAdapter.class);
    private final List<Note> notes;
    //设置点击事件的接口
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public NoteRecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).id);
        holder.content.setText(notes.get(position).title);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    public String getNoteId(int position)
    {
        return notes.get(position).id;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView content;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.id);
            content = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + content.getText() + "'";
        }
    }

}
