/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.component.user;

import be.nille.dal.auth.database.exception.DataAccessException;

/**
 *
 * @author Niels Holvoet
 */
public class Token {

    private final String value;
    private final Long userId;

    public Token(final String value, final Long userId) {

        this.value = value;
        this.userId = userId;
    }

    public Long getId() {
        throw new DataAccessException("Id is not available yet, because it was not yet persisted");
    }

    public String getValue() {
        return value;
    }

    public Long getUserId() {
        return userId;
    }

}
