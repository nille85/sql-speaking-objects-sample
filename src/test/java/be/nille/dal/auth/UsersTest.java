/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth;

import be.nille.dal.auth.database.H2Users;
import be.nille.dal.auth.helper.SQLFile;
import be.nille.dal.auth.helper.SQLFileTest;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class UsersTest {

    private JdbcDataSource ds;

    @Before
    public void setup() throws SQLException {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:TEST;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.setUser("sa");
        ds.setPassword("");
        Connection connection = ds.getConnection();

        InputStream inputStream = SQLFileTest.class.getClassLoader().getResourceAsStream("create_database.sql");
        SQLFile sqlFile = new SQLFile(connection, inputStream);
        sqlFile.execute();
    }

    @Test
    public void users() {
        Users users = new H2Users(ds);
        users.findAll().iterator().forEachRemaining(System.out::println);

    }

    @Test
    public void add() {
        Users users = new H2Users(ds);
        users.add("another@doe.be", "john", "user");
        users.findAll().iterator().forEachRemaining(System.out::println);
    }

}
