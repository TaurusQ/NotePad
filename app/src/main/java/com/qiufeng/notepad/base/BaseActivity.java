package com.qiufeng.notepad.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.qiufeng.notepad.AppContext;
import com.qiufeng.notepad.R;

import static com.qiufeng.notepad.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //定义用于Log输出的TAG
    private static final String TAG = makeLogTag(BaseActivity.class);
    protected final Activity mContext=this;
    protected Toolbar mToolbar;
    protected InputMethodManager mImm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.setActiveContext(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(hasToolBar()){
            mToolbar= (Toolbar) findViewById(R.id.toolbar);
            ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(true);
        }
        mImm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        findView();
        initView();
        iniToolBar();
        setOnClick();
    }

    protected boolean hasToolBar(){
        return true;
    }

    /**
     * 初始化Toolbar
     * */
    protected void iniToolBar(){
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    /**
     * 获取布局控件
     */
    protected abstract void findView();

    /**
     * 初始化View的一些数据
     */
    protected abstract void initView();

    /**
     * 设置点击监听
     */
    protected abstract void setOnClick();


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
