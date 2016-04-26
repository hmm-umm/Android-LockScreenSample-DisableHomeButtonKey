package com.github.dubu.lockscreenusingservice;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by mugku on 15. 5. 20..
 */
/**
 * SharedPreferences 를 사용 하기 위한 Util
 *
 */
public class SharedPreferencesUtil
{
	private static final String NAME = "LOCKSCREEN";
	public enum Cmd
	{
		/** 초기화 관련 SharedPreferences */
		INIT("null");

		private String mDefault;

		private Cmd(String def) {
			this.mDefault = def;
	    }
		
		public String getDefault(){
			return mDefault;
		}
	}
	
	private static SharedPreferences mPref = null;
	
	/**
	 * 초기화
	 * @param context Context
	 */
	public static void init(Context context)
	{
		mPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}
	
	
	/**
	 * SharedPreferences Data 를 가지고 온다.
	 * @param cmd
	 * @return
	 */
	public static String get(Cmd cmd)
	{
		if (mPref == null || !mPref.contains(cmd.name()))
		{
			SharedPreferences.Editor edit = mPref.edit();
			edit.putString(cmd.toString(), cmd.getDefault());
			edit.commit();
		}
		
		return mPref.getString(cmd.toString(), "null");
	}
	
	/**
	 * SharedPreferences Data 를 설정 한다.
	 * @param cmd Command
	 * @param data 저장 값
	 */
	public static void set(Cmd cmd, String data)
	{
		if (mPref != null)
		{
			SharedPreferences.Editor edit = mPref.edit();
			edit.putString(cmd.toString(), data);
			edit.commit();
		}
	}
	
	/**
	 * calendar에서 사용할 값을 받아서 저장..
	 */
	public static void setBoolean(String _key, boolean _value)
	{
		if( _key == null )
		{
			return;
		}
		
		if ( mPref != null )
		{
			SharedPreferences.Editor edit = mPref.edit();
			edit.putBoolean(_key, _value);
			edit.commit();
		}
	}
	
	/**
	 * setBooleanArray로 설정한 값을 가져오기 위해 만들었음.
	 * 위 get(Cmd cmd)함수로 가져오려고 하면 enum에 없는 값이라서 못 가져옴..
	 */
	public static boolean get(String _key)
	{
		if (mPref == null || !mPref.contains(_key) )
		{
			SharedPreferences.Editor edit = mPref.edit();
			edit.putBoolean(_key, false);
			edit.commit();
		}
		
		return mPref.getBoolean(_key, false);
	}
}
