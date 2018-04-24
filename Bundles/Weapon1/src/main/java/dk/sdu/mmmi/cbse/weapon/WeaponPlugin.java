package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class WeaponPlugin implements IGamePluginService {

    private Boolean status = false;
    private Entity weapon;
    private BundleContext bundleContext;
    private World world;

    public void start(GameData gameData, World world, BundleContext bundleContext) {
        System.out.println("plugin started");

        this.world = world;
        this.bundleContext = bundleContext;
        gameData.setBundleObjAssetPath(FrameworkUtil.getBundle(this.getClass()), "image/");
        weapon = createWeapon(gameData, world, bundleContext);
        world.addEntity(weapon);

        status = true;
    }

    public void stop() {
        world.removeEntity(weapon);
    }

    public boolean getStatus() {
        return status;
    }

    private Entity createWeapon(GameData gameData, World world, BundleContext bundleContext) {
        Entity weaponObject = new Weapon();

<<<<<<< HEAD:Bundles/Weapon/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponPlugin.java
        PositionPart posistionPart = new PositionPart(0,0);
        weaponObject.add(new AssetGenerator(weaponObject, "image/", "Stick_Idle.png"));
=======
        PositionPart posistionPart = new PositionPart(0,0,3);
        weaponObject.add(new AssetGenerator(weaponObject, "image/", "Stick.png"));
>>>>>>> master:Bundles/Weapon1/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponPlugin.java
        weaponObject.add(posistionPart);
        weaponObject.add(new MovingPart(5, 600, 400));
        return weaponObject;
    }
}