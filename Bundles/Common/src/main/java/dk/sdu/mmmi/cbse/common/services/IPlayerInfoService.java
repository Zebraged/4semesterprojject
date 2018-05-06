/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

/**
 *
 * @author Marcg
 */
public interface IPlayerInfoService {

    /**
     * get the x position of the player
     *
     * @return
     */
    public float getX();

    /**
     * get the y position of the player
     *
     * @return
     */
    public float getY();

    public int getLife();

    public void updateLife(int amount);
}
