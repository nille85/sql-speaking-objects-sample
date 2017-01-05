/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.helper;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author nholvoet
 */
public class SQLFile {

    private final Connection connection;
    private final InputStream inputStream;

    public SQLFile(final Connection connection, InputStream inputStream) {
        this.connection = connection;
        this.inputStream = inputStream;
    }

    public void execute() throws SQLException {
        Scanner s = new Scanner(inputStream);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        try (Statement st = connection.createStatement()) {
            while (s.hasNext()) {
                String line = s.next();
                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        }
    }

}
