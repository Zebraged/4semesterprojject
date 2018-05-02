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
import dk.sdu.mmmi.cbse.common.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author raglu
 */
public class WeaponSystem implements IEntityProcessingService {

    private BundleContext bundleContext;

    private IPlayerPositionService iPlayerPositionService;
    private boolean gotReference = false;   // For checking if a IPlayerPositionService-reference is registered 

    private final String[] weaponNames = {"Stick", "Sword", "Cupcake"};     // Current InventorySystem, might need a better solution
    private int currentWeaponNum = 0;

    private boolean shiftPressed, spacePressed;      //isPressed() doesn't work properly
    private boolean isFacingLeft;
    private float xPositionAdder;

    public void process(GameData gameData, World world) {

        checkOrientation(gameData);

        if (!gotReference) {
            createReference();
        } else {
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
        try {
            bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
            iPlayerPositionService = bundleContext.getService(bundleContext.getServiceReference(IPlayerPositionService.class));
            gotReference = true;
        } catch (NullPointerException ex) {
            // IPlayerPostionService is not registered
            // no exception message for now
        }
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
                    positionPart.setX(iPlayerPositionService.getX() + xPositionAdder);
                    positionPart.setY(iPlayerPositionService.getY() - 22);
                }
            } else {
                assetGenerator.changeImage(weaponNames[currentWeaponNum] + "_Idle.png");
                positionPart.setX(iPlayerPositionService.getX() + xPositionAdder);
                positionPart.setY(iPlayerPositionService.getY() + 10);
            }

            positionPart.process(gameData, weapon);
            assetGenerator.process(gameData, weapon);

            shiftPressed = gameData.getKeys().isDown(SHIFT);     //isPressed doesn't work properly
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
