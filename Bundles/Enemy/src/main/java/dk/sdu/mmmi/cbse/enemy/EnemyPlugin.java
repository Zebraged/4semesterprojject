/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemy.type.Enemy;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class EnemyPlugin implements IGamePluginService{
    
    World world;
    GameData data;
    boolean status = false;
    
    @Override
    public void start(GameData gameData, World world, BundleContext context) {
        this.status = true;
        this.world = world;
        this.data = gameData;
        data.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        ServiceReference service = context.getServiceReference(IEntityGenerator.class);
        IEntityGenerator enemyGen = (IEntityGenerator) context.getService(service);
    }

    @Override
    public void stop() {
        this.status = false;
        for(Entity entity : world.getEntities(Enemy.class)){
            world.removeEntity(entity);
        }
    }

    @Override
    public boolean getStatus() {
        return status;
    }
    
}
