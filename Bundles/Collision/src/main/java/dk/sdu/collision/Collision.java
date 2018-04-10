/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.ICollisionService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jesper
 */
public class Collision implements ICollisionService {

    void start() {

    }

    private final static HashMap<String, PlatformObj> PlatformObj = new HashMap<String, PlatformObj>(); //saves all platforms for collision detection.
    private final static HashMap<String, PlayerObj> PlayerObj = new HashMap<String, PlayerObj>(); // saves all players for collision detection.
    private final static HashMap<String, PosObj> EnemyObj = new HashMap<String, PosObj>(); // saves all the enemies..
    private CollisionPart col = CollisionPart.getInstance();

    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {

            if (entity.getSource().toString().matches(ObjTypes.PLAYER.url())) {

                addObj(PlayerObj, entity, ObjTypes.PLAYER); // ads the player as an position obj.

            } else if (entity.getSource().toString().matches(ObjTypes.TEDDY.url())) {

                addObj(EnemyObj, entity, ObjTypes.ENEMY); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches(ObjTypes.CLOUD.url())) {

                addObj(EnemyObj, entity, ObjTypes.ENEMY); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches(ObjTypes.UNICORN.url())) {

                addObj(EnemyObj, entity, ObjTypes.ENEMY); // ads the Enemy as an position obj.

            } else if (entity.getSource().toString().matches(ObjTypes.PLATFORM.url())) {

                addObj(PlatformObj, entity, ObjTypes.PLATFORM); // adds the player as an position obj.
            }
        }
        //CheckPlayerPlatformCollision();
        CheckPlayerEnemyPlatformCollision(PlayerObj, PlatformObj, gameData);
        CheckPlayerEnemyPlatformCollision(EnemyObj, PlatformObj, gameData);

    }

    /**
     * If player or enemy stand on a platform, it will use the y-value as the
     * players or enemies new y-value.
     *
     * @param collection1 Player or enemy Hashmap
     * @param collection2 Platform Hashmap
     */
    private void CheckPlayerEnemyPlatformCollision(HashMap collection1, HashMap collection2, GameData gameData) {
        Iterator<Map.Entry<String, PosObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PosObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms

        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> firstObj = firstColObj.next();
            PosObj firstPosObj = firstObj.getValue(); // player obj

            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PosObj> platform = secColObj.next();
                PosObj platformPos = platform.getValue(); // platform obj
                checkCollision(firstPosObj, platformPos, gameData);

//                if (checkCollision(firstPosObj, platformPos, gameData)) { // if collision.
                //
                //                    Entity firstE = firstPosObj.getEntity();
                //                    Entity platformE = platformPos.getEntity();
                //
                //                    PositionPart firstP = firstE.getPart(PositionPart.class);
                //                    PositionPart platformP = platformE.getPart(PositionPart.class);
                //                    MovingPart firstM = firstE.getPart(MovingPart.class);
                //
                //                    // firstP.setY(platformP.getY() + 32);
                //                    firstM.setIsGrounded(true);
                //                }
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
    private void checkCollision(PosObj RectA, PosObj RectB, GameData gameData) {

        float AposY = RectA.getY1();

        for (float i = 0; i < 8; i++) {

            AposY -= i;

            float dis = AposY - RectB.getY2(); 
            
            if (dis < 8 && dis >= 0 && RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1()) {
                col.setMaxY(RectB.getY2() + dis);
            }

        }

//        if (RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() < RectB.getY2() && RectA.getY2() > RectB.getY1()) {
//
//        }
        //return true;
    }

    /**
     * Adds an entity to a map to check for collision after all entities is
     * found and sorted
     *
     * @param collection the collection to add the entity to
     * @param e the entity
     */
    private void addObj(HashMap collection, Entity e, ObjTypes type) {

        String id = e.getID();

        if (type == ObjTypes.PLAYER) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlayerObj(e, 23, 29));
                System.out.println("Player made");
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.PLATFORM) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlatformObj(e, 32, 32));
                System.out.println("Platform made");
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.ENEMY) {
            if (!collection.containsKey(id)) {
                //  collection.put(id, new PlayerObj(e, 32, 32));
            } else {
//                PosObj o = (PosObj) collection.get(id);
//                o.updatePos(e); // update pos
            }
        }
    }
}

//       System.out.println(RectA.gethysteresis());
//        
//        if (RectA.getX1() > RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() < RectB.getY2() - RectB.gethysteresis() && RectA.getY2() > RectB.getY1()) {
//           // RectA.setLastDir("right");
//            RectB.setLastDir("right");
//        } else if (RectA.getX1() < RectB.getX2() && RectA.getX2() < RectB.getX1() && RectA.getY1() < RectB.getY2() - RectB.gethysteresis() && RectA.getY2() > RectB.getY1()) {
//          //  RectA.setLastDir("left");
//            RectB.setLastDir("left");
//        } else if (RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() > RectB.getY2() && RectA.getY2() > RectB.getY1()) {
//            RectB.setLastDir("top");
//          //  RectA.setLastDir("top");
//        } else if (RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() < RectB.getY2() && RectA.getY2() < RectB.getY1()) {
//          //  RectA.setLastDir("bottom");
//            RectB.setLastDir("bottom");
//        }
//
//        if (RectA.getX1() < RectB.getX2() && RectA.getX2() > RectB.getX1() && RectA.getY1() < RectB.getY2() && RectA.getY2() > RectB.getY1()) {
//            System.out.println(RectB.getLastDir());
//
//            if (RectB.getLastDir().equals("left")) {
//
//                col.setRightAllowed(false);
//            } else {
//                col.setRightAllowed(true);
//            }
//            if (RectB.getLastDir().equals("bottom")) {
//
//            }
//
//            if (RectB.getLastDir().equals("right")) {
//                col.setLeftAllowed(false);
//
//            } else {
//                col.setLeftAllowed(true);
//            }
