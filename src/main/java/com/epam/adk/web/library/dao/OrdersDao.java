package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Order;

import java.util.List;

/**
 * Interface OrdersDao created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public interface OrdersDao extends Dao<Order> {

    int getNumberRowsByStatus(boolean status) throws DaoException;

    List<Order> readRangeByStatus(boolean status, int offset, int limit) throws DaoException;


}
