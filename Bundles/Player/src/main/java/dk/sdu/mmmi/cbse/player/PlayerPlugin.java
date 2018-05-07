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
import dk.sdu.mmmi.cbse.common.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;

/**
 *
 * @author Marcg
 */
public class PlayerPlugin implements IGamePluginService {

    private IPlayerInfoService position;

    Boolean status = false;
    Entity player;
    World world;
    BundleContext context;

    public void start(GameData gameData, World world, BundleContext context) {
        this.world = world;
        this.context = context;
        status = true;
        System.out.println("plugin started");
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        player = createPlayer(gameData, world);
        player.setAlignment(1);
        world.addEntity(player);
        context.registerService(IPlayerInfoService.class.getName(), position, null);
    }

    public void stop() {
        status = false;
        ServiceReference reference = context.getServiceReference(IPlayerInfoService.class);
        context.ungetService(reference);
        world.removeEntity(player);
        System.out.println("plugin stopped");
    }

    public boolean getStatus() {
        return status;
    }

    private Entity createPlayer(GameData gameData, World world) {
        Entity player = new Player();

        PositionPart posPart = new PositionPart(48, 34, 3);
        LifePart lifePart = new LifePart(5, 5000);
        PlayerPosition playPos = new PlayerPosition();
        player.add(new AssetGenerator(player, "image/", "Player_idle1.png"));
        player.add(lifePart);
        player.add(posPart);
        player.add(new SizePart(16, 16));
        player.add(new MovingPart(150, 800, 550));
        player.add(new CollisionPart());
        playPos.addPositionPart(posPart);
        playPos.setLifePart(lifePart);
        position = playPos;

        return player;
    }

}
