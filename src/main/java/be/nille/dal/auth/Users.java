/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth;

import java.util.Optional;

/**
 *
 * @author nholvoet
 */
public interface Users {

    Optional<User> findOne(Long id);

    Iterable<User> findAll();

    User add(String email, String password, String role);

}
