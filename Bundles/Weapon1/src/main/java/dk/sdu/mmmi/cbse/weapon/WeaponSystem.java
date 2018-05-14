package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SHIFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.entityparts.SizePart;
import dk.sdu.mmmi.cbse.common.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author raglu
 */
public class WeaponSystem implements IEntityProcessingService {

    private BundleContext bundleContext;

    private IPlayerInfoService iPlayerInfoService;

    private final String[] weaponNames = {"Stick", "Sword", "Cupcake"};     // Current InventorySystem, might need a better solution
    private int currentWeaponNum = 0;
    private ServiceReference ref;
    private boolean shiftPressed, spacePressed;      //to avoid multiple checks
    private boolean isFacingLeft;
    private float xPositionAdder;
    private float attackTime;

    public void process(GameData gameData, World world) {

        checkOrientation(gameData);

        createReference();
        if(ref != null){
            iPlayerInfoService = (IPlayerInfoService) bundleContext.getService(ref);
            processWeapons(gameData, world);
            processProjectiles(gameData, world);   
        }
    }

    private void checkOrientation(GameData gameData) {
        if (gameData.getKeys().isDown(LEFT)) {
            isFacingLeft = true;
        } else if (gameData.getKeys().isDown(RIGHT)) {
            isFacingLeft = false;
        }
    }

    private void createReference() {
            bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
            ref = bundleContext.getServiceReference(IPlayerInfoService.class);
           
    }

    private void processWeapons(GameData gameData, World world) {

        if (isFacingLeft) {
            xPositionAdder = -22;
        } else {
            xPositionAdder = 22;
        }

        for (Entity weapon : world.getEntities(Weapon.class)) {
            PositionPart positionPart = weapon.getPart(PositionPart.class);
            AssetGenerator assetGenerator = weapon.getPart(AssetGenerator.class);

            assetGenerator.setMirror(isFacingLeft);

            if (gameData.getKeys().isPressed(SHIFT) && !shiftPressed) {
                currentWeaponNum++;
                currentWeaponNum = currentWeaponNum % weaponNames.length;
            }

            if (gameData.getKeys().isPressed(SPACE)) {
                if (currentWeaponNum > 1 && !spacePressed) {
                    createProjectile(weapon, world);
                } else {
                    assetGenerator.changeImage(weaponNames[currentWeaponNum] + "_Attack.png");
                    positionPart.setX(iPlayerInfoService.getX() + xPositionAdder);
                    positionPart.setY(iPlayerInfoService.getY() - 22);

                    attackTime = 0.05f;
                    weapon.add(new SizePart(18, 32));
                }

            } else {
                assetGenerator.changeImage(weaponNames[currentWeaponNum] + "_Idle.png");
                positionPart.setX(iPlayerInfoService.getX() + xPositionAdder);
                positionPart.setY(iPlayerInfoService.getY() + 10);
            }
            
            attackTime -= gameData.getDelta();
            
            if(attackTime <= 0){
                weapon.remove(SizePart.class);
            }

            positionPart.process(gameData, weapon);
            assetGenerator.process(gameData, weapon);

            shiftPressed = gameData.getKeys().isDown(SHIFT);     
            spacePressed = gameData.getKeys().isDown(SPACE);
        }
    }

    private void createProjectile(Entity entity, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        Entity projectile = new Projectile();

        projectile.add(new AssetGenerator(projectile, "image/", weaponNames[currentWeaponNum] + "_Idle.png"));
        projectile.add(new PositionPart(x, y, 3));
        projectile.add(new MovingPart(350));
        projectile.add(new TimerPart(2));
        projectile.add(new SizePart(8, 6));

        MovingPart movingPart = projectile.getPart(MovingPart.class);

        movingPart.setLeft(isFacingLeft);
        movingPart.setRight(!isFacingLeft);

        world.addEntity(projectile);
    }

    private void processProjectiles(GameData gameData, World world) {
        for (Entity projectile : world.getEntities(Projectile.class)) {
            MovingPart movingPart = projectile.getPart(MovingPart.class);
            TimerPart timerPart = projectile.getPart(TimerPart.class);

            timerPart.reduceExpiration(gameData.getDelta());
            if (timerPart.getExpiration() <= 0) {
                world.removeEntity(projectile);
            }

            movingPart.process(gameData, projectile);
        }
    }
}
