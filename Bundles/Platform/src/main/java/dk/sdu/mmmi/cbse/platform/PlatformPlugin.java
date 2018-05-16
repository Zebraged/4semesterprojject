/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.platform;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author Chris
 */
public class PlatformPlugin implements IGamePluginService {

    Boolean status = false;
    Entity platform;
    GameData data;
    private BundleContext context;
    World world;
    private ArrayList<PlatformObj> platforms = new ArrayList();
    

    public void start(GameData gameData, World world, BundleContext context) {
        status = true;
        this.data = gameData;
        this.world = world;
        this.context = context;
        
        
        data.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");



    }
    public void stop() {
        this.status = false;
        for (Entity entity : world.getEntities(Platform.class)) {
            world.removeEntity(entity);
        }
    }

    @Override
    public boolean getStatus() {
        return status;
    }

}
