package com.github.dubu.lockscreenusingservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.dubu.lockscreenusingservice.Lockscreen;
import com.github.dubu.lockscreenusingservice.LockscreenUtil;
import com.github.dubu.lockscreenusingservice.R;
import com.github.dubu.lockscreenusingservice.SharedPreferencesUtil;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


/**
 * Created by mugku on 15. 5. 20..
 */
public class LockscreenViewService extends Service {
    private final int LOCK_OPEN_OFFSET_VALUE = 50;
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private View mLockscreenView = null;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private RelativeLayout mBackgroundLayout = null;
    private RelativeLayout mBackgroundInLayout = null;
    private ImageView mBackgroundLockImageView = null;
    private RelativeLayout mForgroundLayout = null;
    private RelativeLayout mStatusBackgruondDummyView = null;
    private RelativeLayout mStatusForgruondDummyView = null;
    private ShimmerTextView mShimmerTextView = null;
    private boolean mIsLockEnable = false;
    private boolean mIsSoftkeyEnable = false;
    private int mDeviceWidth = 0;
    private int mDevideDeviceWidth = 0;
    private float mLastLayoutX = 0;
    private int mServiceStartId = 0;
    private SendMassgeHandler mMainHandler = null;
//    private boolean sIsSoftKeyEnable = false;

    private class SendMassgeHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            changeBackGroundLockView(mLastLayoutX);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SharedPreferencesUtil.init(mContext);
//        sIsSoftKeyEnable = SharedPreferencesUtil.get(Lockscreen.ISSOFTKEY);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMainHandler = new SendMassgeHandler();
        if (isLockScreenAble()) {
            if (null != mWindowManager) {
                if (null != mLockscreenView) {
                    mWindowManager.removeView(mLockscreenView);
                }
                mWindowManager = null;
                mParams = null;
                mInflater = null;
                mLockscreenView = null;
            }
            initState();
            initView();
            attachLockScreenView();
        }
        return LockscreenViewService.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        dettachLockScreenView();
    }


    private void initState() {

        mIsLockEnable = LockscreenUtil.getInstance(mContext).isStandardKeyguardState();
        if (mIsLockEnable) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                    PixelFormat.TRANSLUCENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mIsLockEnable && mIsSoftkeyEnable) {
                mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            } else {
                mParams.flags = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
            }
        } else {
            mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }

        if (null == mWindowManager) {
            mWindowManager = ((WindowManager) mContext.getSystemService(WINDOW_SERVICE));
        }
    }

    private void initView() {
        if (null == mInflater) {
            mInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (null == mLockscreenView) {
            mLockscreenView = mInflater.inflate(R.layout.view_locokscreen, null);

        }
    }

    private boolean isLockScreenAble() {
        boolean isLock = SharedPreferencesUtil.get(Lockscreen.ISLOCK);
        if (isLock) {
            isLock = true;
        } else {
            isLock = false;
        }
        return isLock;
    }


    private void attachLockScreenView() {

        if (null != mWindowManager && null != mLockscreenView && null != mParams) {
            mLockscreenView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            mWindowManager.addView(mLockscreenView, mParams);
            settingLockView();
        }

    }


    private boolean dettachLockScreenView() {
        if (null != mWindowManager && null != mLockscreenView) {
            mWindowManager.removeView(mLockscreenView);
            mLockscreenView = null;
            mWindowManager = null;
            stopSelf(mServiceStartId);
            return true;
        } else {
            return false;
        }
    }


    private void settingLockView() {
        mBackgroundLayout = (RelativeLayout) mLockscreenView.findViewById(R.id.lockscreen_background_layout);
        mBackgroundInLayout = (RelativeLayout) mLockscreenView.findViewById(R.id.lockscreen_background_in_layout);
        mBackgroundLockImageView = (ImageView) mLockscreenView.findViewById(R.id.lockscreen_background_image);
        mForgroundLayout = (RelativeLayout) mLockscreenView.findViewById(R.id.lockscreen_forground_layout);
        mShimmerTextView = (ShimmerTextView) mLockscreenView.findViewById(R.id.shimmer_tv);
        (new Shimmer()).start(mShimmerTextView);
        mForgroundLayout.setOnTouchListener(mViewTouchListener);

        mStatusBackgruondDummyView = (RelativeLayout) mLockscreenView.findViewById(R.id.lockscreen_background_status_dummy);
        mStatusForgruondDummyView = (RelativeLayout) mLockscreenView.findViewById(R.id.lockscreen_forground_status_dummy);
        setBackGroundLockView();

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        mDeviceWidth = displayMetrics.widthPixels;
        mDevideDeviceWidth = (mDeviceWidth / 2);
        mBackgroundLockImageView.setX((int) (((mDevideDeviceWidth) * -1)));

        //kitkat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int val = LockscreenUtil.getInstance(mContext).getStatusBarHeight();
            RelativeLayout.LayoutParams forgroundParam = (RelativeLayout.LayoutParams) mStatusForgruondDummyView.getLayoutParams();
            forgroundParam.height = val;
            mStatusForgruondDummyView.setLayoutParams(forgroundParam);
            AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
            alpha.setDuration(0); // Make animation instant
            alpha.setFillAfter(true); // Tell it to persist after the animation ends
            mStatusForgruondDummyView.startAnimation(alpha);
            RelativeLayout.LayoutParams backgroundParam = (RelativeLayout.LayoutParams) mStatusBackgruondDummyView.getLayoutParams();
            backgroundParam.height = val;
            mStatusBackgruondDummyView.setLayoutParams(backgroundParam);
        }
    }

    private void setBackGroundLockView() {
        if (mIsLockEnable) {
            mBackgroundInLayout.setBackgroundColor(getResources().getColor(R.color.lock_background_color));
            mBackgroundLockImageView.setVisibility(View.VISIBLE);

        } else {
            mBackgroundInLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            mBackgroundLockImageView.setVisibility(View.GONE);
        }
    }


    private void changeBackGroundLockView(float forgroundX) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (forgroundX < mDeviceWidth) {
                mBackgroundLockImageView.setBackground(getResources().getDrawable(R.drawable.lock));
            } else {
                mBackgroundLockImageView.setBackground(getResources().getDrawable(R.drawable.unlock));
            }
        } else {
            if (forgroundX < mDeviceWidth) {
                mBackgroundLockImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.lock));
            } else {
                mBackgroundLockImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.unlock));
            }
        }
    }


    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        private float firstTouchX = 0;
        private float layoutPrevX = 0;
        private float lastLayoutX = 0;
        private float layoutInPrevX = 0;
        private boolean isLockOpen = false;
        private int touchMoveX = 0;
        private int touchInMoveX = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {// 0
                    firstTouchX = event.getX();
                    layoutPrevX = mForgroundLayout.getX();
                    layoutInPrevX = mBackgroundLockImageView.getX();
                    if (firstTouchX <= LOCK_OPEN_OFFSET_VALUE) {
                        isLockOpen = true;
                    }
                }
                break;
                case MotionEvent.ACTION_MOVE: { // 2
                    if (isLockOpen) {
                        touchMoveX = (int) (event.getRawX() - firstTouchX);
                        if (mForgroundLayout.getX() >= 0) {
                            mForgroundLayout.setX((int) (layoutPrevX + touchMoveX));
                            mBackgroundLockImageView.setX((int) (layoutInPrevX + (touchMoveX / 1.8)));
                            mLastLayoutX = lastLayoutX;
                            mMainHandler.sendEmptyMessage(0);
                            if (mForgroundLayout.getX() < 0) {
                                mForgroundLayout.setX(0);
                            }
                            lastLayoutX = mForgroundLayout.getX();
                        }
                    } else {
                        return false;
                    }
                }
                break;
                case MotionEvent.ACTION_UP: { // 1
                    if (isLockOpen) {
                        mForgroundLayout.setX(lastLayoutX);
                        mForgroundLayout.setY(0);
                        optimizeForground(lastLayoutX);
                    }
                    isLockOpen = false;
                    firstTouchX = 0;
                    layoutPrevX = 0;
                    layoutInPrevX = 0;
                    touchMoveX = 0;
                    lastLayoutX = 0;
                }
                break;
                default:
                    break;
            }

            return true;
        }
    };

    private void optimizeForground(float forgroundX) {
//        final int devideDeviceWidth = (mDeviceWidth / 2);
        if (forgroundX < mDevideDeviceWidth) {
            int startPostion = 0;
            for (startPostion = mDevideDeviceWidth; startPostion >= 0; startPostion--) {
                mForgroundLayout.setX(startPostion);
            }
        } else {
            TranslateAnimation animation = new TranslateAnimation(0, mDevideDeviceWidth, 0, 0);
            animation.setDuration(300);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mForgroundLayout.setX(mDevideDeviceWidth);
                    mForgroundLayout.setY(0);
                    dettachLockScreenView();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            mForgroundLayout.startAnimation(animation);
        }
    }
}
