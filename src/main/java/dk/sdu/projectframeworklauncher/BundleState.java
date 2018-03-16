/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.projectframeworklauncher;

/**
 *
 * @author Jesper
 */
public enum BundleState {
    INSTALLED("Installed"), UNINSTALLED("Uninstalled"), RESOLVED("Resolved"), STARTING("Starting"), ACTIVE("Active"), STOPPING("Stopping"),ERROR("Error");
    private String name;

    BundleState(String url) {
        this.name = url;
    }

    public String getState() {
        return name;
    }
}
