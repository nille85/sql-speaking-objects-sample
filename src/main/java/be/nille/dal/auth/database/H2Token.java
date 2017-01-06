/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.Token;
import lombok.ToString;

/**
 *
 * @author Niels Holvoet
 */
@ToString
public class H2Token extends Token {

    private final Long id;

    public H2Token(final Long id, final String value, final Long userId) {
        super(value, userId);
        this.id = id;

    }

    @Override
    public Long getId() {
        return id;
    }

}
