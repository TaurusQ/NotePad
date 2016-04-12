package qiufeng.android.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiufeng.android.R;
import qiufeng.android.base.BaseActivity;
import qiufeng.android.utils.Constans;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = makeLogTag(MainActivity.class);
    public NoteListFragment noteListFragment;
    public ExampleFragment exampleFragment;

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
        startActivityForResult(new Intent(MainActivity.this,NoteDetailsActivity.class)
            , Constans.WRITE_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LOGI(TAG, "requestCode的值：" + requestCode);
        LOGI(TAG, "resultCode的值：" + resultCode);
        LOGI(TAG, "data的值：" + data.toString());

        if (resultCode != RESULT_OK){
            return;
        }

        //新建笔记
        if (requestCode == Constans.WRITE_NOTE_REQUEST_CODE){

        }

        //修改笔记
        if (requestCode == Constans.MODIFY_NOTE_REQUEST_CODE){

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
//        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                //展开搜索框
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                //关闭搜索框
//                return true;
//            }
//        });
//        return true;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击确认键时执行（不执行任何操作）
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字改变时执行查询操作

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            selectFragment(0);
        } else if (id == R.id.nav_gallery) {
            selectFragment(1);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
                if (exampleFragment == null)
                {
                    exampleFragment = new ExampleFragment();
                    ft.add(R.id.fl_container,exampleFragment);
                }
                else
                {
                    ft.show(exampleFragment);
                }
                break;
        }
        ft.commit();
    }

    public void initAllPage(FragmentTransaction ft) {
        if (noteListFragment != null) {
            ft.hide(noteListFragment);
        }
        if (exampleFragment != null)
        {
            ft.hide(exampleFragment);
        }
    }
}
