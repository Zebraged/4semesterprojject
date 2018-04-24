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
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class PlayerPlugin implements IGamePluginService {

    private IPlayerPositionService position;
    Boolean status = false;
    Entity player;
    BundleContext context;
    World world;

    public void start(GameData gameData, World world, BundleContext context) {
        this.world = world;
        this.context = context;
        status = true;
        System.out.println("plugin started");
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        player = createPlayer(gameData, world);
        world.addEntity(player);
        context.registerService(IPlayerPositionService.class.getName(), position, null);
    }

    public void stop() {
        status = false;
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
        context.ungetService(reference);
        world.removeEntity(player);
        System.out.println("plugin stopped");
    }

    public boolean getStatus() {
        return status;
    }

    private Entity createPlayer(GameData gameData, World world) {
        Entity player = new Player();
<<<<<<< HEAD
        
        PlayerPosition playerPostition = new PlayerPosition();
        PositionPart positionPart = new PositionPart(48, 125);
        
        player.add(new AssetGenerator(player, "image/", "Player_Idle.png"));
        player.add(positionPart);
        player.add(new MovingPart(100, 800, 400));
        playerPostition.addPositionPart(positionPart);
        position = playerPostition;
=======
        PositionPart posPart = new PositionPart(48,125,3);
        PlayerPosition playPos = new PlayerPosition();
        player.add(new AssetGenerator(player, "image/", "Player_idle1.png"));
        player.add(posPart);
        player.add(new SizePart(32, 32));
        player.add(new MovingPart(5, 600, 400));
        playPos.addPositionPart(posPart);
        position = playPos;
>>>>>>> master
        return player;
    }

}
