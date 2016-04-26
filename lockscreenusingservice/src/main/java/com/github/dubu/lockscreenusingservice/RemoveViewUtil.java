package com.github.dubu.lockscreenusingservice;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by mugku on 15. 5. 20..
 */
public class RemoveViewUtil {
    private static final String TAG = "RemoveViewUtil";

    private volatile static RemoveViewUtil RemoveViewUtilUniqueInstance;

    public static RemoveViewUtil getInstance() {
        if (RemoveViewUtilUniqueInstance == null) {
            synchronized (RemoveViewUtil.class) {
                if (RemoveViewUtilUniqueInstance == null) {
                    RemoveViewUtilUniqueInstance = new RemoveViewUtil();
                }
            }
        }
        return RemoveViewUtilUniqueInstance;
    }

    private RemoveViewUtil() {
    }

    public void unbindDrawables(View view) {
        if (null != view) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        }
    }
}

