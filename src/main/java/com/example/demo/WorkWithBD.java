package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

public class WorkWithBD {
    private static final Logger log= LoggerFactory.getLogger(Controller.class);
    public int Create(User user)
    {
        String url ="jdbc:sqlserver://localhost;databaseName=bd_users;User=user123;Password=123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            log.error("Exception: ", e);
            return 0;
        }
        try(Connection conn= DriverManager.getConnection(url)){
            Statement statement=conn.createStatement();
            user.write();
            String query="insert into users(surname,name,patronym,login,password,registration,pol,role) values(?, ?, ?, ?, ?,CURRENT_TIMESTAMP,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, user.getSurname());
            preparedStmt.setString (2, user.getName());
            preparedStmt.setString(3, user.getPatronym());
            preparedStmt.setString(4, user.getLogin());
            preparedStmt.setString(5, user.getPassword());
            preparedStmt.setString(6, String.valueOf(user.getGender()));
            preparedStmt.setInt(7, user.getRole());
            preparedStmt.execute();
            ResultSet resultSet=statement.executeQuery("select max(id) from users;");
            resultSet.next();
            int id=resultSet.getInt(1);
            conn.close();
            return id;
        }
        catch(SQLException e)
        {
            log.error("Exception: ", e);
            return 0;
        }
    }
    public ArrayList<User> Read()
    {
        String url ="jdbc:sqlserver://localhost;databaseName=bd_users;User=user123;Password=123";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            log.error("Exception: ", e);
            return null;
        }
        try(Connection conn= DriverManager.getConnection(url)){
            Statement statement=conn.createStatement();
            ArrayList<User> users2=new ArrayList<>();
            ResultSet resultSet=statement.executeQuery("select * from users;");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String surname=resultSet.getString("surname");
                String name=resultSet.getString("name");
                String patronym=resultSet.getString("patronym");
                String login=resultSet.getString("login");
                String password=resultSet.getString("password");
                LocalDateTime registration=resultSet.getTimestamp("registration").toLocalDateTime();
                String gender=resultSet.getString("pol");
                char g=' ';
                if (gender!=null) gender.charAt(0);
                short role=resultSet.getShort("role");
                User user=new User(id,surname,name,patronym,login,password,registration,g,role);
                users2.add(user);
            }
            conn.close();
            return users2;
        }
        catch(SQLException e)
        {
            log.error("Exception: ", e);
            return null;
        }
    }
    public boolean Update(User user)
    {
        String url ="jdbc:sqlserver://localhost;databaseName=bd_users;User=user123;Password=123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            log.error("Exception: ", e);
            return false;
        }
        try(Connection conn= DriverManager.getConnection(url)){
            String query="update users set surname=?,name=?,patronym=?,login=?,password=?,pol=?,role=? where id=?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, user.getSurname());
            preparedStmt.setString (2, user.getName());
            preparedStmt.setString(3, user.getPatronym());
            preparedStmt.setString(4, user.getLogin());
            preparedStmt.setString(5, user.getPassword());
            preparedStmt.setString(6, String.valueOf(user.getGender()));
            preparedStmt.setInt(7, user.getRole());
            preparedStmt.setInt(8, user.getId());
            preparedStmt.executeUpdate();
            conn.commit();
            conn.close();
            return true;
        }
        catch(SQLException e)
        {
            log.error("Exception: ", e);
            return false;
        }
    }
    public boolean Delete(int id)
    {
        String url ="jdbc:sqlserver://localhost;databaseName=bd_users;User=user123;Password=123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            log.error("Exception: ", e);
            return false;
        }
        try(Connection conn= DriverManager.getConnection(url)){
            String query="delete from users where id=?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, id);
            preparedStmt.executeUpdate();
            conn.commit();
            conn.close();
            return true;
        }
        catch(SQLException e)
        {
            log.error("Exception: ", e);
            return false;
        }
    }
    public User FindById(int id)
    {
        String url ="jdbc:sqlserver://localhost;databaseName=bd_users;User=user123;Password=123";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            log.error("Exception: ", e);
            return null;
        }
        try(Connection conn= DriverManager.getConnection(url)){
            System.out.println("ПОДКЛЮЧЕННО");
            String query="select * from users where id=?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, id);
            ResultSet resultSet=preparedStmt.executeQuery();
            User user=null;
            if (resultSet.next())
            {
                String login=resultSet.getString("login");
                String surname=resultSet.getString("surname");
                String name=resultSet.getString("name");
                String patronym=resultSet.getString("patronym");
                String password=resultSet.getString("password");
                LocalDateTime registration=resultSet.getTimestamp("registration").toLocalDateTime();
                String gender=resultSet.getString("pol");
                char g=' ';
                if (gender!=null) gender.charAt(0);
                short role=resultSet.getShort("role");
                user=new User(id,surname,name,patronym,login,password,registration,g,role);
                user.write();
            }
            conn.close();
            return user;
        }
        catch(SQLException e)
        {
            log.error("Exception: ", e);
            return null;
        }
    }

}
