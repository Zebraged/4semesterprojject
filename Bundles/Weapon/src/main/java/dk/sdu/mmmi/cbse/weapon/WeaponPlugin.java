package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;

public class WeaponPlugin implements IGamePluginService {

    public void start(GameData gd, World world, BundleContext bc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stop() {
        System.out.println("plugin stopped");
    }

    public boolean getStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Entity createPlayer(GameData gameData, World world) {
        Entity player = new Weapon();
        
        PositionPart posPart = new PositionPart(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        player.add(new AssetGenerator(player, "image/", "Player_idle1.png"));
        player.add(posPart);
        player.add(new MovingPart(5, 600, 400));
        return player;
    }
    
    
}
