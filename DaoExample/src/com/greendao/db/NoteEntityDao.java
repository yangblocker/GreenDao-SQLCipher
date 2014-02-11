package com.greendao.db;


import com.greendao.db.base.BaseEntityDao;

import de.greenrobot.daoexample.Note;
import de.greenrobot.daoexample.NoteDao;

public class NoteEntityDao extends BaseEntityDao<NoteDao, Note, Long> {

	@Override
	protected NoteDao initEntityDao() {
		return DBManager.newSession().getNoteDao();
	}

	public void deleteEntityByKey(long key) {
		getEntityDao().deleteByKey(key);
	}
	
}
