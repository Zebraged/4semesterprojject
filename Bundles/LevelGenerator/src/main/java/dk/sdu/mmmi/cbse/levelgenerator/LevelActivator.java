package dk.sdu.mmmi.cbse.levelgenerator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class LevelActivator implements BundleActivator {
    private LevelGenerator generator;
    public void start(BundleContext context) throws Exception {
        generator = new LevelGenerator("map_example.lvl");
        generator.generate();
    }

    public void stop(BundleContext context) throws Exception {
        generator = null;
    }

}
