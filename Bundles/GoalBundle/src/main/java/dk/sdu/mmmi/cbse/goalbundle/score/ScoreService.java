/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.goalbundle.score;

import dk.sdu.mmmi.cbse.common.services.IScoreService;

/**
 *
 * @author Marcg
 */
public class ScoreService implements IScoreService{
    
    int score = 0;
    int time = 300;
    float timecount = 0;
    
    
    public ScoreService(){
        
    }

    public int getCurrentScore() {
        return score;
    }

    public int getFinalScore() {
        return (score + time);
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
