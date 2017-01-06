/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth;

import be.nille.dal.auth.database.exception.DataAccessException;
import lombok.ToString;

/**
 *
 * @author nholvoet
 */
@ToString
public class User {

    private final String email;
    private final String password;
    private final String role;

    public User(final String email, final String password, final String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        throw new DataAccessException("Id is not available yet, because it was not yet persisted");
    }

}
