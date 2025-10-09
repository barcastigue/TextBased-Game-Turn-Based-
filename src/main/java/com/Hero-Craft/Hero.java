/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Draftv2;

/**
 *
 * @author DELL
 */
public class Hero extends Character {

    public Hero(String name, String faction) {
        super(name, faction);
    }

    @Override
    public void displayInfo() {
        System.out.println(name + " (" + faction + ")");
    }
}

