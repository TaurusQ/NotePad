package qiufeng.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import qiufeng.android.R;
import qiufeng.android.adapter.NoteRecyclerViewAdapter;
import qiufeng.android.base.BaseActivity;
import qiufeng.android.base.BaseFragment;
import qiufeng.android.model.NoteInfo;
import qiufeng.android.utils.Constans;

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

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_note_list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (BaseActivity) getActivity();

        initData();

        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteRecyclerViewAdapter(notes);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new NoteRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
                //将NoteInfo对象转换为json数据
                NoteInfo noteInfo = adapter.getNoteInfo(position);
                intent.putExtra(Constans.CURRENT_NOTE_INFO,noteInfo.toJson(noteInfo));
                activity.startActivityForResult(intent, Constans.MODIFY_NOTE_REQUEST_CODE);
            }
        });
    }

    private void initData()
    {
        notes = new ArrayList<NoteInfo>();
        notes.add(new NoteInfo("文字内容",21321,12321312));
        notes.add(new NoteInfo("文字内容",31321,22321312));
        notes.add(new NoteInfo("文字内容",41321,32321312));
        notes.add(new NoteInfo("文字内容",51321,42321312));
    }
}
