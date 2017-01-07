/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.component.user;

import be.nille.dal.component.user.User;
import be.nille.dal.auth.database.H2User;
import be.nille.dal.auth.database.exception.DataAccessException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author nholvoet
 */
public class UserTest {

    @Test(expected = DataAccessException.class)
    public void getId() {

        User user = new User("tester@test.be", "password", "admin");
        user.getId();

    }

    @Test
    public void wrapUser() {
        User origin = new User("tester@test.be", "password", "admin");
        User user = new H2User(1L, origin);
        assertTrue(1L == user.getId());

    }

}
