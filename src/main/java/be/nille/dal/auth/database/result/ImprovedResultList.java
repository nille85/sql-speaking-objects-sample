/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database.result;

import be.nille.dal.auth.database.exception.DataAccessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author nholvoet
 */
public class ImprovedResultList<T> {

    public List<T> mapFromResultSet(ResultSet rs, Function0<T> lambda) {
        Function<ResultSet, List<T>> func = (resultSet) -> {
            try {
                List<T> entries = new ArrayList<>();
                while (resultSet.next()) {
                    T entry = lambda.apply();
                    entries.add(entry);
                }
                return entries;
            } catch (SQLException ex) {
                throw new DataAccessException("Mapping exception occurred while translating data from database", ex);
            }
        };

        return func.apply(rs);
    }

}
