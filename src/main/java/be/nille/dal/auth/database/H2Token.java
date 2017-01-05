/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.Token;

/**
 *
 * @author Niels Holvoet
 */
public class H2Token implements Token {
    
    private final Long id;
    private final String value;
    
    public H2Token(final Long id, final String value){
        this.id = id;
        this.value = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }
    
    
    
}
