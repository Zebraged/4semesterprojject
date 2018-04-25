/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapServices;

import Map.Map;
import Map.MapObj;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * The mapbundle is the background in the game.
 *
 * @author Guest Account
 */
public class MapPlugin implements IGamePluginService {

    private boolean status = false; // status if the bundle has been started or not
    private String id; // id of the entity class instance.
    private Entity map;
    private World world;
    private ArrayList<MapObj> maps = new ArrayList();

    /**
     * *
     * Start Method to start the bundle.
     *
     * @param gameData
     * @param world
     * @param context
     */
    public void start(GameData gameData, World world, BundleContext context) {
        this.world = world;
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        createMap(gameData, world); // create the map and its configuration

        status = true; // set the start status to true
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
    private void createMap(GameData gameData, World world) {
        map = new Map();
        AssetGenerator generator = new AssetGenerator(map, "image/", "Map1.png");
        map.add(generator);
        map.add(new PositionPart(0, -180, 1));
        
        world.addEntity(map);
        
        map = new Map();
        generator = new AssetGenerator(map, "image/", "Map1.png");
        map.add(generator);
        map.add(new PositionPart(2880, -180, 1));
        
        world.addEntity(map);
    }

    @Override
    public void stop() {
        status = false;
        for(Entity ent : world.getEntities(Map.class)){
            world.removeEntity(ent);   
        }
    }

}
