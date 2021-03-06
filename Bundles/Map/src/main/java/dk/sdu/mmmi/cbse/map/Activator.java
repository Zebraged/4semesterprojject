package dk.sdu.mmmi.cbse.map;

import MapServices.MapPlugin;
import MapServices.MapProcessor;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private IGamePluginService plugin = new MapPlugin();
    

    /**
     * Starts the mapbundle, first method to be called when the osgi calls the
     * bundle.
     *
     * @param context
     * @throws Exception
     */
    public void start(BundleContext context) throws Exception {

        context.registerService(IGamePluginService.class.getName(), plugin, null);
    }

    /**
     * Stops the mapbundle
     *
     * @param context
     * @throws Exception
     */
    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
