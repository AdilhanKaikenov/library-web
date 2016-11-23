package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.BaseEntity;

/**
 * Interface Dao created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public interface Dao<T extends BaseEntity> {

    T create(T entity) throws DaoException;

    T read(int id) throws DaoException;

    T update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;

}