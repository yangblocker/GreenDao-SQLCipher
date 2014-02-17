package de.greenrobot.daoexample;

import com.greendao.db.DBManager;

import android.app.Application;

public class DApp extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		DBManager.init(this);
	}
	
	@Override
	public void onTerminate() {
		
		DBManager.destroy();
		super.onTerminate();
	}
}
