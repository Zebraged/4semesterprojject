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
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Marcg
 */
public class PlayerPlugin implements IGamePluginService {

    private IPlayerPositionService position;
    private Boolean status = false;
    private Entity player;
    private BundleContext context;
    private World world;

    public void start(GameData gameData, World world, BundleContext context) {
        System.out.println("plugin started");
        
        this.world = world;
        this.context = context;
        
        player = createPlayer(gameData, world);
        world.addEntity(player);
        context.registerService(IPlayerPositionService.class.getName(), position, null);
        
        status = true;
    }

    public void stop() {
        status = false;
        world.removeEntity(player);
        System.out.println("plugin stopped");
    }

    public boolean getStatus() {
        return status;
    }

    private Entity createPlayer(GameData gameData, World world) {
        Entity player = new Player();
        PositionPart posPart = new PositionPart(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        PlayerPosition playPos = new PlayerPosition();
        player.add(new AssetGenerator(player, "image/", "Player_idle1.png"));
        player.add(posPart);
        player.add(new MovingPart(5, 600, 400));
        playPos.addPositionPart(posPart);
        position = playPos;
        return player;
    }

}
