/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.GravityPart_PLACEHOLDER;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.type.Enemy;
import dk.sdu.mmmi.cbse.enemy.type.TeddyEnemy;
import java.util.Random;

/**
 *
 * @author Marcg
 */
public class EnemyProcess implements IEntityProcessingService{
    
    
    
    @Override
    public void process(GameData gameData, World world) {
        for(Entity entity : world.getEntities(TeddyEnemy.class)){
            AssetGenerator assetGen = entity.getPart(AssetGenerator.class);
            GravityPart_PLACEHOLDER gravity = entity.getPart(GravityPart_PLACEHOLDER.class);
            PositionPart position = entity.getPart(PositionPart.class);
            float x = position.getX();
            float y = position.getY();
            Random rand = new Random();
            int i = rand.nextInt(2);
            if(position.getX() < x){
                assetGen.nextImage("Walk", false);
            } else if(position.getX() > x){
                assetGen.nextImage("Walk", true);
            } else if (position.getX() == x && position.getY() == y){
                assetGen.nextImage("Idle", true);
            }
            assetGen.process(gameData, entity);
            //gravity.process(gameData, entity);
        }
    }
    
}
