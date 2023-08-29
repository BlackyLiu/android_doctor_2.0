package com.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.base.activity.ChatActivity;
import com.base.activity.R;
import com.base.activity.SessionActivity;
import com.base.bean.Chat;
import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.bean.User;
import com.base.dao.ChatDao;
import com.base.util.Config;
import com.base.util.TimeUtils;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.ninegridimageview.LQRNineGridImageView;
import com.netease.nimlib.sdk.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageFragment extends BaseFragment {

    private List<Chat> mRecentContactList = new ArrayList<>();

    @InjectView(R.id.cvMessage)
    RecyclerView mCvMessage;
    ChatActivity activity;


    private LQRAdapterForRecyclerView<Chat> mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ChatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void init() {
        //监听最近联系人
        observeRecentContact();
        //初始化总未读数
        updateTotalUnReadCount();
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_message, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        getLocalRecentData();
        //获取当前用户的通信列表数据
        mRecentContactList = ChatDao.getInstance().queryTxYh(Config.getCurId());
//        ChatDao.getInstance().query()

        setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    Map<Long, String> judgeMap = new HashMap<>();


    public String getShowName(User user) {
        return (!TextUtils.isEmpty(user.getRealName())) ? user.getRealName() : user.getUsername();
    }

    //设置页面
    private void setAdapter() {
        judgeMap.clear();
        List<Chat> mChats = new ArrayList<>();
        Long id = -1L;
        if(Config.role.equals("doctor")){
            Doctor d = (Doctor) Config.currUser;
            id = d.getId();
        }else {
            Patient p = (Patient) Config.currUser;
            id = p.getId();
        }
        for (Chat item : mRecentContactList) {
            //显示唯一的用户
            long showId = -1;

            //多个聊天记录只显示一个用户的信息
            if (item.getRecvName().equals(Config.getCurShowName()) ){
                showId = item.getSendId();
            } else {
                showId = item.getRecvId();
            }
            if (judgeMap.containsKey(showId)) {
                continue;
            }
            judgeMap.put(showId, "1");
            mChats.add(item);
        }
        mAdapter = new LQRAdapterForRecyclerView<Chat>(getActivity(), R.layout.item_message_rv, mChats) {
            @Override
            public void convert(final LQRViewHolderForRecyclerView helper, final Chat item, int position) {

                final ImageView ivHeader = helper.getView(R.id.ivHeader);//单聊头像
                final LQRNineGridImageView ngivHeader = helper.getView(R.id.ngiv);//群聊头像

                //设置单聊
                ivHeader.setVisibility(View.VISIBLE);
                ngivHeader.setVisibility(View.GONE);
                String xsName = "";
                if(item.getRecvName().equals(Config.getCurShowName())){
                     xsName = item.getSendName();
                }else {
                     xsName = item.getRecvName();
                }

                if(Config.role.equals("doctor")){
                    ivHeader.setImageResource(R.drawable.br);
                }else {
                    ivHeader.setImageResource(R.drawable.ys);
                }
//                //设置默认头像
//                ivHeader.setImageResource(R.mipmap.default_header);
                helper.setText(R.id.tvName, xsName);
//                //设置未读消息数
//                helper.setViewVisibility(R.id.tvUnread, 10 > 0 ? View.VISIBLE : View.GONE).setText(R.id.tvUnread, String.valueOf(10));
                helper.setViewVisibility(R.id.tvUnread, View.GONE);
//
//                //设置显示文本和时间
                helper.setText(R.id.tvMsg, item.getContent())
                        .setText(R.id.tvTime, TimeUtils.getMsgFormatTime(item.getCreateTime().getTime()));
//
//                //条目点击跳转至聊天界面
                helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SessionActivity.class);
                        if (item.getRecvName().equals(Config.getCurShowName())) {
                            Config.hisId = item.getSendId();
                            Config.hisName = item.getSendName();
                        } else {
                            Config.hisId = item.getRecvId();
                            Config.hisName = item.getRecvName();
                        }
                        startActivity(intent);
                        //清空当前联系人的未读数
                    }
                });

            }
        };


        mCvMessage.setAdapter(mAdapter);
    }

    /**
     * 加载本地联系人信息
     */
    private void getLocalRecentData() {
        //获取最近联系人
    }

    /**
     * 更新最近联系人的本地信息
     */
    private void updateRecentContactInfoFromServer() {
    }


    /**
     * 更新最近联系人中消息未读总数
     */
    private void updateTotalUnReadCount() {
    }


    private Observer<List<Chat>> mMessageObserver;

    /**
     * 监听最近联系人
     */
    private void observeRecentContact() {
        mMessageObserver = new Observer<List<Chat>>() {
            @Override
            public void onEvent(List<Chat> recentContacts) {
                //遍历最近更新的联系人，如果在数据集合中有的话，去掉数据集合中原来的最近联系人，再最新的最近联系人添加到数据集合中
                if (recentContacts != null && recentContacts.size() > 0) {
                    if (mAdapter != null) {
                        int index;
                        for (Chat r : recentContacts) {
                            index = -1;

                        }
                        updateTotalUnReadCount();
                    }
                }
            }
        };
//        NimRecentContactSDK.observeRecentContact(mMessageObserver, true);
    }

}
