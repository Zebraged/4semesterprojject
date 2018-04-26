package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.ILevelGenerator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class LevelActivator implements IGamePluginService, IEntityProcessingService {

    private LevelGenerator generator;
    private boolean loaded;
    private float timer;
    private final float timerMax = 5.0f;

    @Override
    public void start(GameData gameData, World world, BundleContext context) {
        System.out.println("Level Generator Started!");
        try {
            generator = new LevelGenerator(context, gameData, world);
            context.registerService(ILevelGenerator.class.getName(), generator, null);
            generator.generate();
            loaded = true;
        } catch (IOException ex) {
            Logger.getLogger(LevelActivator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void stop() {
        generator = null;
        loaded = false;
    }

    @Override
    public boolean getStatus() {
        return loaded;
    }

    public void process(GameData gameData, World world) {
        if (generator == null) {
            return;
        }
        timer += gameData.getDelta();
        if (timer > timerMax) {
            timer = 0;
            try {
                generator.checkNewGeneratorsAndGenerate();
            } catch (InterruptedException ex) {
                Logger.getLogger(LevelActivator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LevelActivator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
