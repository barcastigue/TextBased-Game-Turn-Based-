package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private Scanner sc = new Scanner(System.in);
    private BattleManager battleManager = new BattleManager();

    public static void main(String[] args) {
        new MainMenu().display();
    }

    public void display() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                Utility.clearScreen();
                System.out.println("====================================");
                System.out.println("        Welcome To Hero Craft");
                System.out.println("====================================");
                System.out.println("[1] Start Game");
                System.out.println("[2] Description");
                System.out.println("[3] Arcade Mode");
                System.out.println("[4] Quit");
                System.out.print("Enter your choice: ");
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> GameManager.startGame();
                    case "2" -> showDescription();
                    case "3" -> {
                        BaseCharacter selectedCharacter = battleManager.selectSingleHeroForArcade();
                        battleManager.startArcadeMode(selectedCharacter);
                    }
                    case "4" -> isRunning = !confirmQuit();
                    default -> {
                        System.out.println("Invalid choice! Please enter 1, 2, 3, or 4.");
                        Utility.pause();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type! Please enter a valid option.");
                sc.nextLine();
                Utility.pause();
            }
        }
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

    private boolean confirmQuit() {
        while (true) {
            System.out.print("Are you sure you want to quit? (Y/N): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                System.out.println("Thanks for playing HeroCraft: Legends!");
                System.out.println("Goodbye, hero!");
                return true;
            } else if (input.equals("n")) {
                System.out.println("Returning to main menu...");
                Utility.pause();
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
}