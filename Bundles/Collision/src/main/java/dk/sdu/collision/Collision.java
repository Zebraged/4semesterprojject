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
import java.util.ArrayList;
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

        CheckPlayerPlatformCollision(PlayerObj, PlatformObj, gameData);

    }

    /**
     * If player or enemy stand on a platform, it will use the y-value as the
     * players or enemies new y-value.
     *
     * @param collection1 Player or enemy Hashmap
     * @param collection2 Platform Hashmap
     */
    private void CheckPlayerPlatformCollision(HashMap collection1, HashMap collection2, GameData gameData) {
        Iterator<Map.Entry<String, PosObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PosObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms

        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> firstObj = firstColObj.next();
            PosObj firstPosObj = firstObj.getValue(); // player obj

            ArrayList<Float> yBvalue = new ArrayList();
            ArrayList<Float> yTvalue = new ArrayList();
            ArrayList<Float> xRvalue = new ArrayList();
            ArrayList<Float> xLvalue = new ArrayList();

            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PosObj> platform = secColObj.next();
                PosObj platformPos = platform.getValue(); // platform obj

                Float yBval = 0f;
                Float yTval = 0f;
                Float xRval = 0f;
                Float xLval = 0f;

                yBvalue.add(checkYBCollision(firstPosObj, platformPos, gameData, yBval));
                yTvalue.add(checkYTCollision(firstPosObj, platformPos, gameData, yTval));
                xRvalue.add(checkXRCollision(firstPosObj, platformPos, gameData, xRval));
                xLvalue.add(checkXLCollision(firstPosObj, platformPos, gameData, xLval));
                //       System.out.println(xLvalue);
            }

            /**
             * Set the max/min value
             */
            SetMaxvalue(yBvalue, "down");
            SetMaxvalue(xLvalue, "left");
            SetMaxvalue(xRvalue, "right");
            SetMaxvalue(yTvalue, "top");

        }
    }

    private void SetMaxvalue(ArrayList<Float> value, String dir) {

        if (!value.isEmpty()) {

            float higstvalue = 0;

            for (float f : value) {

                if (higstvalue < f) {
                    higstvalue = f;
                }
            }

            if (dir.equals("down")) {
                col.setMinY(higstvalue);
            }
            if (dir.equals("left")) {
                col.setMinX(higstvalue);
            }
            if (dir.equals("right")) {
                col.setMaxX(higstvalue);
            }
            if (dir.equals("top")) {
                col.setMaxY(higstvalue);
            }

        } else {
            if (dir.equals("down")) {
                col.setMinY(0);
            } else if (dir.equals("left")) {
                col.setMinX(0);
            } else if (dir.equals("right")) {
                col.setMaxX(0);
            } else if (dir.equals("top")) {
                col.setMaxY(0);
            }

        }
    }

    /**
     * Checks for a collision between two shapes.
     *
     * @param player first shape
     * @param platform sec shape
     * @return true if found else false.
     */
    private Float checkYBCollision(PosObj player, PosObj platform, GameData gameData, Float result) {

        float AposY = player.getY1();

        float playpos = platform.getY2();

        for (float i = 0; i < 10; i++) {

            float playerPosY = AposY - i;

            float dis = playpos - playerPosY;

            if (dis < 10 && dis >= 0 && player.getX1() < platform.getX2() && player.getX2() > platform.getX1()) {

                result = (platform.getY2() + 1);

                break;
            }

        }
        return result;
    }

    private Float checkYTCollision(PosObj player, PosObj platform, GameData gameData, Float result) {

        float AposY = player.getY2();

        float playpos = platform.getY1();

        for (float i = 0; i < 10; i++) {

            float playerPosY = AposY - i;

            float dis = playpos - playerPosY;

            if (dis > -10 && dis < 0 && player.getX1() < platform.getX2() && player.getX2() > platform.getX1()) {

                result = (platform.getY1() - 31);

                break;
            }

        }
        return result;
    }

    /**
     * Checks if the player hit to the right.
     *
     * @param player
     * @param platform
     * @param gameData
     * @param result
     * @return
     */
    private Float checkXRCollision(PosObj player, PosObj platform, GameData gameData, Float result) {

        float AposY = player.getX2();
        float playpos = platform.getX1();

        for (float i = 0; i < 10; i++) {

            float playerPosX = AposY + i;

            float dis = playpos - playerPosX;
            if (dis < 10 && dis > 0 && player.getY1() < platform.getY2() && player.getY2() > platform.getY1()) {
                result = (platform.getX1() - 25);
                break;
            }

        }
        return result;
    }

    private Float checkXLCollision(PosObj player, PosObj platform, GameData gameData, Float result) {

        float AposY = player.getX1();
        float playpos = platform.getX2();

        for (float i = 0; i < 10; i++) {

            float playerPosX = AposY + i;
            float dis = playpos - playerPosX;

            if (dis > -10 && dis < 0 && player.getY1() < platform.getY2() && player.getY2() > platform.getY1()) {
                result = (platform.getX2());

                break;
            }
        }
        return result;
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
                // System.out.println("Player made");
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.PLATFORM) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlatformObj(e, 32, 32));
                //   System.out.println("Platform made");
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.ENEMY) {
            if (!collection.containsKey(id)) {

            } else {

            }
        }
    }
}
