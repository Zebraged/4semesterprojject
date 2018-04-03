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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.File;
import java.util.ArrayList;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Chris
 */
public class PlatformPlugin implements IGamePluginService {

    Boolean status = false;
    Entity platform;
    private BundleContext context;
    World world;
    private ArrayList<PlatformObj> platforms = new ArrayList();

    public void start(GameData gameData, World world, BundleContext context) {
        this.world = world;
        this.context = context;
        status = true;
        System.out.println("plugin started");
        createPlatform(gameData, world);
        world.addEntity(platform);

    }
    /***
     * creates a platform with a picture, and some coordinates.
     * @param gameData 
     * @param world 
     */
    private void createPlatform(GameData gameData, World world) {

        platform = new Platform();

        File files = null;
        files = new File("./Bundles/Platform/src/main/resources/image/Idle/");
        File[] fileslist = files.listFiles();
        if (fileslist != null) {

            for (File file : fileslist) {
                if (file.getName().endsWith(".png")) {
                    platforms.add(new PlatformObj(file.getName(), 0, 0));
                    platform.add(new AssetGenerator(platform, "image/", file.getName()));
                }
            }
        } else {
            System.out.println("No platforms found in bundle image folder");
        }

        platform.add(new PositionPart(platforms.get(0).getxPos(), platforms.get(0).getyPos()));

    }

    public void stop() {
        world.removeEntity(platform);
        status = false;
    }

    public boolean getStatus() {
        return status;
    }

}