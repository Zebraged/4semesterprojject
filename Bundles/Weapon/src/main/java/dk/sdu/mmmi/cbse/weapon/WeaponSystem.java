package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SHIFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
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

    private String[] weapons = {"Stick", "Sword"};
    private int equiped = 0;
    private boolean changing = false;

    public void process(GameData gameData, World world) {
        try {
            for (Entity weapon : world.getEntities(Weapon.class)) {
                PositionPart positionPart = weapon.getPart(PositionPart.class);
                AssetGenerator assetGenerator = weapon.getPart(AssetGenerator.class);

                if (!gotReference) {
                    createReference();
                }

                if (gameData.getKeys().isDown(SHIFT) && !changing) {
                    equiped++;
                    equiped = equiped % weapons.length;
                    System.out.println(equiped);
                    assetGenerator.addImagePath("image/" + weapons[equiped] + "/");
                    changing = true;
                } else {
                    changing = false;
                }

                if (gameData.getKeys().isDown(SPACE)) {
                    assetGenerator.changeImage("Attack.png");
                    positionPart.setX(iPlayerPositionService.getX() + 20);
                    positionPart.setY(iPlayerPositionService.getY() - 17);

                } else {
                    assetGenerator.changeImage("Idle.png");
                    positionPart.setX(iPlayerPositionService.getX() + 20);
                    positionPart.setY(iPlayerPositionService.getY() + 15);
                }

                positionPart.process(gameData, weapon);
                assetGenerator.process(gameData, weapon);
            }
        } catch (NullPointerException ex) {
            // no exception message for now
        }
    }

    private void createReference() {
        bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        iPlayerPositionService = bundleContext.getService(bundleContext.getServiceReference(IPlayerPositionService.class));

        gotReference = true;
    }
}
