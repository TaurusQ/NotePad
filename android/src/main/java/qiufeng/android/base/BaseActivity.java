package qiufeng.android.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import qiufeng.android.AppContext;
import qiufeng.android.R;

import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public abstract class BaseActivity extends AppCompatActivity{
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(BaseActivity.class);
    protected final Activity mContext=this;
    protected InputMethodManager mImm;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.setActiveContext(this);
        onCreate();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(AppContext.getActiveContext(getClass().getSimpleName()));
        /**
        if(hasToolBar()){
            ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(true);
        }*/
        mImm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        iniToolBar();
        initView();
    }

    protected boolean hasToolBar(){
        return true;
    }

    //使用Toast
    public void showTip(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //使用Snackbar
    public void showTip(View fab,String message)
    {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    /**
     * 初始化Toolbar
     * */
    protected void iniToolBar(){
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    /**
     * 获取布局控件
     */
    protected abstract void onCreate();

    /**
     * 初始化View的一些数据
     */
    protected abstract void initView();


    /**
     * 隐藏当前activity的键盘
     */
    public void hideKeyBoard(){
        View v = ((Activity) mContext).getCurrentFocus();
        if (v == null)
            return;
        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
