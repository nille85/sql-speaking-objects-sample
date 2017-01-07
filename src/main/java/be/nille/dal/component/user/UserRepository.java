/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.component.user;

import java.util.Optional;

/**
 *
 * @author nholvoet
 */
public interface UserRepository {

    Optional<User> findOne(Long id);

    Iterable<User> findAll();

    User add(final User user);

}
