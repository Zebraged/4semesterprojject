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

    private static CollisionPart instance = null;

    private CollisionPart() {
        
    }
    
    public static CollisionPart getInstance() {
        if (instance == null) {
            instance = new CollisionPart();
        }
        return instance;
    }

    private float maxX = 0;
    private float maxY = 0;

    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
   
    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

}
