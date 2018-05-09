/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.coreofgame.sound;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import dk.sdu.mmmi.cbse.common.music.MusicData;
import dk.sdu.mmmi.cbse.common.music.MusicPlayer;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Mr. Kinder
 */
public class MusicPlayerCore {

    private HashMap<String, Music> songs;
    private MusicPlayer player;
    private MusicData currentSong;
    private MusicData nextSong;
    private Music music;
    private boolean switchingSong;
    private final float FADE_SPEED = 0.2f;

    //Fade in = 1, Fade out = -1
    private int fadeDirection = 1;

    public MusicPlayerCore() {
        player = MusicPlayer.getMusicPlayer();
        songs = new HashMap();
        System.out.println("MusicPlayer Active");
    }

    public void update(float dt) {
        switch (player.getState()) {
            case PAUSE:
                pause();
                break;

            case PLAY:
                play(dt);
                break;

            case STOP:
                stop();
                break;

            default:
                break;
        }
    }
    
    private void play(float dt) {
        //Urgent Songs have the highest priority. Loaded if existing.
        if (player.hasUrgentSong()) {
            this.nextSong = player.getUrgentSong();
            this.switchingSong = true;
            this.fadeDirection = -1;
        }

        //Changes song to the next in queue. If the current is not looping.
        if (this.currentSong == null) {
            this.nextSong = player.getNextSong();
            this.switchingSong = true;
            this.fadeDirection = -1;
        }

        //Changes to the next song
        if (switchingSong) {
            changeSong(dt);
        }

        //Music playing bug fix.
        if (music != null) {
            if (music.getVolume() > 0 && !music.isPlaying()) {
                music.play();
            }
        }
    }
    
    private void pause() {
        if(music.isPlaying()) {
            music.pause();
        }
    }
    
    private void stop() {
        if(music.isPlaying()) {
            music.stop();
        }
    }

    private void changeSong(float dt) {
        //If music is null, it'll load a new song.
        if (music == null) {
            //OpenAL bug: Don't remove this try-catch.
            //The game will crash. It finds the song, tries to load it.
            //If unable to allocate audio buffers, it'll try again.
            try {
                if (nextSong == null) {
                    return;
                } else {
                    currentSong = nextSong;
                }
                this.music = this.getMusic(nextSong.getPath());
                this.music.setVolume(0);
                this.fadeDirection = 1;
                this.nextSong = null;
                this.music.play();
                prepareMusicListener();
            } catch (com.badlogic.gdx.utils.GdxRuntimeException e) {
                this.music = null;
                this.nextSong = currentSong;
                this.songs.remove(this.nextSong.getPath());
                fadeDirection = -1;
                return;
            }
        }

        //Fades the music out if negative, in if positive.
        this.music.setVolume(this.music.getVolume() + ((dt * this.FADE_SPEED) * this.fadeDirection));

        //Stops the song if the volume is 0 or below.
        if (this.fadeDirection == -1) {
            if (this.music.getVolume() < 0) {
                this.music.stop();
                this.music = null;
            }
        }

        //If the volume is high enough, i'll stop the song changing process.
        if (this.fadeDirection == 1) {
            if (this.music.getVolume() > 0.7f) {
                this.switchingSong = false;
                this.music.setVolume(0.7f);
            }
        }
    }

    private void prepareMusicListener() {
        this.music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                if (!currentSong.isLooping()) {
                    switchingSong = true;
                    currentSong = null;
                }
            }
        });
    }

    public void dispose() {
        if(music != null){
            this.music.dispose();
        }
    }

    private Music getMusic(String path) {
        Music m = songs.get(path);

        if (m == null) {
            File file = new File(path);
            m = Gdx.audio.newMusic(new FileHandle(file));
            songs.put(path, m);
        }

        return m;
    }
}
