/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.database.exception.DataAccessException;
import be.nille.dal.component.user.User;
import be.nille.dal.component.user.User;
import be.nille.dal.component.user.UserRepository;
import be.nille.dal.auth.database.result.ImprovedResultList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author nholvoet
 */
public class H2UserRepository implements UserRepository {

    private final DataSource dataSource;

    public H2UserRepository(final DataSource dataSource) {
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

        ImprovedResultList<User> resultList = new ImprovedResultList<>();
        return resultList.mapFromResultSet(rs,
                () -> {
                    try {
                        Long id = rs.getLong("USER_ID");
                        String email = rs.getString("USER_EMAIL");
                        String password = rs.getString("USER_PASSWORD");
                        String role = rs.getString("USER_ROLE");
                        return new H2User(id,
                                new User(email, password, role)
                        );
                    } catch (SQLException ex) {
                        throw new DataAccessException("An exception occurred while trying to map user", ex);
                    }
                }
        );

    }

    @Override
    public User add(User user) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            final String sql = "INSERT INTO USER (USER_EMAIL, USER_PASSWORD, USER_ROLE)"
                    + " VALUES (?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            int id = stmt.executeUpdate();
            return new H2User((long) id,
                    new User(user.getEmail(), user.getPassword(), user.getRole())
            );
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to add a user", ex);
        }
    }

}
