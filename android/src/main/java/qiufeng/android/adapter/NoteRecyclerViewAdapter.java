package qiufeng.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qiufeng.android.R;
import qiufeng.android.model.NoteInfo;

import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(NoteRecyclerViewAdapter.class);
    private final List<NoteInfo> notes;
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

    public NoteRecyclerViewAdapter(List<NoteInfo> notes) {
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NoteInfo noteInfo = getNoteInfo(position);
//        holder.title.setText(noteInfo.getCreate_date());
        holder.content.setText(noteInfo.getContent());

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

    public NoteInfo getNoteInfo(int position)
    {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView content;
        public final TextView date;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.note_title);
            content = (TextView) view.findViewById(R.id.note_content);
            date = (TextView) view.findViewById(R.id.note_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + content.getText() + "'";
        }
    }
}
