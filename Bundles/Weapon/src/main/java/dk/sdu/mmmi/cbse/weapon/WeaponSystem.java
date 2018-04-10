/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author Marcg
 */
public class WeaponSystem implements IEntityProcessingService {

<<<<<<< HEAD
    private boolean gotReference = false;

    private BundleContext bundleContext;

    private IPlayerPositionService iPlayerPositionService;

    public void process(GameData gameData, World world) {
        try {
            for (Entity weapon : world.getEntities(Weapon.class)) {
                PositionPart positionPart = weapon.getPart(PositionPart.class);
                AssetGenerator assetGenerator = weapon.getPart(AssetGenerator.class);

                if (!gotReference) {
                    createReference();
                }

                positionPart.setX(iPlayerPositionService.getX());
                positionPart.setY(iPlayerPositionService.getY());

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
=======
    public void process(GameData gd, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

>>>>>>> parent of 316f1cf... Weapon følger player nu
}
