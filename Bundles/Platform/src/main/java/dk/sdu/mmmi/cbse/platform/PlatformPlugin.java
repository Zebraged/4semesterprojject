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

    public void start(GameData gameData, World world, BundleContext context) {
        this.world = world;
        this.context = context;
        status = true;
        System.out.println("plugin started");
        createPlatform(gameData, world);
        world.addEntity(platform);

    }
    /***
     * sdfsdf
     * @param gameData sadfasd
     * @param world asdfasdf
     */
    private void createPlatform(GameData gameData, World world) {

        platform = new Platform();

        File files = null;
        files = new File("./Bundles/Platform/src/main/resources/image/Idle/");
        File[] fileslist = files.listFiles();
        if (fileslist != null) {

            for (File file : fileslist) {
                if (file.getName().endsWith(".png")) {
                    // kan tilføje et objeckt af platform
                    platform.add(new AssetGenerator(platform, "image/", file.getName()));
                }
            }
        } else {
            System.out.println("No platforms found in bundle image folder");
        }

        platform.add(new PositionPart(1, 1));

    }

    public void stop() {
        world.removeEntity(platform);
        status = false;
    }

    public boolean getStatus() {
        return status;
    }

}
