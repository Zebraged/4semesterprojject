/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private float z;

    /**
     *
     * @param x
     * @param y
     */
    public PositionPart(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public float getY() {
        return y;
    }
    
    public float getZ(){
        return z;
    }

    /**
     *
     * @param newX
     */
    public void setX(float newX) {
        this.x = newX;
    }
    
    /**
     *
     * @param newY
     */
    public void setY(float newY) {
        this.y = newY;
    }
    public void setZ(float newZ){
        this.z = newZ;
    }

    /**
     *
     * @param newX
     * @param newY
     */
    public void setPosition(float newX, float newY, float newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    /**
     *
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
    
    
    
}
