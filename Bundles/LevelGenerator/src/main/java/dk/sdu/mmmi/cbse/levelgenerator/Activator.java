/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 *
 * @author Kristian
 */
public class Activator implements BundleActivator {
    private LevelActivator act;
    
    ArrayList<ServiceRegistration> list = new ArrayList();
    
    public void start(BundleContext context) throws Exception {
        list.add(context.registerService(IGamePluginService.class.getName(), (act = new LevelActivator()), null));
        list.add(context.registerService(IEntityProcessingService.class.getName(), act, null));
    }
    
    public void stop(BundleContext context) throws Exception {
        act.stop();
        for(ServiceRegistration serv: list){
            serv.unregister();
        }
    }
    
}
