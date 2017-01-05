/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

/**
 *
 * @author nholvoet
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
