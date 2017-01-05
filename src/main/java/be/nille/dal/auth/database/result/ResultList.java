/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels Holvoet
 */
public abstract class ResultList<T> {

    final List<T> list;

    public ResultList(final ResultSet resultSet) throws SQLException {
        this.list = fromResultSet(resultSet);
    }

    public List<T> getList() {
        return list;
    }

    public abstract T map() throws SQLException;

    private List<T> fromResultSet(final ResultSet resultSet) throws SQLException {
        List<T> entries = new ArrayList<>();
        while (resultSet.next()) {
            T entry = map();
            entries.add(entry);
        }
        return entries;
    }

}
