/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.User;
import be.nille.dal.auth.User;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
public class H2User extends User {

    private final Long id;

    public H2User(final Long id, final User origin) {
        super(origin.getEmail(), origin.getPassword(), origin.getRole());
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

}
