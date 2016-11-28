package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.Dao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;

/**
 * Abstract class JdbcDao created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final Logger log = LoggerFactory.getLogger(JdbcDao.class);

    private Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public T create(T entity) throws DaoException {
        log.debug("Entering JdbcDao class, create() method.");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getCreateQuery());
            preparedStatement = setFieldsInCreatePreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            Integer id = getID(resultSet);
            if (id != null) {
                entity.setId(id);
            }
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, create() method. {}", e);
            throw new DaoException(MessageFormat.format("Error: JdbcDao class, create() method. {0}", e));
        } finally {
            close(preparedStatement, resultSet);
        }
        log.debug("Leaving JdbcDao class, create() method.");
        log.debug("New entity successfully created, type = {}, id = {}", entity.getClass().getSimpleName(), entity.getId());
        return entity;
    }

    private Integer getID(ResultSet generatedKeys) throws DaoException {
        log.debug("Entering JdbcDao class, getID() method.");
        Integer id = null;
        try {
            while (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            log.error("I can not get the ID from generatedKeys, JdbcDao class, getID() method. {}", e);
            throw new DaoException(MessageFormat.format(
                    "I can not get the ID from generatedKeys, JdbcDao class, getID() method. {0}", e));
        }
        log.debug("Leaving JdbcDao class getID() method result = {}", id);
        return id;
    }

    protected abstract PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, T entity) throws DaoException;

    protected abstract String getCreateQuery();

    protected abstract String getTableName();

    @Override
    public T read(int id) throws DaoException {
        return null;
    }

    @Override
    public T update(T entity) throws DaoException {
        return null;
    }

    @Override
    public void delete(T entity) throws DaoException {
    }

    protected void close(PreparedStatement preparedStatement, ResultSet resultSet) throws DaoException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error("Error: JdbcDao class, close() method. Can not close preparedStatement. {}", e);
                throw new DaoException(MessageFormat.format(
                        "Error: JdbcDao class, close() method. Can not close preparedStatement. {0}", e));
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("Error: JdbcDao class, close() method. Can not close resultSet. {}", e);
                throw new DaoException(MessageFormat.format(
                        "Error: JdbcDao class, close() method. Can not close resultSet. {0}", e));
            }
        }
    }
}

