package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USER(id bigint auto_increment primary key," +
                "name varchar(35) not null," +
                "lastName varchar(45) not null," +
                "age int not null," +
                "constraint id_UNIQUE unique (id))";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE = "DROP TABLE IF EXISTS USER";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String ADD = "INSERT INTO USER (name, lastname, age) VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = Util.getConnection().prepareStatement(ADD);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setLong(3, age);
            statement.executeUpdate();
            System.out.println("User  именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String REM = "DELETE FROM USER WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(REM);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String GET_ALL = "SELECT ID, NAME, LASTNAME, AGE FROM USER";
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);

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
        String DELETE = "DELETE FROM USER";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
