/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapServices;

import Map.Map;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;

/**
 * The mapbundle is the background in the game.
 *
 * @author Guest Account
 */
public class MapPlugin implements IGamePluginService {

    private boolean status = false; // status if the bundle has been started or not
    private String id; // id of the entity class instance.

    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * *
     * Start Method to start the bundle.
     *
     * @param gameData
     * @param world
     * @param context
     */
    public void start(GameData gameData, World world, BundleContext context) {

        Entity map = createMap(gameData, world);
        this.id = map.getID();
        world.addEntity(map);

        status = true;
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * *
     * Gets the current status of the bundle if it has been started or not
     *
     * @return status boolean true = installed
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Generates the map and add the images as an entity object.
     *
     * @param gameData
     * @param world
     * @return Entity obj that holds the background images.
     */
    private Entity createMap(GameData gameData, World world) {

        Entity map = new Map();
        map.add(new AssetGenerator(map, "image/", "Map1.png")); //
        map.add(new AssetGenerator(map, "image/", "Map2.png"));
        map.add(new PositionPart(0, 0));

        return map;

    }

}
