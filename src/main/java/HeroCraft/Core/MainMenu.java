package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;

public class MainMenu {
    private final BattleManager battleManager = new BattleManager();

    public void display() {
        showLogoScreen();
        boolean isRunning = true;

        while (isRunning) {
            Utility.clearScreen();
            showMainMenu();

            String choice = Utility.getValidatedString("Enter your choice: ", "1", "2", "3", "4");

            switch (choice) {
                case "1" -> GameManager.startGame();
                case "2" -> showDescription();
                case "3" -> startArcadeMode();
                case "4" -> isRunning = !confirmQuit();
            }                                              //▒▓
            }
    }                                       //▓▒
    private void showLogoScreen(){
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒║");
        System.out.println("║▒▓██╗▒▒██╗▒██████╗▒█████╗▒▒▒▒█████╗▒▒▒▒█████╗▒▓█████╗▒▒▒██████╗▒▓███████╗█████████╗▓║");
        System.out.println("║▓▒██║▒▒██║▒██╔═══╝▒██╔══██╗▒██╔══██╗▒██╔════╝▒██╔══██╗▒██╔══██╗▒██╔════╝▒╚══██╔══╝▓▒║");
        System.out.println("║▒▓███████║▒██████╗▒█████╔╝▒▒██║▒▒██║▒██║▒▒▒▒▒▒█████╔╝▒▒███████║▒█████╗▒▒▒▒▒▒██║▒▒▒▒▓║");
        System.out.println("║▓▒██╔══██║▒██╔═══╝▒██╔══██╗▒██║▒▒██║▒██║▒▒▒▒▒▒██╔══██╗▒██╔══██║▒██╔══╝▒▒▒▒▒▒██║▒▒▒▓▒║");
        System.out.println("║▒▓██║▒▒██║▒██████╗▒██║▒▒██║▒╚█████╔╝▒╚██████╗▒██║▒▒██║▒██║▒▒██║▒██║▒▒▒▒▒▒▒▒▒██║▒▒▒▒▓║");
        System.out.println("║▓▒╚═╝▒▒╚═╝▒╚═════╝▒╚═╝▒▒╚═╝▒▒╚════╝▒▒▒╚═════╝▒╚═╝▒▒╚═╝▒╚═╝▒▒╚═╝▒╚═╝▒▒▒▒▒▒▒▒▒╚═╝▒▒▒▓▒║");
        System.out.println("║▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓║");
        System.out.println("║▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓ Welcome To Hero-Craft!! ▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒║");
        System.out.println("║▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        /*System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒║");
        System.out.println("║▓▒██╗▒▓██╗▓██████╗▓█████╗▒▓▒▓█████╗▒▓▒▓█████╗▒▓█████╗▒▓▒█████╗▒▓▒███████╗▒████████╗▒▓▒▓║");
        System.out.println("║▒▓██║▓▒██║▒██╔═══╝▒██╔══██▒▓██╔══██╗▒▓██╔═══╝▓▒██╔══██╗▒██╔══██╗▒██╔════╝▓╚══██╔══╝▓▒▓▒║");
        System.out.println("║▓▒███████║▓██████╗▓█████╔╝▓▒██║▓▒██║▓▒██║▒▓▒▓▒▓█████╔╝▒▓███████║▓█████╗▒▓▒▓▒▓██║▓▒▓▒▓▒▓║");
        System.out.println("║▒▓██╔══██║▒██║═══╝▒██╔══██╗▓██║▒▓██║▒▓██║▓▒▓▒▓▒██╔══██╗▓██╔══██║▒██╔══╝▓▒▓▒▓▒██║▒▓▒▓▒▓▒║");
        System.out.println("║▓▒██║▒▓██║▓██████╗▓██║▓▒██║▒╚█████╔╝▓▒╚██████╗▓██║▓▒██║▒██║▒▓██║▓██║▓▒▓▒▓▒▓▒▓██║▓▒▓▒▓▒▓║");
        System.out.println("║▒▓╚═╝▓▒╚═╝▒╚═════╝▒╚═╝▒▓╚═╝▓▒╚════╝▓▒▓▒╚═════╝▒╚═╝▒▓╚═╝▓╚═╝▓▒╚═╝▒╚═╝▒▓▒▓▒▓▒▓▒╚═╝▒▓▒▓▒▓▒║");
        System.out.println("║▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓║");
        System.out.println("║▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒ Welcome To Hero-Craft!! ▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒║");
        System.out.println("║▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓▒▓║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
        */Utility.pause();
    }
    private void showMainMenu() {
        System.out.println("\n\n\n╔══════════════════════════════════════╗");
        System.out.println("║                                      ║");
        System.out.println("║              HERO CRAFT              ║");
        System.out.println("║                                      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ [1] » Start Game                     ║");
        System.out.println("║ [2] » Description                    ║");
        System.out.println("║ [3] » Arcade Mode                    ║");
        System.out.println("║ [4] » Quit                           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    private void showDescription() {
    Utility.clearScreen();

    // ASCII ART WITH COLOR + TYPEWRITER
    String logo =
            "██╗  ██╗███████╗██████╗  ██████╗      ██████╗██████╗  █████╗ ███████╗████████╗\n" +
            "██║  ██║██╔════╝██╔══██╗██╔═══██╗    ██╔════╝██╔══██╗██╔══██╗██╔════╝╚══██╔══╝\n" +
            "███████║█████╗  ██████╔╝██║   ██║    ██║     ██████╔╝███████║█████╗     ██║   \n" +
            "██╔══██║██╔══╝  ██╔══██╗██║   ██║    ██║     ██╔══██╗██╔══██║██╔══╝     ██║   \n" +
            "██║  ██║███████╗██║  ██║╚██████╔╝    ╚██████╗██║  ██║██║  ██║██╗        ██║   \n" +
            "╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝      ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝        ╚═╝   \n";

    System.out.println(Utility.CYAN);
    Utility.typeWriter(logo, 1);
    System.out.println(Utility.RESET);

    // TITLE BOX
    System.out.println(Utility.YELLOW + "╔══════════════════════════════════════════════════════════════╗");
    System.out.println("║                      GAME DESCRIPTION                        ║");
    System.out.println("╠══════════════════════════════════════════════════════════════╣" + Utility.RESET);

    Utility.typeWriter(Utility.GREEN + "Welcome to HeroCraft: Legends!", 12);
    System.out.println(Utility.RESET);

    Utility.typeWriter("- A turn-based combat RPG with 12 unique heroes.", 3);
    Utility.typeWriter("- Supports both 1v1 and 3v3 battle modes.", 3);
    Utility.typeWriter("- Faction bonuses grant random HP/MP buffs.", 3);
    Utility.typeWriter("- Includes tarot rounds, status effects, and cooldowns.", 3);
    Utility.typeWriter("- Fully menu-driven and beginner-friendly.", 3);

    System.out.println(Utility.YELLOW + "╠══════════════════════════════════════════════════════════════╣");
    System.out.println("║                         TAROT CARDS                          ║");
    System.out.println("╠══════════════════════════════════════════════════════════════╣" + Utility.RESET);

    Utility.typeWriter(Utility.PURPLE + "Vincent (Silent Gaze)" + Utility.RESET, 3);
    Utility.typeWriter("  » Silenced: Prevents skill use for 1 turn.\n", 2);

    Utility.typeWriter(Utility.PURPLE + "Jharvis (Chaos Jester)" + Utility.RESET, 3);
    Utility.typeWriter("  » Confusion: 51% chance to attack allies for 2 turns.\n", 2);

    Utility.typeWriter(Utility.PURPLE + "Jurick (Injury-Seeker)" + Utility.RESET, 3);
    Utility.typeWriter("  » Vulnerable: Target takes +30% damage for 1 turn.\n", 2);

    Utility.typeWriter(Utility.PURPLE + "Neihl (Night's Herald)" + Utility.RESET, 3);
    Utility.typeWriter("  » Sleep: Target cannot act for 1 turn.\n", 2);

    Utility.typeWriter(Utility.PURPLE + "Ram (Exhausting Force)" + Utility.RESET, 3);
    Utility.typeWriter("  » Weaken: Reduces attack power for 1 turn.\n", 2);


    System.out.println(Utility.YELLOW + "╠══════════════════════════════════════════════════════════════╣");
    System.out.println("║                         FACTION BUFFS                        ║");
    System.out.println("╠══════════════════════════════════════════════════════════════╣" + Utility.RESET);

    Utility.typeWriter("- When 2+ heroes share a faction, they gain bonus HP & MP.", 3);

    System.out.println(Utility.YELLOW + "╠══════════════════════════════════════════════════════════════╣");
    System.out.println("║                          GAME MODES                          ║");
    System.out.println("╠══════════════════════════════════════════════════════════════╣" + Utility.RESET);

    Utility.typeWriter("- 1v1 Mode", 3);
    Utility.typeWriter("- 3v3 Mode", 3);

    System.out.println(Utility.YELLOW + "╠══════════════════════════════════════════════════════════════╣");
    System.out.println("║                         BATTLE MODES                         ║");
    System.out.println("╠══════════════════════════════════════════════════════════════╣" + Utility.RESET);

    Utility.typeWriter("- PvP  (Player vs Player)", 3);
    Utility.typeWriter("- PvAI (Player vs AI)", 3);

    System.out.println(Utility.YELLOW + "╚══════════════════════════════════════════════════════════════╝" + Utility.RESET);
    Utility.typeWriter(Utility.RED + "Bleeding" + Utility.RESET, 3);
    Utility.typeWriter("  » Inflicts 10 damage per turn for 2 turns.\n", 2);

    Utility.typeWriter(Utility.GREEN + "Poisoned" + Utility.RESET, 3);
    Utility.typeWriter("  » Inflicts 5 damage per turn for 2 turns.", 2);
    Utility.typeWriter("  » Healing received is reduced by 40%.\n", 2);

    Utility.typeWriter(Utility.CYAN + "Soaked" + Utility.RESET, 3);
    Utility.typeWriter("  » Greatly reduces enemy AGI (Agility).\n", 2);

    Utility.typeWriter(Utility.YELLOW + "Burn" + Utility.RESET, 3);
    Utility.typeWriter("  » Inflicts 8 damage per turn for 3 turns.\n", 2);

    Utility.typeWriter(Utility.PURPLE + "Taunted" + Utility.RESET, 3);
    Utility.typeWriter("  » Forces the enemy to attack the taunter for 1 turn.\n", 2);
    Utility.pause();
}


    private void startArcadeMode() {
        BaseCharacter selectedCharacter = battleManager.selectSingleHeroForArcade();
        battleManager.startArcadeMode(selectedCharacter);
    }

    private boolean confirmQuit() {

    Utility.clearScreen();

    // Header box
    System.out.println(Utility.YELLOW + "╔══════════════════════════════════════════════════════╗");
    System.out.println("║                 EXIT CONFIRMATION                    ║");
    System.out.println("╚══════════════════════════════════════════════════════╝" + Utility.RESET);

    // Prompt with typewriter
    Utility.typeWriter("Are you sure you want to quit the game? (Y/N)" + Utility.RESET, 8);
    System.out.print("> ");

    String input = Utility.getValidatedString("", "Y", "N");

    if (input.equalsIgnoreCase("Y")) {

        Utility.clearScreen();

        System.out.println(Utility.YELLOW + "╔══════════════════════════════════════════════════════╗");
        System.out.println("║                     FAREWELL HERO                    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝" + Utility.RESET);

        Utility.typeWriter("Thanks for playing HeroCraft: Legends!", 10);
        Utility.typeWriter("Goodbye, hero...\n" + Utility.RESET, 10);

        Utility.delay(600);
        return true;

    } else {

        Utility.clearScreen();

        System.out.println(Utility.YELLOW + "╔══════════════════════════════════════════════════════╗");
        System.out.println("║                 RETURNING TO MENU...                 ║");
        System.out.println("╚══════════════════════════════════════════════════════╝" + Utility.RESET);

        Utility.typeWriter("You decide to continue your journey!" + Utility.RESET, 10);

        Utility.pause();
        return false;
        }
    }
}