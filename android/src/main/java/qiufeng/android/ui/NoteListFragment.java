package qiufeng.android.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import qiufeng.android.R;
import qiufeng.android.adapter.AdapterType;
import qiufeng.android.adapter.NoteRecyclerViewAdapter;
import qiufeng.android.base.BaseActivity;
import qiufeng.android.base.BaseFragment;
import qiufeng.android.listener.ISearchAdapter;
import qiufeng.android.listener.ItemClickListener;
import qiufeng.android.listener.ItemLongClickListener;
import qiufeng.android.model.NoteInfo;
import qiufeng.android.utils.Constans;
import qiufeng.android.utils.DBHelper;
import qiufeng.android.utils.Settings;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class NoteListFragment extends BaseFragment {
    public static final String TAG = makeLogTag(NoteListFragment.class);

    public BaseActivity activity;
    public NoteRecyclerViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    public List<NoteInfo> notes;
    public NoteInfo selectNote;
    public Settings settings;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
//    @Bind(R.id.fab)
//    FloatingActionButton fab;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_note_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (BaseActivity) getActivity();
        settings = new Settings(activity);

        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteRecyclerViewAdapter(activity);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(NoteInfo info) {
                Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
                //将NoteInfo对象转换为json数据
                selectNote = info;
                intent.putExtra(Constans.CURRENT_NOTE_INFO, selectNote.toJson(selectNote));
                activity.startActivityForResult(intent, Constans.MODIFY_NOTE_REQUEST_CODE);
            }
        });

        adapter.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(NoteInfo info) {
                //长按执行删除操作
                selectNote = info;
                AlertDialog dialog = new AlertDialog
                        .Builder(activity, R.style.Base_Theme_AppCompat_Light_Dialog_Alert)
                        .create();
                dialog.setTitle("确定删除吗？");
                dialog.setButton(Dialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNote();
                    }
                });
                dialog.show();
            }
        });
    }

    /**
    @OnClick(R.id.fab)
    public void clickFab()
    {
        activity.startActivityForResult(new Intent(activity, NoteDetailsActivity.class)
                , Constans.WRITE_NOTE_REQUEST_CODE);
    }
    */

    @Override
    public void onResume() {
        super.onResume();
        notifyDataChanged();
    }

    //刷新数据
    public void notifyDataChanged()
    {
        adapter.getNoteInfos();
        adapter.notifyDataSetChanged();
    }

    //保存笔记
    public void saveNote(String content,long update)
    {
        selectNote.setContent(content);
        selectNote.setUpdate_date(update);
        selectNote.save();
        notifyDataChanged();
    }

    //删除笔记
    public void deleteNote()
    {
        selectNote.delete();
        notifyDataChanged();
    }

    //搜索笔记
    public void searchNote(String newText)
    {
//        final List<NoteInfo> infos = DBHelper.getAllNote();
        final List<NoteInfo> infos = settings.getAllNote();
        int size = infos.size();
        for (int i = size - 1; i >= 0; i--) {
            String content = infos.get(i).getContent();
            if (!content.contains(newText)) {
                infos.remove(i);
            }
        }
        adapter.setDataAndType(AdapterType.SEARCH_TYPE, new ISearchAdapter() {
            @Override public List<NoteInfo> get() {
                return infos;
            }
        });
        notifyDataChanged();
    }
}
