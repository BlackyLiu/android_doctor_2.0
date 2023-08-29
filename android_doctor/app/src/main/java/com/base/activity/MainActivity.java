package com.base.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.base.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


public class MainActivity extends Activity {
    //成员变量
    GridView gvinfo;
    //patient菜单
    String[] title = new String[]{"Personal center", "online medical", "Make an appointment","Medical record"};
    int[] image = new int[]{R.drawable.user, R.drawable.chat, R.drawable.yuyue, R.drawable.jl};

    //doctor菜单
    String[] teacherTitle = new String[]{"Personal center", "online medical", "My reservation"};
    int[] teacherImage = new int[]{R.drawable.user, R.drawable.chat, R.drawable.yy};

    ArrayList<Map<String, Object>> data;//用来包装的数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//         创建一个对象 进行--准备数据源
        this.data = new ArrayList<Map<String, Object>>();

        //实际应用的数据
        String[] appData = null;
        int[] appImage = null;
        switch (Config.role) {
            case "doctor":
                appData = teacherTitle;
                appImage = teacherImage;
                break;
            default:
                appData = title;
                appImage = image;
                break;
        }

        for (int i = 0; i < appData.length; i++) {
            //构建 一个 Map对象  子类 hash map
            Map tem = new HashMap();
            //键--值--对--组成一个小布局
            tem.put("mainimage", appImage[i]);
            tem.put("miantitle", appData[i]);
            //“仓库”添加的内容 都是 map 对象--把每一个小布局放入到界面中显示
            data.add(tem);
        }
        //初始化简单的适配器  数据源  与 GridView 中 “桥”  上下文    数据源           布局
        SimpleAdapter sadapter = new SimpleAdapter(this, data, R.layout.mygrid,
                new String[]{"mainimage", "miantitle"}, new int[]{R.id.gridimage, R.id.gridtext});
        //初始化 GridView并与控件进行绑定
        this.gvinfo = (GridView) findViewById(R.id.gridView1);
        //设置GridView的适配器
        this.gvinfo.setAdapter(sadapter);

        //gvinfo添加事件处理
        this.gvinfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                //事件处理     position  点击GridView 中的 单元 序号
                handleFunc(position);
            }
        });
    }

    /**
     * 点击 管理图像 事件处理
     */
    public void handleFunc(int id) {
        Intent intent = null;
        if (Config.role.equals("patient")) {
            String func = title[id];
            switch (func) {
                case "Personal center":
                    intent = new Intent(MainActivity.this, MyInfoActivity.class);
                    startActivity(intent);
                    break;
                case "online medical":
                    intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                    break;
                case "Make an appointment":
                    intent = new Intent(MainActivity.this, AppointmentActivity.class);
                    startActivity(intent);
                    break;
                case "Medical record":
                    intent = new Intent(MainActivity.this, AppointmentRecordsActivity.class);
                    startActivity(intent);
                    break;
            }
        } else if (Config.role.equals("doctor")) {
            String func = teacherTitle[id];
            switch (func) {
                case "Personal center":
                    intent = new Intent(MainActivity.this, MyInfoActivity.class);
                    startActivity(intent);
                    break;
                case "online medical":
                    intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                    break;
                case "My reservation":
                    intent = new Intent(MainActivity.this, DoctorAppointmentActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 软件使用帮助中“联系作者”事件处理方法
     */
    public void lianxi() {
        //方法二
        Uri uri = Uri.parse("http://qm.qq.com/cgi-bin/qm/qr?k=XALdOZEPRx8ndbgaQoetggHd-mGfeusx");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            //用户选择了退出
            AlertDialog.Builder buidler = new AlertDialog.Builder(this);
//        	buidler.setTitle("tip").setMessage("Are you sure you want to exit the program？");
            buidler.setTitle("tip");
            buidler.setMessage("Are you sure you want to exit the program？");
            //yes按钮
            buidler.setPositiveButton("yes", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            //no按钮
            buidler.setNegativeButton("no", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            //创建对话框--进行显示对话框
            buidler.create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}
