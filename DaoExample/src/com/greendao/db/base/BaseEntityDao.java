package com.greendao.db.base;


import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public abstract class BaseEntityDao<DAO extends AbstractDao<E, K>, E, K>{
	public static final boolean DEBUG = true;
	private DAO mDao;
	
	private boolean forCurThread = false;
	
	static {
		if (DEBUG) {
			QueryBuilder.LOG_SQL = true;
			QueryBuilder.LOG_VALUES = true;
		}
	}
	
	public BaseEntityDao() {
		mDao = initEntityDao();
	}
	
	public void setForCurThread(boolean curThread) {
		this.forCurThread = curThread;
	}
	
	protected abstract DAO initEntityDao();
	
	public DAO getEntityDao() {
		return mDao;
	}
	
	public void insertEntity(E entity) {
		mDao.insert(entity);
	}
	
	public void deleteEntity(E entity) {
		mDao.delete(entity);
	}
	
	public void deleteAll() {
		mDao.deleteAll();
	}
	
	
	public void deleteEntityByKey(K key) {
		mDao.deleteByKey(key);
	}
	
	
	public List<E> queryEntitiesByKey(K key, boolean asc) {
		QueryBuilder<E> qb = mDao.queryBuilder().where(mDao.getPkProperty().eq(key), (WhereCondition)null);
		if (asc) {
			qb.orderAsc(mDao.getPkProperty());
		} else {
			qb.orderDesc(mDao.getPkProperty());
		}
		if (forCurThread) {
			return qb.build().forCurrentThread().list();
		} else {
			return qb.list();
		}
	}
	
	public E queryEntityByKey(K key) {
		QueryBuilder<E> qb = mDao.queryBuilder().where(mDao.getPkProperty().eq(key), (WhereCondition)null);
		if (forCurThread) {
			return qb.build().forCurrentThread().unique();
		}
		return qb.unique();
	}
	
	public List<E> queryEntitiesByArgs(WhereCondition ...whereCondition) {
		if (whereCondition == null || whereCondition.length == 0) {
			if (forCurThread) {
				return mDao.queryBuilder().build().forCurrentThread().list();
			}
			return mDao.queryBuilder().list();
		}
		if (forCurThread) {
			return mDao.queryBuilder().where(null, whereCondition).build().forCurrentThread().list();
		}
		return mDao.queryBuilder().where(null, whereCondition).list();
	}
	
	public List<E> queryEntities(String where, String... selectionArg) {
		return mDao.queryRaw(where, selectionArg);
	}

}
