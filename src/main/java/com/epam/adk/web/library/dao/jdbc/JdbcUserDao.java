package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * JdbcUserDao class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

    private static final String TABLE_NAME = "USER";
    private static final String USER_CREATE_QUERY = "INSERT INTO USER (LOGIN, PASSWORD, EMAIL, FIRSTNAME, SURNAME, " +
            "PATRONYMIC, GENDER, ADDRESS, MOBILE_PHONE, ROLE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private Connection connection;

    public JdbcUserDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, User entity) throws DaoException {
        log.debug("Entering JdbcUserDao class, setFieldsInCreatePreparedStatement() method");
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
        } catch (SQLException e) {
            log.error("Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. " +
                    "I can not set fields into statement. {}", e);
            throw new DaoException(MessageFormat.format(
                    "Error: JdbcUserDao class setFieldsInCreatePreparedStatement() method. " +
                            "I can not set fields into statement. {0}", e));
        }
        log.debug("Exit JdbcUserDao class, setFieldsInCreatePreparedStatement() method");
        return preparedStatement;
    }

    @Override
    protected String getCreateQuery() {
        return USER_CREATE_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
