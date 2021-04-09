package serverside.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
    private static List<User> userList;

    public List<User> getUserList() {
        return this.userList;
    }

    public DataBase() {
        try {
            setConnection();
            createDb();
            writeDB();
            readDB();
            closeDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void setConnection() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:");
    }

    public static void createDb() throws SQLException {
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE if not exists 'users'('login' text, 'password' text, 'nick' text);");
    }

    public static void writeDB() throws SQLException {
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('P', '1', 'p');");
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('V', '2', 'v');");
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('S', '3', 's');");
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('K', '4', 'k');");
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('G', '5', 'g');");
        statement.execute("INSERT INTO 'users' ('login', 'password', 'nick') VALUES ('X', '6', 'x');");
    }

    public static void readDB() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
            userList = new ArrayList<>();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String nick = resultSet.getString("nick");
                userList.add(new User(login, password, nick));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void closeDB() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    static class User {
        String login;
        String password;
        String nick;

        public User(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }

    }
}
