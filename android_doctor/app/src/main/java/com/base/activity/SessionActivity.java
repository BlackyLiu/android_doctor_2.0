package com.base.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.base.adapter.SessionAdapter;
import com.base.bean.Chat;
import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.bean.User;
import com.base.dao.ChatDao;
import com.base.sdk.attach.StickerAttachment;
import com.base.util.Commons;
import com.base.util.Config;
import com.base.util.KeyBoardUtils;
import com.base.util.UIUtils;
import com.base.view.DotView;
import com.lqr.emoji.EmoticonPickerView;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.IEmoticonSelectedListener;
import com.lqr.recyclerview.LQRRecyclerView;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class SessionActivity extends AppCompatActivity implements IEmoticonSelectedListener, BGARefreshLayout.BGARefreshLayoutDelegate, IAudioRecordCallback {

    public static final int IMAGE_PICKER = 100;

    //当前会话信息
    public String mSessionId;//单聊的联系人的id，群聊是群id

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.refreshLayout)
    BGARefreshLayout mRefreshLayout;

    @InjectView(R.id.cvMessage)
    LQRRecyclerView mCvMessage;

    @InjectView(R.id.llButtomFunc)
    LinearLayout mLlButtomFunc;
    @InjectView(R.id.ivAudio)
    ImageView mIvAudio;
    @InjectView(R.id.etContent)
    EditText mEtContent;
    @InjectView(R.id.btnAudio)
    Button mBtnAudio;
    @InjectView(R.id.ivEmo)
    ImageView mIvEmo;
    @InjectView(R.id.ivAdd)
    ImageView mIvAdd;

    @InjectView(R.id.btnSend)
    Button mBtnSend;
    @InjectView(R.id.flBottom)
    FrameLayout mFlBottom;
    @InjectView(R.id.epv)
    EmoticonPickerView mEpv;
    @InjectView(R.id.vpFunc)
    ViewPager mVpFunc;

    @InjectView(R.id.dv)
    DotView mDv;
    @InjectView(R.id.flPlayAudio)
    FrameLayout mFlPlayAudio;
    @InjectView(R.id.cTimer)
    Chronometer mCTimer;
    @InjectView(R.id.tvTimerTip)
    TextView mTvTimerTip;
    //    @InjectView(R.id.llPlayVideo)
//    LinearLayout mLlPlayVideo;
    //    @InjectView(R.id.vrvVideo)
//    LQRVideoRecordView mVrvVideo;
//    @InjectView(R.id.tvTipOne)
//    TextView mTvTipOne;
//    @InjectView(R.id.tvTipTwo)
//    TextView mTvTipTwo;
    //    @InjectView(R.id.rp)
//    LQRRecordProgress mRp;
//    @InjectView(R.id.btnVideo)
//    Button mBtnVideo;
    //发送会话的功能类
    private SessionAdapter mAdapter;

    private List<Chat> mMessages = new ArrayList<>();
//    private User user = Config.currUser;
    Timer timer = Commons.timer;

    private Runnable mCvMessageScrollToBottomTask = new Runnable() {
        @Override
        public void run() {
            //移动到最后
            mCvMessage.moveToPosition(mMessages.size() - 1);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        ButterKnife.inject(this);
        initToolbar();
        initData();
        //emoj相关控件
        initEmotionPickerView();
        initEmotionKeyboard();
        initRefreshLayout();
        initListener();
        //显示出默认的数据
        closeKeyBoardAndLoseFocus();
    }


    @Override
    public void onResume() {
        setAdapter();
        super.onResume();
    }

    public void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new SessionAdapter(this, mMessages);
            mCvMessage.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStickerSelected(String catalog, String chartlet) {
        StickerAttachment stickerAttachment = new StickerAttachment(catalog, chartlet);
        sendStickerMsg(stickerAttachment);
    }

    /**
     * 发送贴图消息
     *
     * @param stickerAttachment
     */
    private void sendStickerMsg(StickerAttachment stickerAttachment) {

    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    //初始化通信双方数据
    public void initData() {
//        mMessages = ChatDao.queryYxQb(user.getId(), Config.hisId);
        setAdapter();
        observData();
    }

    public void observData(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Chat c = new Chat();
                Long recvId = -1L;
                if(Config.role.equals("doctor")){
                    Doctor doctor = (Doctor) Config.currUser;
                    recvId = doctor.getId();
                }else {
                    Patient patient = (Patient) Config.currUser;
                    recvId= patient.getId();
                }
//                c.setRecvId(recvId);
//                c.setSendId(Config.hisId);
                List<Chat> tempMsgs = ChatDao.getInstance().queryYxQb(recvId,Config.hisId);
                if(tempMsgs.size() > mMessages.size()){
                    mMessages = tempMsgs;
                    UIUtils.postTaskSafely(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new SessionAdapter(SessionActivity.this, mMessages);
                            mCvMessage.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            cvScrollToBottom();
                        }
                    });
                }
            }
        },0,3000);
    }







    @OnClick({R.id.btnSend})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                sendTextMsg();
                break;
        }
    }

    public void initListener() {
        //监听文本输入框，有值则显示发送按钮，无值则隐藏发送按钮
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtContent.getText().toString())) {
                    mIvAdd.setVisibility(View.VISIBLE);
                    mBtnSend.setVisibility(View.GONE);
                } else {
                    mIvAdd.setVisibility(View.GONE);
                    mBtnSend.setVisibility(View.VISIBLE);
                }
            }
        });

        //监听文本输入框的焦点获取，当获取焦点显示软键盘时，将消息列表滚动到最后一行
        mEtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cvScrollToBottom();
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
    }

    private EmotionKeyboard mEmotionKeyboard;

    /**
     * 设置表情、贴图控件
     */
    private void initEmotionPickerView() {
        mEpv.setWithSticker(true);
        mEpv.show(this);
        mEpv.attachEditText(mEtContent);
    }

    /**
     * 初始化表情软键盘
     */
    private void initEmotionKeyboard() {
        //1、创建EmotionKeyboard对象
        mEmotionKeyboard = EmotionKeyboard.with(this);
        //2、绑定输入框控件
        mEmotionKeyboard.bindToEditText(mEtContent);
        //3、绑定输入框上面的消息列表控件（这里用的是RecyclerView，其他控件也可以，注意该控件是会影响输入框位置的控件）
        mEmotionKeyboard.bindToContent(mCvMessage);
        //4、绑定输入框下面的底部区域（这里是把表情区和功能区共放在FrameLayout下，所以绑定的控件是FrameLayout）
        mEmotionKeyboard.setEmotionView(mFlBottom);
        //5、绑定表情按钮（可以绑定多个，如微信就有2个，一个是表情按钮，一个是功能按钮）
        mEmotionKeyboard.bindToEmotionButton(mIvEmo, mIvAdd);
        //6、当在第5步中绑定了多个EmotionButton时，这里的回调监听的view就有用了，注意是为了判断是否要自己来控制底部的显隐，还是交给EmotionKeyboard控制
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                if (mBtnAudio.getVisibility() == View.VISIBLE) {
                    hideBtnAudio();
                }
                //输入框底部显示时
                if (mFlBottom.getVisibility() == View.VISIBLE) {
                    //表情控件显示而点击的按钮是ivAdd时，拦截事件，隐藏表情控件，显示功能区
                    if (mEpv.getVisibility() == View.VISIBLE && view.getId() == R.id.ivAdd) {
                        mEpv.setVisibility(View.GONE);
                        mLlButtomFunc.setVisibility(View.VISIBLE);
                        return true;
                        //功能区显示而点击的按钮是ivEmo时，拦截事件，隐藏功能区，显示表情控件
                    } else if (mLlButtomFunc.getVisibility() == View.VISIBLE && view.getId() == R.id.ivEmo) {
                        mEpv.setVisibility(View.VISIBLE);
                        mLlButtomFunc.setVisibility(View.GONE);
                        return true;
                    }
                } else {
                    //点击ivEmo，显示表情控件
                    if (view.getId() == R.id.ivEmo) {
                        mEpv.setVisibility(View.VISIBLE);
                        mLlButtomFunc.setVisibility(View.GONE);
                        //点击ivAdd，显示功能区
                    } else {
                        mEpv.setVisibility(View.GONE);
                        mLlButtomFunc.setVisibility(View.VISIBLE);
                    }
                }
                cvScrollToBottom();
                return false;
            }
        });
    }

    private void hideBtnAudio() {
        mBtnAudio.setVisibility(View.GONE);
        mEtContent.setVisibility(View.VISIBLE);
        mIvEmo.setVisibility(View.VISIBLE);
        //打开键盘
        openKeyBoardAndGetFocus();
    }

    /**
     * 获取焦点，并打开键盘
     */
    private void openKeyBoardAndGetFocus() {
        mEtContent.requestFocus();
        KeyBoardUtils.openKeybord(mEtContent, this);
    }

    @OnTouch(R.id.cvMessage)
    public boolean cvTouch() {
        if (mEtContent.hasFocus()) {
            closeKeyBoardAndLoseFocus();
            return true;
        } else if (mFlBottom.getVisibility() == View.VISIBLE) {
            mFlBottom.setVisibility(View.GONE);
            closeKeyBoardAndLoseFocus();
            return true;
        }
        return false;
    }

    public String getShowName(User user) {
        return (!TextUtils.isEmpty(user.getRealName())) ? user.getRealName() : user.getUsername();
    }

    /**
     * 发送文字消息
     */
    public void sendTextMsg() {
        String content = mEtContent.getText().toString();
        Chat c = new Chat();
        Long sendId =  -1L;
        String  showName = "";
        if(Config.role.equals("doctor")){
            Doctor doctor = (Doctor) Config.currUser;
            sendId = doctor.getId();
            showName = doctor.getRealName();
        }else {
            Patient patient = (Patient) Config.currUser;
            sendId= patient.getId();
            showName = patient.getRealName();
        }
        if (!TextUtils.isEmpty(content)) {
            Chat chat = new Chat();
            chat.setContent(content);
            chat.setSendId(sendId);
            chat.setSendName(showName);
            chat.setRecvId(Config.hisId);
            chat.setRecvName(Config.hisName);
            ChatDao.getInstance().add(chat); //添加聊天数据
            chat.setCreateTime(new Date());
            sendMsg(chat);
            mEtContent.setText("");
        }
    }

    /**
     * 发送消息的统一步骤
     */
    private void sendMsg(Chat chat) {
        //添加对应的消息数据
//        发送消息
        mAdapter.addLastItem(chat);
        mAdapter.notifyDataSetChanged();
        cvScrollToBottom();
    }

    /**
     * 消息列表滚动至最后
     */
    private void cvScrollToBottom() {
        UIUtils.postTaskDelay(mCvMessageScrollToBottomTask, 100);
    }


    /**
     * 失去焦点，并关闭键盘
     */
    private void closeKeyBoardAndLoseFocus() {
        mEtContent.clearFocus();
        KeyBoardUtils.closeKeybord(mEtContent, this);
        mFlBottom.setVisibility(View.GONE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onEmojiSelected(String s) {

    }


    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File audioFile, RecordType recordType) {

    }

    @Override
    public void onRecordSuccess(File audioFile, long audioLength, RecordType recordType) {

    }

    @Override
    public void onRecordFail() {

    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(int maxTime) {

    }
}
