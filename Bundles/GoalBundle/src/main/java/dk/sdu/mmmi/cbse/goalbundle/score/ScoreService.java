/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.goalbundle.score;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IScoreService;

/**
 *
 * @author Marcg
 */
public class ScoreService implements IScoreService{
    
    int score = 0;
    int time = 300;
    float timecount = 0;
    GameData data = GameData.getGameData();
    
    public ScoreService(){
        
    }

    public int getCurrentScore() {
        return score;
    }

    public int getFinalScore() {
        if(data.isGameWon() == true){
            return (score + time);
        }
        return score;
    }

    public int getTimer(float deltaTime) {
        timecount += deltaTime;
        if (timecount >= 1 && time > 0){
            time--;
            timecount = 0;
        }
        return time;
    }
    
    public void addScore(int score) {
        this.score += score;
    }

    public void reduceScore(int score) {
        this.score -= score;
    }

    
    
}
