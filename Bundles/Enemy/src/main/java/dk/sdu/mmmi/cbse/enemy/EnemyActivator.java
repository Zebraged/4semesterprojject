package dk.sdu.mmmi.cbse.enemy;


import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;

public class EnemyActivator implements BundleActivator {

    private IGamePluginService plugin = new EnemyPlugin();
    private IEntityProcessingService process = new EnemyProcess();
    private IEntityGenerator factory = new EnemyFactory();
    
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityProcessingService.class.getName(), process, null);
        context.registerService(IEntityGenerator.class.getName(), factory, null);
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
