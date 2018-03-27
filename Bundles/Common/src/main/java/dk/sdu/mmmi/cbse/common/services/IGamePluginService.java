package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.osgi.framework.BundleContext;

public interface IGamePluginService {
    void start(GameData gameData, World world, BundleContext context);

    void stop();
    
    boolean getStatus();
}
