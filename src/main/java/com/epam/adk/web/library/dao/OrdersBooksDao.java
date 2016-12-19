package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.OrderBook;

/**
 * Interface OrdersBooksDao created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public interface OrdersBooksDao extends Dao<OrderBook> {

    OrderBook read(int userID, int bookID) throws DaoException;

    void delete(int userID, int bookID) throws DaoException;

    int countOrderedBooks(OrderBook order) throws DaoException;

    int getNumberRowsByBookId(int bookID) throws DaoException;

    int countAvailableAmount(int bookID) throws DaoException;

}
