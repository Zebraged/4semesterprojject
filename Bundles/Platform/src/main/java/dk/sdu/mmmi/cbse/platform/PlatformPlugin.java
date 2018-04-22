/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.platform;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.util.ArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

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
<<<<<<< HEAD
        
        System.out.println("plugin started");
        
        
        ServiceReference service = context.getServiceReference(IEntityGenerator.class);
        IEntityGenerator platformGen = (IEntityGenerator) context.getService(service);
        platformGen.generate("platform", 1, 1,3, world, data);
        platformGen.generate("platform", 1, 2,3, world, data);
        platformGen.generate("platform", 1, 3,3, world, data);
        platformGen.generate("platform", 2, 1,3, world, data);
        platformGen.generate("platform", 3, 1,3, world, data);
        platformGen.generate("platform", 4, 1,3, world, data);
        platformGen.generate("platform", 5, 1,3, world, data);
        platformGen.generate("platform", 6, 1,3, world, data);
        platformGen.generate("platform", 7, 1, 3,world, data);
        platformGen.generate("platform", 8, 1,3, world, data);
        platformGen.generate("platform", 9, 1,3, world, data);
        platformGen.generate("platform", 10, 2,3, world, data);
        platformGen.generate("platform", 11, 2,3, world, data);
        platformGen.generate("platform", 12, 3,3, world, data);
        platformGen.generate("platform", 13, 4,3, world, data);
        platformGen.generate("platform", 14, 5,3, world, data);
        platformGen.generate("platform", 15, 5,3, world, data);
        platformGen.generate("platform", 15, 6,3, world, data);
        platformGen.generate("platform", 15, 7,3, world, data);
=======
        data.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        System.out.println("Platform plugin started");

>>>>>>> master

    }

    /**
     * *
     * creates a platform with a picture, and some coordinates.
     *
     * @param gameData
     * @param world
     */
    /**
     * private void createPlatform(GameData gameData, World world) {
     *
     * platform = new Platform();
     *
     * File files = null; files = new
     * File("./Bundles/Platform/src/main/resources/image/Idle/"); File[]
     * fileslist = files.listFiles(); if (fileslist != null) {
     *
     * for (File file : fileslist) { if (file.getName().endsWith(".png")) {
     * //platforms.add(new PlatformObj(file.getName(), 0, 0)); platform.add(new
     * AssetGenerator(platform, "image/", file.getName()));
     *
     * }
     * }
     * } else { System.out.println("No platforms found in bundle image folder");
     * }
     *
     * platform.add(new PositionPart(platforms.get(0).getxPos(),
     * platforms.get(0).getyPos()));
     *
     * }
     *
     */
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
