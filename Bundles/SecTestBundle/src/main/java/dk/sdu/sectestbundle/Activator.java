package dk.sdu.sectestbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("I'm your secound bundle GG");
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Damm you motherfucker!!!");
    }

}
