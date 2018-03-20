/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapServices;

import Map.Map;
import Map.MapAssetManager;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.map.MapServices;

/**
 *
 * @author Guest Account
 */
public class MapProcessor implements IEntityProcessingService {

    MapServices mam = new MapAssetManager();

    private void updateSprite(Entity entity) {
        entity.setSprite(mam.findPath());

    }

    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Map.class)) {

            /*PositionPart positionPart = entity.getPart(PositionPart.class);
            positionPart.process(gameData, entity);
             */
            updateSprite(entity);

        }
    }

}
