/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.InputStream;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Marcg
 */
public class PlayerPlugin implements IGamePluginService{
    
    Boolean status = false;
    Entity player;
    BundleContext context;

    public void start(GameData gameData, World world, BundleContext context) {
        this.context = context;
        status = true;
        System.out.println("plugin started");
        player = createPlayer(gameData, world);
        world.addEntity(player);
    }

    public void stop() {
        System.out.println("plugin stopped");
    }

    public boolean getStatus() {
        return status;
    }
    
    private Entity createPlayer(GameData gameData, World world){
        Entity player = new Player();
        player.add(new AssetGenerator(player, "dk/sdu/mmmi/cbse/player/image/Player_idle1.png"));
        player.add(new PositionPart(200, 200, 1));
        return player;
    }
    
}
