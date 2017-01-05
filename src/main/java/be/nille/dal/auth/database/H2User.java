/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth.database;

import be.nille.dal.auth.User;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
public class H2User implements User {

    private final Long id;
    private final String email;
    private final String password;
    private final String role;

    public H2User(final Long id, final String email, final String password, final String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getRole() {
        return role;
    }

}
