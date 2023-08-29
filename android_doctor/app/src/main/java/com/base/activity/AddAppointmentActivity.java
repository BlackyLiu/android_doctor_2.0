package com.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.bean.Appointment;
import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.dao.AppointmentDao;
import com.base.dao.PatientDao;
import com.base.util.Config;
import com.base.util.DateOlds;
import com.base.util.Regs;
import com.base.util.Strings;
import com.base.util.Threads;
import com.base.util.UIUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddAppointmentActivity extends BaseActivity {
    //预约doctor
    @InjectView(R.id.yyYs)
    EditText yyYs;

    //description
    @InjectView(R.id.yySm)
    EditText yySm;

    //预约时间
    @InjectView(R.id.yySj)
    EditText yySj;

    @InjectView(R.id.btnOk)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.inject(this);
        Patient p = (Patient) Config.currUser;
        Doctor d = (Doctor) Config.appointmentInfo;
        yyYs.setText(d.getRealName());
        yySm.setText(d.getRemark());
    }


    @OnClick({R.id.btnOk})
    public void click(View view) {

        showMaterialDialog("", "Whether to pay 10 yuan to start an appointment?","yes", "no", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = yySj.getText().toString();
                if(!Strings.isEmpty(s)){
                    UIUtils.showToast("Please fill in the appointment time.");
                }
                try {
                    Date date = DateOlds.parseD(s);
                    Patient p = (Patient) Config.currUser;
                    Doctor d = (Doctor) Config.appointmentInfo;
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(p.getId());
                    appointment.setBeAppointmentId(d.getId());
                    appointment.setAppointmentName(p.getRealName());
                    appointment.setBeAppointmentName(d.getRealName());
                    appointment.setStatus("Reservation in progress");
                    appointment.setAppointmentTime(date);
                    AppointmentDao.getInstance().add(appointment);
                    UIUtils.showToast("Appointment success");
                    Intent intent = new Intent(AddAppointmentActivity.this,AppointmentActivity.class);
                    startActivity(intent);
                    finish();
                } catch (ParseException e) {
                    UIUtils.showToast("The time format is wrong. Please fill in the data in the format of 2023-08-14 09:00:00.");
                    return;
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMaterialDialog();
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
