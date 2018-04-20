package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.services.ICollisionService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {


    public void start(BundleContext context) throws Exception {

        Collision collision = new Collision();

        context.registerService(ICollisionService.class.getName(), collision, null);

        collision.start();
    }

    public void stop(BundleContext context) throws Exception {
       
    }

}
