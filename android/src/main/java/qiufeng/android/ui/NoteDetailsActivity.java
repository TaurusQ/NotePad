package qiufeng.android.ui;


import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import qiufeng.android.R;
import qiufeng.android.base.BaseActivity;
import qiufeng.android.model.NoteInfo;
import qiufeng.android.utils.Constans;
import qiufeng.android.utils.DateUtils;

import static qiufeng.android.utils.LogUtils.LOGI;
import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class NoteDetailsActivity extends BaseActivity {
    public static final String TAG = makeLogTag(NoteDetailsActivity.class);
    public static final String ARG_NOTE_ID = "note_id";

    public String receiveJson,sendJson;
    public String contentBefore,contentAfter;
    public NoteInfo noteInfo;

    private ShareActionProvider shareAction;

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_create)
    TextView tvCreate;

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_note_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        noteInfo = new NoteInfo();

        //接收传递过来的值
        if (intent != null) {
            receiveJson = intent.getStringExtra(Constans.CURRENT_NOTE_INFO);
            if (!TextUtils.isEmpty(receiveJson)) {
                noteInfo = noteInfo.fromJson(receiveJson);
                initExsitData();
                setTitle("修改笔记");
            } else {
                setTitle("创建笔记");
                noteInfo.setCreate_date(new Date().getTime());
                //保存创建时间
                tvCreate.setText("创建于：" + DateUtils.longToString(noteInfo.getCreate_date()));
            }
        }

        //读取已有的内容
        contentBefore = noteInfo.getContent();
    }

    private void initExsitData() {
        if (noteInfo != null) {
            etContent.setText(noteInfo.getContent());
            tvCreate.setText("创建于："+DateUtils.longToString(noteInfo.getCreate_date()));
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        /**
        MenuItem item = menu.findItem(R.id.action_share);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, etContent.getText().toString());
        shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        shareAction.setShareIntent(intent);
        shareAction.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                startActivity(intent);
                return true;
            }
        });
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //点击向上键
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_settings:
                showTip("点击");
                break;
            case R.id.action_share:
                actionShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        saveData();
        finish();
    }

    private void actionShare() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "my string");
        i.putExtra(Intent.EXTRA_TEXT, etContent.getText().toString());
        startActivity(i);

    }
    //退出时保存数据
    private void saveData() {
        contentAfter = etContent.getText().toString();

        //如果内容没有改变则保存
        if (!TextUtils.equals(contentAfter,contentBefore)) {
            LOGI(TAG,"保存的内容不相同");
            //设置笔记的内容和修改时间
            noteInfo.setContent(contentAfter);
            noteInfo.setUpdate_date(new Date().getTime());
            Intent data = new Intent();
            //将对象信息转换成json信息传送
            data.putExtra(Constans.SAVE_NOTE_INFO,noteInfo.toJson(noteInfo));
            setResult(RESULT_OK, data);
        }else{
            LOGI(TAG,"保存的内容相同");
            setResult(RESULT_CANCELED);
        }

    }
}
