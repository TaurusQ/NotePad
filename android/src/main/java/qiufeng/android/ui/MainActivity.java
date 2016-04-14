package qiufeng.android.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiufeng.android.R;
import qiufeng.android.base.BaseActivity;
import qiufeng.android.model.NoteInfo;
import qiufeng.android.utils.Constans;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = makeLogTag(MainActivity.class);
    public NoteListFragment noteListFragment;
    public AboutFragment aboutFragment;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar();
    }

    private void setUpToolbar() {

    }

    @Override
    protected void initView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        selectFragment(0);
    }


    @OnClick(R.id.fab)
    public void clickFab() {
        //跳转到新建note页面
        startActivityForResult(new Intent(MainActivity.this, NoteDetailsActivity.class)
                , Constans.WRITE_NOTE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        LOGI(TAG, "requestCode的值：" + requestCode);
//        LOGI(TAG, "resultCode的值：" + resultCode);
//        LOGI(TAG, "data的值：" + data.toString());

        if (resultCode != RESULT_OK){
            return;
        }

        //读取传递过来的json信息
        NoteInfo noteInfo = new NoteInfo();
        noteInfo = noteInfo.fromJson(data.getStringExtra(Constans.SAVE_NOTE_INFO));

        //新建笔记
        if (requestCode == Constans.WRITE_NOTE_REQUEST_CODE){
            //如果noteInfo的内容不为空，则保存
            if (!TextUtils.isEmpty(noteInfo.getContent())) {
                saveNote(noteInfo);
            }
        }

        //修改笔记
        if (requestCode == Constans.MODIFY_NOTE_REQUEST_CODE){
            //如果修改之后的内容为空，则删除
            if (TextUtils.isEmpty(noteInfo.getContent())) {
                noteListFragment.deleteNote();
            }
            else {
                noteListFragment.saveNote(noteInfo.getContent(),noteInfo.getUpdate_date());
            }
        }
    }

    //保存笔记
    private void saveNote(NoteInfo noteInfo) {
        noteInfo.save();
        noteListFragment.notifyDataChanged();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(navigationView)) {
            drawer.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击确认键时执行（不执行任何操作）
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字改变时执行查询操作
                noteListFragment.searchNote(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            selectFragment(0);
        } else if (id == R.id.nav_about) {
            selectFragment(1);
        }
        else if (id == R.id.nav_setting) {
            startActivity(new Intent(this,SettingsActivity.class));
        }
        else if (id == R.id.nav_create) {
            startActivityForResult(new Intent(MainActivity.this, NoteDetailsActivity.class)
                    , Constans.WRITE_NOTE_REQUEST_CODE);
        }
        drawer.closeDrawer(navigationView);
        return true;
    }

    public void selectFragment(int index) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        initAllPage(ft);
        switch (index) {
            case 0:
                if (noteListFragment == null) {
                    noteListFragment = new NoteListFragment();
                    ft.add(R.id.fl_container, noteListFragment);
                } else {
                    ft.show(noteListFragment);
                }
                break;
            case 1:
                if (aboutFragment == null)
                {
                    aboutFragment = new AboutFragment();
                    ft.add(R.id.fl_container, aboutFragment);
                }
                else
                {
                    ft.show(aboutFragment);
                }
                break;
        }
        ft.commit();
    }

    public void initAllPage(FragmentTransaction ft) {
        if (noteListFragment != null) {
            ft.hide(noteListFragment);
        }
        if (aboutFragment != null)
        {
            ft.hide(aboutFragment);
        }
    }
}
