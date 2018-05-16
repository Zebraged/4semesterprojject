/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.other;

/**
 *
 * @author Mr. Kinder
 */
public class ExpandedMath {

    /**
     * If the value is not within the range of x1 and x2, it will return the highest/lowest of those two.
     * @param value
     * @param x1
     * @param x2
     * @return a clamped value between x1 and x2
     */
    public static float clamp(float value, float x1, float x2) {
        //Find the heighest value
        float heighestValue = (x1 > x2) ? x1 : x2;
        float lowestValue = (heighestValue == x1) ? x2 : x1;

        //Compare
        if (value > heighestValue) {
            value = heighestValue;
        } else if(value < lowestValue) {
            value = lowestValue;
        }

        return value;
    }
}
