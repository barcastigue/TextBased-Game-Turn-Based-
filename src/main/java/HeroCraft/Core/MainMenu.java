/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeroCraft.Core;

/**
 * 
 * @author DELL
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private Scanner sc = new Scanner(System.in);
    private GameManager gameManager = new GameManager();
    private Description description = new Description();

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
                System.out.println("[3] Quit");
                System.out.print("Enter your choice: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1" -> gameManager.startGame();
                    case "2" -> description.show();
                    case "3" -> isRunning = confirmQuit();
                    default -> {
                        System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                        Utility.pause();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type! Please enter a number.");
                sc.nextLine();
                Utility.pause();
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
                Utility.pause();
            }
        }
        System.out.println("Thanks for playing HeroCraft: Legends!");
    }

    private boolean confirmQuit() {
        while (true) {
            System.out.print("Are you sure you want to quit? (Y/N): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
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