package com.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.base.adapter.MainPagerAdapter;
import com.base.fragment.BaseFragment;
import com.base.fragment.ContactsFragment;
import com.base.fragment.MessageFragment;
import com.base.util.Config;
import com.base.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    private ContactsFragment mContactsFragment;
    private MessageFragment messageFragment;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.vpContent)
    ViewPager mVpContent;

    // 底部
    @InjectView(R.id.llButtom)
    LinearLayout mLlBottom;

    @InjectView(R.id.llContacts)
    LinearLayout llContacts;

    @InjectView(R.id.tvMessageNormal)
    TextView mTvMessageNormal;
    @InjectView(R.id.tvMessagePress)
    TextView mTvMessagePress;
    @InjectView(R.id.tvMessageTextNormal)
    TextView mTvMessageTextNormal;
    @InjectView(R.id.tvMessageTextPress)
    TextView mTvMessageTextPress;
    @InjectView(R.id.tvMessageCount)
    public TextView mTvMessageCount;

    @InjectView(R.id.tvContactsNormal)
    TextView mTvContactsNormal;
    @InjectView(R.id.tvContactsPress)
    TextView mTvContactsPress;
    @InjectView(R.id.tvContactsTextNormal)
    TextView mTvContactsTextNormal;
    @InjectView(R.id.tvContactsTextPress)
    TextView mTvContactsTextPress;
    @InjectView(R.id.tvContactCount)
    public TextView mTvContactCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main); //调用方法初始化
        ButterKnife.inject(this);

        initToolbar();
        initData();
        initListener();
        //默认选中第一个
        setTransparency();
        mTvMessagePress.getBackground().setAlpha(255);
        mTvMessageTextPress.setTextColor(Color.argb(255, 69, 192, 26));

        //设置ViewPager的最大缓存页面
        mVpContent.setOffscreenPageLimit(3);

    }

    private List<BaseFragment> mFragments;
    public void initData() {
        //创建4个界面的Fragment
        mFragments = new ArrayList<>();
        messageFragment = new MessageFragment();
        //先添加聊天的东西
        mFragments.add(messageFragment);
        if(!Config.role.equals("doctor")){
            mContactsFragment = new ContactsFragment();
            mFragments.add(mContactsFragment);
        }else {
            llContacts.setVisibility(View.GONE);
        }
        //设置中间内容vp适配器
        mVpContent.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mVpContent.setCurrentItem(0);

    }


    private void initToolbar() {
        //设置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chat window");
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
    }


    @OnClick({R.id.llMessage, R.id.llContacts})
    public void click(View view) {
        setTransparency();
        switch (view.getId()) {
            case R.id.llMessage:
                mVpContent.setCurrentItem(0, false);
                mTvMessagePress.getBackground().setAlpha(255);
                mTvMessageTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
            case R.id.llContacts:
                mVpContent.setCurrentItem(1, false);
                mTvContactsPress.getBackground().setAlpha(255);
                mTvContactsTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
        }
    }

    /**
     * 把press图片、文字全部隐藏(设置透明度)
     */
    private void setTransparency() {
        mTvMessageNormal.getBackground().setAlpha(255);
        mTvContactsNormal.getBackground().setAlpha(255);
//        mTvDiscoveryNormal.getBackground().setAlpha(255);
//        mTvMeNormal.getBackground().setAlpha(255);
        mTvMessagePress.getBackground().setAlpha(1);
        mTvContactsPress.getBackground().setAlpha(1);
//        mTvDiscoveryPress.getBackground().setAlpha(1);
//        mTvMePress.getBackground().setAlpha(1);
        mTvMessageTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvContactsTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
//        mTvDiscoveryTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
//        mTvMeTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvMessageTextPress.setTextColor(Color.argb(0, 69, 192, 26));
//        mTvMessageTextPress.setTextColor(Color.WHITE);
        mTvContactsTextPress.setTextColor(Color.argb(0, 69, 192, 26));
//        mTvDiscoveryTextPress.setTextColor(Color.argb(0, 69, 192, 26));
//        mTvMeTextPress.setTextColor(Color.argb(0, 69, 192, 26));

    }


    public void initListener() {
        //设置vp的滑动监听事件，控制底部图标渐变
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //根据ViewPager滑动位置更改透明度
                int diaphaneity_one = (int) (255 * positionOffset);
                int diaphaneity_two = (int) (255 * (1 - positionOffset));
                switch (position) {
                    case 0:
                        mTvMessageNormal.getBackground().setAlpha(diaphaneity_one);
                        mTvMessagePress.getBackground().setAlpha(diaphaneity_two);
//                        mTvContactsNormal.getBackground().setAlpha(diaphaneity_two);
//                        mTvContactsPress.getBackground().setAlpha(diaphaneity_one);
                        mTvMessageTextNormal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                        mTvMessageTextPress.setTextColor(Color.argb(diaphaneity_two, 69, 192, 26));
//                        mTvContactsTextNormal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
//                        mTvContactsTextPress.setTextColor(Color.argb(diaphaneity_one, 69, 192, 26));
                        break;
                    case 1:
                        mTvContactsNormal.getBackground().setAlpha(diaphaneity_one);
                        mTvContactsPress.getBackground().setAlpha(diaphaneity_two);
                        mTvContactsTextNormal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                        mTvContactsTextPress.setTextColor(Color.argb(diaphaneity_two, 69, 192, 26));
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
                //如果是“通讯录”页被选中，则显示快速导航条
                if (position == 1) {
                    mContactsFragment.showQuickIndexBar(true);
                } else {
                    mContactsFragment.showQuickIndexBar(false);
                }

                //根据position刷新对应Fragment的数据
                mFragments.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    //滚动过程中隐藏快速导航条
                    mContactsFragment.showQuickIndexBar(false);
                } else {
                    mContactsFragment.showQuickIndexBar(true);
                }
            }
        });
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
            buidler.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            //no按钮
            buidler.setNegativeButton("no", new DialogInterface.OnClickListener() {
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
