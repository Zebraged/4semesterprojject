/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.collision;

/**
 * Enum class that contains all urls to the modules that collision uses to locate each module, objects also used to create objects in collision class.
 * @author Jesper
 */
public enum ObjTypes {
    PLAYER("dk.sdu.mmmi.cbse.player.Player.*"),
    ENEMY("dk.sdu.mmmi.cbse.enemy.type.Enemy.*"),
    PLATFORM("dk.sdu.mmmi.cbse.platform.Platform.*"),
    TEDDY("dk.sdu.mmmi.cbse.enemy.type.TeddyEnemy.*"),
    CLOUD("dk.sdu.mmmi.cbse.enemy.type.CloudEnemy.*"),
    UNICORN("dk.sdu.mmmi.cbse.enemy.type.UnicornEnemy.*");

    private String url;

    ObjTypes(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}
