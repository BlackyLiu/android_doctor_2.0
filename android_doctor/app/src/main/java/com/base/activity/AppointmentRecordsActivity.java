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
import com.base.util.DateOlds;
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

public class AppointmentRecordsActivity extends BaseActivity {
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
//    List<Doctor> doctors;

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
        Appointment appointment = new Appointment();
        appointment.setStatus("End of appointment");
        appointment.setAppointmentId(Config.getCurId());
        appointments = AppointmentDao.getInstance().query(appointment);
        setDataAndUpdateView();
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
                        Appointment appointment = new Appointment();
                        appointment.setStatus("End of appointment");
                        appointment.setAppointmentId(Config.getCurId());
                        appointment.setBeAppointmentName(text);
                        appointments = AppointmentDao.getInstance().query(appointment);
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
                mFooterTv.setText(appointments.size() + " Appointment record");
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
        mAdapter = new LQRAdapterForRecyclerView<Appointment>(this, R.layout.item_appointment_records_cv, appointments) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Appointment item, int position) {
                helper.setText(R.id.tvName, item.getBeAppointmentName());
                helper.setImageResource(R.id.ivHeader, R.drawable.ys);
                helper.setText(R.id.sj, DateOlds.formatDate(item.getAppointmentTime()));
                helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AppointmentRecordsActivity.this, AppointmentInfoActivity.class);
                        Config.appointmentInfo = item;
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
