/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.component.user;

import be.nille.dal.auth.database.FluentJdbcTokenRepository;
import be.nille.dal.auth.database.FluentJdbcUserRepository;
import be.nille.dal.auth.database.H2UserRepository;
import be.nille.dal.auth.helper.SQLFile;
import be.nille.dal.auth.helper.SQLFileTest;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.codejargon.fluentjdbc.api.query.Query;

/**
 *
 * @author nholvoet
 */
public class UserRepositoryTest {

    private JdbcDataSource ds;
    private Query query;

    @Before
    public void setup() throws SQLException {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:TEST;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.setUser("sa");
        ds.setPassword("");
        
        RepositoryInitializer<Query> initializer = new FluentJdbcInitializer(ds);
        query = initializer.initialize();
        
        Connection connection = ds.getConnection();
        InputStream inputStream = SQLFileTest.class.getClassLoader().getResourceAsStream("create_database.sql");
        SQLFile sqlFile = new SQLFile(connection, inputStream);
        sqlFile.execute();
    }

    @Test
    public void users() {
        UserRepository repository = new FluentJdbcUserRepository(query);
        repository.findAll().iterator().forEachRemaining(System.out::println);

    }

    @Test
    public void addUser() {
        UserRepository repository = new FluentJdbcUserRepository(query);
        User user = new User("another@doe.be", "john", "user");
        repository.add(user);
        repository.findAll().iterator().forEachRemaining(System.out::println);
    }

    @Test
    public void addToken() {

        UserRepository users = new FluentJdbcUserRepository(query);
        TokenRepository tokens = new FluentJdbcTokenRepository(query);

        String tokenValue = "token value";
        Long userId = 1L;

        users.findOne(userId)
                .ifPresent(user -> {
                    tokens.addTokenForUser(new Token(tokenValue,user.getId()));
                });
        tokens.findByUserId(userId)
                .stream()
                .forEach(System.out::println);

    }

}
