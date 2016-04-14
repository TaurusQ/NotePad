package qiufeng.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qiufeng.android.R;
import qiufeng.android.listener.ISearchAdapter;
import qiufeng.android.listener.ItemClickListener;
import qiufeng.android.listener.ItemLongClickListener;
import qiufeng.android.model.NoteInfo;
import qiufeng.android.utils.DBHelper;
import qiufeng.android.utils.DateUtils;
import qiufeng.android.utils.Settings;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(NoteRecyclerViewAdapter.class);
    private List<NoteInfo> noteInfos;
    private Context context;
    private Settings settings;
    /**
     * 适配器类型
     */
    private AdapterType type = AdapterType.NOTE_TYPE;
    ISearchAdapter data;

    /**
     * 条目点击事件
     */
    private ItemClickListener mItemClickListener;
    /**
     * 长按点击事件
     */
    private ItemLongClickListener mItemLongClickListener;

    /**
     * 设置点击监听器
     *
     * @param mItemClickListener 监听器
     */
    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /**
     * 设置长按监听器
     *
     * @param mItemLongClickListener 监听器
     */
    public void setItemLongClickListener(ItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }


    public NoteRecyclerViewAdapter(Context context) {
        this.context = context;
        //默认为普通类型
        setDataAndType(AdapterType.NOTE_TYPE, null);
        settings = new Settings(context);
        getNoteInfos();
    }

    /**
     * 获取所有的笔记
     */
    public void getNoteInfos() {
        switch (type) {
            case NOTE_TYPE:
//                noteInfos = DBHelper.getAllNote();
                noteInfos = settings.getAllNote();
                break;
            case SEARCH_TYPE:
                noteInfos = data.get();
                break;
        }
    }

    /**
     * 设置数据和数据类型
     *
     * @param type 类型
     * @param data 数据
     */
    public void setDataAndType(AdapterType type, ISearchAdapter data) {
        this.type = type;
        this.data = data;
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
        holder.noteContent.setText(noteInfo.getContent());
        holder.noteCreate.setText("创建于："+ DateUtils.longToString(noteInfo.getCreate_date()));
        holder.noteUpdate.setText("修改于："+DateUtils.longToString(noteInfo.getUpdate_date()));
        LOGI(TAG,"on bind view holder执行");

        holder.noteCreate.setVisibility(settings.isToShowCreate()?View.VISIBLE:View.GONE);
        holder.noteUpdate.setVisibility(settings.isToShowUpdate()?View.VISIBLE:View.GONE);
        holder.noteContent.setMaxLines(settings.isToShowAllContent()?1000:2);
    }

    //通过位置获取对应的NoteInfo对象
    public NoteInfo getNoteInfo(int position) {
        return noteInfos.get(position);
    }

    @Override
    public int getItemCount() {
        return noteInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        @Bind(R.id.note_content)
        TextView noteContent;
        @Bind(R.id.note_create)
        TextView noteCreate;
        @Bind(R.id.note_update)
        TextView noteUpdate;

        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(getNoteInfo(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mItemLongClickListener.onItemLongClick(getNoteInfo(getAdapterPosition()));
            return true;
        }
    }
}
