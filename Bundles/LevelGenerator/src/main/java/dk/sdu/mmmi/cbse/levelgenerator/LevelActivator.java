package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleContext;

public class LevelActivator implements IGamePluginService {

    private LevelGenerator generator;
    private boolean loaded;

    @Override
    public void start(GameData gameData, World world, BundleContext context) {
        System.out.println("Level Generator Started!");
        try {
            generator = new LevelGenerator(context, gameData, world);
            generator.generate();
            loaded = true;
        } catch (IOException ex) {
            Logger.getLogger(LevelActivator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void stop() {
        //generator = null;
    }

    @Override
    public boolean getStatus() {
        return loaded;
    }

}
