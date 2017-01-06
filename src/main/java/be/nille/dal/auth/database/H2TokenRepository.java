/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.Token;
import be.nille.dal.auth.TokenRepository;
import be.nille.dal.auth.database.exception.DataAccessException;
import be.nille.dal.auth.database.result.ResultList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author nholvoet
 */
public class H2TokenRepository implements TokenRepository {

    private final DataSource dataSource;

    public H2TokenRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Token> findByUserId(Long userId) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            String selectSQL = "SELECT * FROM TOKEN WHERE USER_ID = ?";
            PreparedStatement stmt = connection.prepareStatement(selectSQL);
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Token> tokens = fromResultSet(rs);
            return tokens;
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to retrieve all users", ex);
        }
    }

    private List<Token> fromResultSet(final ResultSet rs) throws SQLException {
        return new ResultList<Token>(rs) {
            @Override
            public Token map() throws SQLException {
                Long id = rs.getLong("TOKEN_ID");
                String value = rs.getString("TOKEN_VALUE");
                Long userId = rs.getLong("USER_ID");

                return new H2Token(id, value, userId);
            }
        }.getList();
    }

    @Override
    public Token addTokenForUser(Long userId, String value) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            final String sql = "INSERT INTO TOKEN (TOKEN_VALUE, USER_ID)"
                    + " VALUES (?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, value);
            stmt.setLong(2, userId);
            int id = stmt.executeUpdate();
            return new H2Token((long) id, value, userId);
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to add a user", ex);
        }
    }

}
