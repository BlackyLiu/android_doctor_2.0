package com.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.dao.DoctorDao;
import com.base.dao.PatientDao;
import com.base.util.Config;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends Activity {
    //成员变量
    @InjectView(R.id.loginditname)
    EditText editname;
    @InjectView(R.id.logeditpass)
    EditText editpass;
    @InjectView(R.id.Logbutloging)
    Button butlogin;
    @InjectView(R.id.logbutredister)
    Button butreg;
    @InjectView(R.id.RadioGroup1)
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //调用方法初始化
        ButterKnife.inject(this);

        //为“新用户注册” 添加按钮监听事件
        this.butreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //切换到用户注册界面的切换方法
                Intent abc = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(abc);

            }
        });
        //点击登录按钮 为登录按钮事件添加监听
        this.butlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //调用相关登录方法
                loginAction();
            }
        });
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    Toast.makeText(LoginActivity.this, "Data input is incomplete, please modify.", Toast.LENGTH_SHORT).show();
                    break;
                case 0x02:
                    Toast.makeText(LoginActivity.this, "The mailbox or password is wrong. Please modify it！", Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    Toast.makeText(LoginActivity.this, "User login succeeded！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    //销毁登陆注册界面--返回直接退出
					LoginActivity.this.finish();
                    break;
                default:
                    throw new IllegalStateException("Wrong value: " + msg.what);
            }
        }
    };

    /**
     * 登录按钮功能
     */
    public void loginAction() {
        //1.获取用户输入的用户名与password
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                String name = editname.getText().toString();
                String pass = editpass.getText().toString();
                //2.判断输入是否为空
                if (name.length() == 0 || pass.length() == 0) {
                    message.what = 0x01;
                    handler.sendMessage(message);
                    return;
                }
                int count = radioGroup.getChildCount();
                boolean login = false;
                for (int i = 0; i < count; i++) {
                    RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                    if (rb.isChecked()) {
                        String text = rb.getText().toString();
                        if (text.equals("doctor")) {
                            Doctor doctor = new Doctor();
                            doctor.setEmail(name);
                            doctor.setPassword(pass);
                            List<Doctor> data = DoctorDao.getInstance().query(doctor);
                            Config.role = "doctor";
                            if (data != null&& data.size()>0) {
                                Config.currUser = data.get(0);
                                login = true;
                            }
                        } else {
                            Patient patient = new Patient();
                            Config.role = "patient";
                            patient.setEmail(name);
                            patient.setPassword(pass);
                            List<Patient> data = PatientDao.getInstance().query(patient);
                            if (data != null && data.size()>0) {
                                Config.currUser = data.get(0);
                                login = true;
                            }
                        }
                        break;
                    }
                }
                if (login) {
                    //如果用户信息表中用户信息为空--登录失败
                    message.what = 0x03;
                    handler.sendMessage(message);
                } else {
                    //如果用户信息表中有用户信息--登录成功--合法用户
                    message.what = 0x02;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}








