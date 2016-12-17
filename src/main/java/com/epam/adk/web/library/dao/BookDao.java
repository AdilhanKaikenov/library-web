package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;

import java.util.List;

/**
 * Interface BookDao created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public interface BookDao extends Dao<Book> {

    List<Book> findByTitle(String title) throws DaoException;

    int countAvailableAmount(int bookID) throws DaoException;
}
