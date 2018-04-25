package dk.sdu.mmmi.cbse.goalbundle;

import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IScoreService;
import dk.sdu.mmmi.cbse.goalbundle.score.ScoreService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    
    IEntityGenerator generator = new GoalFactory();
    IGamePluginService plugin = new GoalPlugin();
    IEntityProcessingService process = new GoalProcess();
    IScoreService score = new ScoreService();
    
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityGenerator.class.getName(), generator, null);
        context.registerService(IEntityProcessingService.class, process, null);
        context.registerService(IScoreService.class, score, null);
    }

    public void stop(BundleContext context) throws Exception {
        plugin.stop();
    }

}
