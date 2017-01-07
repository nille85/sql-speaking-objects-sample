/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.component.user.Token;
import be.nille.dal.component.user.TokenRepository;
import be.nille.dal.auth.database.exception.DataAccessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

/**
 *
 * @author nholvoet
 */
public class FluentJdbcTokenRepository implements TokenRepository {

    private final Query query;

    public FluentJdbcTokenRepository(final Query query) {
        this.query = query;
    }

    @Override
    public List<Token> findByUserId(Long userId) {
        return query.select("SELECT * FROM TOKEN WHERE USER_ID = ?")
                .params(userId)
                .listResult(resultSet -> mapResultSet(resultSet));
    }



    private Token mapResultSet(final ResultSet rs) throws SQLException {
        try {
            Long id = rs.getLong("TOKEN_ID");
            String value = rs.getString("TOKEN_VALUE");
            Long userId = rs.getLong("USER_ID");
            return new H2Token(id,
                    new Token(value, userId)
            );
        } catch (SQLException ex) {
            throw new DataAccessException("An exception occurred while trying to map user", ex);
        }
    }

    @Override
    public Token addTokenForUser(final Token token) {
         UpdateResultGenKeys<Long> result = query.update("INSERT INTO TOKEN (TOKEN_VALUE, USER_ID)"
                    + " VALUES (?,?);")
                .params(token.getValue(), token.getUserId())
                .runFetchGenKeys(Mappers.singleLong());
        Long id = result.generatedKeys().get(0);
        return new H2Token(id, new Token(token.getValue(),token.getUserId()));
    }

}
