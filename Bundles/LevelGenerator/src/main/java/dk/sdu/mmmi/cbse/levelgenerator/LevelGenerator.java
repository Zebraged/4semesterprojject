/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

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

/**
 *
 * @author Mr. Kinder
 */
public class LevelGenerator {

    private String path;
    private String tileSetPath;
    private String background;
    private Command currentCommand = Command.NONE;
    private ISpecificParser settingsParser, objectsParser, tilePlacementParser;

    public LevelGenerator(String path) {
        settingsParser = new SettingsParser();
        objectsParser = new ObjectsParser();
        tilePlacementParser = new TilePlacementParser();
        this.path = path;
    }

    public void generate() throws FileNotFoundException, IOException {
        prepareAssets();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line;
        while ((line = reader.readLine()) != null) {
            //Go to next line if line is empty.
            if (line.length() == 0) {
                continue;
            }

            parse(line);
        }
    }

    private void prepareAssets() {
        //Prepare backgrounds and things alike.
    }

    private void parse(String line) {
        if (line.endsWith(":")) {
            currentCommand = Command.getCommand(line.substring(0, line.length()-1));
        }

        switch (currentCommand) {
            case MAP_SETTINGS:
                settingsParser.parse(line);
                break;
            case OBJECTS:
                objectsParser.parse(line);
                break;

            case TILES:
                tilePlacementParser.parse(line);
                break;

            default:
                System.out.println("The command is invalid!");
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        LevelGenerator generator = new LevelGenerator("map_example.lvl");
        generator.generate();
    }
}

enum Command {
    NONE, MAP_SETTINGS, TILES, OBJECTS;

    static Command getCommand(String cmdName) {
        return Command.valueOf(cmdName.toUpperCase());
    }
}
