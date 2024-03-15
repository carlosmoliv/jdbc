package org.example;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            DataSource dataSource = createDataSource();
            Connection connection = dataSource.getConnection();
            System.out.println(connection.isValid(0));

            selectExample(connection);
            insertExample(connection);
            updateExample(connection);
            deleteExample(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';");
        return ds;
    }

    private static void selectExample(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from USERS where name = ?");
        ps.setString(1, "Aragorn");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
        }
    }

    private static void insertExample(Connection connection) throws SQLException {
        PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
        insertPs.setString(1, "Gandalf");
        int insertCount = insertPs.executeUpdate();
        System.out.println("Insert Count: " + insertCount);
    }

    private static void updateExample(Connection connection) throws SQLException {
        PreparedStatement updatePs = connection.prepareStatement("update USERS set name = ? where name = ?");
        updatePs.setString(1, "Gandalf the white");
        updatePs.setString(2, "Gandalf");
        int updateCount = updatePs.executeUpdate();
        System.out.println("Update Count: " + updateCount);
    }

    private static void deleteExample(Connection connection) throws SQLException {
        PreparedStatement deletePs = connection.prepareStatement("delete from USERS where name = ?");
        deletePs.setString(1, "Aragorn");
        int deleteCount = deletePs.executeUpdate();
        System.out.println("Delete Count: " + deleteCount);
    }
}
