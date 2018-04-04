package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

    private List<Bundle> activatedBundles = new ArrayList();

    public void start(BundleContext context) throws Exception {

        Collision collision = new Collision();

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        System.out.println(Arrays.toString(context.getServiceReferences(IGamePluginService.class.getName(), null)));
//        
//        ServiceReference[] s = context.getServiceReferences(IGamePluginService.class.getName(), null);
//        activatedBundles = Arrays.asList(context.getBundles()); // get all installed bundles 
//
//        for (Bundle b : activatedBundles) {
//
//            
//            
//            
//            
//           // System.out.println(Arrays.toString(b.getRegisteredServices()));
////            
////            ServiceReference[] all = b.getRegisteredServices();
////            
////            Entity a = all[0].getClass().getClassLoader():
////                    
////            System.out.println(Arrays.toString(all));
////            
////            IGamePluginService c = (IGamePluginService) b.getBundleContext().getServiceReference(IGamePluginService.class.getName());
////            if (c != null) {
//                
//        //    }
//        }
        
        // System.out.println(Arrays.toString(context.getBundles()));
        context.registerService(IEntityProcessingService.class.getName(), collision, null);

        collision.start();
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
