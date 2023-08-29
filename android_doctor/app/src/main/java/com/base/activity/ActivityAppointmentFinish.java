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
import com.base.bean.Patient;
import com.base.dao.AppointmentDao;
import com.base.dao.PatientDao;
import com.base.util.Config;
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


public class ActivityAppointmentFinish extends Activity {
    //成员变量
    @InjectView(R.id.jl)
    EditText jl;
    @InjectView(R.id.cf)
    EditText cf;


    @InjectView(R.id.btnOk)
    Button btnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_finish);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btnOk})
    public void click(View view) {
        String jl = this.jl.getText().toString();
        if(Strings.isEmpty(jl)){
            UIUtils.showToast("Medical record cannot be empty.");
            return;
        }
        String s = cf.getText().toString();
        if(Strings.isEmpty(s)){
            UIUtils.showToast("Prescription cannot be empty.");
            return;
        }
        Appointment appointment = (Appointment) Config.appointmentInfo;
        appointment.setMedicalRecord(jl);
        appointment.setPrescription(s);
        appointment.setStatus("End of appointment");
        boolean update = AppointmentDao.getInstance().update(appointment);
        if(update){
            UIUtils.showToast("Processing reservation succeeded.");
            Intent intent = new Intent(ActivityAppointmentFinish.this,DoctorAppointmentActivity.class);
            startActivity(intent);
            finish();
        }

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
