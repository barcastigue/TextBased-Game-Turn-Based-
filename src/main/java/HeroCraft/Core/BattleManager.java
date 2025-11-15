/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeroCraft.Core;

/**
 * 
 * @author DELL
 */
import HeroCraft.Character.BaseCharacter;
import HeroCraft.Character.Human.*;
import HeroCraft.Character.DemiHuman.*;
import HeroCraft.Character.Mystic.*;

import java.util.*;

public class BattleManager {
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    // -------------------- Tarot Card --------------------
    private static class TarotCard {
        String name;
        String description;
        String effectType;
        int duration;

        TarotCard(String name, String description, String effectType, int duration) {
            this.name = name;
            this.description = description;
            this.effectType = effectType;
            this.duration = duration;
        }
    }

    private List<TarotCard> tarotDeck = Arrays.asList(
        new TarotCard("Vincent (Silent Gaze)", "Silence: Enemies cannot use abilities for 1 turn.", "Silence", 1),
        new TarotCard("Jharvis (Chaos Jester)", "Confusion: 51% chance enemies attack allies for 2 turns.", "Confusion", 2),
        new TarotCard("Jurick (Injury-Seeker)", "Vulnerable: Enemies take 30% more damage for 1 turn.", "Vulnerable", 1),
        new TarotCard("Neihl (Night's Herald)", "Sleep: Enemies skip 1 turn.", "Sleep", 1),
        new TarotCard("Ram (Exhausting Force)", "Weaken: Enemies deal 30% less damage for 1 turn.", "Weaken", 1)
    );

    // -------------------- Battle Start --------------------
    public void startPvPBattle(int mode) {
        ArrayList<BaseCharacter> player1Team = new ArrayList<>();
        ArrayList<BaseCharacter> player2Team = new ArrayList<>();

        selectHeroes(player1Team, "Player 1", mode);
        selectHeroes(player2Team, "Player 2", mode);

        applyFactionBuffs(player1Team);
        applyFactionBuffs(player2Team);

        System.out.println("\n️ PvP Battle Start!");
        battleLoop(player1Team, player2Team, mode, true);
    }

    public void startPvAIBattle(int mode) {
        ArrayList<BaseCharacter> playerTeam = new ArrayList<>();
        ArrayList<BaseCharacter> aiTeam = new ArrayList<>();

        selectHeroes(playerTeam, "Player", mode);
        selectHeroesAI(aiTeam, mode);

        applyFactionBuffs(playerTeam);
        applyFactionBuffs(aiTeam);

        System.out.println("\n PvAI Battle Start!");
        battleLoop(playerTeam, aiTeam, mode, false);
    }

    // -------------------- AI Hero Selection --------------------
    private void selectHeroesAI(ArrayList<BaseCharacter> team, int mode) {
        ArrayList<BaseCharacter> availableHeroes = getAllHeroes();
        System.out.println("\n--- AI is choosing its heroes ---");
        for (int i = 0; i < mode; i++) {
            try { Thread.sleep(700); } catch (InterruptedException e) {}
            int choice = rand.nextInt(availableHeroes.size());
            BaseCharacter selected = cloneHero(availableHeroes.get(choice));
            team.add(selected);
            availableHeroes.remove(choice);
            System.out.println(" AI selects: >>> " + selected.getName() + " <<< [" + selected.getFaction() + "]");
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
        Utility.pause();
    }

    private void aiTurn(BaseCharacter enemy, ArrayList<BaseCharacter> playerTeam, int mode) {
        enemy.reduceStatusDuration();

        if (!enemy.isAlive()) return;
        if (enemy.hasStatus("Sleep")) {
            System.out.println(enemy.getName() + " is sleeping and skips the turn!");
            return;
        }

        ArrayList<BaseCharacter> aliveHeroes = new ArrayList<>();
        for (BaseCharacter h : playerTeam) if (h.isAlive()) aliveHeroes.add(h);
        if (aliveHeroes.isEmpty()) return;

        BaseCharacter target;

        if (enemy.hasStatus("Confusion") && rand.nextInt(100) < 51) {
            target = playerTeam.get(rand.nextInt(playerTeam.size()));
            System.out.println(enemy.getName() + " is confused and attacks an ally!");
        } else {
            target = aliveHeroes.get(rand.nextInt(aliveHeroes.size()));
        }
        System.out.println("\n==================== AI TURN ====================");
        System.out.println(enemy.getName() + " attacks >>> " + target.getName() + " <<< !");
        enemy.basicAttack(target);
        System.out.println("================================================");
    }

    private void battleLoop(ArrayList<BaseCharacter> team1, ArrayList<BaseCharacter> team2, int mode, boolean isPvP) {
        boolean battleOver = false;
        int roundCounter = 1;

        while (!battleOver) {
            Utility.clearScreen();
            System.out.println("\n==========  ROUND " + roundCounter + "  ==========");

            // Tarot card phase every 3 rounds
            if (roundCounter % 3 == 0) {
                System.out.println("\n Tarot Card Phase Begins!");
                tarotPhase(team1, team2, isPvP);
            }

            for (BaseCharacter hero : team1) {
                hero.reduceStatusDuration();
                if (!hero.isAlive() || hero.hasStatus("Sleep")) continue;
                playerTurn(hero, team2, mode, isPvP ? "Player 1" : "Player");
                if (isTeamDefeated(team2)) {
                    System.out.println(isPvP ? "\n Player 1 Wins!" : "\n Player Wins!");
                    battleOver = true;
                    break;
                }
            }
            if (battleOver) break;

            // Team 2 turn
            for (BaseCharacter hero : team2) {
                hero.reduceStatusDuration();
                if (!hero.isAlive()) continue;
                if (isPvP) playerTurn(hero, team1, mode, "Player 2");
                else aiTurn(hero, team1, mode);
                if (isTeamDefeated(team1)) {
                    System.out.println(isPvP ? "\n Player 2 Wins!" : "\n AI Wins!");
                    battleOver = true;
                    break;
                }
            }
            roundCounter++;
            Utility.pause();
        }
        System.out.println("\nBattle Over!");
        Utility.pause();
    }

    private void tarotPhase(ArrayList<BaseCharacter> team1, ArrayList<BaseCharacter> team2, boolean isPvP) {
        applyTarotChoice(team1, team2, "Player 1");
        if (isPvP) applyTarotChoice(team2, team1, "Player 2");
        else applyTarotChoiceAI(team2, team1);
    }

    private void applyTarotChoice(ArrayList<BaseCharacter> team, ArrayList<BaseCharacter> enemyTeam, String playerName) {
        System.out.println("\n " + playerName + ", choose a Tarot Card:");
        List<TarotCard> cards = getRandomTarotCards(3);

        for (int i = 0; i < cards.size(); i++) {
            TarotCard c = cards.get(i);
            System.out.println((i + 1) + ". " + c.name + " — " + c.description);
        }

        int choice = -1;
        while (true) {
            try {
                System.out.print("Choose (1-3): ");
                choice = Integer.parseInt(sc.nextLine()) - 1;
                if (choice < 0 || choice >= cards.size()) continue;
                break;
            } catch (Exception e) {}
        }

        TarotCard chosen = cards.get(choice);
        System.out.println("\n " + playerName + " chose " + chosen.name + "!");
        for (BaseCharacter enemy : enemyTeam) {
            enemy.applyStatus(chosen.effectType, chosen.duration);
        }
    }

    private void applyTarotChoiceAI(ArrayList<BaseCharacter> aiTeam, ArrayList<BaseCharacter> playerTeam) {
        List<TarotCard> cards = getRandomTarotCards(3);
        TarotCard chosen = cards.get(rand.nextInt(3));

        System.out.println("\n AI draws tarot cards...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("AI selects " + chosen.name + " — " + chosen.description);

        for (BaseCharacter player : playerTeam) {
            player.applyStatus(chosen.effectType, chosen.duration);
        }
    }

    private List<TarotCard> getRandomTarotCards(int count) {
        ArrayList<TarotCard> copy = new ArrayList<>(tarotDeck);
        Collections.shuffle(copy);
        return copy.subList(0, count);
    }
    
    private void selectHeroes(ArrayList<BaseCharacter> team, String owner, int mode) {
        System.out.println("\nSelect heroes for " + owner + " team (" + mode + " heroes):");
        ArrayList<BaseCharacter> availableHeroes = getAllHeroes();

        for (int i = 0; i < mode; i++) {
            System.out.println("\nAvailable Heroes:");
            for (int j = 0; j < availableHeroes.size(); j++) {
                BaseCharacter h = availableHeroes.get(j);
                System.out.println((j + 1) + ". " + h.getName() + " [" + h.getFaction() + "]");
            }

            int choice;
            while (true) {
                try {
                    System.out.print("Choose hero " + (i + 1) + ": ");
                    choice = Integer.parseInt(sc.nextLine()) - 1;
                    if (choice < 0 || choice >= availableHeroes.size()) throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input! Try again.");
                }
            }

            BaseCharacter heroCopy = cloneHero(availableHeroes.get(choice));
            team.add(heroCopy);
            availableHeroes.remove(choice);
        }
    }

    private BaseCharacter cloneHero(BaseCharacter hero) {
        return switch (hero.getName()) {
            case "Swordsman" -> new Swordsman();
            case "Assassin" -> new Assassin();
            case "Priest" -> new Priest();
            case "Monk" -> new Monk();
            case "Archer" -> new Archer();
            case "Gunner" -> new Gunner();
            case "Minotaur" -> new Minotaur();
            case "Werewolf" -> new Werewolf();
            case "Summoner" -> new Summoner();
            case "Elementalist" -> new Elementalist();
            case "Druid" -> new Druid();
            case "Kitsune" -> new Kitsune();
            default -> null;
        };
    }

    private ArrayList<BaseCharacter> getAllHeroes() {
        return new ArrayList<>(Arrays.asList(
            new Swordsman(), new Assassin(), new Priest(), new Monk(),
            new Archer(), new Gunner(), new Minotaur(), new Werewolf(),
            new Summoner(), new Elementalist(), new Druid(), new Kitsune()
        ));
    }

    // faction buffs
    private void applyFactionBuffs(ArrayList<BaseCharacter> team) {
        Map<String, Integer> factionCount = new HashMap<>();
        for (BaseCharacter hero : team)
            factionCount.put(hero.getFaction(), factionCount.getOrDefault(hero.getFaction(), 0) + 1);

        for (String faction : factionCount.keySet()) {
            int count = factionCount.get(faction);
            if (count >= 2) {
                for (BaseCharacter hero : team) {
                    if (hero.getFaction().equals(faction)) {
                        int hpBuff = (int)(hero.getMaxHP() * (5 + rand.nextInt(11)) / 100.0);
                        int mpBuff = (int)(hero.getMaxMP() * (5 + rand.nextInt(11)) / 100.0);
                        hero.setCurrentHP(hero.getCurrentHP() + hpBuff);
                        hero.setCurrentMP(hero.getCurrentMP() + mpBuff);
                        System.out.println(hero.getName() + " receives Faction Buff! +" + hpBuff + " HP, +" + mpBuff + " MP.");
                    }
                }
            }
        }
    }

    private boolean isTeamDefeated(ArrayList<BaseCharacter> team) {
        for (BaseCharacter hero : team) if (hero.isAlive()) return false;
        return true;
    }

    private void playerTurn(BaseCharacter hero, ArrayList<BaseCharacter> enemies, int mode, String playerName) {
        if (!hero.isAlive() || hero.hasStatus("Sleep")) return;

        System.out.println("\n" + playerName + "'s " + hero.getName() + " turn (HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP() + ")");
        BaseCharacter target;

        if (mode == 1) target = enemies.get(0);
        else {
            displayTeam(enemies, "Enemies");
            int targetIndex;
            while (true) {
                try {
                    System.out.print("Select target: ");
                    targetIndex = Integer.parseInt(sc.nextLine()) - 1;
                    if (targetIndex < 0 || targetIndex >= enemies.size() || !enemies.get(targetIndex).isAlive()) throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid target! Try again.");
                }
            }
            target = enemies.get(targetIndex);
        }
        hero.basicAttack(target);
    }

    private void displayTeam(ArrayList<BaseCharacter> team, String title) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < team.size(); i++) {
            BaseCharacter h = team.get(i);
            System.out.println((i + 1) + ". " + h.getName() + " (HP: " + h.getCurrentHP() + "/" + h.getMaxHP() + ")");
        }
    }
}