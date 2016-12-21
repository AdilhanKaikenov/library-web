package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.Dao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.JdbcDaoConfigurationException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.model.BaseEntity;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

/**
 * Abstract class JdbcDao created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see Dao
 */
public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    private static final Logger log = LoggerFactory.getLogger(JdbcDao.class);

    protected static PropertiesManager queryProperties;

    private static final String QUERY_PROPERTIES_FILE_NAME = "query.properties";
    private String GENERAL_SELECT_COUNT_PLUS_TABLE_QUERY = queryProperties.get("select.count.from");

    protected static final int ZERO = 0;
    protected static final int FIRST_COLUMN_INDEX = 1;
    protected static final int FIRST_PARAMETER_INDEX = 1;
    protected static final int SECOND_PARAMETER_INDEX = 2;
    protected static final int THIRD_PARAMETER_INDEX = 3;
    protected static final int FOURTH_PARAMETER_INDEX = 4;
    protected static final int FIFTH_PARAMETER_INDEX = 5;
    protected static final int SIXTH_PARAMETER_INDEX = 6;
    protected static final int SEVENTH_PARAMETER_INDEX = 7;
    protected static final int EIGHTH_PARAMETER_INDEX = 8;
    protected static final int NINTH_PARAMETER_INDEX = 9;
    protected static final int TENTH_PARAMETER_INDEX = 10;

    private Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return connection;
    }

    public static void configure() throws JdbcDaoConfigurationException {
        try {
            queryProperties = new PropertiesManager(QUERY_PROPERTIES_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new JdbcDaoConfigurationException("Error: JdbcDao class, configure() method.", e);
        }
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
            log.debug("New entity successfully created, type = {}, id = {}", entity.getClass().getSimpleName(), entity.getId());
            log.debug("Leaving JdbcDao class, create() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, create() method. {}", e);
            throw new DaoException("Error: JdbcDao class, create() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return entity;
    }

    @Override
    public T read(int id) throws DaoException {
        log.debug("Entering JdbcDao class, read(id) method");
        T result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getReadByIdQuery());
            preparedStatement = setFieldInReadByIdPreparedStatement(preparedStatement, id);
            resultSet = preparedStatement.executeQuery();
            result = createFrom(resultSet);
            log.debug("Leaving JdbcDao class, read(id) method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class read(id) method. I can not read entity by id from resultSet. {}", e);
            throw new DaoException("Error: JdbcDao class read(id) method. I can not read entity by id from resultSet.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public T read(T entity) throws DaoException {
        log.debug("Entering JdbcDao class, read() method");
        T result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getReadByEntityQuery());
            preparedStatement = setFieldsInReadByEntityPreparedStatement(preparedStatement, entity);
            resultSet = preparedStatement.executeQuery();
            result = createFrom(resultSet);
            log.debug("Leaving JdbcDao class, read() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, read() method. I can not read by entity: {}", e);
            throw new DaoException("Error: JdbcDao class, read() method. I can not read by entity:", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public List<T> readRange(int offset, int limit) throws DaoException {
        log.debug("Entering JdbcDao class, readRange() method. Offset = {}, limit = {}", offset, limit);
        List<T> result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getReadRangeQuery());
            preparedStatement = setFieldsInReadRangePreparedStatement(preparedStatement, offset, limit);
            resultSet = preparedStatement.executeQuery();
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcDao class, readRange() method. Amount of result = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcDao class readRange() method. I can not read range entity from resultSet. {}", e);
            throw new DaoException("Error: JdbcDao class readRange() method. I can not read range entity from resultSet.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public List<T> readAllByIdParameter(int id) throws DaoException {
        log.debug("Entering JdbcDao class, readAllByIdParameter() method");
        List<T> result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getReadAllByIdParameterQuery());
            preparedStatement = setFieldInReadAllByIdParameterPreparedStatement(preparedStatement, id);
            resultSet = preparedStatement.executeQuery();
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcDao class, readAllByIdParameter() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class readAllByIdParameter() method. {}", e);
            throw new DaoException("Error: JdbcDao class readAllByIdParameter() method. ", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public List<T> readRangeByIdParameter(int id, int offset, int limit) throws DaoException {
        log.debug("Entering JdbcDao class, readRangeByIdParameter() method. ID = {}", id);
        List<T> result;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(getReadRangeByIdParameterQuery());
            preparedStatement = setFieldsInReadRangeByIdParameterPreparedStatement(preparedStatement, id, offset, limit);
            resultSet = preparedStatement.executeQuery();
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcDao class, readRangeByIdParameter() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class readRangeByIdParameter() method. I can not read range entities. {}", e);
            throw new DaoException("Error: JdbcDao class readRangeByIdParameter() method. I can not read range entities.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public void update(T entity) throws DaoException {
        log.debug("Entering JdbcDao class, update() method. ");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getUpdateByEntityQuery());
            preparedStatement = setFieldsInUpdateByEntityPreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
            log.debug("Leaving JdbcDao class, update() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class update() method. I can not update entity. {}", e);
            throw new DaoException("Error: JdbcDao class update() method. I can not update entity. ", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void delete(T entity) throws DaoException {
        log.debug("Entering JdbcDao class, delete() method. Entity ID = {}", entity.getId());
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getDeleteQuery());
            preparedStatement = setFieldsInDeleteByEntityStatement(preparedStatement, entity);
            preparedStatement.execute();
            log.debug("Leaving JdbcDao class, delete() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class delete() method. I can not delete entity. {}", e);
            throw new DaoException("Error: JdbcDao class delete() method. I can not delete entity. ", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void deleteByIdParameter(int id) throws DaoException {
        log.debug("Entering JdbcDao class, delete() method. Entity ID = {}", id);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getDeleteByIdQuery());
            preparedStatement = setFieldsInDeleteByIdParameter(preparedStatement, id);
            preparedStatement.execute();
            log.debug("Leaving JdbcDao class, delete() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class delete() method. I can not delete entity. {}", e);
            throw new DaoException("Error: JdbcDao class delete() method. I can not delete entity. ", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    protected T createFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcDao class, createFrom() method");
        T entity;
        List<T> entities = createListFrom(resultSet);
        if (entities.size() == ZERO) {
            throw new DaoException(MessageFormat.format(
                    "Error: JdbcDao class createFrom() method. I can not create entity from resultSet. Entities size = {0}", entities.size()));
        }
        entity = entities.get(ZERO);
        log.debug("Leaving JdbcDao class, createFrom() method.");
        return entity;
    }

    @Override
    public int getNumberRowsByIdParameter(int id) throws DaoException {
        log.debug("Entering JdbcDao class, getNumberRowsByIdParameter() method. Id = {}", id);
        int numberRows = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(getCountNumberRowsByIdParameterQuery());
            preparedStatement = setFieldInCountNumberRowsByIdPreparedStatement(preparedStatement, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberRows = resultSet.getInt(FIRST_COLUMN_INDEX);
            }
            log.debug("Leaving JdbcDao class getNumberRowsByIdParameter() method. Rows number = {}", numberRows);
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, getNumberRowsByIdParameter() method. {}", e);
            throw new DaoException("Error: JdbcDao class, getNumberRowsByIdParameter() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return numberRows;
    }

    @Override
    public int getNumberRows() throws DaoException {
        log.debug("Entering JdbcDao class, getNumberRows() method.");
        int numberRows = 0;
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(getCountNumberRowsQuery());
            if (resultSet.next()) {
                numberRows = resultSet.getInt(FIRST_COLUMN_INDEX);
            }
            log.debug("Leaving JdbcDao class getNumberRows() method. Rows number = {}", numberRows);
        } catch (SQLException e) {
            log.error("Error: JdbcDao class, getNumberRows() method. {}", e);
            throw new DaoException("Error: JdbcDao class, getNumberRows() method.", e);
        } finally {
            closeResultSet(resultSet);
        }
        return numberRows;
    }

    private Integer getID(ResultSet generatedKeys) throws DaoException {
        log.debug("Entering JdbcDao class, getID() method.");
        Integer id = null;
        try {
            while (generatedKeys.next()) {
                id = generatedKeys.getInt(FIRST_COLUMN_INDEX);
            }
            log.debug("Leaving JdbcDao class getID() method. result = {}", id);
        } catch (SQLException e) {
            log.error("Error: I can not get the ID from generatedKeys, JdbcDao class, getID() method. {}", e);
            throw new DaoException("Error: I can not get the ID from generatedKeys, JdbcDao class, getID() method.", e);
        }
        return id;
    }

    protected void close(PreparedStatement preparedStatement, ResultSet resultSet) throws DaoException {
        closePreparedStatement(preparedStatement);
        closeResultSet(resultSet);
    }

    protected void closePreparedStatement(PreparedStatement preparedStatement) throws DaoException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error("Error: JdbcDao class, close() method. Can not close preparedStatement. {}", e);
                throw new DaoException("Error: JdbcDao class, close() method. Can not close preparedStatement.", e);
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) throws DaoException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("Error: JdbcDao class, close() method. Can not close resultSet. {}", e);
                throw new DaoException("Error: JdbcDao class, close() method. Can not close resultSet.", e);
            }
        }
    }

    protected PreparedStatement setFieldInReadByIdPreparedStatement(PreparedStatement preparedStatement, int id) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldInReadByIdPreparedStatement() method. ID = {}", id);
        try {
            log.debug("Set id: {}", id);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, id);
            log.debug("Leaving JdbcDao class, setFieldInReadByIdPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldInReadByIdPreparedStatement() method. I can not set field into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldInReadByIdPreparedStatement() method. I can not set field into statement.", e);
        }
        return preparedStatement;
    }

    private PreparedStatement setFieldsInDeleteByIdParameter(PreparedStatement preparedStatement, int id) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldsInDeleteByIdParameter() method. ID = {}", id);
        try {
            log.debug("Set id: {}", id);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, id);
            log.debug("Leaving JdbcDao class, setFieldsInDeleteByIdParameter() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldsInDeleteByIdParameter() method. I can not set field into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldsInDeleteByIdParameter() method. I can not set field into statement.", e);
        }
        return preparedStatement;
    }


    protected PreparedStatement setFieldsInReadRangePreparedStatement(PreparedStatement preparedStatement, int offset, int limit) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldsInReadRangePreparedStatement() method.");
        try {
            log.debug("Set limit: {}", limit);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, limit);
            log.debug("Set offset: {}", offset);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, offset);
            log.debug("Leaving JdbcDao class, setFieldsInReadRangePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldsInReadRangePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldsInReadRangePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    protected PreparedStatement setFieldsInReadRangeByIdParameterPreparedStatement(PreparedStatement preparedStatement, int id, int offset, int limit) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldsInReadRangeByIdParameterPreparedStatement() method.");
        try {
            log.debug("Set id: {}", id);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, id);
            log.debug("Set limit: {}", limit);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, limit);
            log.debug("Set offset: {}", offset);
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, offset);
            log.debug("Leaving JdbcDao class, setFieldsInReadRangeByIdParameterPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldsInReadRangeByIdParameterPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldsInReadRangeByIdParameterPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    protected PreparedStatement setFieldInCountNumberRowsByIdPreparedStatement(PreparedStatement preparedStatement, int id) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldInCountNumberRowsByIdPreparedStatement() method.");
        try {
            log.debug("Set id: {}", id);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, id);
            log.debug("Leaving JdbcDao class, setFieldInCountNumberRowsByIdPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldInCountNumberRowsByIdPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcBookDao class setFieldInCountNumberRowsByIdPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    protected PreparedStatement setFieldInReadAllByIdParameterPreparedStatement(PreparedStatement preparedStatement, int id) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldInReadAllByIdParameterPreparedStatement() method.");
        try {
            log.debug("Set id: {}", id);
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, id);
            log.debug("Leaving JdbcDao class, setFieldInReadAllByIdParameterPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldInReadAllByIdParameterPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldInReadAllByIdParameterPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    protected PreparedStatement setFieldsInDeleteByEntityStatement(PreparedStatement preparedStatement, T entity) throws DaoException {
        log.debug("Entering JdbcDao class, setFieldsInDeleteByEntityStatement() method.");
        try {
            log.debug("Set entity id: {}", entity.getId());
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, entity.getId());
            log.debug("Leaving JdbcDao class, setFieldsInDeleteByEntityStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcDao class setFieldsInDeleteByEntityStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcDao class setFieldsInDeleteByEntityStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    protected String getCountNumberRowsQuery() {
        return GENERAL_SELECT_COUNT_PLUS_TABLE_QUERY + getTableName();
    }

    protected abstract String getTableName();

    protected abstract String getDeleteQuery();

    protected abstract String getCreateQuery();

    protected abstract String getReadByIdQuery();

    protected abstract String getReadRangeQuery();

    protected abstract String getDeleteByIdQuery();

    protected abstract String getReadByEntityQuery();

    protected abstract String getUpdateByEntityQuery();

    protected abstract String getReadAllByIdParameterQuery();

    protected abstract String getReadRangeByIdParameterQuery();

    protected abstract String getCountNumberRowsByIdParameterQuery();

    protected abstract List<T> createListFrom(ResultSet resultSet) throws DaoException;

    protected abstract PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, T entity) throws DaoException;

    protected abstract PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, T entity) throws DaoException;

    protected abstract PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, T entity) throws DaoException;
}

