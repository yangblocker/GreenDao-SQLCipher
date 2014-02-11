package com.greendao.db;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Application;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoMaster.DevOpenHelper;
import de.greenrobot.daoexample.DaoSession;

public class DBManager {
	private final static String DB_NAME = "notes-db";

	private DBManager() {}

	private static DevOpenHelper sDevOpenHelper;
	private static DaoMaster sDaoMaster;

	public static void init(Application app) {
		sDevOpenHelper = new DaoMaster.DevOpenHelper(app, DB_NAME, null);
		
		SQLiteDatabase.loadLibs(app.getApplicationContext());
		
		sDaoMaster = new DaoMaster(sDevOpenHelper.getWritableDatabase("123456"));
	}

	public static void destroy() {
		try {
			if (sDaoMaster != null) {
				sDaoMaster.getDatabase().close();
				sDaoMaster = null;
			}

			if (sDevOpenHelper != null) {
				sDevOpenHelper.close();
				sDevOpenHelper = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DaoSession newSession() {
		if (sDaoMaster == null) {
			throw new RuntimeException("sDaoMaster is null.");
		}
		return sDaoMaster.newSession();
	}

}
