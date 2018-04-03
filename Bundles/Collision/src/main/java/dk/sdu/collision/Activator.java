package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private List<Bundle> activatedBundles = new ArrayList();
    
    public void start(BundleContext context) throws Exception {

        Collision collision = new Collision();
        
        activatedBundles = Arrays.asList(context.getBundles()); // get all installed bundles 

        for (Bundle b : activatedBundles){
            
            b.getBundleContext().getServiceReference(IGamePluginService.class.getName());
            
        }
        
        
       // System.out.println(Arrays.toString(context.getBundles()));
        
        context.registerService(IEntityProcessingService.class.getName(), collision, null);

        collision.start();
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
