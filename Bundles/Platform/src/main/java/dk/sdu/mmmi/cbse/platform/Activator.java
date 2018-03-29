package dk.sdu.mmmi.cbse.platform;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    private IGamePluginService plugin = new PlatformPlugin();
    //private IEntityProcessingService process = new PlatformProcess();

    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        //context.registerService(IEntityProcessingService.class.getName(), process, null);
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
