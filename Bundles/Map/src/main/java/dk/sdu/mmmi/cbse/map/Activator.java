package dk.sdu.mmmi.cbse.map;

import MapServices.MapPlugin;
import MapServices.MapProcessor;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private IGamePluginService plugin = new MapPlugin();
    private IEntityProcessingService process = new MapProcessor();

    /**
     * Starts the mapbundle
     * @param context
     * @throws Exception 
     */
    public void start(BundleContext context) throws Exception {

        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityProcessingService.class.getName(), process, null);
    }

    /**
     * Stops the mapbundle
     * @param context
     * @throws Exception 
     */
    public void stop(BundleContext context) throws Exception {
        context.ungetService(context.getServiceReference(MapPlugin.class.getName()));
    }

}
