package com.base.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.base.bean.Appointment;
import com.base.bean.Doctor;
import com.base.bean.User;
import com.base.dao.AppointmentDao;
import com.base.dao.DoctorDao;
import com.base.util.Config;
import com.base.util.FirstLetterUtil;
import com.base.util.Strings;
import com.base.util.UIUtils;
import com.base.view.QuickIndexBar;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DoctorAppointmentActivity extends BaseActivity {
//    @InjectView(R.id.toolbar)
//    Toolbar mToolbar;

    private LQRAdapterForRecyclerView<Appointment> mAdapter;

    @InjectView(R.id.rvContacts)
    LQRRecyclerView mRvContacts;
    @InjectView(R.id.quickIndexBar)
    QuickIndexBar mQuickIndexBar;
    @InjectView(R.id.tvLetter)
    TextView mTvLetter;


    TextView mFooterTv;
    //    private User curUser = Config.currUser;
    List<Appointment> appointments;


    @Override
    public void initView() {
        setContentView(R.layout.fragment_appointment);
        ButterKnife.inject(this);
        initToolbar();
        initHeaderViewAndFooterView();
    }

    @InjectView(R.id.etSearch)
    EditText mEtSearch;

    @Override
    public void initData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                appointments = AppointmentDao.getInstance().queryDoctorApp(Config.getCurId());
                DoctorAppointmentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataAndUpdateView();
                    }
                });

            }
        },0,3000);
    }

    private void initToolbar() {
        showSearch();
    }

    public void showSearch() {
        mEtSearch.setVisibility(View.VISIBLE);
        mEtSearch.setHintTextColor(UIUtils.getColor(R.color.gray2));
        mEtSearch.setTextColor(UIUtils.getColor(R.color.white));
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        String text = mEtSearch.getText().toString();
                        List<Appointment> searchs = new ArrayList<>();
                        for (Appointment appointment : appointments) {
                            if (appointment.getAppointmentName().contains(text)) {
                                searchs.add(appointment);
                            }
                        }
                        appointments = searchs;
                        //更新底部状态栏
                        setDataAndUpdateView();
                        break;
                }
                return false;
            }
        });
    }

    private void setDataAndUpdateView() {
        if (appointments != null) {
            if (mFooterTv != null) {
                mFooterTv.setVisibility(View.VISIBLE);
                mFooterTv.setText(appointments.size() + " patient");
            }
        } else {
            mFooterTv.setVisibility(View.GONE);
        }
        setAdapter();
    }

    public String getShowName(User user) {
        return (!TextUtils.isEmpty(user.getRealName())) ? user.getRealName() : user.getUsername();
    }

    private void setAdapter() {
        mAdapter = new LQRAdapterForRecyclerView<Appointment>(this, R.layout.item_doctor_cv, appointments) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Appointment item, int position) {
                helper.setText(R.id.tvName, item.getAppointmentName());
//                helper.setImageResource(R.id.ivHeader, R.mipmap.default_header);
                //查询自己与该doctor的预约记录的最近一条数据
                String status = item.getStatus();
                helper.setText(R.id.textView, status);
                if (status.equals("Reservation in progress")) {
                    //同意预约 和拒绝预约
                    helper.setText(R.id.seeBtn, "Agree");
                    helper.setText(R.id.deleteBtn, "Refuse");
                    helper.setViewVisibility(R.id.addBtn, View.GONE);
                } else if (status.equals("Appointment success")) {
                    //handle an appointment
                    helper.setViewVisibility(R.id.seeBtn, View.GONE);
                    helper.setViewVisibility(R.id.deleteBtn, View.GONE);
                    helper.setText(R.id.addBtn, "handle an appointment");
                }


//
                String showName = item.getAppointmentName();
                String currentLetter = FirstLetterUtil.getFirstOne(showName);
                String str = "";
                if (position == 0) {
                    str = currentLetter;
                } else {
                    //得到上一个字母
                    Appointment preUser = appointments.get(position - 1);
//                    String preShowName = !TextUtils.isEmpty(preUser.getRealName()) ? preUser.getRealName() : preUser.getUsername();
                    String preShowName = preUser.getAppointmentName();
                    String preLetter = FirstLetterUtil.getFirstOne(preShowName);
                    //如果和上一个字母的首字母不同则显示字母栏
                    if (!preLetter.equalsIgnoreCase(currentLetter)) {
                        str = currentLetter;
                    }
                    int nextIndex = position + 1;

                    if (nextIndex < appointments.size() - 1) {
                        //得到下一个字母
                        Appointment nextUser = appointments.get(nextIndex);
                        String nextShowName = nextUser.getAppointmentName();
                        String nextLetter = FirstLetterUtil.getFirstOne(nextShowName);
                        //如果和下一个字母的首字母不同则隐藏下划线
                        if (!nextLetter.equalsIgnoreCase(currentLetter)) {
                            helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                        } else {
                            helper.setViewVisibility(R.id.vLine, View.VISIBLE);
                        }
                    } else {
                        helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                    }
                    //点击条目跳转好友栏
                }
//                //每次都设置一下 下划线和字母
                if (position == appointments.size() - 1) {
                    helper.setViewVisibility(R.id.vLine, View.GONE);
                }
//               //根据str是否为空决定字母栏是否显示
                if (TextUtils.isEmpty(str)) {
                    helper.setViewVisibility(R.id.tvIndex, View.GONE);
                } else {
                    helper.setViewVisibility(R.id.tvIndex, View.VISIBLE);
                    helper.setText(R.id.tvIndex, currentLetter);
                }

                if(!(status.equals("")|| status.equals("End of appointment"))){
                    helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DoctorAppointmentActivity.this, AppointmentInfoActivity.class);
                            Config.appointmentInfo = item;
                            startActivity(intent);
                        }
                    });
                }


//                //条目点击跳转好友信息查看界面
                helper.getView(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String showName = item.getAppointmentName();
                        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(DoctorAppointmentActivity.this).builder()
                                .setTitle("Please enter a reason for rejection.")
                                .setEditText("");
                        myAlertInputDialog.setPositiveButton("yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                showMsg(myAlertInputDialog.getResult());
                                String result = myAlertInputDialog.getResult();
                                if(Strings.isEmpty(result)){
                                    UIUtils.showToast("Please output the reason for rejection.");
                                    return;
                                }

                                item.setStatus("Reservation failed");
                                item.setMedicalRecord(result);
                                boolean update = AppointmentDao.getInstance().update(item);
//                                if(update){
//
//                                }
                                initData();
                                myAlertInputDialog.dismiss();
                            }
                        }).setNegativeButton("no", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myAlertInputDialog.dismiss();
                            }
                        });
                        myAlertInputDialog.show();
                     }
                });
                helper.getView(R.id.seeBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setStatus("Appointment success");
                        boolean update = AppointmentDao.getInstance().update(item);
                        if(update){
                             UIUtils.showToast("Processing succeeded.");
                             initData();
                        }
                    }
                });
//
                helper.getView(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Config.appointmentInfo = item;
                        Intent intent = new Intent(DoctorAppointmentActivity.this,ActivityAppointmentFinish.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        };
        //加入脚部
        mAdapter.addFooterView(mFooterTv);
        //设置适配器
        mRvContacts.setAdapter(mAdapter.getHeaderAndFooterAdapter());
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


    private void initHeaderViewAndFooterView() {
        mFooterTv = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(50));
        mFooterTv.setLayoutParams(params);
        mFooterTv.setGravity(Gravity.CENTER);
    }

}
