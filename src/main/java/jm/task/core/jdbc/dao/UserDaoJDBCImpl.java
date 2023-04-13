package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String create = "CREATE TABLE Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20),lastName VARCHAR(20), age INT)";
            statement.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String drop = "DROP TABLE USERS";
            statement.executeUpdate(drop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String save = "INSERT INTO Users( name, lastName, age) Values (?,?,?) ";
        try (PreparedStatement statement = connection.prepareStatement(save)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) throws SQLException {
        String del = "DELETE FROM Users WHERE Id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(del)) {
            statement.setByte(1, (byte) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String all = "SELECT id, name, lastName, age FROM Users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(all);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            System.out.println(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
