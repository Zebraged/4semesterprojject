package dk.sdu.mmmi.cbse.goalbundle;

import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IScoreService;
import dk.sdu.mmmi.cbse.goalbundle.score.ScoreService;
import java.util.ArrayList;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    
    IEntityGenerator generator = new GoalFactory();
    IGamePluginService plugin = new GoalPlugin();
    IEntityProcessingService process = new GoalProcess();
    IScoreService score = new ScoreService();
    
    ArrayList<ServiceRegistration> list = new ArrayList();
    
    public void start(BundleContext context) throws Exception {
        list.add(context.registerService(IGamePluginService.class.getName(), plugin, null));
        list.add(context.registerService(IEntityGenerator.class.getName(), generator, null));
        list.add(context.registerService(IEntityProcessingService.class, process, null));
        list.add(context.registerService(IScoreService.class, score, null));
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
        for(ServiceRegistration serv: list){
            serv.unregister();
        }
    }

}
