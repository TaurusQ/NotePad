package com.qiufeng.notepad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.qiufeng.notepad.adapter.NoteRecyclerViewAdapter;
import com.qiufeng.notepad.R;
import com.qiufeng.notepad.model.Note;
import com.qiufeng.notepad.model.NoteBean;
import com.qiufeng.notepad.ui.fragment.NoteDetailFragment;

import java.util.ArrayList;
import java.util.List;

import static com.qiufeng.notepad.utils.LogUtils.LOGD;
import static com.qiufeng.notepad.utils.LogUtils.makeLogTag;

/**
 * An activity representing a list of Notes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoteDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoteListActivity extends AppCompatActivity {
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(NoteListActivity.class);
    //判断是否双屏
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private NoteRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        initView();

        //判断是否需要双屏显示
        if (findViewById(R.id.note_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteRecyclerViewAdapter(NoteBean.ITEMS);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new NoteRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(NoteDetailFragment.ARG_ITEM_ID, adapter.getNoteId(position));
                    NoteDetailFragment fragment = new NoteDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.note_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(NoteListActivity.this, NoteDetailActivity.class);
                    intent.putExtra(NoteDetailFragment.ARG_ITEM_ID, adapter.getNoteId(position));
                    startActivity(intent);
                }
            }
        });
        assert recyclerView != null;
    }
}
