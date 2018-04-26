/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.hud;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.AssetGenerator;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Guest Account
 */
public class HudProcessing implements IEntityProcessingService {

    public void process(GameData gameData, World world) {
        BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);

        float heartPosition = -270;
        float playerX = 0;
        float halfPlayerX = 0;
        int displayWidth = 0;

        float finalPosition = 0;
        for (Entity life : world.getEntities(Life.class)) {
            PositionPart positionPart = life.getPart(PositionPart.class);

            if (reference != null) {
                IPlayerPositionService playerPosition = (IPlayerPositionService) context.getService(reference);
                float temp = playerPosition.getX();
                playerX = temp;

                int temp2 = gameData.getDisplayWidth();
                displayWidth = temp2;

                halfPlayerX = playerX / 2;
                //finalPosition = (heartPosition + (playerX * 2)) - (displayWidth / 2);
                finalPosition = heartPosition + gameData.getCamX() + ((halfPlayerX) * gameData.getZoom());

                positionPart.setX(finalPosition);
                
                positionPart.setY(gameData.getCamY() + 140);
            }

            AssetGenerator assetGen = life.getPart(AssetGenerator.class);
            positionPart.process(gameData, life);
            assetGen.process(gameData, life);

            //positionPart.setX(finalPosition);
            heartPosition = heartPosition + 30;
        }
    }

}
