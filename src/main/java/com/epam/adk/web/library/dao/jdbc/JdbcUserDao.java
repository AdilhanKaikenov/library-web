package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.DaoUnsupportedOperationException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcUserDao class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see UserDao
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final String TABLE_NAME = "user";
    private static final String SELECT_COLUMNS_PART = queryProperties.get("select.user.columns");
    private static final String CREATE_QUERY = queryProperties.get("insert.user");
    private static final String SELECT_BY_LOGIN_PASSWORD = queryProperties.get("select.by.login.password");
    private static final String SELECT_RANGE = queryProperties.get("select,range.users");
    private static final String SELECT_BY_ID = queryProperties.get("select.user.by.id");
    private static final String UPDATE_QUERY = queryProperties.get("update.user");
    private static final String DELETE_QUERY = queryProperties.get("delete.user");

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<User> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcUserDao class, createListFrom() method");
        List<User> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                log.debug("Creating user getFromValue resultSet");
                log.debug("set id");
                user.setId(resultSet.getInt(ID_COLUMN_NAME));
                log.debug("set login");
                user.setLogin(resultSet.getString(LOGIN_COLUMN_NAME));
                log.debug("set password");
                user.setPassword(resultSet.getString(PASSWORD_COLUMN_NAME));
                log.debug("set email");
                user.setEmail(resultSet.getString(EMAIL_COLUMN_NAME));
                log.debug("set firstname");
                user.setFirstname(resultSet.getString(FIRSTNAME_COLUMN_NAME));
                log.debug("set surname");
                user.setSurname(resultSet.getString(SURNAME_COLUMN_NAME));
                log.debug("set patronymic");
                user.setPatronymic(resultSet.getString(PATRONYMIC_COLUMN_NAME));
                log.debug("set gender");
                user.setGender(Gender.getFromValue(resultSet.getString(GENDER_COLUMN_NAME)));
                log.debug("set address");
                user.setAddress(resultSet.getString(ADDRESS_COLUMN_NAME));
                log.debug("set mobile phone");
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE_COLUMN_NAME));
                log.debug("set role");
                user.setRole(Role.getFromValue(resultSet.getString(ROLE_COLUMN_NAME)));
                log.debug("set status");
                user.setStatus(resultSet.getBoolean(STATUS_COLUMN_NAME));
                log.debug("User successfully created in createListFrom() method. User id = {}", user.getId());
                result.add(user);
            }
            log.debug("Leaving JdbcUserDao class, createListFrom() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcUserDao class createListFrom() method. I can not create List of users getFromValue resultSet.", e);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInCreatePreparedStatement() method. User = {}", user.getLogin());
        try {
            log.debug("Set login: {}", user.getLogin());
            preparedStatement.setString(FIRST_PARAMETER_INDEX, user.getLogin());
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(SECOND_PARAMETER_INDEX, user.getPassword());
            log.debug("Set email: {}", user.getEmail());
            preparedStatement.setString(THIRD_PARAMETER_INDEX, user.getEmail());
            log.debug("Set firstname: {}", user.getFirstname());
            preparedStatement.setString(FOURTH_PARAMETER_INDEX, user.getFirstname());
            log.debug("Set surname: {}", user.getSurname());
            preparedStatement.setString(FIFTH_PARAMETER_INDEX, user.getSurname());
            log.debug("Set patronymic: {}", user.getPatronymic());
            preparedStatement.setString(SIXTH_PARAMETER_INDEX, user.getPatronymic());
            log.debug("Set gender: {}",  user.getGender());
            preparedStatement.setInt(SEVENTH_PARAMETER_INDEX, user.getGender().ordinal());
            log.debug("Set address: {}", user.getAddress());
            preparedStatement.setString(EIGHTH_PARAMETER_INDEX, user.getAddress());
            log.debug("Set mobile phone: {}", user.getMobilePhone());
            preparedStatement.setString(NINTH_PARAMETER_INDEX, user.getMobilePhone());
            log.debug("Set role: {}", user.getRole());
            preparedStatement.setInt(TENTH_PARAMETER_INDEX, user.getRole().ordinal());
            log.debug("Leaving JdbcUserDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method. User = {}", user.getLogin());
        try {
            log.debug("Set login: {}", user.getLogin());
            preparedStatement.setString(FIRST_PARAMETER_INDEX, user.getLogin());
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(SECOND_PARAMETER_INDEX, user.getPassword());
            log.debug("Leaving JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcUserDao class setFieldsInReadByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set password: {}", user.getPassword());
            preparedStatement.setString(FIRST_PARAMETER_INDEX, user.getPassword());
            log.debug("Set email: {}", user.getEmail());
            preparedStatement.setString(SECOND_PARAMETER_INDEX, user.getEmail());
            log.debug("Set address: {}", user.getAddress());
            preparedStatement.setString(THIRD_PARAMETER_INDEX, user.getAddress());
            log.debug("Set mobile phone: {}", user.getMobilePhone());
            preparedStatement.setString(FOURTH_PARAMETER_INDEX, user.getMobilePhone());
            log.debug("Set role: {}", user.getRole());
            preparedStatement.setInt(FIFTH_PARAMETER_INDEX, user.getRole().ordinal());
            log.debug("Set status, is active: {}", user.isStatus());
            preparedStatement.setBoolean(SIXTH_PARAMETER_INDEX, user.isStatus());
            log.debug("Set user ID: {}", user.getId());
            preparedStatement.setInt(SEVENTH_PARAMETER_INDEX, user.getId());
            log.debug("Leaving JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcUserDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_COLUMNS_PART + SELECT_BY_ID;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE;
    }

    @Override
    protected String getReadByEntityQuery() {
        return SELECT_COLUMNS_PART + SELECT_BY_LOGIN_PASSWORD;
    }

    @Override
    protected String getDeleteByIdQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }
}
