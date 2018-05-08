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
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;

/**
 *
 * @author Marcg
 */
public class PlayerProcess implements IEntityProcessingService {

    private int oldLife = 0;
    
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            AssetGenerator assetGen = player.getPart(AssetGenerator.class);
            LifePart life = player.getPart(LifePart.class);
            if(life.getLife() <= 0){
                gameData.setGameLost(true);
            }
            
            float x = positionPart.getX();
            float y = positionPart.getY();
            
            
            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP)); 
            
            
            if (oldLife != 0 && oldLife > life.getLife() && positionPart.getX() < x ){
                x = x - 5;
            } else if(oldLife != 0 && oldLife > life.getLife()) {
                x = x + 5;
            }
            
            

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            oldLife = life.getLife();
            if(positionPart.getX() > x){
                assetGen.nextImage("Idle", false);
            } else if(positionPart.getX() < x){
                assetGen.nextImage("Idle", true);
            } else if (positionPart.getX() == x && positionPart.getY() == y){
                assetGen.nextImage("Idle", true);
            }
            
            assetGen.process(gameData, player);
            
        }
    }
}
