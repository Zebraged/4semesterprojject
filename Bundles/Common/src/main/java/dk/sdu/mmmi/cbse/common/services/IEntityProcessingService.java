package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author Marcg
 */
public interface IEntityProcessingService {

    /**
     * process the plugin, running the functions of each entity are created.
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
