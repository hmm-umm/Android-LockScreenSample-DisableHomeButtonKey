package com.github.dubu.lockscreenusingservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.github.dubu.lockscreenusingservice.service.LockscreenViewService;

/**
 * Created by mugku on 15. 3. 16..
 */
public class LockscreenActivity extends Activity {
    private final String TAG = "LockscreenActivity";
    private static Context sLockscreenActivityContext = null;
    ;
    private RelativeLayout mLockscreenMainLayout = null;

    public static SendMassgeHandler mMainHandler = null;

    public PhoneStateListener phoneStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        sLockscreenActivityContext = this;
        mMainHandler = new SendMassgeHandler();
//        getWindow().setType(2004);
//        getWindow().addFlags(524288);
//        getWindow().addFlags(4194304);
        ///
        getWindow().setType(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        initLockScreenUi();

        setLockGuard();
    }

    private class SendMassgeHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finishLockscreenAct();
        }
    }

    private void finishLockscreenAct() {
        finish();
    }


    private void initLockScreenUi() {
        setContentView(R.layout.activity_lockscreen);
        mLockscreenMainLayout = (RelativeLayout) findViewById(R.id.lockscreen_main_layout);
        mLockscreenMainLayout.getBackground().setAlpha(15);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void setLockGuard() {
        boolean isLockEnable = false;
        if (!LockscreenUtil.getInstance(sLockscreenActivityContext).isStandardKeyguardState()) {
            isLockEnable = false;
        } else {
            isLockEnable = true;
        }

        Intent startLockscreenIntent = new Intent(this, LockscreenViewService.class);
        startService(startLockscreenIntent);

        boolean isSoftkeyEnable = LockscreenUtil.getInstance(sLockscreenActivityContext).isSoftKeyAvail(this);
        SharedPreferencesUtil.setBoolean(Lockscreen.ISSOFTKEY, isSoftkeyEnable);
        if (!isSoftkeyEnable) {
            mMainHandler.sendEmptyMessage(0);
        } else if (isSoftkeyEnable) {
            if (isLockEnable) {
                mMainHandler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}
