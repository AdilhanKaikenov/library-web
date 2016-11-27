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

    @Override
    public T create(T entity) throws DaoException {
        log.debug("Entering JdbcDao class, create() method.");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery());
            preparedStatement = setFieldsInCreatePreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Integer id = getID(generatedKeys);
            if (id != null) {
                entity.setId(id);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, create() method. {}", e);
            throw new DaoException(MessageFormat.format("Error: JdbcDao class, create() method. {0}", e));
        }
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
        log.debug("JdbcDao class getID() method result = {}", id);
        return id;
    }

    protected abstract PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, T entity) throws DaoException;

    protected abstract String getCreateQuery();

    protected abstract String getTableName();

    @Override
    public int countParameter(String columnName, String parameter) throws DaoException {
        log.debug("Entering countParameter() method, arguments: columnName = {}, parameter = {}", columnName, parameter);
        String query = "SELECT COUNT('" + columnName + "') FROM PUBLIC." + getTableName() + " WHERE " + columnName + " = '" + parameter + "'";
        int count = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                count = resultSet.getInt(1);
                log.debug("JdbcDao class countParameter() result = {}", count);
            }
        } catch (SQLException e) {
            log.error("Error: Can not count '{}' parameter. JdbcDao class countParameter() method: {}", parameter, e);
            throw new DaoException(MessageFormat.format(
                    "Error: Can not count {0} parameter. JdbcDao class countParameter() method: {1}", parameter, e));
        }
        return count;
    }

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
}

