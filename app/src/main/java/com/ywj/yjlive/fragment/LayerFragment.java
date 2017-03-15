package com.ywj.yjlive.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ywj.yjlive.R;
import com.ywj.yjlive.adapter.AudienceAdapter;
import com.ywj.yjlive.adapter.GiftAdapter;
import com.ywj.yjlive.adapter.MessageAdapter;
import com.ywj.yjlive.adapter.base.BaseAdapter;
import com.ywj.yjlive.bean.Gift;
import com.ywj.yjlive.listenter.SoftKeyBoardListener;
import com.ywj.yjlive.utils.DisplayUtil;
import com.ywj.yjlive.widget.CustomRoundView;
import com.ywj.yjlive.widget.HorizontalListView;
import com.ywj.yjlive.widget.MagicTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import tyrantgit.widget.HeartLayout;

/**
 * 该Fragment是用于dialogFragment中的pager，为了实现滑动隐藏交互Fragment的
 * 交互的操作都在这个界面实现的，如果大家要改交互主要修改这个界面就可以了
 * <p>
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。
 */
public class LayerFragment extends Fragment implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    /**
     * 标示判断
     */
    private boolean isOpen;
    private long liveTime;

    /**
     * 界面相关
     */
    @BindView(R.id.hlvaudience)
    HorizontalListView hlvaudience;
    @BindView(R.id.llpicimage1)
    LinearLayout llpicimage;
    @BindView(R.id.tvqq)
    TextView tvqq;
    @BindView(R.id.tvtime)
    TextView tvtime;
    @BindView(R.id.tvdate)
    TextView tvdate;
    @BindView(R.id.rlsentimenttime)
    RelativeLayout rlsentimenttime;
    @BindView(R.id.llgiftcontent)
    LinearLayout llgiftcontent;
    @BindView(R.id.lvmessage)
    ListView lvmessage;
    @BindView(R.id.tvChat)
    TextView tvChat;
    @BindView(R.id.tvGift)
    TextView tvGift;
    @BindView(R.id.tvLike)
    TextView tvLike;
    @BindView(R.id.etInput)
    EditText etInput;
    @BindView(R.id.sendInput)
    TextView sendInput;
    @BindView(R.id.llinputparent)
    LinearLayout llinputparent;
    @BindView(R.id.two_fragment_hear)
    HeartLayout twoFragmentHear;
    @BindView(R.id.layout_bottom)
    FrameLayout layoutBottom;

    /**
     * 动画相关
     */
    private NumAnim giftNumAnim;
    private TranslateAnimation inAnim;
    private TranslateAnimation outAnim;
    private AnimatorSet animatorSetHide = new AnimatorSet();
    private AnimatorSet animatorSetShow = new AnimatorSet();

    /**
     * 数据相关
     */
    private List<View> giftViewCollection = new ArrayList<View>();
    private List<String> messageData = new LinkedList<>();
    private MessageAdapter messageAdapter;
    Context mcontext;
    private  PopupWindow  giftPopupWindow;
    ArrayList<Gift> gifts =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.effect_dome, container, false);
        ButterKnife.bind(this, view);
        getData();
        mcontext= getActivity();
        getLove();
        tvChat.setOnClickListener(this);
        tvGift.setOnClickListener(this);
        sendInput.setOnClickListener(this);
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_in);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.gift_out);
        giftNumAnim = new NumAnim();
        clearTiming();
        return view;
    }

    /**
     * 飘心效果
     */
    private void getLove() {
        twoFragmentHear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoFragmentHear.addHeart(Color.RED);
                twoFragmentHear.addHeart(Color.parseColor("#ff00ddff"));
                twoFragmentHear.addHeart(Color.parseColor("#ff99cc00"));
                twoFragmentHear.addHeart(Color.parseColor("#ffffbb33"));
                twoFragmentHear.addHeart(Color.parseColor("#ffff4444"));

            }
        });


        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        twoFragmentHear.addHeart(Color.RED);
                        twoFragmentHear.addHeart(Color.BLUE);

                    }
                });
            }
        };
        timer.schedule(task, 500, 500);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llinputparent.getVisibility() == View.VISIBLE) {
                    tvChat.setVisibility(View.VISIBLE);
                    llinputparent.setVisibility(View.GONE);
                    hideKeyboard();
                }
            }
        });
        softKeyboardListnenr();
        for (int x = 0; x < 20; x++) {
            messageData.add("1603A: you are nice" + x);
        }
        messageAdapter = new MessageAdapter(getActivity(), messageData);
        lvmessage.setAdapter(messageAdapter);
        lvmessage.setSelection(messageData.size());
        hlvaudience.setAdapter(new AudienceAdapter(getActivity()));
        startTimer();
    }

    public void pople() {

        if(giftPopupWindow==null) {

            initPopupWindow();
        }
        if(!giftPopupWindow.isShowing()) {
            giftPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            giftPopupWindow.showAsDropDown(lvmessage,0,-width/2);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChat:/*聊天*/
                showChat();
                break;
            case R.id.sendInput:/*发送*/
                sendText();
                break;
            case R.id.tvGift://送礼
                pople();
                break;
        }
    }

    /**
     * 添加礼物view,(考虑垃圾回收)
     */
    private View addGiftView() {
        View view = null;
        if (giftViewCollection.size() <= 0) {
            /*如果垃圾回收中没有view,则生成一个*/
            view = LayoutInflater.from(getActivity()).inflate(R.layout.item_gift, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 10;
            view.setLayoutParams(lp);
            llgiftcontent.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    giftViewCollection.add(view);
                }
            });
        } else {
            view = giftViewCollection.get(0);
            giftViewCollection.remove(view);
        }
        return view;
    }

    /**
     * 删除礼物view
     */
    private void removeGiftView(final int index) {
        final View removeView = llgiftcontent.getChildAt(index);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llgiftcontent.removeViewAt(index);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeView.startAnimation(outAnim);
            }
        });
    }

    /**
     * 显示礼物的方法
     */
    private void showGift(String tag) {
        View giftView = llgiftcontent.findViewWithTag(tag);
        if (giftView == null) {/*该用户不在礼物显示列表*/

            if (llgiftcontent.getChildCount() > 2) {/*如果正在显示的礼物的个数超过两个，那么就移除最后一次更新时间比较长的*/
                View giftView1 = llgiftcontent.getChildAt(0);
                CustomRoundView picTv1 = (CustomRoundView) giftView1.findViewById(R.id.crvheadimage);
                long lastTime1 = (Long) picTv1.getTag();
                View giftView2 = llgiftcontent.getChildAt(1);
                CustomRoundView picTv2 = (CustomRoundView) giftView2.findViewById(R.id.crvheadimage);
                long lastTime2 = (Long) picTv2.getTag();
                if (lastTime1 > lastTime2) {/*如果第二个View显示的时间比较长*/
                    removeGiftView(1);
                } else {/*如果第一个View显示的时间长*/
                    removeGiftView(0);
                }
            }

            giftView = addGiftView();/*获取礼物的View的布局*/
            giftView.setTag(tag);/*设置view标识*/

            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);
            final MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            giftNum.setText("x1");/*设置礼物数量*/
            crvheadimage.setTag(System.currentTimeMillis());/*设置时间标记*/
            giftNum.setTag(1);/*给数量控件设置标记*/

            llgiftcontent.addView(giftView);/*将礼物的View添加到礼物的ViewGroup中*/
            llgiftcontent.invalidate();/*刷新该view*/
            giftView.startAnimation(inAnim);/*开始执行显示礼物的动画*/
            inAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    giftNumAnim.start(giftNum);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {/*该用户在礼物显示列表*/
            CustomRoundView crvheadimage = (CustomRoundView) giftView.findViewById(R.id.crvheadimage);/*找到头像控件*/
            MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.giftNum);/*找到数量控件*/
            int showNum = (Integer) giftNum.getTag() + 1;
            giftNum.setText("x" + showNum);
            giftNum.setTag(showNum);
            crvheadimage.setTag(System.currentTimeMillis());
            giftNumAnim.start(giftNum);
        }
    }

    /**
     * 显示聊天布局
     */
    private void showChat() {
        tvChat.setVisibility(View.GONE);
        llinputparent.setVisibility(View.VISIBLE);
        llinputparent.requestFocus();
        showKeyboard();
    }

    /**
     * 发送消息
     */
    private void sendText() {
        if (!etInput.getText().toString().trim().isEmpty()) {
            messageData.add("yj: " + etInput.getText().toString().trim());
            etInput.setText("");
            messageAdapter.NotifyAdapter(messageData);
            lvmessage.setSelection(messageData.size());
            hideKeyboard();
        } else
            hideKeyboard();
    }

    /**
     * 开始计时功能
     */
    private void startTimer() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.add(Calendar.HOUR_OF_DAY, -8);
        Date time = calendar.getTime();
        liveTime = time.getTime();
        handler.post(timerRunnable);
    }

    /**
     * 显示软键盘并因此头布局
     */
    private void showKeyboard() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etInput, InputMethodManager.SHOW_FORCED);
            }
        }, 100);
    }

    /**
     * 隐藏软键盘并显示头布局
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
    }

    /**
     * 软键盘显示与隐藏的监听
     */
    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {/*软键盘显示：执行隐藏title动画，并修改listview高度和装载礼物容器的高度*/
                animateToHide();
                dynamicChangeListviewH(100);
                dynamicChangeGiftParentH(true);
            }

            @Override
            public void keyBoardHide(int height) {/*软键盘隐藏：隐藏聊天输入框并显示聊天按钮，执行显示title动画，并修改listview高度和装载礼物容器的高度*/
                tvChat.setVisibility(View.VISIBLE);
                llinputparent.setVisibility(View.GONE);
                animateToShow();
                dynamicChangeListviewH(150);
                dynamicChangeGiftParentH(false);
            }
        });
    }

    /**
     * 动态的修改listview的高度
     *
     * @param heightPX
     */
    private void dynamicChangeListviewH(int heightPX) {
        ViewGroup.LayoutParams layoutParams = lvmessage.getLayoutParams();
        layoutParams.height = DisplayUtil.dip2px(getActivity(), heightPX);
        lvmessage.setLayoutParams(layoutParams);
    }

    /**
     * 动态修改礼物父布局的高度
     *
     * @param showhide
     */
    private void dynamicChangeGiftParentH(boolean showhide) {
        if (showhide) {/*如果软键盘显示中*/
            if (llgiftcontent.getChildCount() != 0) {
                /*判断是否有礼物显示，如果有就修改父布局高度，如果没有就不作任何操作*/
                ViewGroup.LayoutParams layoutParams = llgiftcontent.getLayoutParams();
                layoutParams.height = llgiftcontent.getChildAt(0).getHeight();
                llgiftcontent.setLayoutParams(layoutParams);
            }
        } else {/*如果软键盘隐藏中*/
            /*就将装载礼物的容器的高度设置为包裹内容*/
            ViewGroup.LayoutParams layoutParams = llgiftcontent.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            llgiftcontent.setLayoutParams(layoutParams);
        }
    }

    /**
     * 头部布局执行显示的动画
     */
    private void animateToShow() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rlsentimenttime, "translationX", -rlsentimenttime.getWidth(), 0);
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(llpicimage, "translationY", -llpicimage.getHeight(), 0);
        animatorSetShow.playTogether(leftAnim, topAnim);
        animatorSetShow.setDuration(300);
        animatorSetShow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetShow.start();
        }
    }

    /**
     * 头部布局执行退出的动画
     */
    private void animateToHide() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rlsentimenttime, "translationX", 0, -rlsentimenttime.getWidth());
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(llpicimage, "translationY", 0, -llpicimage.getHeight());
        animatorSetHide.playTogether(leftAnim, topAnim);
        animatorSetHide.setDuration(300);
        animatorSetHide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOpen = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isOpen = true;
            }
        });
        if (!isOpen) {
            animatorSetHide.start();
        }
    }

    /**
     * 循环执行线程
     */
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(timerRunnable, 1000);
            long sysTime = System.currentTimeMillis();
            liveTime += 1000;
            CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", liveTime);
            CharSequence sysDateStr = DateFormat.format("yyyy/MM/dd", sysTime);
            tvtime.setText(sysTimeStr);
            tvdate.setText(sysDateStr);
        }
    };

    /**
     * 定时清除礼物
     */
    private void clearTiming() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = llgiftcontent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = llgiftcontent.getChildAt(i);
                    CustomRoundView crvheadimage = (CustomRoundView) view.findViewById(R.id.crvheadimage);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (Long) crvheadimage.getTag();
                    if ((nowtime - upTime) >= 3000) {
                        removeGiftView(i);
                        return;
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 3000);
    }


    /**
     * 数字放大动画
     */
    public class NumAnim {
        private Animator lastAnimator = null;

        public void start(View view) {
            if (lastAnimator != null) {
                lastAnimator.removeAllListeners();
                lastAnimator.end();
                lastAnimator.cancel();
            }
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1.0f);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1.0f);
            AnimatorSet animSet = new AnimatorSet();
            lastAnimator = animSet;
            animSet.setDuration(200);
            animSet.setInterpolator(new OvershootInterpolator());
            animSet.playTogether(anim1, anim2);
            animSet.start();
        }
    }

    private void initPopupWindow() {
        giftPopupWindow=new PopupWindow(getContext());
        //获取popwindow焦点
        giftPopupWindow.setFocusable(true);
        //设置PopupWindow可触摸
        giftPopupWindow.setTouchable(true);
        //设置popwindow如果点击外面区域，便关闭。
        giftPopupWindow.setOutsideTouchable(true);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        //DP转成Px

        int width =wm.getDefaultDisplay().getWidth();
        //设置宽和高
        giftPopupWindow.setWidth(width);
        giftPopupWindow.setHeight(width/2);
        //参数 设置透明背景
        giftPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        View view=View.inflate(mcontext,R.layout.popwindow_gift,null);

        final ViewPager popw_vp= (ViewPager) view.findViewById(R.id.popw_vp);
        popw_vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                int count=gifts.size();
                if(count%8==0) {
                    return count/8;
                }else {
                    return count/8+1;
                }
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(mcontext, R.layout.vp_item_dome, null);
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
                RecyclerView gift_list = (RecyclerView) view.findViewById(R.id.vp_rv_item);
                TextView gift_page = (TextView) view.findViewById(R.id.vp_tv_item);
                gift_page.setText("" + (position + 1));
                int start = 8 * position < gifts.size() ? 8 * position : 0;
                int last = (8 * position + 8) < gifts.size() ? (8 * position + 8) : gifts.size();
                ArrayList<Gift> datas = new ArrayList<Gift>();
                for (int i=start;i<last;i++){
                    datas.add(gifts.get(i));
                }
                GiftAdapter vpGiftAdapter =    new GiftAdapter(mcontext,datas);
                gift_list.setAdapter(vpGiftAdapter);
                vpGiftAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showGift("yj"+position);
                    }
                });
                gift_list.setLayoutManager(new GridLayoutManager(mcontext, 4));
                container.addView(view);
                return view;
            }
        });
        giftPopupWindow.setContentView(view);
    }

    void  getData(){
        Gift gift=new Gift() ;
        gift.setVpImg(R.drawable.bq4);
        gift.setVpTv("礼物");
        gifts.add(gift);
        for (int i=0;i<50;i++){
            gift=new Gift() ;
            gift.setVpImg(R.drawable.bq4);
            gift.setVpTv("礼物");
            gifts.add(gift);
        }
    }
}