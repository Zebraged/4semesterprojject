/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;

/**
 *
 * @author Marcg
 */
public class PlayerProcess implements IEntityProcessingService{

    public void process(GameData gameData, World world) {
        for(Entity entity : world.getEntities(Player.class)){
            PositionPart part = entity.getPart(PositionPart.class);
            if(gameData.getKeys().isDown(GameKeys.UP) == true){
                part.setY(part.getY() + 5);
            } else if(gameData.getKeys().isDown(GameKeys.DOWN) == true){
                part.setY(part.getY() - 5);
            } else if(gameData.getKeys().isDown(GameKeys.LEFT) == true){
                part.setX(part.getX() - 5);
            } else if(gameData.getKeys().isDown(GameKeys.RIGHT) == true){
                part.setX(part.getX() + 5);
            } 
        }
    }
    
}
