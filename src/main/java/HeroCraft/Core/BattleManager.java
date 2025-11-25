package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;
import HeroCraft.Character.Human.*;
import HeroCraft.Character.DemiHuman.*;
import HeroCraft.Character.Mystic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class BattleManager {
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    void startBattle(ArrayList<BaseCharacter> team1, ArrayList<BaseCharacter> team2) {

    }

    private static class TarotCard {
        String name;
        String description;
        String effectType;
        int duration;
        TarotCard(String name, String desc, String effectType, int dur) { this.name=name; this.description=desc; this.effectType=effectType; this.duration=dur;}
    }

    private List<TarotCard> tarotDeck = Arrays.asList(
        new TarotCard("Vincent (Silent Gaze)", "Silence: Prevents abilities for 1 turn.", "Silence", 1),
        new TarotCard("Jharvis (Chaos Jester)", "Confusion: 51% chance to attack allies for 2 turns.", "Confusion", 2),
        new TarotCard("Jurick (Injury-Seeker)", "Vulnerable: +30% damage taken for 1 turn.", "Vulnerable", 1),
        new TarotCard("Neihl (Night's Herald)", "Sleep: Skip 1 turn.", "Sleep", 1),
        new TarotCard("Ram (Exhausting Force)", "Weaken: -30% damage for 1 turn.", "Weaken", 1)
    );

    // ---------- Start battles ----------
    public void startPvPBattle(int mode) {
        ArrayList<BaseCharacter> p1 = new ArrayList<>();
        ArrayList<BaseCharacter> p2 = new ArrayList<>();
        selectHeroes(p1, "Player 1", mode);
        selectHeroes(p2, "Player 2", mode);
        applyFactionBuffs(p1); applyFactionBuffs(p2);
        System.out.println("\n");
        System.out.println("████████████████████████████████");
        System.out.println("██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██");
        System.out.println("██▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓██");
        System.out.println("██▓▒▒   PvP Battle Start!   ▒▒▓█");
        System.out.println("██▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓██");
        System.out.println("██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██");
        System.out.println("████████████████████████████████");
        System.out.println("\n");

        battleLoop(p1,p2,mode,true);
    }

    public void startPvAIBattle(int mode) {
        ArrayList<BaseCharacter> player = new ArrayList<>();
        ArrayList<BaseCharacter> ai = new ArrayList<>();
        selectHeroes(player, "Player", mode);
        selectHeroesAI(ai, mode);
        applyFactionBuffs(player); applyFactionBuffs(ai);
        System.out.println("\n PvAI Battle Start!");
        battleLoop(player,ai,mode,false);
    }

    // ---------- Hero selection ----------
    private void selectHeroes(ArrayList<BaseCharacter> team, String owner, int mode) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            " + owner + " Hero Selection           ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        ArrayList<BaseCharacter> available = getAllHeroes();
        for (int i = 0; i < mode; i++) {
            // Header for available heroes
            System.out.println("\n╔═════════════════════════════════════════════╗");
            System.out.println("║                Available Heroes              ║");
            System.out.println("╠════╦══════════════════╦════════════╦════════╣");
            System.out.printf("║ %-2s │ %-16s │ %-10s │ %-6s ║\n", "No", "Hero Name", "Faction", "HP/MP");
            System.out.println("╠════╬══════════════════╬════════════╬════════╣");

            // List heroes
            for (int j = 0; j < available.size(); j++) {
                BaseCharacter h = available.get(j);
                System.out.printf("║ %-2d │ %-16s │ %-10s │ %3d/%-3d║\n",
                    j + 1,
                    h.getName(),
                    h.getFaction(),
                    h.getCurrentHP(),
                    h.getCurrentMP()
                );
            }
            System.out.println("╚════╩══════════════════╩════════════╩════════╝");

            // Player choice
            int choice = -1;
            while (true) {
                try {
                    System.out.print("Choose hero " + (i + 1) + " (1-" + available.size() + "): ");
                    choice = Integer.parseInt(sc.nextLine()) - 1;
                    if (choice < 0 || choice >= available.size()) throw new Exception();
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Invalid input! Enter a valid hero number.");
                }
            }

            BaseCharacter pickedHero = cloneHero(available.get(choice));
            team.add(pickedHero);
            available.remove(choice);

            // Confirm selection
            System.out.println("\n║ Hero " + (i + 1) + " Selected: " + pickedHero.getName() +
                               " [" + pickedHero.getFaction() + "] " +
                               "HP:" + pickedHero.getCurrentHP() + "/" + pickedHero.getMaxHP() +
                               " | MP:" + pickedHero.getCurrentMP() + "/" + pickedHero.getMaxMP() + " ║");
            Utility.pause();
        }

        // Final team summary
        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║              " + owner + " Team Finalized!       ║");
        System.out.println("╠════╦══════════════════╦════════════╦════════╣");
        System.out.printf("║ %-2s │ %-16s │ %-10s │ %-6s ║\n", "No", "Hero Name", "Faction", "HP/MP");
        System.out.println("╠════╬══════════════════╬════════════╬════════╣");
        for (int i = 0; i < team.size(); i++) {
            BaseCharacter h = team.get(i);
            System.out.printf("║ %-2d │ %-16s │ %-10s │ %3d/%-3d║\n",
                i + 1,
                h.getName(),
                h.getFaction(),
                h.getCurrentHP(),
                h.getCurrentMP()
            );
        }
        System.out.println("╚════╩══════════════════╩════════════╩════════╝");
    }

    private void selectHeroesAI(ArrayList<BaseCharacter> team, int mode) {
        ArrayList<BaseCharacter> available = getAllHeroes();

        // Flat, clean header
        System.out.println("\n╔════════ AI HERO SELECTION ════════╗\n");

        for (int i = 0; i < mode; i++) {
            // AI thinking animation
            System.out.print("AI selecting");
            for (int j = 0; j < 3; j++) {
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
                System.out.print(".");
            }
            System.out.println();

            // Pick hero
            int ch = rand.nextInt(available.size());
            BaseCharacter pick = cloneHero(available.get(ch));
            team.add(pick);
            available.remove(ch);

            // Announce selection
            System.out.println(" » " + pick.getName() + " [" + pick.getFaction() + "] « \n");
            try { Thread.sleep(400); } catch (InterruptedException ignored) {}
        }

        // Footer
        System.out.println("╚════════ AI FINISHED ══════════════╝\n");

        Utility.pause();
    }
    
    BaseCharacter selectSingleHeroForArcade() {
    ArrayList<BaseCharacter> available = getAllHeroes();

    // Header
    System.out.println("\n╔═════════════════════════════════════════════╗");
    System.out.println("║              Available Heroes               ║");
    System.out.println("╠════╦══════════════════╦════════════╦════════╣");
    System.out.printf("║ %-2s │ %-16s │ %-10s │ %-6s ║\n", "No", "Hero Name", "Faction", "HP/MP");
    System.out.println("╠════╬══════════════════╬════════════╬════════╣");

    // List heroes
    for (int i = 0; i < available.size(); i++) {
        BaseCharacter h = available.get(i);
        System.out.printf("║ %-2d │ %-16s │ %-10s │ %3d/%-3d║\n",
            i + 1,
            h.getName(),
            h.getFaction(),
            h.getCurrentHP(),
            h.getCurrentMP()
        );
    }
    System.out.println("╚════╩══════════════════╩════════════╩════════╝");

    // Player selection
    int idx = -1;
    while (true) {
        try {
            System.out.print(" » ");
            idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx < 0 || idx >= available.size()) throw new Exception();
            break;
        } catch (Exception e) {
            System.out.println(" Invalid input! Enter a valid hero number.");
        }
    }

    BaseCharacter pickedHero = cloneHero(available.get(idx));

    // Confirmation
    System.out.println("\n║ Hero Selected: " + pickedHero.getName() +
                       " [" + pickedHero.getFaction() + "] " +
                       "HP:" + pickedHero.getCurrentHP() + "/" + pickedHero.getMaxHP() +
                       " | MP:" + pickedHero.getCurrentMP() + "/" + pickedHero.getMaxMP() + " ║");
    Utility.pause();

    return pickedHero;
}

    private BaseCharacter cloneHero(BaseCharacter hero) {
        switch (hero.getName()) {
            case "Swordsman": return new Swordsman();
            case "Assassin": return new Assassin();
            case "Priest": return new Priest();
            case "Monk": return new Monk();
            case "Archer": return new Archer();
            case "Gunner": return new Gunner();
            case "Minotaur": return new Minotaur();
            case "Werewolf": return new Werewolf();
            case "Summoner": return new Summoner();
            case "Elementalist": return new Elementalist();
            case "Druid": return new Druid();
            case "Kitsune": return new Kitsune();
            default: return null;
        }
    }

    private ArrayList<BaseCharacter> getAllHeroes() {
        return new ArrayList<>(Arrays.asList(
            new Swordsman(), new Assassin(), new Priest(), new Monk(),
            new Archer(), new Gunner(), new Minotaur(), new Werewolf(),
            new Summoner(), new Elementalist(), new Druid(), new Kitsune()
        ));
    }

    // ---------- Battle loop ----------
    private void battleLoop(ArrayList<BaseCharacter> team1, ArrayList<BaseCharacter> team2, int mode, boolean isPvP) {
        boolean over=false;
        int round=1;
        while (!over) {
            Utility.clearScreen();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓║");
            System.out.println("║▓░░░░░░░░░░░░« ROUND "+ round +" »░░░░░░░░░░░░░▓║");
            System.out.println("║▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓║");
            System.out.println("╚══════════════════════════════════════╝");
            // tarot phase every 3 rounds
            if (round % 3 == 0) {
                System.out.println("\n Tarot Phase!");
                tarotPhase(team1, team2, isPvP);
            }

            // Team1 turn sequence
            for (BaseCharacter h : team1) {
                if (!h.isAlive()) continue;
                // At start of hero turn check statuses that skip turns
                if (h.hasStatus("Sleep")) {
                    System.out.println(h.getName()+" is asleep and skips the turn.");
                    h.reduceStatusDuration();
                    h.reduceCooldowns();
                    continue;
                }
                // Player or AI controlled
                if (isPvP || (!isPvP && team1==team2)) {// comparing identical expressions error/ need to debug
                    // if the team1 belongs to Player in PvAI, player controls; in PvP both are player-controlled
                    playerTurn(h, team2, mode, isPvP ? "Player 1" : "Player");
                } else {
                    aiTurn(h, team2, mode);
                }
                // post-turn handle
                h.reduceStatusDuration();
                h.reduceCooldowns();
                if (isTeamDefeated(team2)) { System.out.println(isPvP ? "Player 1 Wins!" : "Player Wins!"); over=true; break;}
            }
            if (over) break;

            // Team2 turn
            for (BaseCharacter h : team2) {
                if (!h.isAlive()) continue;
                if (h.hasStatus("Sleep")) {
                    System.out.println(h.getName()+" is asleep and skips the turn.");
                    h.reduceStatusDuration();
                    h.reduceCooldowns();
                    continue;
                }
                if (isPvP) playerTurn(h, team1, mode, "Player 2");
                else aiTurn(h, team1, mode);
                h.reduceStatusDuration();
                h.reduceCooldowns();
                if (isTeamDefeated(team1)) { System.out.println(isPvP ? "Player 2 Wins!" : "AI Wins!"); over=true; break;}
            }

            round++;
            Utility.pause();
        }
        System.out.println("\nBattle Over!");
        Utility.pause();
    }

    // ---------- Player Turn (with skill menu) ----------
    private void playerTurn(BaseCharacter hero, ArrayList<BaseCharacter> enemies, int mode, String playerName) {
        if (!hero.isAlive()) return;

        // Check if hero is asleep (skip turn)
        if (hero.hasStatus("Sleep")) {
            System.out.println(hero.getName() + " is asleep and cannot act this turn.");
            hero.reduceStatusDuration();
            hero.reduceCooldowns();
            return;
        }

        System.out.println("\n" + playerName + "'s " + hero.getName() +
                " turn (HP: " + hero.getCurrentHP() + "/" + hero.getMaxHP() +
                " | MP: " + hero.getCurrentMP() + "/" + hero.getMaxMP() + ")");

        // choose target if 3v3, otherwise 1v1 auto-target
        BaseCharacter target = null;
        if (mode == 1) {
            for (BaseCharacter e : enemies) {
                if (e.isAlive()) { target = e; break; }
            }
            if (target == null) return;
        } else {
            displayTeam(enemies, "Enemies");
            int idx = -1;
            while (true) {
                try {
                    System.out.print("Select target number: ");
                    idx = Integer.parseInt(sc.nextLine()) - 1;
                    if (idx < 0 || idx >= enemies.size() || !enemies.get(idx).isAlive()) throw new Exception();
                    break;
                } catch (Exception e) { System.out.println("Invalid selection."); }
            }
            target = enemies.get(idx);
        }

        // ---------- Skill selection ----------
        int sel = -1;
        while (true) {
            System.out.println("\nChoose action:");
            for (int i = 1; i <= 3; i++) {
                String skillName = hero.getSkillName(i);
                int mpCost = hero.getSkillMPCost(i);
                int cd = hero.getSkillCooldown(i);
                if (hero.hasStatus("Silence")) {
                    System.out.println(i + ". " + skillName + " (Silenced!)");
                } else {
                    System.out.println(i + ". " + skillName + " (MP: " + mpCost + ", CD: " + cd + ")");
                }
            }
            System.out.println("4. Basic Attack");

            try {
                System.out.print("Enter 1-4: ");
                sel = Integer.parseInt(sc.nextLine());
                if (sel < 1 || sel > 4) throw new Exception();

                // If silenced, skills cannot be used
                if (hero.hasStatus("Silence") && sel >= 1 && sel <= 3) {
                    System.out.println(hero.getName() + " is silenced and cannot use skills! Choose Basic Attack.");
                    continue;
                }

                if (sel == 4) break; // Basic attack always usable

                int mpCost = hero.getSkillMPCost(sel);
                int cd = hero.getSkillCooldown(sel);

                if (cd > 0) {
                    System.out.println("Skill is on cooldown. Choose another action.");
                    continue;
                }
                if (!hero.hasMP(mpCost)) {
                    System.out.println("Not enough MP to cast this skill. Choose another action.");
                    continue;
                }
                break; // valid skill
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        // ---------- Execute action ----------
        if (sel == 4) {
            // Basic attack
            if (hero.hasStatus("Confusion") && rand.nextInt(100) < 51) {
                ArrayList<BaseCharacter> alive = new ArrayList<>();
                for (BaseCharacter e : enemies) if (e.isAlive()) alive.add(e);
                BaseCharacter alt = alive.get(rand.nextInt(alive.size()));
                System.out.println(hero.getName() + " is confused and hits " + alt.getName() + " instead!");
                hero.basicAttack(alt);
            } else {
                hero.basicAttack(target);
            }
        } else {
            hero.useSkill(sel - 1, target, null, enemies);
        }
    }
        // ---------- AI Turn ----------
        private void aiTurn(BaseCharacter enemy, ArrayList<BaseCharacter> playerTeam, int mode) {
            if (!enemy.isAlive()) return;

            // Check for Sleep
            if (enemy.hasStatus("Sleep")) {
                System.out.println("\n" + enemy.getName() + " is asleep and cannot act this turn.");
                enemy.reduceStatusDuration();
                enemy.reduceCooldowns();
                return;
            }

            System.out.println("\n████████████████████████████████████████");
            System.out.println("█▓▒░          «  AI TURN  »         ░▒▓█");
            System.out.println("████████████████████████████████████████");
            System.out.println("╔───────────────────────────────────────");
            System.out.println("║  " + enemy.getName()+"                             ║");
            System.out.println("╠───────────────────────────────────────");
            System.out.println("║  HP " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() 
                    + "   │   MP " + enemy.getCurrentMP() + "/" + enemy.getMaxMP()+"           ║");
            System.out.println("╚───────────────────────────────────────");

            // Choose a target
            ArrayList<BaseCharacter> alive = new ArrayList<>();
            for (BaseCharacter p : playerTeam) if (p.isAlive()) alive.add(p);
            if (alive.isEmpty()) return;
            BaseCharacter target = alive.get(rand.nextInt(alive.size()));

            // ---------- AI Action ----------
            if (enemy.hasStatus("Silence")) {
                // Only basic attack
                System.out.println(enemy.getName() + " is silenced and can only use Basic Attack!");
                if (enemy.hasStatus("Confusion") && rand.nextInt(100) < 51) {
                    BaseCharacter alt = alive.get(rand.nextInt(alive.size()));
                    System.out.println(enemy.getName() + " is confused and attacks " + alt.getName() + " instead!");
                    enemy.basicAttack(alt);
                } else {
                    enemy.basicAttack(target);
                }
            } else {
                // Normal AI skill decision
                List<Integer> usableSkills = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    if (!enemy.skillOnCooldown(i) && enemy.hasMP(enemy.getSkillMPCost(i + 1))) {
                        usableSkills.add(i);
                    }
                }

                int chosenAction;
                if (!usableSkills.isEmpty()) {
                    Collections.shuffle(usableSkills);
                    chosenAction = usableSkills.get(0) + 1; // 1-based index
                } else {
                    chosenAction = 4; // fallback to basic attack
                }

                // ---------- Perform Action ----------
                if (chosenAction == 4) {
                    if (enemy.hasStatus("Confusion") && rand.nextInt(100) < 51) {
                        BaseCharacter alt = alive.get(rand.nextInt(alive.size()));
                        System.out.println(enemy.getName() + " is confused and attacks " + alt.getName() + " instead!");
                        enemy.basicAttack(alt);
                    } else {
                        System.out.println(enemy.getName() + " attacks >>> " + target.getName() + " <<< !");
                        enemy.basicAttack(target);
                    }
                } else {
                    enemy.useSkill(chosenAction - 1, target, null, playerTeam);
                }
            }

        System.out.println("================================================");
        enemy.reduceStatusDuration();
        enemy.reduceCooldowns();
    }
    // ---------- Tarot system ----------
    private void tarotPhase(ArrayList<BaseCharacter> team1, ArrayList<BaseCharacter> team2, boolean isPvP) {
        applyTarotChoice(team1, team2, "Player 1");
        if (isPvP) applyTarotChoice(team2, team1, "Player 2");
        else applyTarotChoiceAI(team2, team1);
    }

    private void applyTarotChoice(ArrayList<BaseCharacter> team, ArrayList<BaseCharacter> enemyTeam, String playerName) {
        System.out.println("\n🎴 " + playerName + ", choose a Tarot Card:");
        List<TarotCard> cards = getRandomTarotCards(3);
        for (int i=0;i<cards.size();i++) {
            System.out.println((i+1)+". "+cards.get(i).name+" — "+cards.get(i).description);
        }
        int c = -1;
        while (true) {
            try {
                System.out.print("Choose (1-3): ");
                c = Integer.parseInt(sc.nextLine()) - 1;
                if (c < 0 || c >= cards.size()) throw new Exception();
                break;
            } catch (Exception e) { System.out.println("Invalid."); }
        }
        TarotCard chosen = cards.get(c);
        System.out.println(playerName+" chose "+chosen.name+"!");
        // let player choose specific enemy target(s)
        ArrayList<BaseCharacter> alive = new ArrayList<>();
        for (BaseCharacter e: enemyTeam) if (e.isAlive()) alive.add(e);
        if (alive.isEmpty()) return;
        // prompt for single target for simplicity
        System.out.println("Select target for the tarot effect:");
        for (int i=0;i<alive.size();i++) System.out.println((i+1)+". "+alive.get(i).getName());
        int t=-1;
        while (true) {
            try {
                System.out.print("Target (1-"+alive.size()+"): ");
                t = Integer.parseInt(sc.nextLine()) - 1;
                if (t < 0 || t >= alive.size()) throw new Exception();
                break;
            } catch (Exception e) { System.out.println("Invalid."); }
        }
        BaseCharacter target = alive.get(t);
        target.applyStatus(chosen.effectType, chosen.duration);
    }

    private void applyTarotChoiceAI(ArrayList<BaseCharacter> aiTeam, ArrayList<BaseCharacter> playerTeam) {
        List<TarotCard> cards = getRandomTarotCards(3);
        TarotCard chosen = cards.get(rand.nextInt(cards.size()));
        System.out.println("\n🤖 AI selects tarot: "+chosen.name);
        ArrayList<BaseCharacter> alive = new ArrayList<>();
        for (BaseCharacter p : playerTeam) if (p.isAlive()) alive.add(p);
        if (alive.isEmpty()) return;
        BaseCharacter t = alive.get(rand.nextInt(alive.size()));
        t.applyStatus(chosen.effectType, chosen.duration);
    }

    private List<TarotCard> getRandomTarotCards(int count) {
        ArrayList<TarotCard> copy = new ArrayList<>(tarotDeck);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }

    // ---------- Utilities ----------
    private void displayTeam(ArrayList<BaseCharacter> team, String title) {
        System.out.println("\n--- " + title + " ---");
        for (int i=0;i<team.size();i++) {
            BaseCharacter h = team.get(i);
            System.out.println((i+1)+". "+h.getName()+" (HP:"+h.getCurrentHP()+"/"+h.getMaxHP()+" | MP:"+h.getCurrentMP()+"/"+h.getMaxMP()+")");
        }
    }

    private boolean isTeamDefeated(ArrayList<BaseCharacter> team) {
        for (BaseCharacter h: team) if (h.isAlive()) return false;
        return true;
    }

    // faction buff (kept from earlier)
    private void applyFactionBuffs(ArrayList<BaseCharacter> team) {
        Map<String, Integer> factionCount = new HashMap<>();
        for (BaseCharacter hero : team) {
            factionCount.put(hero.getFaction(), factionCount.getOrDefault(hero.getFaction(), 0) + 1);
        }

        boolean anyBuff = false;
        for (String fac : factionCount.keySet()) {
            int count = factionCount.get(fac);
            if (count >= 2) {
                anyBuff = true;
                System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
                System.out.println("║           Faction Buff Activated!                                  ║");
                System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                System.out.println("║      Faction: " + fac + " (+" + count + " members)                                   ║");

                for (BaseCharacter hero : team) {
                    if (hero.getFaction().equals(fac)) {
                        int hpBuff = (int)(hero.getMaxHP() * (5 + rand.nextInt(11)) / 100.0);
                        int mpBuff = (int)(hero.getMaxMP() * (5 + rand.nextInt(11)) / 100.0);

                        hero.setCurrentHP(hero.getCurrentHP() + hpBuff);
                        hero.setCurrentMP(hero.getCurrentMP() + mpBuff);

                        // Display hero and buff with a visual bar
                        System.out.println("╠────────────────────────────────────────────────────────────────────╣");
                        System.out.printf("║ %-12s │ HP: %3d/%-3d [%s] │ MP: %3d/%-3d [%s] ║\n",
                            hero.getName(),
                            hero.getCurrentHP(),
                            hero.getMaxHP(),
                            getBar(hero.getCurrentHP(), hero.getMaxHP(), 10, '▓', '░'),
                            hero.getCurrentMP(),
                            hero.getMaxMP(),
                            getBar(hero.getCurrentMP(), hero.getMaxMP(), 10, '█', '▒')
                        );
                        System.out.println("║  Buff: +" + hpBuff + " HP, +" + mpBuff + " MP                                               ║");
                    }
                }
                System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            }
        }

        if (!anyBuff) {
            System.out.println("\n║ No Faction Buffs applied this turn.           ║");
        }
    }

    // Helper method to generate a small visual bar
    private String getBar(int current, int max, int length, char filled, char empty) {
        int filledLength = (int) ((current / (double) max) * length);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filledLength; i++) bar.append(filled);
        for (int i = filledLength; i < length; i++) bar.append(empty);
        return bar.toString();
    }
    public void startArcadeMode(BaseCharacter player) {
        ArrayList<BaseCharacter> enemyTeam = new ArrayList<>();
        int roundCounter = 1;
        int defeatedCount = 0;
        final int totalEnemies = 12;

        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓║");
        System.out.println("║▓░░░░░░░░░░░░« ARCADE MODE START! »░░░░░░░░░▓║");
        System.out.println("║▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓║");
        System.out.println("╚═════════════════════════════════════════════╝");

        while (player.isAlive() && defeatedCount < totalEnemies) {
            enemyTeam.clear();
            BaseCharacter enemy = cloneHero(getAllHeroes().get(rand.nextInt(getAllHeroes().size())));
            enemyTeam.add(enemy);
            player.resetHPMP();

            System.out.println("\n╔─────────────────────────────────────╗");
            System.out.println("║    «   NEW ENEMY APPROACHES!   »    ║");
            System.out.println("╚─────────────────────────────────────╝");
            System.out.println("   » " + enemy.getName());
            System.out.println("   » [" + enemy.getFaction() + "]");

            System.out.println("\n░▒▓█ « ROUND " + roundCounter + " » █▓▒░");
            Utility.pause();

            battleLoopArcade(player, enemyTeam);

            if (player.isAlive()) {
                defeatedCount++;
                roundCounter++;
                System.out.println("\n╔─────────────────────────────╗");
                System.out.println("║   «   ENEMY DEFEATED!   »   ║");
                System.out.println("╚─────────────────────────────╝");
                System.out.println("   Total Wins: " + defeatedCount + " / " + totalEnemies);
                if (defeatedCount == totalEnemies) {
                    System.out.println("\n████████████████████████████████████████████████████████████████████████████");
                    System.out.println("█   «   Congratulations ! You've cleared Arcade Mode! Grand Victory!    »   █");
                    System.out.println("█████████████████████████████████████████████████████████████████████████████");

                    break;  // Exit loop since player cleared all enemies
                } else {
                    System.out.println("\n░▒▓ « Here comes, round " + roundCounter + "! » ▓▒░");
                    Utility.pause();
                }
            }
        }

        if (!player.isAlive()) {
            System.out.println("\n╔─────────────────────────────╗");
            System.out.println("║      «  GAME OVER  »        ║");
            System.out.println("╠─────────────────────────────╣");
            System.out.println("║      Wins: " + defeatedCount + " / " + totalEnemies + "             ║");
            System.out.println("╚─────────────────────────────╝");
        }

        Utility.pause();
    }


        private void battleLoopArcade(BaseCharacter player, ArrayList<BaseCharacter> enemyTeam) {
        boolean over = false;
        while (!over) {
            Utility.clearScreen();
            System.out.println("\nPlayer HP: " + player.getCurrentHP() + "/" + player.getMaxHP() + 
                               " | MP: " + player.getCurrentMP() + "/" + player.getMaxMP());
            ArrayList<BaseCharacter> aliveEnemies = new ArrayList<>();
            for (BaseCharacter e : enemyTeam) if (e.isAlive()) aliveEnemies.add(e);
            if (aliveEnemies.isEmpty()) { over = true; break; }
            playerTurn(player, enemyTeam, 1, "Player");
            player.reduceStatusDuration();
            player.reduceCooldowns();
            for (BaseCharacter e : enemyTeam) {
                if (!e.isAlive()) continue;
                aiTurn(e, new ArrayList<>(Arrays.asList(player)), 1);
                e.reduceStatusDuration();
                e.reduceCooldowns();
            }
            if (!player.isAlive()) { over = true; break; }
        }
    }
}