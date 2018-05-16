/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Jesper
 */
public class PlatformObj extends PositionObj {
    /**
     * Instantiates a new platform object.
     * @param e
     * @param sizex
     * @param sizey 
     */
    public PlatformObj(Entity e, int sizex, int sizey) {
        super(e, sizex, sizey);
    }
    
}
