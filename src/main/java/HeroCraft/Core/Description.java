package HeroCraft.Core;

public class Description {

    public void show() {
        try {
            Utility.clearScreen();

            System.out.println("==========================================");
            System.out.println("              GAME DESCRIPTION");
            System.out.println("==========================================");

            System.out.println("Welcome to HeroCraft: Legends!");
            System.out.println();
            System.out.println("- A turn-based combat RPG with 12 unique heroes.");
            System.out.println("- Supports both 1v1 and 3v3 battle modes.");
            System.out.println("- Faction bonuses give random HP/MP buffs.");
            System.out.println("- Includes tarot rounds, status effects, and cooldowns.");
            System.out.println("- Fully menu-driven and beginner-friendly.");
            System.out.println("__________________________________________");

            System.out.println("TAROT CARD EFFECTS");
            System.out.println("------------------------------------------");

            System.out.println("- Vincent (Silent Gaze)");
            System.out.println("    Silenced: Prevents skill use for 1 turn.");
            System.out.println();

            System.out.println("- Jharvis (Chaos Jester)");
            System.out.println("    Confusion: Enemy may attack allies for 2 turns");
            System.out.println("    (51% chance).");
            System.out.println();

            System.out.println("- Jurick (Injury-Seeker)");
            System.out.println("    Vulnerable: Target takes +30% damage for 1 turn.");
            System.out.println();

            System.out.println("- Neihl (Night's Herald)");
            System.out.println("    Sleep: Prevents all actions for 1 turn.");
            System.out.println();

            System.out.println("- Ram (Exhausting Force)");
            System.out.println("    Weaken: Reduces attack power for 1 turn.");
            System.out.println("__________________________________________");

            System.out.println("FACTION BUFFS");
            System.out.println("------------------------------------------");
            System.out.println("- If two or more characters share a faction,");
            System.out.println("  they gain bonus HP and MP during battle.");
            System.out.println("__________________________________________");

            System.out.println("GAME MODES");
            System.out.println("------------------------------------------");
            System.out.println("- 1v1 Mode");
            System.out.println("- 3v3 Mode");
            System.out.println();

            System.out.println("BATTLE MODES");
            System.out.println("------------------------------------------");
            System.out.println("- PvP  (Player vs Player)");
            System.out.println("- PvAi (Player vs AI)");
            System.out.println("==========================================");

            Utility.pause();
        } catch (Exception e) {
            System.out.println("Error while displaying description: " + e.getMessage());
        }
    }
}