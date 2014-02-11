package com.greendao.db;


import com.greendao.db.base.BaseEntityDao;

import de.greenrobot.daoexample.Order;
import de.greenrobot.daoexample.OrderDao;

public class OrderEntityDao extends BaseEntityDao<OrderDao, Order, Long>{

	public void deleteEntityByKey(long key) {
		getEntityDao().deleteByKey(key);
	}

	@Override
	public OrderDao initEntityDao() {
		return DBManager.newSession().getOrderDao();
	}

}
