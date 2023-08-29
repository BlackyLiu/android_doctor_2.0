package com.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.base.bean.Appointment;
import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.bean.User;
import com.base.dao.AppointmentDao;
import com.base.dao.DoctorDao;
import com.base.dao.PatientDao;
import com.base.util.Config;
import com.base.util.DateOlds;
import com.base.util.Strings;
import com.base.util.UIUtils;
import com.lqr.optionitemview.OptionItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AppointmentInfoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    //    @InjectView(R.id.llHeader)
//    LinearLayout mLlHeader;
//    @InjectView(R.id.ivHeader)
//    ImageView mIvHeader;
    //工号
    @InjectView(R.id.oivGh)
    OptionItemView oivGh;

    //预约doctor
    @InjectView(R.id.oivYs)
    OptionItemView oivYs;
    //patient
    @InjectView(R.id.oivTr)
    OptionItemView oivTr;

    //工号
    @InjectView(R.id.oivBh)
    OptionItemView oivBh;
    //description
    @InjectView(R.id.oivSm)
    OptionItemView oivSm;
    //Reservation status
    @InjectView(R.id.oivStatus)
    OptionItemView oivStatus;

    //Medical record
    @InjectView(R.id.oivJl)
    OptionItemView oivJl;

    //预约时间
    @InjectView(R.id.oivTime)
    OptionItemView oivTime;

    //contactInformation
    @InjectView(R.id.oivContact)
    OptionItemView oivContact;

    //处方
    @InjectView(R.id.oivCf)
    OptionItemView oivCf;

    @Override
    public void initView() {
        setContentView(R.layout.activity_appointment_info);
        ButterKnife.inject(this);
//        initToolbar();
    }

    @Override
    public void initData() {
//        mOivName.setRightText(curUser.getRealName());
//        mOivAccount.setRightText(curUser.getUsername());
//        pass.setRightText(curUser.getPassword());

        final List<String> records = new ArrayList<>();
        if (Config.role.equals("doctor")) {
            //此时详情页就是patient的信息
            Doctor d = (Doctor) Config.currUser;
            Appointment appointment1 = (Appointment) Config.appointmentInfo;

            Patient p = new Patient();
            p.setId(appointment1.getAppointmentId());
            List<Patient> data = PatientDao.getInstance().query(p);
//            String recodes = "";
            if (d != null && data.size() == 1) {
                p = data.get(0);
                //获取数据
                oivYs.setRightText(Config.getCurShowName());
                oivTr.setRightText(appointment1.getAppointmentName());
                oivSm.setRightText(d.getRemark());
                oivStatus.setRightText(appointment1.getStatus());
//                oivJl.setRightText(appointment1.getMedicalRecord());

                records.add(appointment1.getMedicalRecord());

                oivTime.setRightText(DateOlds.formatDate(appointment1.getAppointmentTime()));
                oivContact.setRightText(p.getContactInformation());
                oivCf.setRightText(appointment1.getPrescription());
                oivBh.setRightText(p.getMedicalTreatmentNo());
                oivGh.setRightText(d.getJobNumber());
            }
        } else {
            //如果是patient
            if (Config.appointmentInfo instanceof Doctor) {
                Doctor d = (Doctor) Config.appointmentInfo;
                //获取数据
                Appointment appointment = new Appointment();
                appointment.setBeAppointmentId(d.getId());
                appointment.setAppointmentId(Config.getCurId());
                Patient p = (Patient) Config.currUser;
                List<Appointment> data = AppointmentDao.getInstance().query(appointment);
                if (data != null && data.size() > 0) {
                    Appointment appointment1 = data.get(0);
                    oivYs.setRightText(d.getRealName());
                    oivTr.setRightText(p.getRealName());
                    oivSm.setRightText(d.getRemark());
                    oivStatus.setRightText(appointment1.getStatus());
                    records.add(appointment1.getMedicalRecord());
//                    oivJl.setRightText(appointment1.getMedicalRecord());
                    oivTime.setRightText(DateOlds.formatDate(appointment1.getAppointmentTime()));
                    oivContact.setRightText(p.getContactInformation());
                    oivCf.setRightText(appointment1.getPrescription());
                    oivBh.setRightText(p.getMedicalTreatmentNo());
                    oivGh.setRightText(d.getJobNumber());
                }
            } else {
                Appointment appointment = (Appointment) Config.appointmentInfo;
                Patient p = (Patient) Config.currUser;
                Doctor t = new Doctor();
                t.setId(appointment.getBeAppointmentId());
                List<Doctor> data = DoctorDao.getInstance().query(t);
                if (data != null && data.size() > 0) {
                    Doctor d = data.get(0);
                    oivYs.setRightText(d.getRealName());
                    oivTr.setRightText(p.getRealName());
                    oivSm.setRightText(d.getRemark());
                    oivStatus.setRightText(appointment.getStatus());
                    records.add(appointment.getMedicalRecord());
//                    oivJl.setRightText(appointment.getMedicalRecord());
                    oivTime.setRightText(DateOlds.formatDate(appointment.getAppointmentTime()));
                    oivContact.setRightText(p.getContactInformation());
                    oivCf.setRightText(appointment.getPrescription());
                    oivBh.setRightText(p.getMedicalTreatmentNo());
                    oivGh.setRightText(d.getJobNumber());
                }
            }

        }
        oivJl.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                String s = "";
                if(records.size()>0){
                    s = records.get(0);
                }
                UIUtils.showToast(Strings.isEmpty(s)?"No record yet.":s);
            }

            @Override
            public void centerOnClick() {
                String s = "";
                if(records.size()>0){
                    s = records.get(0);
                }
                UIUtils.showToast(Strings.isEmpty(s)?"No record yet.":s);
            }

            @Override
            public void rightOnClick() {
                String s = "";
                if(records.size()>0){
                    s = records.get(0);
                }
                UIUtils.showToast(Strings.isEmpty(s)?"No record yet.":s);
            }
        });


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
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(curUser.getRealName());
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
    }
}
