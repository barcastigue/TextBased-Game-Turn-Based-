/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Draftv2;

/**
 *
 * @author DELL
 */
public abstract class Character {
    protected String name;
    protected String faction;

    public Character(String name, String faction) {
        this.name = name;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public String getFaction() {
        return faction;
    }

    public abstract void displayInfo();
}

