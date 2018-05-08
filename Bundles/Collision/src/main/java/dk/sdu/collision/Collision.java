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

    private final static HashMap<String, PlatformObj> PlatformObj = new HashMap<String, PlatformObj>(); //saves all platforms for collision detection.
    private final static HashMap<String, PlayerObj> PlayerObj = new HashMap<String, PlayerObj>(); // saves all players for collision detection.
    private final static HashMap<String, PlayerObj> EnemyObj = new HashMap<String, PlayerObj>(); // saves all the enemies..
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
            checkRange = new Rectangle((int) playerPos.getX(), (int) playerPos.getY(), 200, 200);
            for (Entity entity : world.getEntities()) {
                PositionPart part = entity.getPart(PositionPart.class);
                SizePart size = entity.getPart(SizePart.class);
                
                if (size != null && checkRange.intersects(new Rectangle((int)part.getX(), (int)part.getY(), size.getHeight(), size.getWidth()))) {
                    if (entity.getSource().toString().matches(ObjTypes.PLAYER.url())) {
                        addObj(PlayerObj, entity, ObjTypes.PLAYER); // ads the player as an position obj.
                        playerFound = true;
                    } else if (entity.getSource().toString().matches(ObjTypes.ENEMY.url())) {

                        addObj(EnemyObj, entity, ObjTypes.ENEMY); // ads the Enemy as an position obj.
                        
                    } else if (entity.getSource().toString().matches(ObjTypes.PLATFORM.url())) {

                        addObj(PlatformObj, entity, ObjTypes.PLATFORM); // adds the player as an position obj.
                        platformFound = true;
                    }
                }
            }
        if (!playerFound) { // removes players if module uninstalled.
            PlayerObj.clear();
            disableMinMax();
        } else if (!platformFound) {
            clearMaps();
            disableMinMax();
        }
            CheckEntityCollision(PlayerObj, PlatformObj);
            CheckEntityCollision(EnemyObj, PlatformObj);
            CheckEnemyCollision(PlayerObj, EnemyObj);
        } else {
            clearMaps();
        } 
    }

    public void clearMaps() {
        PlatformObj.clear();
        PlayerObj.clear();
        EnemyObj.clear();
    }

    /**
     * Disables the min/max pixel movement.
     */
    private void disableMinMax() {
        for(Entity ent : world.getEntities()){
            if(ent.containPart(CollisionPart.class)){
                col = ent.getPart(CollisionPart.class);
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
    private void CheckEntityCollision(HashMap collection1, HashMap collection2) {
        Iterator<Map.Entry<String, PosObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PosObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms
        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> firstObj = firstColObj.next();
            PosObj firstPosObj = firstObj.getValue(); // player obj
            col = firstPosObj.getEntity().getPart(CollisionPart.class);
            ArrayList<Float> yBvalue = new ArrayList();
            ArrayList<Float> yTvalue = new ArrayList();
            ArrayList<Float> xRvalue = new ArrayList();
            ArrayList<Float> xLvalue = new ArrayList();
            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PosObj> platform = secColObj.next();
                PosObj platformPos = platform.getValue(); // platform obj
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
    
    private void CheckEnemyCollision(HashMap collection1, HashMap collection2) {
        Iterator<Map.Entry<String, PosObj>> firstColObj = collection1.entrySet().iterator(); // go through all players found 
        Iterator<Map.Entry<String, PosObj>> secColObj = collection2.entrySet().iterator(); // go through all platforms
        while (firstColObj.hasNext()) { // iterate over all players found
            Map.Entry<String, PosObj> firstObj = firstColObj.next();
            PosObj firstPosObj = firstObj.getValue(); // player obj
            PositionPart pos = firstPosObj.getEntity().getPart(PositionPart.class);
            Rectangle player = new Rectangle((int)pos.getX(), (int)pos.getY(), 23, 29);
            while (secColObj.hasNext()) { // check for collision with all platforms
                Map.Entry<String, PosObj> platform = secColObj.next();
                PosObj enemy = platform.getValue(); // platform obj

                PositionPart enemyPos = enemy.getEntity().getPart(PositionPart.class);
                SizePart enemySize = enemy.getEntity().getPart(SizePart.class);
                Rectangle enemyRect = new Rectangle((int)enemyPos.getX(), (int)enemyPos.getY(), enemySize.getWidth(), enemySize.getHeight());
                if(player.intersects(enemyRect)){
                    LifePart life = firstPosObj.getEntity().getPart(LifePart.class);
                    if(life != null){
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
    private Float checkYBCollision(PosObj player, PosObj platform) {

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

    private Float checkYTCollision(PosObj player, PosObj platform) {
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
    private Float checkXRCollision(PosObj player, PosObj platform) {
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

    private Float checkXLCollision(PosObj player, PosObj platform) {
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
                collection.put(id, new PlayerObj(e, 23, 29));
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.PLATFORM) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlatformObj(e, 32, 32));
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
        if (type == ObjTypes.ENEMY) {
            if (!collection.containsKey(id)) {
                collection.put(id, new PlayerObj(e, 23, 29));
            } else {
                PosObj o = (PosObj) collection.get(id);
                o.updatePos(e); // update pos
            }
        }
    }
}
