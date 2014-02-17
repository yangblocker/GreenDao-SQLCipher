package com.greendao.db;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	}

	
	public void doSomething(Context context) {
		SQLiteDatabase.loadLibs(context); 
		DBHelper dbHelper = new DBHelper(context, "db-name", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase("secret_key");  
	}
}

