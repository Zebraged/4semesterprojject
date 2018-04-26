/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.hud;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import java.util.ArrayList;
import java.util.Collection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author Guest Account
 */
public class HudPlugin implements IGamePluginService {

    BundleContext context;
    World world;
    Boolean status = false;
    LifeManager lifeManager = new LifeManager();
    Collection<Entity> temp = new ArrayList<Entity>();
    private int lifeNumber = lifeManager.getLife();

    private Entity createLife(int i) {
        Entity life = new Life();
        PositionPart posPart = new PositionPart((i * 40), 320, 4);
        life.add(posPart);
        life.add(new AssetGenerator(life, "image/", "Heart.png"));

        return life;
    }
    

    private Collection<Entity> createLives() {
        Collection<Entity> temp = new ArrayList();

        for (int i = 1; i <= lifeNumber; i++) {

            temp.add(createLife(i));
        }
        return temp;
    }

    public boolean getStatus() {
        return status;
    }

    public void start(GameData gameData, World world, org.osgi.framework.BundleContext context) {
        this.world = world;
        this.context = context;
        status = true;
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        System.out.println("HUD STARTED");

        temp = createLives();
        for (Entity e : temp) {
            world.addEntity(e);
        }

    }

    public void stop() {
        status = false;
        temp = createLives();
        for (Entity e : temp) {

            world.removeEntity(e);
        }
        System.out.println("HUD STOPPED");
    }

}
