package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class WeaponPlugin implements IGamePluginService {

    private Boolean status = false;
    private Entity weapon;
    private World world;

    public void start(GameData gameData, World world, BundleContext bundleContext) {
        this.world = world;
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

        weaponObject.add(new AssetGenerator(weaponObject, "image/", "Stick_Idle.png"));
        weaponObject.add(new PositionPart(0, 0, 3));
        weaponObject.add(new SizePart(32, 32));
        weaponObject.add(new CollisionPart());
        
        return weaponObject;
    }
}
