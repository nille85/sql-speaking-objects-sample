/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.helper;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class SQLFileTest {

    @Before
    public void setup() {

    }

    @Test
    public void importSQL() throws ClassNotFoundException, SQLException {
        /*
         Class.forName("org.h2.Driver");
         Connection connection = DriverManager.getConnection(
         "jdbc:h2:mem:TEST;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
         "sa",
         "");
         */
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:TEST;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.setUser("sa");
        ds.setPassword("");
        Connection connection = ds.getConnection();

        InputStream inputStream = SQLFileTest.class.getClassLoader().getResourceAsStream("create_database.sql");
        SQLFile sqlFile = new SQLFile(connection, inputStream);

        sqlFile.execute();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
        connection.commit();

        while (rs.next()) {
            String email = rs.getString("USER_EMAIL");
            System.out.println(email);
        }

    }

}
