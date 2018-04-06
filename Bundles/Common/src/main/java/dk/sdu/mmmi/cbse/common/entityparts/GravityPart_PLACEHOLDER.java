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
 * @author Marcg
 */
public class GravityPart_PLACEHOLDER implements EntityPart{
    
    float entityY;
    double time = 0.0;
    float velocity = 0.0f;
    final float FORCE = 10.0f;
    final float MASS = 1.0f;
    final float MAX_SPEED = 8;
   
    /**
     *
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart position = entity.getPart(PositionPart.class);
        float delta = gameData.getDelta();
        entityY = position.getY();
        
        if (velocity >= MAX_SPEED){
            velocity = MAX_SPEED;
            position.setY(entityY - velocity * delta);
        } else {
            velocity = velocity + (FORCE/MASS) * delta;
            position.setY(entityY - velocity * delta);
        }
        
       
    }
    
}
