package Draftv2;

import java.util.Scanner;

public class Game {
    private final Scanner sc = new Scanner(System.in);
    private final Hero[] allHeroes;

    public Game() {
        allHeroes = new Hero[]{
            new Hero("Swordsman", "Human"),
            new Hero("Assassin", "Human"),
            new Hero("Priest", "Human"),
            new Hero("To be created by Neihl", "Human"),
            new Hero("Archer", "Demi-Human"),
            new Hero("Gunner", "Demi-Human"),
            new Hero("To be created by Jurick", "Demi-Human"),
            new Hero("To be created by Ram", "Demi-Human"),
            new Hero("Summoner", "Mystic"),
            new Hero("Elementalist", "Mystic"),
            new Hero("Druid", "Mystic"),
            new Hero("Kitsune", "Mystic")
        };
    }

    public void startGame() {
        int mode = chooseBattleMode();

        if (mode == 1) {
            start1v1();
        } else {
            start3v3();
        }
    }

    private int chooseBattleMode() {
        int mode = 0;
        boolean isEnable = true;

        System.out.println("=== HERO CRAFT ===");
        System.out.println("Select Battle Mode:");
        System.out.println("1. 1v1");
        System.out.println("2. 3v3");

        while (isEnable) {
            try {
                System.out.print("Enter choice: ");
                mode = sc.nextInt();
                if (mode == 1 || mode == 2) {
                    isEnable = false;
                } else {
                    System.out.println("Invalid number! Pick 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                sc.next(); // clear invalid input
            }
        }

        return mode;
    }

    private void start1v1() {
        System.out.println("\n--- 1v1 Battle ---");
        Hero player1 = pickHero("Player 1");
        Hero player2 = pickHero("Player 2");

        System.out.println("\n--- Heroes Ready ---");
        System.out.println("Player 1 chose: " + player1.getName());
        System.out.println("Player 2 chose: " + player2.getName());
    }

    private void start3v3() {
        System.out.println("\n--- 3v3 Battle ---");
        Hero[] team1 = pickTeam("Team 1", 3);
        Hero[] team2 = pickTeam("Team 2", 3);

        System.out.println("\n--- Teams Ready ---");
        displayTeam("Team 1", team1);
        displayTeam("Team 2", team2);
    }

    private Hero pickHero(String playerName) {
        int factionChoice = chooseFaction(playerName, 1);
        Hero[] factionHeroes = getHeroesByFaction(factionChoice);

        displayAvailableHeroes(factionHeroes);
        int heroChoice = chooseHero(playerName, 1, factionHeroes.length);

        return factionHeroes[heroChoice - 1];
    }

    private Hero[] pickTeam(String teamName, int size) {
        Hero[] team = new Hero[size];

        for (int i = 0; i < size; i++) {
            int factionChoice = chooseFaction(teamName, i + 1);
            Hero[] factionHeroes = getHeroesByFaction(factionChoice);

            displayAvailableHeroes(factionHeroes);
            int heroChoice = chooseHero(teamName, i + 1, factionHeroes.length);

            team[i] = factionHeroes[heroChoice - 1];
        }

        return team;
    }

    private int chooseFaction(String teamName, int num) {
        int choice = 0;
        boolean isEnable = true;

        while (isEnable) {
            try {
                System.out.println("\nChoose a Faction:");
                System.out.println("1. Human");
                System.out.println("2. Demi-Human");
                System.out.println("3. Mystic");
                System.out.print(teamName + ", pick faction #" + num + ": ");
                choice = sc.nextInt();

                if (choice >= 1 && choice <= 3) {
                    isEnable = false;
                } else {
                    System.out.println("Enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
            }
        }

        return choice;
    }

    private Hero[] getHeroesByFaction(int factionChoice) {
        String faction = "";
        if (factionChoice == 1) {
            faction = "Human";
        } else if (factionChoice == 2) {
            faction = "Demi-Human";
        } else if (factionChoice == 3) {
            faction = "Mystic";
        }

        int count = 0;
        for (int i = 0; i < allHeroes.length; i++) {
            if (allHeroes[i].getFaction().equals(faction)) {
                count++;
            }
        }

        Hero[] heroes = new Hero[count];
        int index = 0;
        for (int i = 0; i < allHeroes.length; i++) {
            if (allHeroes[i].getFaction().equals(faction)) {
                heroes[index] = allHeroes[i];
                index++;
            }
        }

        return heroes;
    }

    private void displayAvailableHeroes(Hero[] heroes) {
        System.out.println("\nAvailable Heroes:");
        for (int i = 0; i < heroes.length; i++) {
            System.out.println((i + 1) + ". " + heroes[i].getName());
        }
    }

    private int chooseHero(String teamName, int num, int max) {
        int choice = 0;
        boolean isEnable = true;

        while (isEnable) {
            try {
                System.out.print(teamName + ", pick hero #" + num + ": ");
                choice = sc.nextInt();

                if (choice >= 1 && choice <= max) {
                    isEnable = false;
                } else {
                    System.out.println("Enter a valid number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
            }
        }

        return choice;
    }

    private void displayTeam(String teamName, Hero[] team) {
        System.out.print(teamName + " chose: ");
        for (int i = 0; i < team.length; i++) {
            System.out.print(team[i].getName());
            if (i < team.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
