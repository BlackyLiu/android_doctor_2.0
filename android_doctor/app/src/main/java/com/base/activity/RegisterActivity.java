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

import com.base.bean.Patient;
import com.base.dao.PatientDao;
import com.base.util.Regs;
import com.base.util.Strings;
import com.base.util.Threads;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterActivity extends Activity {
    //成员变量
    @InjectView(R.id.nameText)
    EditText nameText;
    @InjectView(R.id.emailText)
    EditText emailText;

    @InjectView(R.id.passwordText)
    EditText passwordText;

    @InjectView(R.id.birthdayText)
    EditText birthdayText;

    @InjectView(R.id.contactText)
    EditText contactText;

    @InjectView(R.id.btnOk)
    Button btnOk;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    String info = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
                case 0x02:
                    info = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, info, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    //销毁注册界面-直接退出
                    RegisterActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btnOk})
    public void click(View view) {
        Threads.executorService.submit(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                String name = nameText.getText().toString();
                if (Strings.isEmpty(name)) {
                    message.what = 0x01;
                    message.obj = "Please enter realName";
                    handler.sendMessage(message);
                    return;
                }
                String email = emailText.getText().toString();
                if (Strings.isEmpty(email)) {
                    message.what = 0x01;
                    message.obj = "Please enter email";
                    handler.sendMessage(message);
                    return;
                }

                if (!Regs.isEmail(email)) {
                    message.what = 0x01;
                    message.obj = "Please enter the correct email format.";
                    handler.sendMessage(message);
                    return;
                }

                String password = passwordText.getText().toString();
                if (Strings.isEmpty(password)) {
                    message.what = 0x01;
                    message.obj = "Please enter password";
                    handler.sendMessage(message);
                    return;
                }

                String birthday = birthdayText.getText().toString();
                if (Strings.isEmpty(birthday)) {
                    message.what = 0x01;
                    message.obj = "Please enter birthday";
                    handler.sendMessage(message);
                    return;
                }

                try {
                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = simpleDateFormat.parse(birthday);
                } catch (ParseException e) {
                    message.what = 0x01;
                    message.obj = "Please enter the correct birthday format.Please enter the correct birthday format.\n";
                    handler.sendMessage(message);
                    return;
                }


                String contact = contactText.getText().toString();
                if (Strings.isEmpty(contact)) {
                    message.what = 0x01;
                    message.obj = "Please enter the contact number.";
                    handler.sendMessage(message);
                    return;
                }

                Patient t = new Patient();
                t.setRealName(name);
                t.setEmail(email);
                t.setPassword(password);
                t.setBirthday(birthday);
                t.setContactInformation(contact);
                boolean reg = PatientDao.getInstance().reg(t);
                if (reg) {
                    message.what = 0x02;
                    message.obj = "Registration succeeded.";
                }else {
                    message.what = 0x01;
                    message.obj = "Registration failed,Duplicate email name";
                }
                handler.sendMessage(message);
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
