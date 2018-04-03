package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PlayerActivator implements BundleActivator {

    private IGamePluginService plugin = new PlayerPlugin();
    private IEntityProcessingService process = new PlayerProcess();
    
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityProcessingService.class.getName(), process, null);
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
