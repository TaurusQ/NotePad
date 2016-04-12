package qiufeng.android.ui;


import android.content.Intent;
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
    public NoteInfo noteInfo;

//    private int requestCode = -1;

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
//                requestCode = Constans.MODIFY_NOTE_REQUEST_CODE;
            } else {
                setTitle("创建笔记");
                noteInfo.setCreate_date(new Date().getTime());
                tvCreate.setText("创建于：" + DateUtils.longToString(noteInfo.getCreate_date()));
//                requestCode = Constans.WRITE_NOTE_REQUEST_CODE;
            }
        }
    }

    private void initExsitData() {
        if (noteInfo != null) {
            etContent.setText(noteInfo.getContent());
            tvCreate.setText("创建于："+noteInfo.getCreate_date());
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveData();
        finish();
    }

    //退出时保存数据
    private void saveData() {
        noteInfo.setContent(etContent.getText().toString());
        noteInfo.setUpdate_date(new Date().getTime());
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
}
