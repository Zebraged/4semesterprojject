/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
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

    private final static HashMap<String, PosObj> PlatformObj = new HashMap<String, PosObj>(); //saves all platforms for collision detection.
    private final static HashMap<String, PosObj> PlayerObj = new HashMap<String, PosObj>(); // saves all players for collision detection.
    private final static HashMap<String, PosObj> EnemyObj = new HashMap<String, PosObj>(); // saves all the enemies..

    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {
            if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.player.Player.*")) {

                addObj(PlayerObj, entity); // ads the player as an position obj.

            } else if (entity.getSource().getClass().getName().equals("dk.sdu.mmmi.cbse.enemy.type.Enemy")) {

                addObj(EnemyObj, entity); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.enemy.type.CloudEnemy.*")) {

                addObj(EnemyObj, entity); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.enemy.type.UnicornEnemy.*")) {

                addObj(EnemyObj, entity); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches("dk.sdu.mmmi.cbse.platform.Platform.*")) {

                addObj(PlatformObj, entity); // adds the player as an position obj.
            }
        }
        //CheckPlayerPlatformCollision();
        CheckPlayerEnemyPlatformCollision(PlayerObj, PlatformObj);
        CheckPlayerEnemyPlatformCollision(EnemyObj, PlatformObj);
    }

    /**
     * If player or enemy stand on a platform, it will use the y-value as the
     * players or enemies new y-value.
     *
     * @param collection1 Player or enemy Hashmap
     * @param collection2 Platform Hashmap
     */
    private void CheckPlayerEnemyPlatformCollision(HashMap collection1, HashMap collection2) {
        Iterator<Map.Entry<String, PosObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PosObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms

        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> firstObj = firstColObj.next();
            PosObj firstPosObj = firstObj.getValue(); // player obj

            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PosObj> platform = secColObj.next();
                PosObj platformPos = platform.getValue(); // player obj

                if (checkCollision(firstPosObj, platformPos)) { // if collision.

                    Entity firstE = firstPosObj.getEntity();
                    Entity platformE = platformPos.getEntity();

                    PositionPart firstP = firstE.getPart(PositionPart.class);
                    PositionPart platformP = platformE.getPart(PositionPart.class);
                    MovingPart firstM = firstE.getPart(MovingPart.class);

                    firstP.setY(platformP.getY() + 32);
                    firstM.setIsGrounded(true);
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
