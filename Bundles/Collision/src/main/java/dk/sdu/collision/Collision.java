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
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
//import dk.sdu.mmmi.cbse.enemy.EnemyPlugin;
//import dk.sdu.mmmi.cbse.enemy.type.Enemy;
//import dk.sdu.mmmi.cbse.platform.Platform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jesper
 */
public class Collision implements IEntityProcessingService {
    
    void start() {
        
    }
    
    private final static HashMap<String, PosObj> PlatformObj = new HashMap<String, PosObj>();
    private final static HashMap<String, PosObj> PlayerObj = new HashMap<String, PosObj>();
    
    public void process(GameData gameData, World world) {

        //  ArrayList<PosObj> posObjects = new ArrayList();
        // System.out.println("Im runnging!!");
        for (Entity entity : world.getEntities()) {
            
            if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.player.Player.*")) {
                // System.out.println("player found -> " + entity.getSource().toString());

                addObj(PlayerObj, entity); // ads the player as an position obj.

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.enemy.type.TeddyEnemy.*")) {
                // System.out.println("Teddy found -> " + entity.getSource().toString());

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.enemy.type.CloudEnemy.*")) {
                // System.out.println("Cloud found -> " + entity.getSource().toString());

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.enemy.type.UnicornEnemy.*")) {
                // System.out.println("Unicorn found -> " + entity.getSource().toString());

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.platform.Platform.*")) {
                // System.out.println("Platform found -> " + entity.getSource().toString());

                addObj(PlatformObj, entity); // ads the player as an position obj.
            }
        }
        
        Iterator<Map.Entry<String, PosObj>> playerObj = PlayerObj.entrySet().iterator();
        Iterator<Map.Entry<String, PosObj>> platformObj = PlatformObj.entrySet().iterator();
        while (playerObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> playerobj = playerObj.next();
            PosObj playerPos = playerobj.getValue(); // player obj

            while (platformObj.hasNext()) { // check if player has collision with platform.
                Map.Entry<String, PosObj> platform = platformObj.next();
                PosObj platformPos = platform.getValue(); // player obj

                if (checkCollision(playerPos, platformPos)) { // if collision.
                    Entity e = playerPos.getEntity();
                    MovingPart mov = e.getPart(MovingPart.class);
                    mov.setIsGrounded(true);
                }
                
            }
            
        }
    }

    /**
     * Checks for a collision between two shapes.
     *
     * @param RectA first shape
     * @param RectB sec shape
     * @return true if found else false.
     */
    private boolean checkCollision(PosObj RectA, PosObj RectB) {
        if (RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() < RectB.getY2() && RectA.getY2() > RectB.getY1()) {
            return true;
        } else {
            return false;
        }
        
    }

    /**
     * Adds an entity to a map to check for collision after all entities is
     * found and sorted
     *
     * @param collection the collection to add the entity to
     * @param e the entity
     */
    private void addObj(HashMap collection, Entity e) {
        
        String id = e.getID();
        
        if (!collection.containsKey(id)) {
            collection.put(id, new PosObj(e, 32, 32));
        } else {
            PosObj o = (PosObj) collection.get(id);
            o.updatePos(e); // update pos
        }
        
    }
    
}
