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
 * @see UserDao
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final String TABLE_NAME = "user";
    private static final String CREATE_QUERY = "INSERT INTO user (login, password, email, firstname, surname, " +
            "patronymic, gender, address, mobile_phone, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_LOGIN_PASSWORD = "SELECT user.id, user.login, user.password, user.email, user.firstname, " +
            "user.surname, user.patronymic, gender.type AS gender, user.address, user.mobile_phone, role.type AS role, user.status FROM user " +
            "INNER JOIN role ON user.role = role.id " +
            "INNER JOIN gender ON user.gender = gender.id WHERE login = ? AND password = ?";
    private static final String SELECT_ALL = "SELECT user.id, user.login, user.password, user.email, user.firstname," +
            "user.surname, user.patronymic, gender.type AS gender, user.address, user.mobile_phone, role.type AS role, user.status FROM user " +
            "INNER JOIN role ON user.role = role.id " +
            "INNER JOIN gender ON user.gender = gender.id";
    private static final String SELECT_RANGE = "SELECT user.id, user.login, user.password, user.email, user.firstname," +
            "user.surname, user.patronymic, gender.type AS gender, user.address, user.mobile_phone, role.type AS role, user.status FROM user " +
            "INNER JOIN role ON user.role = role.id " +
            "INNER JOIN gender ON user.gender = gender.id ORDER BY user.login LIMIT ? OFFSET ?";
    private static final String SELECT_BY_ID = "SELECT user.id, user.login, user.password, user.email, user.firstname," +
            "user.surname, user.patronymic, gender.type AS gender, user.address, user.mobile_phone, role.type AS role, user.status FROM user " +
            "INNER JOIN role ON user.role = role.id " +
            "INNER JOIN gender ON user.gender = gender.id WHERE user.id = ?";
    private static final String UPDATE_QUERY = "UPDATE user SET password = ?, email = ?, address = ?, mobile_phone = ?, role = ?, status = ? WHERE id LIKE ?";

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
            log.debug("Leaving JdbcUserDao class, createListFrom() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class createListFrom() method. I can not create List of users from resultSet. {}", e);
            throw new DaoException("Error: JdbcUserDao class createListFrom() method. I can not create List of users from resultSet.", e);
        }
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
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, User user) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getMobilePhone());
            preparedStatement.setInt(5, user.getRole().ordinal());
            preparedStatement.setBoolean(6, user.isStatus());
            preparedStatement.setInt(7, user.getId());
            log.debug("Leaving JdbcUserDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcUserDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }


    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
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
        return SELECT_ALL;
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
        return SELECT_RANGE;
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

}
