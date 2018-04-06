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
    
    public PlatformFactory(){
    }
    
    private Entity createPlatform(String name, int x, int y){
        Entity entity = new Platform();
        entity.add(new AssetGenerator(entity, "image/", "GrassPlatform.png"));
        entity.add(new PositionPart(x, y));
        return entity;
    }
    
    @Override
    public void generate(String identifier, int x, int y, World world, GameData data) {
        
        this.world = world;
        this.data = data;
        Entity platform = null;
        switch(identifier.toLowerCase()){
            case "platform":
                platform = createPlatform(identifier, x, y);
                world.addEntity(platform);
                break;
                default:
                System.out.println("Unknown platform type");
                break;
        
        }
        }
    
}
