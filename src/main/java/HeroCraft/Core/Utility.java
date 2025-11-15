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

public class Utility {
    private static Scanner sc = new Scanner(System.in);

    public static void pause() {
        try {
            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error during pause: " + e.getMessage());
        }
    }

    public static void clearScreen() {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error clearing screen.");
        }
    }
}
