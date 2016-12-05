package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.exception.DaoException;
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
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final String TABLE_NAME = "USER";
    private static final String CREATE_QUERY = "INSERT INTO USER (LOGIN, PASSWORD, EMAIL, FIRSTNAME, SURNAME, " +
            "PATRONYMIC, GENDER, ADDRESS, MOBILE_PHONE, ROLE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_LOGIN_PASSWORD = "SELECT USER.ID, USER.LOGIN, USER.PASSWORD, USER.EMAIL, USER.FIRSTNAME, " +
            "USER.SURNAME, USER.PATRONYMIC, GENDER.GENDER_TYPE AS GENDER, USER.ADDRESS, USER.MOBILE_PHONE, ROLE.ROLE_TYPE AS ROLE, USER.STATUS FROM USER " +
            "INNER JOIN ROLE ON USER.ROLE = ROLE.ID " +
            "INNER JOIN GENDER ON USER.GENDER = GENDER.ID WHERE LOGIN = ? AND PASSWORD = ?";
    private static final String SELECT_ALL_FROM_USER = "SELECT USER.ID, USER.LOGIN, USER.PASSWORD, USER.EMAIL, USER.FIRSTNAME," +
            "USER.SURNAME, USER.PATRONYMIC, GENDER.GENDER_TYPE AS GENDER, USER.ADDRESS, USER.MOBILE_PHONE, ROLE.ROLE_TYPE AS ROLE, USER.STATUS FROM USER" +
            "INNER JOIN ROLE ON USER.ROLE = ROLE.ID" +
            "INNER JOIN GENDER ON USER.GENDER = GENDER.ID";
    private static final String SELECT_BY_ID = "SELECT USER.ID, USER.LOGIN, USER.PASSWORD, USER.EMAIL, USER.FIRSTNAME," +
            "USER.SURNAME, USER.PATRONYMIC, GENDER.GENDER_TYPE AS GENDER, USER.ADDRESS, USER.MOBILE_PHONE, ROLE.ROLE_TYPE AS ROLE, USER.STATUS FROM USER" +
            "INNER JOIN ROLE ON USER.ROLE = ROLE.ID" +
            "INNER JOIN GENDER ON USER.GENDER = GENDER.ID WHERE PUBLIC.USER.ID = ?";

    public JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<User> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcUserDao class, createListFrom() method");
        List<User> result = new ArrayList<>();
        try {
            while (resultSet.next()){
                User user = new User();
                log.debug("Creating user from resultSet");
                user.setId(resultSet.getInt("ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setFirstname(resultSet.getString("FIRSTNAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setPatronymic(resultSet.getString("PATRONYMIC"));
                user.setGender(Gender.from(resultSet.getString("GENDER")));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setMobilePhone(resultSet.getString("MOBILE_PHONE"));
                user.setRole(Role.from(resultSet.getString("ROLE")));
                user.setStatus(resultSet.getBoolean("STATUS"));
                log.debug("User successfully created in createFrom() method. User id = {}", user.getId());
                result.add(user);
            }
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class createListFrom() method. I can not create List of user from resultSet. {}", e);
            throw new DaoException("Error: JdbcUserDao class createListFrom() method. I can not create List of user from resultSet.", e);
        }
        log.debug("Leaving JdbcUserDao class, createListFrom() method.");
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, User entity) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInCreatePreparedStatement() method. User = {}", entity.getLogin());
        try {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getFirstname());
            preparedStatement.setString(5, entity.getSurname());
            preparedStatement.setString(6, entity.getPatronymic());
            preparedStatement.setInt(7, entity.getGender().ordinal());
            preparedStatement.setString(8, entity.getAddress());
            preparedStatement.setString(9, entity.getMobilePhone());
            preparedStatement.setInt(10, entity.getRole().ordinal());
            preparedStatement.setBoolean(11, true);
            log.debug("Leaving JdbcUserDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, User entity) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method. User = {}", entity.getLogin());
        try {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            log.debug("Leaving JdbcUserDao class, setFieldsInReadByEntityPreparedStatement() method");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInReadByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInReadByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getReadAllQuery() {
        return SELECT_ALL_FROM_USER;
    }

    @Override
    protected String getReadByEntityQuery() {
        return SELECT_BY_LOGIN_PASSWORD;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getReadRangeQuery() {
        return null;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldInReadAllByIdParameterPreparedStatement(PreparedStatement preparedStatement, int id) throws DaoException {
        return null;
    }

    @Override
    protected PreparedStatement setFieldInCountNumberRowsByIdPreparedStatement(PreparedStatement preparedStatement, int id) {
        return null;
    }

}
