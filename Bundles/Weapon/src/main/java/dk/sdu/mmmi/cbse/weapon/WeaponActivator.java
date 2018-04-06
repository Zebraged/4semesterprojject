package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WeaponActivator implements BundleActivator {

    private IGamePluginService plugin = new WeaponPlugin();
    private IEntityProcessingService process = new WeaponSystem();
    
    
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityProcessingService.class.getName(), process, null);
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
