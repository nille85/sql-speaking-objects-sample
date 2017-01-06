/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth;

import java.util.List;

/**
 *
 * @author nholvoet
 */
public interface TokenRepository {

    List<Token> findByUserId(Long userId);

    Token addTokenForUser(Long userId, String value);

}
