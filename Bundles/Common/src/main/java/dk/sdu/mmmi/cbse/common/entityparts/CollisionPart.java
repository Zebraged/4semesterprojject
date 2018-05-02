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
 * @author Jesper
 */
public class CollisionPart implements EntityPart {

    private float minX = 0;
    private float maxX = 0;
    private float minY = 0;
    private float maxY = 0;

    public CollisionPart() {

    }

    

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float maxY) {
        this.minY = maxY;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    float getMinX() {
        return minX;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMaxY(){
        return maxY;
    }
    
}
