package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    
    private String playerID;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        Entity player = new Player();
        playerID = world.addEntity(player);
        System.out.println("Player Bundle started!!");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(playerID);
    }

}
