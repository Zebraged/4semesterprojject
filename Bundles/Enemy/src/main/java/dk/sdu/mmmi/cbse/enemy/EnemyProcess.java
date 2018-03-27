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
import dk.sdu.mmmi.cbse.common.entityparts.GravityPart;
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
            GravityPart gravity = entity.getPart(GravityPart.class);
            Random rand = new Random();
            int i = rand.nextInt(2);
            if(gameData.getKeys().isDown(GameKeys.LEFT) == true){
                assetGen.nextImage("Walk", true);
            } else if(gameData.getKeys().isDown(GameKeys.RIGHT) == true){
                assetGen.nextImage("Walk", false);
            } else if(gameData.getKeys().isDown(GameKeys.DOWN) == true){
                System.out.println("down");
                gravity.process(gameData, entity);
            }
            assetGen.process(gameData, entity);
        }
    }
    
}
