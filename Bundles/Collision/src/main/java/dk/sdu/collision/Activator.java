package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.services.ICollisionService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {
    
    private Collision collision;

    public void start(BundleContext context) throws Exception {

        collision = new Collision();

        context.registerService(ICollisionService.class.getName(), collision, null);

    }

    public void stop(BundleContext context) throws Exception {
       collision.stop();
    }

}
