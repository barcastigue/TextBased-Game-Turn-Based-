package HeroCraft.Core;

import java.util.Scanner;

public class Utility {
    private static final Scanner sc = new Scanner(System.in);

    /** Pauses the console until the user presses Enter */
    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    /** Clears the console screen (works in most terminals) */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(long ms) {
        try { Thread.sleep(ms); } catch (Exception e) {}
    }

    // Typewriter effect
    public static void typeWriter(String text, long speed) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(speed); } catch (Exception e) {}
        }
        System.out.println();
    }

    // Color codes
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    
    /** Gets a validated string input that matches one of the allowed options */
    public static String getValidatedString(String prompt, String... allowedOptions) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            for (String option : allowedOptions) {
                if (input.equalsIgnoreCase(option)) {
                    return input;
                }
            }
            System.out.println("Invalid input! Allowed options: " + String.join("/", allowedOptions));
        }
    }

    /** Gets a validated integer input within a specified range */
    public static int getValidatedInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Input must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Please enter an integer.");
            }
        }
    }
}