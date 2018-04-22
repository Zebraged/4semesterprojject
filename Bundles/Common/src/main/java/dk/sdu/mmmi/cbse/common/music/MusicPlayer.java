/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.music;

import java.util.LinkedList;

/**
 *
 * @author Mr. Kinder
 */
public class MusicPlayer {
    //A queue for the music.
    private LinkedList<MusicData> data;
    
    //If a new song i wanted asap.
    private MusicData urgentSong;
    private static MusicPlayer player;
    private MusicPlayerState state;
    
    public static MusicPlayer getMusicPlayer() {
        if(player == null) {
            player = new MusicPlayer();
        }
        return player;
    }
    
    public MusicPlayer() {
        this.data = new LinkedList();
        this.state = MusicPlayerState.PLAY;
    }
    
    /**
     * Queues the next song to play.
     * @param path 
     */
    public void queueSong(String path, boolean isLooping) {
        this.data.addLast(new MusicData(path, isLooping));
    }
    
    /**
     * This should only be called from the core module.
     * @return Next queued song. Null if empty
     */
    public MusicData getNextSong() {
        return this.data.pollLast();
    }
    
    /**
     * Urgent: Overrides the current playing song.
     * @return true if it has a song that must play immediately.
     */
    public boolean hasUrgentSong() {
        return urgentSong != null;
    }
    
    public MusicData getUrgentSong() {
        MusicData nextSong = urgentSong;
        urgentSong = null;
        return nextSong;
    }

    /**
     * @return the state
     */
    public MusicPlayerState getState() {
        return state;
    }

    /**
     * Stop: wipes the music queue and everything.
     * Pause: pauses everything.
     * Play: resumes
     * @param state the state to set
     */
    public void setState(MusicPlayerState state) {
        this.state = state;
        
        if(this.state == MusicPlayerState.STOP) {
            this.data.clear();
            this.urgentSong = null;
        }
    }

    /**
     * @param urgentSong the urgentSong to set
     */
    public void setUrgentSong(String path, boolean isLooping) {
        this.urgentSong = new MusicData(path, isLooping);
    }
}