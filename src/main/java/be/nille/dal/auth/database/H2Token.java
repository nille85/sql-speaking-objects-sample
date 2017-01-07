/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.component.user.Token;
import lombok.ToString;

/**
 *
 * @author Niels Holvoet
 */
@ToString
public class H2Token extends Token {

    private final Long id;

    public H2Token(final Long id, final Token origin) {
        super(origin.getValue(), origin.getUserId());
        this.id = id;

    }

    @Override
    public Long getId() {
        return id;
    }

}
