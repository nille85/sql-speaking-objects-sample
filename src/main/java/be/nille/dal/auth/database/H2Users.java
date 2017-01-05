/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.database.result.ResultList;
import be.nille.dal.auth.User;
import be.nille.dal.auth.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author nholvoet
 */
public class H2Users implements Users {

    private final DataSource dataSource;

    public H2Users(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<User> findAll() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
            List<User> users = fromResultSet(rs);
            return users;
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to retrieve all users", ex);
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            String selectSQL = "SELECT * FROM USER WHERE USER_ID = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSQL);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            List<User> users = fromResultSet(rs);
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }
            return Optional.empty();

        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to retrieve all users", ex);
        }
    }

    private List<User> fromResultSet(final ResultSet rs) throws SQLException {
        return new ResultList<User>(rs) {
            @Override
            public User map() throws SQLException {
                Long id = rs.getLong("USER_ID");
                String email = rs.getString("USER_EMAIL");
                String password = rs.getString("USER_PASSWORD");
                String role = rs.getString("USER_ROLE");
                return new H2User(id, email, password, role);
            }
        }.getList();
    }

    

    @Override
    public User add(String email, String password, String role) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            final String sql = "INSERT INTO USER (USER_EMAIL, USER_PASSWORD, USER_ROLE)"
                    + " VALUES (?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int id = stmt.executeUpdate();
            return new H2User((long) id, email, password, role);
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to add a user", ex);
        }
    }
    
    
   

}
