package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;

public class MainMenu {
    private final BattleManager battleManager = new BattleManager();

    public void display() {
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
            }
        }
    }

    private void showMainMenu() {
        System.out.println("====================================");
        System.out.println("       Welcome To Hero Craft");
        System.out.println("====================================");
        System.out.println("[1] Start Game");
        System.out.println("[2] Description");
        System.out.println("[3] Arcade Mode");
        System.out.println("[4] Quit");
    }
    

    private void showDescription() {
        System.out.println("\n=== GAME DESCRIPTION ===");
        System.out.println("- Turn-based Hero Battle Game");
        System.out.println("- Playable modes: PvP and PvAI");
        System.out.println("- Battle types: 1v1 and 3v3");
        System.out.println("- Factions: Human, Demi-Human, Mystic (2+ heroes in same faction gain buffs)");
        System.out.println("- Each hero has a basic attack and status effects");
        System.out.println("- Tarot Card phase triggers every 3 rounds (affects enemies)");
        System.out.println("- Objective: Defeat all enemy heroes!\n");
        Utility.pause();
    }

    private void startArcadeMode() {
        BaseCharacter selectedCharacter = battleManager.selectSingleHeroForArcade();
        battleManager.startArcadeMode(selectedCharacter);
    }

    private boolean confirmQuit() {
        String input = Utility.getValidatedString("Are you sure you want to quit? (Y/N): ", "Y", "N");
        if (input.equalsIgnoreCase("Y")) {
            System.out.println("Thanks for playing HeroCraft: Legends!");
            System.out.println("Goodbye, hero!");
            return true;
        } else {
            System.out.println("Returning to main menu...");
            Utility.pause();
            return false;
        }
    }
}