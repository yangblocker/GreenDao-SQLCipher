package com.greendao.db.base;


import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public abstract class BaseEntityDao<DAO extends AbstractDao<E, K>, E, K>{
	public static final boolean DEBUG = true;
	private DAO aDao;
	
	private boolean forCurThread = false;
	
	static {
		if (DEBUG) {
			QueryBuilder.LOG_SQL = true;
			QueryBuilder.LOG_VALUES = true;
		}
	}
	
	public BaseEntityDao() {
		aDao = initEntityDao();
	}
	
	public void setForCurThread(boolean curThread) {
		this.forCurThread = curThread;
	}
	
	protected abstract DAO initEntityDao();
	
	public DAO getEntityDao() {
		return aDao;
	}
	
	public void insertEntity(E entity) {
		aDao.insert(entity);
	}
	
	public void deleteEntity(E entity) {
		aDao.delete(entity);
	}
	
	public void deleteAll() {
		aDao.deleteAll();
	}
	
	
	public void deleteEntityByKey(K key) {
		aDao.deleteByKey(key);
	}
	
	
	public List<E> queryEntitiesByKey(K key, boolean asc) {
		QueryBuilder<E> qb = aDao.queryBuilder().where(aDao.getPkProperty().eq(key), (WhereCondition)null);
		if (asc) {
			qb.orderAsc(aDao.getPkProperty());
		} else {
			qb.orderDesc(aDao.getPkProperty());
		}
		if (forCurThread) {
			return qb.build().forCurrentThread().list();
		} else {
			return qb.list();
		}
	}
	
	public E queryEntityByKey(K key) {
		QueryBuilder<E> qb = aDao.queryBuilder().where(aDao.getPkProperty().eq(key), (WhereCondition)null);
		if (forCurThread) {
			return qb.build().forCurrentThread().unique();
		}
		return qb.unique();
	}
	
	public List<E> queryEntitiesByArgs(WhereCondition ...whereCondition) {
		if (whereCondition == null || whereCondition.length == 0) {
			if (forCurThread) {
				return aDao.queryBuilder().build().forCurrentThread().list();
			}
			return aDao.queryBuilder().list();
		}
		if (forCurThread) {
			return aDao.queryBuilder().where(null, whereCondition).build().forCurrentThread().list();
		}
		return aDao.queryBuilder().where(null, whereCondition).list();
	}
	
	public List<E> queryEntities(String where, String selectionArg) {
		return aDao.queryRaw(where, selectionArg);
	}

}
