/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

/**
 *
 * @author Marcg
 */
public interface IScoreService {
    public int getCurrentScore();
    public int getFinalScore();
    public int getTimer(float deltaTime);
    public void addScore(int score);
    public void reduceScore(int score);
}
