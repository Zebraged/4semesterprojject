package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Marcg
 */
public interface IGamePluginService {

    /**
     * starts the plugin, making the plugin avaiable for the other bundels
     * @param gameData data from the game
     * @param world world object
     * @param context needs the context of the framework that it's installed in
     */
    void start(GameData gameData, World world, BundleContext context);

    /**
     * stops the plugin, making all functions unavailable
     */
    void stop();
    
    /**
     * shows if the plugin is active.
     * @return if true: the plugin is active and running. if false the plugin is installed but not active.
     */
    boolean getStatus();
}
