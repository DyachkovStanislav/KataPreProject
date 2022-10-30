package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USER(id bigint auto_increment primary key," +
            "name varchar(35) not null," +
            "lastName varchar(45) not null," +
            "age int not null," +
            "constraint id_UNIQUE unique (id))";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS USER";
    private final String DELETE = "DELETE FROM USER";
    private final String GET_ALL = "SELECT ID, NAME, LASTNAME, AGE FROM USER";
    private final String ADD = "INSERT INTO USER (name, lastname, age) VALUES(?, ?, ?)";
    private final String REM = "DELETE FROM USER WHERE ID = ?";


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util util = new Util();
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(CREATE_TABLE);
            preparedStatement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Util util = new Util();
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Util util = new Util();
        try {
            PreparedStatement statement = util.getConnection().prepareStatement(ADD);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setLong(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        Util util = new Util();
        try {
            User user = new User();
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(REM);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        Util util = new Util();
        List<User> list = new ArrayList<>();
        try (Connection connection = util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public void cleanUsersTable() {
        Util util = new Util();
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
