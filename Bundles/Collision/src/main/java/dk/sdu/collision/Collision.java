/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.ICollisionService;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;

/**
 *
 * @author Jesper
 */
public class Collision implements ICollisionService {

    /**
     * If collision is stopped, disable min/max pixels.
     */
    public void stop() {
        disableMinMax();
    }

    //saves all objects for collision detection.
    private final static HashMap<String, PlatformObj> PlatformObjects = new HashMap<String, PlatformObj>();
    private final static HashMap<String, EntitySizeObj> PlayerObjects = new HashMap<String, EntitySizeObj>();
    private final static HashMap<String, EntitySizeObj> EnemyObjects = new HashMap<String, EntitySizeObj>();
    private final static HashMap<String, EntitySizeObj> WeaponObjects = new HashMap<String, EntitySizeObj>();

    private CollisionPart col;
    private Rectangle checkRange;
    private World world;

    public void process(GameData gameData, World world) {
        this.world = world;
        boolean playerFound = false;
        boolean platformFound = false;
        BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference ref = context.getServiceReference(IPlayerInfoService.class);

        if (ref != null) {
            IPlayerInfoService playerPos = (IPlayerInfoService) context.getService(ref);
            for (Entity entity : world.getEntities()) {
                PositionPart part = entity.getPart(PositionPart.class);
                SizePart size = entity.getPart(SizePart.class);
                
                if (size != null) {
                    if (entity.getSource().toString().matches(ObjTypes.PLAYER.url())) {
                        addObj(PlayerObjects, entity, ObjTypes.PLAYER); // ads the player as an position obj.
                        playerFound = true;
                    } else if (entity.getSource().toString().matches(ObjTypes.ENEMY.url())) {

                        addObj(EnemyObjects, entity, ObjTypes.ENEMY); // ads the Enemy as an position obj.

                    } else if (entity.getSource().toString().matches(ObjTypes.PLATFORM.url())) {

                        addObj(PlatformObjects, entity, ObjTypes.PLATFORM); // adds the player as an position obj.
                        platformFound = true;
                    } else if (entity.getSource().toString().matches(ObjTypes.WEAPON.url())) {

                        addObj(WeaponObjects, entity, ObjTypes.WEAPON); // ads the Weapons as an position obj.

                    }
                }
            }
            if (!playerFound) { // removes players if module uninstalled.
                PlayerObjects.clear();
                disableMinMax();
            } else if (!platformFound) {
                clearMaps();
                disableMinMax();
            }
            CheckTerrainCollision(PlayerObjects, PlatformObjects);
            CheckTerrainCollision(EnemyObjects, PlatformObjects);
            CheckCollision(PlayerObjects, EnemyObjects);
            CheckCollision(EnemyObjects, WeaponObjects);
        } else {
            clearMaps();
        }
        clearMaps();
    }

    public void clearMaps() {
        PlatformObjects.clear();
        PlayerObjects.clear();
        EnemyObjects.clear();
        WeaponObjects.clear();
    }

    /**
     * Disables the min/max pixel movement.
     */
    private void disableMinMax() {
        for (Entity ent : world.getEntities()) {
            if (ent.containPart(CollisionPart.class
            )) {
                col = ent.getPart(CollisionPart.class
                );
                col.setMaxX(0);
                col.setMaxY(0);
                col.setMinX(0);
                col.setMinY(0);
            }
        }

    }

    /**
     * If player or enemy stand on a platform, it will use the y-value as the
     * players or enemies new y-value.
     *
     * @param collection1 Player or enemy Hashmap
     * @param collection2 Platform Hashmap
     */
    private void CheckTerrainCollision(HashMap collection1, HashMap collection2) {
        Iterator<Map.Entry<String, PositionObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PositionObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms
        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PositionObj> firstObj = firstColObj.next();
            PositionObj firstPosObj = firstObj.getValue(); // player obj
            col
                    = firstPosObj.getEntity().getPart(CollisionPart.class
                    );
            ArrayList<Float> yBvalue = new ArrayList();
            ArrayList<Float> yTvalue = new ArrayList();
            ArrayList<Float> xRvalue = new ArrayList();
            ArrayList<Float> xLvalue = new ArrayList();
            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PositionObj> platform = secColObj.next();
                PositionObj platformPos = platform.getValue(); // platform obj
                yBvalue.add(checkYBCollision(firstPosObj, platformPos));
                yTvalue.add(checkYTCollision(firstPosObj, platformPos));
                xRvalue.add(checkXRCollision(firstPosObj, platformPos));
                xLvalue.add(checkXLCollision(firstPosObj, platformPos));
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

    private void CheckCollision(HashMap collection1, HashMap collection2) {
        Iterator<Map.Entry<String, PositionObj>> firstCollisionObj = collection1.entrySet().iterator();
        Iterator<Map.Entry<String, PositionObj>> secondCollisionObj = collection2.entrySet().iterator();

        while (firstCollisionObj.hasNext()) {
            Map.Entry<String, PositionObj> firstObj = firstCollisionObj.next();
            PositionObj firstPositionObj = firstObj.getValue();

            PositionPart firstPosition = firstPositionObj.getEntity().getPart(PositionPart.class);
            SizePart firstSize = firstPositionObj.getEntity().getPart(SizePart.class);
            Rectangle firstRectangle = new Rectangle((int) firstPosition.getX(), (int) firstPosition.getY(), firstSize.getWidth(), firstSize.getHeight());

            while (secondCollisionObj.hasNext()) {
                Map.Entry<String, PositionObj> platform = secondCollisionObj.next();
                PositionObj secondPositionObj = platform.getValue();

                PositionPart secoundPosition = secondPositionObj.getEntity().getPart(PositionPart.class);
                SizePart secondSize = secondPositionObj.getEntity().getPart(SizePart.class);
                Rectangle secondRectangle = new Rectangle((int) secoundPosition.getX(), (int) secoundPosition.getY(), secondSize.getWidth(), secondSize.getHeight());

                if (firstRectangle.intersects(secondRectangle)) {
                    LifePart life = firstPositionObj.getEntity().getPart(LifePart.class);
                    if (life != null) {
                        life.updateLife(-1);
                    }
                }
            }
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

        } else if (dir.equals("down")) {
            col.setMinY(0);
        } else if (dir.equals("left")) {
            col.setMinX(0);
        } else if (dir.equals("right")) {
            col.setMaxX(0);
        } else if (dir.equals("top")) {
            col.setMaxY(0);
        }
    }

    /**
     * Checks for a collision between two shapes.
     *
     * @param player first shape
     * @param platform sec shape
     * @return true if found else false.
     */
    private Float checkYBCollision(PositionObj player, PositionObj platform) {

        Float result = 0f;
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

    private Float checkYTCollision(PositionObj player, PositionObj platform) {
        Float result = 0f;
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
    private Float checkXRCollision(PositionObj player, PositionObj platform) {
        Float result = 0f;
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

    private Float checkXLCollision(PositionObj player, PositionObj platform) {
        Float result = 0f;
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
                collection.put(id, new EntitySizeObj(e, 23, 29));
            } else {
                PositionObj o = (PositionObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.PLATFORM) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlatformObj(e, 32, 32));
            } else {
                PositionObj o = (PositionObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.ENEMY) {
            if (!collection.containsKey(id)) {
                collection.put(id, new EntitySizeObj(e, 23, 29));
            } else {
                PositionObj o = (PositionObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.WEAPON) {
            if (!collection.containsKey(id)) {
                collection.put(id, new EntitySizeObj(e, 32, 32));
            } else {
                PositionObj o = (PositionObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
    }
}
