/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeroCraft.Core;

/**
 * 
 * @author DELL
 */
import java.util.Scanner;

public class GameManager {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMainMenu();
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> startGame();
                case "2" -> showDescription();
                case "3" -> {
                    if (confirmQuit()) running = false;
                }
                default -> System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n========== HERO BATTLE GAME ==========");
        System.out.println("1. Start Game");
        System.out.println("2. Description");
        System.out.println("3. Quit");
        System.out.print("Select an option: ");
    }

    static void startGame() {
        BattleManager battle = new BattleManager();
        int mode = chooseGameMode();      // PvP or PvAI
        int battleType = chooseBattleType(); // 1v1 or 3v3

        if (mode == 1) battle.startPvPBattle(battleType);
        else battle.startPvAIBattle(battleType);

        if (playAgain()) startGame();
    }

    private static int chooseGameMode() {
        while (true) {
            try {
                System.out.println("\n========== Select Game Mode ==========");
                System.out.println("[1] Player vs Player (PvP)");
                System.out.println("[2] Player vs AI (PvAI)");
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1 || choice == 2) return choice;
                System.out.println("Invalid choice! Enter 1 or 2.");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter 1 or 2.");
            }
        }
    }

    private static int chooseBattleType() {
        while (true) {
            try {
                System.out.println("\n========== Select Battle Type ==========");
                System.out.println("1. 1v1");
                System.out.println("2. 3v3");
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) return 1;
                else if (choice == 2) return 3;
                System.out.println("Invalid choice! Enter 1 or 2.");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter 1 or 2.");
            }
        }
    }

    private static void showDescription() {
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

    private static boolean confirmQuit() {
        while (true) {
            try {
                System.out.print("Do you really want to quit? (Y/N): ");
                String input = sc.nextLine().toUpperCase();
                if (input.equals("Y")) return true;
                else if (input.equals("N")) return false;
                else System.out.println("Invalid input! Enter Y or N.");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter Y or N.");
            }
        }
    }

    private static boolean playAgain() {
        while (true) {
            try {
                System.out.print("\nDo you want to play again? (Y/N): ");
                String input = sc.nextLine().toUpperCase();
                if (input.equals("Y")) return true;
                else if (input.equals("N")) return false;
                else System.out.println("Invalid input! Enter Y or N.");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter Y or N.");
            }
        }
    }
}