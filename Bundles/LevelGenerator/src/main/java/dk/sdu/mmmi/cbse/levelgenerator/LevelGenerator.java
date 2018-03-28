/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemyGenerator;
import dk.sdu.mmmi.cbse.common.services.ILevelGenerator;
import dk.sdu.mmmi.cbse.common.services.ITileGenerator;
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
import org.osgi.framework.BundleContext;

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

    public LevelGenerator(BundleContext context, GameData data, World world) {
        
        path="C:/Users/Kristian/Documents/GitHub/4semesterprojject/Bundles/LevelGenerator/map_example.lvl";
        this.context = context;
        this.data = data;
        this.world = world;
        
        inf("Preparing Level Generator..");
        settingsParser = new SettingsParser();
        
        objectsParser = new ObjectsParser(context, data, world);
        tilePlacementParser = new TilePlacementParser(context, data, world);
        
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
                settingsParser.parse(line);
                if(keypair[0].equals("tiles_y")) {
                    tilePlacementParser.setLengthY(Integer.valueOf(keypair[1]));
                }
                break;
            case OBJECTS:
                objectsParser.parse(line);
                break;

            case TILES:
                //tilePlacementParser.parse(line);
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
}

enum Command {
    NONE, MAP_SETTINGS, TILES, OBJECTS;

    static Command getCommand(String cmdName) {
        return Command.valueOf(cmdName.toUpperCase());
    }
}
