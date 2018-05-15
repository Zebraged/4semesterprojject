package dk.sdu.mmmi.cbse.platform;

import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private IGamePluginService plugin = new PlatformPlugin();
    private IEntityGenerator factory = new PlatformFactory();
    
    ArrayList<ServiceRegistration> list = new ArrayList();

    public void start(BundleContext context) throws Exception {
        list.add(context.registerService(IGamePluginService.class.getName(), plugin, null));
        list.add(context.registerService(IEntityGenerator.class.getName(), factory, null));
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
        for(ServiceRegistration serv: list){
            serv.unregister();
        }
    }

}
