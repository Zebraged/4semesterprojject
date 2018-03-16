package dk.sdu.firsttestbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("Hey There! I'm your super awesome OSGI Bundle :D");
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Fuck you for stopping me :( don't you like me anymore :(");
    }

}
