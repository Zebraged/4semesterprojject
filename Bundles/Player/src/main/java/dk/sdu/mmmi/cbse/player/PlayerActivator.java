package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import dk.sdu.mmmi.cbse.common.services.IPlayerInfoService;
import java.util.ArrayList;
import org.osgi.framework.ServiceRegistration;

public class PlayerActivator implements BundleActivator {

    private IGamePluginService plugin = new PlayerPlugin();
    private IEntityProcessingService process = new PlayerProcess();
    ArrayList<ServiceRegistration> list = new ArrayList();
    
    public void start(BundleContext context) throws Exception {
        list.add(context.registerService(IGamePluginService.class.getName(), plugin, null));
        list.add(context.registerService(IEntityProcessingService.class.getName(), process, null));
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
        for(ServiceRegistration serv: list){
            serv.unregister();
        }
    }

}
