package dk.sdu.mmmi.cbse.player;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("Player Bundle activated");
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
