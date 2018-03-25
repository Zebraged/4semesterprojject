/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.type.Enemy;
import java.util.Random;

/**
 *
 * @author Marcg
 */
public class EnemyProcess implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for(Entity entity : world.getEntities(Enemy.class)){
            AssetGenerator assetGen = entity.getPart(AssetGenerator.class);
            Random rand = new Random();
            int i = rand.nextInt(1);
            if(i == 1){
                assetGen.nextImage("Idle");
            } else {
                assetGen.nextImage("Walk");
            }
            
        }
    }
    
}
