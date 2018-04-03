package dk.sdu.collision;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Arrays;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {

        Collision collision = new Collision();

        System.out.println(Arrays.toString(context.getBundles()));
        
        context.registerService(IEntityProcessingService.class.getName(), collision, null);

        collision.start();
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
