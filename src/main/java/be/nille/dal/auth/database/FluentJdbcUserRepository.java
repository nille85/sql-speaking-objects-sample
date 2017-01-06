/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.database.exception.DataAccessException;
import be.nille.dal.auth.User;
import be.nille.dal.auth.UserRepository;
import be.nille.dal.auth.database.result.ImprovedResultList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

/**
 *
 * @author nholvoet
 */
public class FluentJdbcUserRepository implements UserRepository {

    private final Query query;

    public FluentJdbcUserRepository(final DataSource dataSource) {
        FluentJdbc fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(dataSource)
                .build();
        query = fluentJdbc.query();
    }

    @Override
    public Iterable<User> findAll() {
        return query.select("SELECT * FROM USER")
                .listResult(resultSet -> mapResultSet(resultSet));
    }

    @Override
    public Optional<User> findOne(Long id) {

        return query.select("SELECT * FROM USER WHERE USER_ID = ?")
                .params(id)
                .firstResult(resultSet -> mapResultSet(resultSet));

    }

    @Override
    public User add(User user) {
        UpdateResultGenKeys<Long> result = query.update("INSERT INTO USER (USER_EMAIL, USER_PASSWORD, USER_ROLE) VALUES (?,?,?)")
                .params(user.getEmail(), user.getPassword(), user.getRole())
                .runFetchGenKeys(Mappers.singleLong());
        Long id = result.generatedKeys().get(0);
        return new H2User(id, user);
    }

    private User mapResultSet(final ResultSet rs) throws SQLException {
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

}
