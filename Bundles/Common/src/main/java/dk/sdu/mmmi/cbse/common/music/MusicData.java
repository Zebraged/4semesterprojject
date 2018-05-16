/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.music;

/**
 *
 * @author Mr. Kinder
 */
public class MusicData {

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return if the music is looping.
     */
    public boolean isLooping() {
        return loop;
    }
    private String path;
    private boolean loop;
    
    public MusicData(String path, boolean loop) {
        this.path = path;
        this.loop = loop;
    }
}
