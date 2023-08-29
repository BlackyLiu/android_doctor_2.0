package com.base.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.activity.AppointmentActivity;
import com.base.activity.R;
import com.base.activity.SessionActivity;
import com.base.bean.Doctor;
import com.base.bean.User;
import com.base.dao.ChatDao;
import com.base.dao.DoctorDao;
import com.base.util.Config;
import com.base.util.FirstLetterUtil;
import com.base.util.UIUtils;
import com.base.view.QuickIndexBar;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ContactsFragment extends BaseFragment {

    //    private List<User> mContacts = new ArrayList<>();
    private List<Doctor> mContacts = new ArrayList<>();
    private LQRAdapterForRecyclerView<Doctor> mAdapter;
    private int i;

    @InjectView(R.id.rvContacts)
    LQRRecyclerView mRvContacts;
    @InjectView(R.id.quickIndexBar)
    QuickIndexBar mQuickIndexBar;
    @InjectView(R.id.tvLetter)
    TextView mTvLetter;

    //列表首尾布局
    View mHeaderView;
    TextView mFooterTv;

    //联系人列表最上条目
    LinearLayout mLlNewFriend;
    LinearLayout mLlGroupCheat;
    LinearLayout mLlTag;
    LinearLayout mLlOffical;
    private View mVNewFriendUnread;
    private View mVGroupCheatUnread;

//    private  User curUser =Config.currUser;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_contacts, null);
        ButterKnife.inject(this, view);
        initHeaderViewAndFooterView();
        return view;
    }

    private void initHeaderViewAndFooterView() {
        mFooterTv = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(50));
        mFooterTv.setLayoutParams(params);
        mFooterTv.setGravity(Gravity.CENTER);
    }

    //每次都会被调用
    @Override
    public void initData() {
//        mContacts = ChatDao.queryLxr(curUser.getId());


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Doctor d = new Doctor();
                d.setAppointment(true);
                mContacts = DoctorDao.getInstance().query(new Doctor());
                UIUtils.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        setDataAndUpdateView();
                    }
                });
            }
        }, 0, 3000);
//        setDataAndUpdateView();
//        UIUtils.showToast("初始化数据");
    }

    private void setDataAndUpdateView() {
        if (mContacts != null) {
            if (mFooterTv != null) {
                mFooterTv.setVisibility(View.VISIBLE);
                mFooterTv.setText(mContacts.size() + "位联系人");
            }
        } else {
            mFooterTv.setVisibility(View.GONE);
        }
        setAdapter();
    }

    @Override
    public void initListener() {
        mQuickIndexBar.setListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                //显示字母tip
                showLetter(letter);
                //滑动对对应字母条目处
                if ("↑".equalsIgnoreCase(letter)) {
                    mRvContacts.moveToPosition(0);
                } else if ("☆".equalsIgnoreCase(letter)) {
                    mRvContacts.moveToPosition(0);
                } else {
                    //找出第一个对应字母的位置后，滑动到指定位置
                    for (i = 0; i < mContacts.size(); i++) {
                        Doctor doctor = mContacts.get(i);
                        String shwoName = doctor.getRealName();
//                        String shwoName = !TextUtils.isEmpty(user.getRealName()) ? user.getRealName() : user.getUsername();
                        String c = FirstLetterUtil.getFirstOne(shwoName);
                        if (c.equalsIgnoreCase(letter)) {
                            mRvContacts.moveToPosition(i);
                            break;
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        updateHeaderViewUnreadCount();
    }


    /**
     * 更新列表头部中未读消息tip
     */
    public void updateHeaderViewUnreadCount() {
    }


//    public  String  getShowName(Doctor doctor){
//          return   (!TextUtils.isEmpty(doctor.getRealName()))?doctor.getRealName():user.getUsername();
//    }


    //显示列表框和字母表 的界面
    private void setAdapter() {
        mAdapter = new LQRAdapterForRecyclerView<Doctor>(getActivity(), R.layout.item_contact_cv, mContacts) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Doctor item, int position) {
//                String showName = !TextUtils.isEmpty(item.getRealName()) ? item.getRealName() : item.getUsername();
                String showName = item.getRealName();
                helper.setText(R.id.tvName, showName);
//                helper.setImageResource(R.id.ivHeader, R.mipmap.default_header);
                String currentLetter = FirstLetterUtil.getFirstOne(showName);
                String str = "";
                if (position == 0) {
                    str = currentLetter;
                } else {
                    //得到上一个字母
                    Doctor preUser = mContacts.get(position - 1);
//                    String preShowName = !TextUtils.isEmpty(preUser.getRealName()) ? preUser.getRealName() : preUser.getUsername();
                    String preShowName = preUser.getRealName();
                    String preLetter = FirstLetterUtil.getFirstOne(preShowName);
                    //如果和上一个字母的首字母不同则显示字母栏
                    if (!preLetter.equalsIgnoreCase(currentLetter)) {
                        str = currentLetter;
                    }
                    int nextIndex = position + 1;

                    if (nextIndex < mContacts.size() - 1) {
                        //得到下一个字母
                        Doctor nextUser = mContacts.get(nextIndex);
                        String nextShowName = nextUser.getRealName();
//                        String nextShowName = !TextUtils.isEmpty(nextUser.getRealName()) ? nextUser.getRealName() : nextUser.getUsername();
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
                //每次都设置一下 下划线和字母
                if (position == mContacts.size() - 1) {
                    helper.setViewVisibility(R.id.vLine, View.GONE);
                }

                //根据str是否为空决定字母栏是否显示
                if (TextUtils.isEmpty(str)) {
                    helper.setViewVisibility(R.id.tvIndex, View.GONE);
                } else {
                    helper.setViewVisibility(R.id.tvIndex, View.VISIBLE);
                    helper.setText(R.id.tvIndex, currentLetter);
                }

                //条目点击跳转好友信息查看界面
                helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SessionActivity.class);
//                        intent.putExtra("account", item.getAccount());
                        Config.hisId = item.getId();
                        Config.hisName = item.getRealName();
                        startActivity(intent);
                        //清空该好友的消息未读数
//                        NimRecentContactSDK.clearUnreadCount(item.getAccount(), SessionTypeEnum.P2P);
                    }
                });


            }
        };
        //加入脚部
        mAdapter.addFooterView(mFooterTv);
        //设置适配器
//        if (mRvContacts != null)
        mRvContacts.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }

    /**
     * 显示所触摸到的字母
     *
     * @param letter
     */
    protected void showLetter(String letter) {
        mTvLetter.setVisibility(View.VISIBLE);// 设置为可见
        mTvLetter.setText(letter);

        UIUtils.getMainThreadHandler().removeCallbacksAndMessages(null);
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                mTvLetter.setVisibility(View.GONE);
            }
        }, 500);
    }

    /**
     * 是否显示快速导航条
     *
     * @param show
     */
    public void showQuickIndexBar(boolean show) {
        mQuickIndexBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mQuickIndexBar.invalidate();
    }
}
