/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.levelgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
=======
>>>>>>> ef2416003df6ebb96ced24b3e7cdc01b48a2787c

/**
 *
 * @author Mr. Kinder
 */
public class LevelGenerator {

    private String path;
    private String tileSetPath;
    private String background;
    private Command currentCommand;

    public LevelGenerator(String path) {
        this.path = path;
    }
    public void generate() throws FileNotFoundException, IOException {
        prepareAssets();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line;
        while((line=reader.readLine())!=null) {
            parse(line);
        }
    }
    
    private void prepareAssets() {
        //Prepare backgrounds and things alike.
    }
    
    private void parse(String line) {
<<<<<<< HEAD
        if(line.endsWith(":")) {
            currentCommand = Command.getCommand(line.toUpperCase());
        };
=======
>>>>>>> ef2416003df6ebb96ced24b3e7cdc01b48a2787c
        
            case MAP_SETTINGS:
                
                break;
            case OBJECTS:
                
                break;
                
            case TILES:
                
                break;
                
            default:
                System.out.println("The command is invalid!");
                break;
        }
    }
    
}

enum Command {
    MAP_SETTINGS, TILES, OBJECTS;
    
    static Command getCommand(String cmdName) {
        return Command.valueOf(cmdName.toUpperCase());
    }
}
