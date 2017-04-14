package com.github.dubu.lockscreenusingservice;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String NAME = "LOCKSCREEN";

    public enum Cmd {
        INIT("null");

        private String mDefault;

        private Cmd(String def) {
            this.mDefault = def;
        }

        public String getDefault() {
            return mDefault;
        }
    }

    private static SharedPreferences mPref = null;

    public static void init(Context context) {
        mPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static String get(Cmd cmd) {
        if (mPref == null || !mPref.contains(cmd.name())) {
            SharedPreferences.Editor edit = mPref.edit();
            edit.putString(cmd.toString(), cmd.getDefault());
            edit.commit();
        }

        return mPref.getString(cmd.toString(), "null");
    }

    public static void set(Cmd cmd, String data) {
        if (mPref != null) {
            SharedPreferences.Editor edit = mPref.edit();
            edit.putString(cmd.toString(), data);
            edit.commit();
        }
    }

    public static void setBoolean(String _key, boolean _value) {
        if (_key == null) {
            return;
        }

        if (mPref != null) {
            SharedPreferences.Editor edit = mPref.edit();
            edit.putBoolean(_key, _value);
            edit.commit();
        }
    }

    public static boolean get(String _key) {
        if (mPref == null || !mPref.contains(_key)) {
            SharedPreferences.Editor edit = mPref.edit();
            edit.putBoolean(_key, false);
            edit.commit();
        }

        return mPref.getBoolean(_key, false);
    }
}
