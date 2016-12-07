package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.BaseEntity;

import java.util.List;

/**
 * Interface Dao created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public interface Dao<T extends BaseEntity> {

    T create(T entity) throws DaoException;

    T read(int id) throws DaoException;

    T read(T entity) throws DaoException;

    List<T> readAll() throws DaoException;

    List<T> readRange(int offset, int limit) throws DaoException;

    List<T> readAllByIdParameter(int id) throws DaoException;

    List<T> readRangeByIdParameter(int id, int offset, int limit) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;

    int getNumberRowsByIdParameter(int id) throws DaoException;

    int getNumberRows() throws DaoException;

}