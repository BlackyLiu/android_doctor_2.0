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
import com.base.util.UIUtils;
import com.base.view.QuickIndexBar;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppointmentActivity extends BaseActivity {
//    @InjectView(R.id.toolbar)
//    Toolbar mToolbar;

    private LQRAdapterForRecyclerView<Doctor> mAdapter;

    @InjectView(R.id.rvContacts)
    LQRRecyclerView mRvContacts;
    @InjectView(R.id.quickIndexBar)
    QuickIndexBar mQuickIndexBar;
    @InjectView(R.id.tvLetter)
    TextView mTvLetter;


    TextView mFooterTv;
    //    private User curUser = Config.currUser;
    List<Doctor> doctors;


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
//        users = ClassDao.queryAllStuByCId(Config.bjId);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Doctor d = new Doctor();
                d.setAppointment(true);
                doctors = DoctorDao.getInstance().query(d);
                AppointmentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataAndUpdateView();
                    }
                });
            }
        },0,3000);

    }

    private void initToolbar() {
//        mEtSearch.setVisibility(View.VISIBLE);
//        mEtSearch.setHintTextColor(UIUtils.getColor(R.color.gray2));
//        mEtSearch.setTextColor(UIUtils.getColor(R.color.white));
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("学生管理");
//        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
//        mToolbar.setVisibility(View.VISIBLE);
//        mToolbar.setNavigationIcon(R.mipmap.ic_back);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("personal information");
//        mToolbar.setNavigationIcon(R.mipmap.ic_back);
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
                        Doctor d = new Doctor();
                        d.setAppointment(true);
                        d.setRealName(text);
                        doctors = DoctorDao.getInstance().query(d);
                        //更新底部状态栏
                        setDataAndUpdateView();
                        break;
                }
                return false;
            }
        });
    }

    private void setDataAndUpdateView() {
        if (doctors != null) {
            if (mFooterTv != null) {
                mFooterTv.setVisibility(View.VISIBLE);
                mFooterTv.setText(doctors.size() + " doctors");
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
        mAdapter = new LQRAdapterForRecyclerView<Doctor>(this, R.layout.item_appointment_cv, doctors) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Doctor item, int position) {
                helper.setText(R.id.tvName, item.getRealName());
//                helper.setImageResource(R.id.ivHeader, R.mipmap.default_header);
                //查询自己与该doctor的预约记录的最近一条数据
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(Config.getCurId());
                appointment.setBeAppointmentId(item.getId());
                final List<Appointment> d = AppointmentDao.getInstance().query(appointment);

                if (d != null && d.size() > 0) {
                    Appointment appointment1 = d.get(0);
                    String status = appointment1.getStatus();
                    helper.setText(R.id.textView, status);
                    if (status.equals("End of appointment")) {
                        helper.setViewVisibility(R.id.textView, View.INVISIBLE);
                        //可以再次预约
                        helper.setText(R.id.addBtn, "appointment");
                        helper.setViewVisibility(R.id.seeBtn, View.GONE);
                        helper.setViewVisibility(R.id.deleteBtn, View.GONE);
                    } else {
                        helper.setText(R.id.textView, appointment1.getStatus());
                        if (status.equals("Appointment success")) {
                            helper.setViewVisibility(R.id.seeBtn, View.GONE);
                            helper.setViewVisibility(R.id.addBtn, View.GONE);
                            helper.setText(R.id.deleteBtn,"Cancel");

                        } else if (status.equals("Reservation failed")) {
                            //Reservation failed不显示 Cancel the reservation
                            helper.setViewVisibility(R.id.deleteBtn, View.GONE);
                            helper.setText(R.id.seeBtn, "notice");
                            helper.setText(R.id.addBtn, "Re-appointment");

                        } else {
                            //Reservation in progress 不显示Appointment notice 和Make an appointment
                            helper.setViewVisibility(R.id.seeBtn, View.GONE);
                            helper.setViewVisibility(R.id.addBtn, View.GONE);
                            helper.setText(R.id.deleteBtn,"Cancel");
                        }
                    }
                } else {
                    helper.setViewVisibility(R.id.textView, View.GONE);
                    helper.setViewVisibility(R.id.seeBtn, View.GONE);
                    helper.setViewVisibility(R.id.deleteBtn, View.GONE);
                    helper.setText(R.id.addBtn,"appointment");
                }


                String showName = item.getRealName();
                String currentLetter = FirstLetterUtil.getFirstOne(showName);
                String str = "";
                if (position == 0) {
                    str = currentLetter;
                } else {
                    Doctor preUser = doctors.get(position - 1);
//                    String preShowName = !TextUtils.isEmpty(preUser.getRealName()) ? preUser.getRealName() : preUser.getUsername();
                    String preShowName = preUser.getRealName();
                    String preLetter = FirstLetterUtil.getFirstOne(preShowName);
                    //如果和上一个字母的首字母不同则显示字母栏
                    if (!preLetter.equalsIgnoreCase(currentLetter)) {
                        str = currentLetter;
                    }
                    int nextIndex = position + 1;

                    if (nextIndex < doctors.size() - 1) {
                        Doctor nextUser = doctors.get(nextIndex);
                        String nextShowName = nextUser.getRealName();
                        String nextLetter = FirstLetterUtil.getFirstOne(nextShowName);
                        if (!nextLetter.equalsIgnoreCase(currentLetter)) {
                            helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                        } else {
                            helper.setViewVisibility(R.id.vLine, View.VISIBLE);
                        }
                    } else {
                        helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                    }
                }
                if (position == doctors.size() - 1) {
                    helper.setViewVisibility(R.id.vLine, View.GONE);
                }
                if (TextUtils.isEmpty(str)) {
                    helper.setViewVisibility(R.id.tvIndex, View.GONE);
                } else {
                    helper.setViewVisibility(R.id.tvIndex, View.VISIBLE);
                    helper.setText(R.id.tvIndex, currentLetter);
                }
                helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AppointmentActivity.this, AppointmentInfoActivity.class);
                        Config.appointmentInfo = item;
                        startActivity(intent);
                    }
                });
//                //条目点击跳转好友信息查看界面
                helper.getView(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String showName = item.getRealName();
                        showMaterialDialog("", "Do you want to cancel " + showName + "'s reservation?", "yes", "no", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Appointment appointment1= d.get(0);
                                AppointmentDao.getInstance().delete(appointment1.getId());
                                UIUtils.showToast("Cancel successfully");
                                initData();
                                hideMaterialDialog();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hideMaterialDialog();
                            }
                        });

                    }
                });
                helper.getView(R.id.seeBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Appointment a= new Appointment();
                        a.setAppointmentId(Config.getCurId());
                        a.setBeAppointmentId(item.getId());
                        List<Appointment> data = AppointmentDao.getInstance().query(a);
                        Appointment appointment1 = data.get(0);
                        //此时就是失败原因
                        UIUtils.showToast(appointment1.getMedicalRecord());
                    }
                });

                helper.getView(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Config.appointmentInfo = item; //去添加成绩的页面
                        Intent intent = new Intent(AppointmentActivity.this, AddAppointmentActivity.class);
                        startActivity(intent);
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
