/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
//import dk.sdu.mmmi.cbse.enemy.EnemyPlugin;
//import dk.sdu.mmmi.cbse.enemy.type.Enemy;
//import dk.sdu.mmmi.cbse.platform.Platform;
import java.util.ArrayList;

/**
 *
 * @author Jesper
 */
public class Collision implements IEntityProcessingService {

    void start() {

    }

    public void process(GameData gameData, World world) {

        ArrayList<PosObj> posObjects = new ArrayList();
//        System.out.println("Im runnging!!");
//        for (Entity entity : world.getEntities(Platform.class)) {
//            PositionPart pos = entity.getPart(PositionPart.class);
//            
//            posObjects.add(new PosObj(pos.getX(), pos.getY(), 32, 32));
//       
//        }
        
        
//        for (Entity entity : world.getEntities(Enemy.class)) {
//            PositionPart pos = entity.getPart(PositionPart.class);
//
//        }
    }

}
