/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeroCraft.Core;

/**
 * 
 * @author DELL
 */
public class Description {
    public void show() {
        try {
            Utility.clearScreen();
            System.out.println("===== GAME DESCRIPTION =====");
            System.out.println("Welcome to HeroCraft: Legends!");
            System.out.println("- A turn-based combat RPG featuring 12 unique heroes.");
            System.out.println("- Includes both 1v1 and 3v3 battle modes.");
            System.out.println("- Factions grant random HP/MP buffs when allies are teamed.");
            System.out.println("- Features tarot card rounds, cooldowns, and status effects.");
            System.out.println("- Menu-driven, beginner-friendly, and fully text-based.");
            System.out.println("======= Game Feature ========");
            System.out.println("       »Tarot Card)");
            System.out.println("Vincent (Silent Gaze) Card Silenced: Prevents enemies from using abilities for 1 turn. \n" +
"Jharvis (Chaos Jester) Card Confusion: Makes the enemy attack nearby allies for 2 turns chances of it happen is 51%. \n" +
"jurick (Injury-Seeker) Card Vulnerable: Enemies affected take 30% more damage for 1 turn. \n" +
"neihl (Night's Herald) Card Sleep: Makes enemies sleep and prevents attacks for 1 turn. \n" +
"ram (Exhausting Force) Card Weaken: Lowers attack power temporarily for 1 turn.");
            System.out.println("       »Faction Buff)");
            System.out.println("- If there are are two or more same faction the character gains hp and mp");
            System.out.println("       »Game Mode)");
            System.out.println("- players will choose their game mode whether  it could be a 1v1 or 3v3");
            System.out.println("       »Battle Mode)");
            System.out.println("- players will choose their battle mode whether  it could be a PvP or PvAi");
            System.out.println("=============================");
            Utility.pause();
        } catch (Exception e) {
            System.out.println("Error while displaying description: " + e.getMessage());
        }
    }
}