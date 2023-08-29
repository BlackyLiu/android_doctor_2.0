package com.base.adapter;

import android.content.Context;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;

import com.base.activity.R;
import com.base.activity.SessionActivity;
import com.base.bean.Chat;
import com.base.bean.Chat;
import com.base.bean.User;
import com.base.sdk.audio.MessageAudioControl;
import com.base.util.Config;
import com.base.util.TimeUtils;
import com.base.util.UIUtils;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.emoji.MoonUtil;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.netease.nimlib.sdk.msg.constant.MsgTypeEnum.notification;

public class SessionAdapter extends LQRAdapterForRecyclerView<Chat> {

    public static final int CLICK_TO_PLAY_AUDIO_DELAY = 500;
    private Context mContext;

    private static final int NOTIFICATION = R.layout.item_notification;
    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;
    private static final int SEND_STICKER = R.layout.item_sticker_send;
    private static final int RECEIVE_STICKER = R.layout.item_sticker_receive;
    private static final int SEND_IMAGE = R.layout.item_image_send;
    private static final int RECEIVE_IMAGE = R.layout.item_image_receive;
    private static final int SEND_LOCATION = R.layout.item_location_send;
    private static final int RECEIVE_LOCATION = R.layout.item_location_receive;
    private static final int SEND_FILE = R.layout.item_file_send;
    private static final int RECEIVE_FILE = R.layout.item_file_receive;

    private Map<String, Float> mProgress = new HashMap<>();
    private MessageAudioControl mAudioControl;
    private ImageView mAnimationView;
//    private User curUser = Config.currUser;

    public SessionAdapter(Context context, List<Chat> data) {
        super(context, data);
        mContext = context;
        mAudioControl = MessageAudioControl.getInstance(mContext);
    }

    public SessionAdapter(Context context, int defaultLayoutId, List<Chat> data) {
        super(context, defaultLayoutId, data);
    }


    public String getShowName(Chat u) {
        return u.getSendName();
    }

    //设置该模版的消息数据
    @Override
    public void convert(LQRViewHolderForRecyclerView helper, final Chat item, final int position) {
        //设置时间
        setTime(helper, item, position);
        //设置头像
        setHeader(helper, item);
        String showName = getShowName(item);
        //显示昵称
        helper.setViewVisibility(R.id.tvName, showName != null ? View.VISIBLE : View.GONE)
                .setText(R.id.tvName, showName);
        //点击重发
        helper.getView(R.id.ivError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新发送的逻辑
                ((SessionActivity) mContext).initData();
            }
        });

        //文本消息
        setTextMessage(helper, item,position);
    }

    //返回消息的模版
    @Override
    public int getItemViewType(int position) {
        Chat msg = getData().get(position);
//        Long id =Config.getCurId();

        //根据名字判断收发
        if (Config.getCurShowName().equals(msg.getSendName())) {
//            return RECEIVE_TEXT;
            return SEND_TEXT;
        } else {
//            return SEND_TEXT;
            return RECEIVE_TEXT;
        }
//        if (msg.getDirect() == MsgDirectionEnum.Out) {
//            return SEND_TEXT;
//        } else {
//            return RECEIVE_TEXT;
//        }
//
//        return super.getItemViewType(position);
    }


    //设置时间
    private void setTime(LQRViewHolderForRecyclerView helper, Chat item, int position) {
        long create_time = item.getCreateTime().getTime();
        if (position > 0) {
            Chat preMessage = getData().get(position - 1);
            if (create_time - preMessage.getCreateTime().getTime() > (30 * 60 * 1000)) {//与上一条数据相关30分钟则显示时间
                helper.setViewVisibility(R.id.tvTime, View.VISIBLE).setText(R.id.tvTime, TimeUtils.getMsgFormatTime(create_time));
            } else {
                helper.setViewVisibility(R.id.tvTime, View.GONE);
            }
        } else {
            helper.setViewVisibility(R.id.tvTime, View.VISIBLE).setText(R.id.tvTime, TimeUtils.getMsgFormatTime(create_time));
        }
    }

    //设置默认的头像
    private void setHeader(LQRViewHolderForRecyclerView helper, Chat item) {
        ImageView ivAvatar = helper.getView(R.id.ivAvatar);
        if (Config.role.equals("doctor")) {
            if(item.getSendName().equals(Config.getCurShowName())){
                ivAvatar.setImageResource(R.drawable.ys);
            }else {
                ivAvatar.setImageResource(R.drawable.br);
            }

        } else {
            if(item.getSendName().equals(Config.getCurShowName())){
                ivAvatar.setImageResource(R.drawable.br);
            }else {
                ivAvatar.setImageResource(R.drawable.ys);
            }
//            ivAvatar.setImageResource(R.drawable.br);
        }
//        ivAvatar.setImageResource(R.mipmap.default_header);
    }

    //发送内容
    private void setTextMessage(LQRViewHolderForRecyclerView helper, Chat item,int pos) {
        //设置时间
        setTime(helper, item, pos);
        helper.setText(R.id.tvText, item.getContent());
        //识别并显示表情
        MoonUtil.identifyFaceExpression(UIUtils.getContext(), helper.getView(R.id.tvText), item.getContent(), ImageSpan.ALIGN_BOTTOM);
    }

}
