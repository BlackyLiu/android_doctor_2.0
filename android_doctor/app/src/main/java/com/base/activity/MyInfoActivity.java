package com.base.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.bean.User;
import com.base.dao.DoctorDao;
import com.base.util.Config;
import com.base.util.UIUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.lqr.optionitemview.OptionItemView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyInfoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    //    @InjectView(R.id.llHeader)
//    LinearLayout mLlHeader;
//    @InjectView(R.id.ivHeader)
//    ImageView mIvHeader;
    @InjectView(R.id.oivNum)
    OptionItemView oivNum;

    //realName
    @InjectView(R.id.oivName)
    OptionItemView mOivName;
    //帐号名
    @InjectView(R.id.oivEmail)
    OptionItemView oivEmail;


    @InjectView(R.id.oivPass)
    OptionItemView oivPass;

    @InjectView(R.id.oivRole)
    OptionItemView oivRole;

    @InjectView(R.id.oivContact)
    OptionItemView oivContact;

    @InjectView(R.id.oivBirthday)
    OptionItemView oivBirthday;

    @InjectView(R.id.oivDepartments)
    OptionItemView oivDepartments;

    @InjectView(R.id.oivRemark)
    OptionItemView oivRemark;

    @InjectView(R.id.oivAppointment)
    SwitchButton oivAppointment;


    @InjectView(R.id.yyLinear)
    LinearLayout yyLinear;

    private Object curUser = Config.currUser;

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_info);
        ButterKnife.inject(this);
        initData();
        initToolbar();
    }

    @Override
    public void initData() {
        if (Config.role.equals("doctor")) {
            Doctor doctor = (Doctor) Config.currUser;
            //隐藏样式
            oivBirthday.setVisibility(View.GONE);
            oivContact.setVisibility(View.GONE);
            oivNum.setLeftText("jobNumber");
            //设置数据
            oivNum.setRightText(doctor.getJobNumber());
            mOivName.setRightText(doctor.getRealName());
            oivEmail.setRightText(doctor.getEmail());
            oivPass.setRightText(doctor.getPassword());
            oivRole.setRightText("doctor");
            oivDepartments.setRightText(doctor.getDepartments());
            oivRemark.setRightText(doctor.getRemark());
            oivAppointment.setChecked(doctor.getAppointment()==null?false:doctor.getAppointment());
            oivAppointment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                                              Log.d("===", "isChecked:" + isChecked);
                                                              Doctor d = (Doctor) Config.currUser;
                                                              d.setAppointment(isChecked);
                                                              DoctorDao.getInstance().update(d);
                                                              UIUtils.showToast(isChecked ? "Appointment opening" : "Appointment closing");
                                                          }
                                                      }

            );
        } else {
            Patient patient = (Patient) Config.currUser;
            oivDepartments.setVisibility(View.GONE);
            //预约
            yyLinear.setVisibility(View.GONE);
            oivNum.setRightText(patient.getMedicalTreatmentNo());
            mOivName.setRightText(patient.getRealName());
            oivEmail.setRightText(patient.getEmail());
            oivPass.setRightText(patient.getPassword());
            oivRole.setRightText("patient");
            oivContact.setRightText(patient.getContactInformation());
            oivBirthday.setRightText(patient.getBirthday());
        }


    }


    @OnClick({R.id.oivName, R.id.oivPass, R.id.oivContact, R.id.oivBirthday, R.id.oivDepartments, R.id.oivRemark})
    public void click(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.oivName:
//                String realName = curUser.getRealName();
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "realName";
                intent.putExtra("sm", "A good name can make your friends remember you more easily.");
//                Config.myValue = realName;
                startActivity(intent);
                break;
            case R.id.oivPass:
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "password";
                intent.putExtra("sm", "Please reset your password.");
                startActivity(intent);
                break;
            case R.id.oivContact:
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "contactInformation";
                intent.putExtra("sm", "Please reset your contactInformation");
                startActivity(intent);
                break;
            case R.id.oivBirthday:
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "birthday";
                intent.putExtra("sm", "Please reset your birthday");
                startActivity(intent);
                break;
            case R.id.oivDepartments:
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "departments";
                intent.putExtra("sm", "Please reset your departments");
                startActivity(intent);
                break;
            case R.id.oivRemark:
                intent = new Intent(MyInfoActivity.this, ChangeInfoActivity.class);
                Config.myAction = "remark";
                intent.putExtra("sm", "Please reset your description");
                startActivity(intent);
                break;

        }
        finish(); //结束当前页面 生命周期
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
        getSupportActionBar().setTitle("personal information");
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
    }
}
