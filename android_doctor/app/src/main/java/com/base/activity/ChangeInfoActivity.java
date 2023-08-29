package com.base.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.base.dao.DoctorDao;
import com.base.dao.PatientDao;
import com.base.util.Config;
import com.base.util.UIUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChangeInfoActivity extends BaseActivity {

    private String mName;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.btnOk)
    Button mBtnOk;
    @InjectView(R.id.etName)
    EditText mEtName;

    @InjectView(R.id.sm)
    TextView sm;

    @OnClick({R.id.btnOk})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                String name = mEtName.getText().toString();
                if (TextUtils.isEmpty(name.trim())) {
                    showMaterialDialog("tip", "No data entered, please fill in again.", "sure", "", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideMaterialDialog();
                        }
                    }, null);
                } else {
                    showWaitingDialog("Please wait a moment");
                    Map<String, String> map = new HashMap<>();
//                    fields.put(UserInfoFieldEnum.Name, name);
                    String action = Config.myAction;

                    map.put("id", Config.getCurId().toString());
                    map.put(action, name);
                    boolean result = false;
                    if (Config.role.equals("doctor")) {
                        result = DoctorDao.getInstance().update(map);
                    } else {
                        result = PatientDao.getInstance().update(map);
                    }
                    if (result) {
                        try {
                            Class aClass = Config.currUser.getClass();
                            Field declaredField = aClass.getDeclaredField(action);
                            declaredField.setAccessible(true);
                            declaredField.set(Config.currUser, name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        UIUtils.showToast("Update succeeded.");
                        Intent intent = new Intent(ChangeInfoActivity.this, MyInfoActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        UIUtils.showToast("Update failed.");
                    }
                }
                break;
        }
    }

    @Override
    public void init() {
        mName = getIntent().getStringExtra("name");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_change_name);
        ButterKnife.inject(this);
        initToolbar();
//        String action = Config.myAction;
        String s = this.getIntent().getExtras().get("sm").toString();
        sm.setText(s);
//        String val = Config.myValue;
//        mEtName.setText(val);
//        mEtName.setSelection(val.length());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change data");
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mBtnOk.setVisibility(View.VISIBLE);
    }

}
