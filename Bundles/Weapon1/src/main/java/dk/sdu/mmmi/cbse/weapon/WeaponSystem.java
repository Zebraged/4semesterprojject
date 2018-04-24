package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
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

    private boolean gotReference = false;

    private BundleContext bundleContext;

    private IPlayerPositionService iPlayerPositionService;

    private final String[] weaponNames = {"Stick", "Sword", "Cupcake"};
    private int currentWeaponNum = 0;
    private float projectileCooldown = 0;

    public void process(GameData gameData, World world) {
        try {
            for (Entity weapon : world.getEntities(Weapon.class)) {
                PositionPart positionPart = weapon.getPart(PositionPart.class);
                AssetGenerator assetGenerator = weapon.getPart(AssetGenerator.class);

                if (!gotReference) {
                    createReference();
                }

<<<<<<< HEAD:Bundles/Weapon/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponSystem.java
                if (gameData.getKeys().isPressed(SHIFT)) {
                    currentWeaponNum++;
                    currentWeaponNum = currentWeaponNum % weaponNames.length;
                }

                if (gameData.getKeys().isPressed(SPACE)) {
                    if (currentWeaponNum > 1) {
                        if (projectileCooldown <= 0) {
                            shootProjectile(weapon, world);
                            projectileCooldown = 0.1f;
                        } else {
                            projectileCooldown -= gameData.getDelta();
                        }
                    } else {
                        assetGenerator.changeImage(weaponNames[currentWeaponNum] + "_Attack.png");
                        positionPart.setX(iPlayerPositionService.getX() + 20);
                        positionPart.setY(iPlayerPositionService.getY() - 17);
                    }
                } else {
                    assetGenerator.changeImage(weaponNames[currentWeaponNum] + "_Idle.png");
                    positionPart.setX(iPlayerPositionService.getX() + 20);
                    positionPart.setY(iPlayerPositionService.getY() + 15);
=======
                if(gameData.getKeys().isDown(SPACE)){
                    assetGenerator.changeImage("Stick_Attack.png");
                positionPart.setX(iPlayerPositionService.getX() + 20);
                positionPart.setY(iPlayerPositionService.getY() - 12);

                } else{
                    assetGenerator.changeImage("Stick.png");
                positionPart.setX(iPlayerPositionService.getX() + 20);
                positionPart.setY(iPlayerPositionService.getY() + 20);
>>>>>>> master:Bundles/Weapon1/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponSystem.java
                }
                

                positionPart.process(gameData, weapon);
                assetGenerator.process(gameData, weapon);
            }
            for (Entity projectile : world.getEntities(Projectile.class)) {
                MovingPart movingPart = projectile.getPart(MovingPart.class);
                TimerPart timerPart = projectile.getPart(TimerPart.class);

                timerPart.reduceExpiration(gameData.getDelta());
                if (timerPart.getExpiration() <= 0) {
                    world.removeEntity(projectile);
                }

                movingPart.process(gameData, projectile);
            }
        } catch (NullPointerException ex) {
            // IPlayerPostionService is not registered
            // no exception message for now
        }
    }

    private void createReference() {
        bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        iPlayerPositionService = bundleContext.getService(bundleContext.getServiceReference(IPlayerPositionService.class));
<<<<<<< HEAD:Bundles/Weapon/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponSystem.java
        gotReference = true;
    }

    private void shootProjectile(Entity entity, World world) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        Entity projectile = new Projectile();

        projectile.add(new AssetGenerator(projectile, "image/", weaponNames[currentWeaponNum] + "_Idle.png"));
        projectile.add(new PositionPart(x, y));
        projectile.add(new MovingPart(200));
        projectile.add(new TimerPart(2));

        MovingPart movingPart = projectile.getPart(MovingPart.class);

        movingPart.setRight(true);
    }
}
=======

gotReference = true;
    }
}
>>>>>>> master:Bundles/Weapon1/src/main/java/dk/sdu/mmmi/cbse/weapon/WeaponSystem.java
