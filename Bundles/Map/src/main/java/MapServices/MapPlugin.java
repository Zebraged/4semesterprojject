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
import java.util.HashMap;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Guest Account
 */
public class MapPlugin implements IGamePluginService {

    private boolean status = false; // status if the bundle has been started or not

    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void start(GameData gameData, World world, BundleContext context) {

        Entity map = createMap(gameData, world);
        world.addEntity(map);

        status = true;
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean getStatus() {
        return status;
    }

    private Entity createMap(GameData gameData, World world) {

        Entity map = new Map();
        map.add(new AssetGenerator(map, "image/", "Map1.png")); //
        map.add(new AssetGenerator(map, "image/", "Map2.png"));
        map.add(new PositionPart(0, 0));
        //map.add(new PositionPart((gameData.getDisplayHeight()/2), (gameData.getDisplayWidth()/2)));
        return map;

    }

}
