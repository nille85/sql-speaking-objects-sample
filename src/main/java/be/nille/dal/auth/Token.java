/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.auth;

/**
 *
 * @author Niels Holvoet
 */
public interface Token {
    
    Long getId();
    
    String getValue();
    
}
