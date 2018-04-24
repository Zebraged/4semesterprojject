/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityGenerator;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.ILevelGenerator;
import dk.sdu.mmmi.cbse.levelgenerator.parsers.ISpecificParser;
import dk.sdu.mmmi.cbse.levelgenerator.parsers.ObjectsParser;
import dk.sdu.mmmi.cbse.levelgenerator.parsers.SettingsParser;
import dk.sdu.mmmi.cbse.levelgenerator.parsers.TilePlacementParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Mr. Kinder
 */
public class LevelGenerator implements ILevelGenerator {

    private String path;
    private String tileSetPath;
    private String background;
    private Command currentCommand = Command.NONE;
    private ISpecificParser settingsParser, objectsParser;
    private TilePlacementParser tilePlacementParser;
    private BundleContext context;
    private GameData data;
    private World world;
    private Collection<IEntityGenerator> generators;
    private Collection<IEntityGenerator> loadedGenerators;

    public LevelGenerator(BundleContext context, GameData data, World world) {
        this.context = context;
        generators = getGenerators();
        loadedGenerators = new ArrayList();
        

        path="./Bundles/LevelGenerator/map_example.lvl";
        this.context = context;
        this.data = data;
        this.world = world;
        
        inf("Preparing Level Generator..");
        settingsParser = new SettingsParser();
        
        objectsParser = new ObjectsParser(data, world);
        tilePlacementParser = new TilePlacementParser(data, world);
        
    }
    private void inf(String str) {
        System.out.println("LevelGenerator: "+str);
    }

    /**
     * Generates all map data found in the path specified.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void generate() throws FileNotFoundException, IOException {
        loadedGenerators.addAll(generators);
        prepare();
        inf("Beginning generation!..");
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line;
        while ((line = reader.readLine()) != null) {
            //Go to next line if line is empty.
            if (line.length() == 0) {
                continue;
            }

            parse(line);
        }
        inf("Generation done..");
        reader.close();
    }

    private void prepare() {
        //Prepare backgrounds and things alike.
        
    }

    private void parse(String line) {
        //Change command if ends with :
        if (line.endsWith(":")) {
            currentCommand = Command.getCommand(line.substring(0, line.length()-1));
            return;
        }
        //If empty line, don't try to parse it.
        if (line.length() == 0) {
            return;
        }
        
        switch (currentCommand) {
            case MAP_SETTINGS:
                String[] keypair = line.split("=");
                settingsParser.parse(generators, line);
                if(keypair[0].equals("tiles_y")) {
                    tilePlacementParser.setLengthY(Integer.valueOf(keypair[1]));
                }
                break;
            case OBJECTS:
                objectsParser.parse(generators, line);
                break;

            case TILES:
                tilePlacementParser.parse(generators, line);
                break;

            default:
                System.out.println("The command is invalid!");
                break;
        }
    }

    /**
     * Sets the path to the level file. 
     * @param path 
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * Gets all the generators available.
     * @return 
     */
     private Collection<IEntityGenerator> getGenerators(){
        IEntityGenerator gen;
        Collection<IEntityGenerator> generators = new ArrayList(); 
        for(ServiceReference<IEntityGenerator> reference : getGeneratorReferences()){
            gen = (IEntityGenerator) context.getService(reference);
            generators.add(gen);
        }
        return generators;
    }
    
    private Collection<ServiceReference<IEntityGenerator>> getGeneratorReferences() {
        Collection<ServiceReference<IEntityGenerator>> collection = null;
        try {
            collection = this.context.getServiceReferences(IEntityGenerator.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Exception: \t"+ex);
        }
        return collection;
    }
    
    /**
     * Listens for new generators and starts the generator if needed.
     * @throws InterruptedException
     * @throws IOException 
     */
    public void checkNewGeneratorsAndGenerate() throws InterruptedException, IOException {
        Collection<IEntityGenerator> newGens = getGenerators();
        
        newGens.removeAll(loadedGenerators);
        
        if(!newGens.isEmpty()) {
            generators = newGens;
            generate();
        }
    }
}

enum Command {
    NONE, MAP_SETTINGS, TILES, OBJECTS;

    static Command getCommand(String cmdName) {
        return Command.valueOf(cmdName.toUpperCase());
    }
}
