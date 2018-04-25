/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.goalbundle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author Marcg
 */
public class GoalPlugin implements IGamePluginService{
    
    boolean status = false;
    World world;
    public void start(GameData gameData, World world, BundleContext context) {
        status = true;
        this.world = world;
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
    }

    public void stop() {
        status = false;
        for(Entity entity : world.getEntities(Goal.class)){
            world.removeEntity(entity);
        }
    }

    public boolean getStatus() {
        return status;
    }
    
}
