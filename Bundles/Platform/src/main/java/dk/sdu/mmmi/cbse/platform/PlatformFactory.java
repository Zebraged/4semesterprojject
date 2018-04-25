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
import java.io.File;

/**
 *
 * @author Chris
 */
public class PlatformFactory implements IEntityGenerator {

    private GameData data;
    private World world;

    public PlatformFactory() {
    }

    private Entity createPlatform(String name, int x, int y) {
        Entity entity = new Platform();
        PlatformObj platobj = null;

        File files = null;
        files = new File("./Bundles/Platform/src/main/resources/image/Idle/");
        File[] fileslist = files.listFiles();
        if (fileslist != null) {
            boolean foundImage = false;
            for (File file : fileslist) {
                if (file.getName().endsWith(".png")) {
                    platobj = new PlatformObj(file.getName(), x, y, 4);
                    if (!foundImage) { // only load first image to entity
                        entity.add(new AssetGenerator(entity, "image/", file.getName()));
                        foundImage = true;
                    }
                }
            }
        }
        entity.add(new PositionPart(platobj.getxPos(), platobj.getyPos(),platobj.getzPos()));
        return entity;
    }

    @Override
    public void generate(String identifier, int x, int y,int z,  World world, GameData data) {

        this.world = world;
        this.data = data;
        Entity platform = null;
        switch (identifier) {
            case "1":
                platform = createPlatform(identifier, x, y);
                world.addEntity(platform);
                break;
            default:
                break;

        }
    }

}
