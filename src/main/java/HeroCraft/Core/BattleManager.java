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
        System.out.println("\n⚔️ PvP Battle Start!");
        battleLoop(p1,p2,mode,true);
    }

    public void startPvAIBattle(int mode) {
        ArrayList<BaseCharacter> player = new ArrayList<>();
        ArrayList<BaseCharacter> ai = new ArrayList<>();
        selectHeroes(player, "Player", mode);
        selectHeroesAI(ai, mode);
        applyFactionBuffs(player); applyFactionBuffs(ai);
        System.out.println("\n🤖 PvAI Battle Start!");
        battleLoop(player,ai,mode,false);
    }

    // ---------- Hero selection ----------
    private void selectHeroes(ArrayList<BaseCharacter> team, String owner, int mode) {
        System.out.println("\nSelect heroes for " + owner + " team (" + mode + " heroes):");
        ArrayList<BaseCharacter> available = getAllHeroes();
        for (int i=0;i<mode;i++) {
            System.out.println("\nAvailable Heroes:");
            for (int j=0;j<available.size();j++) {
                BaseCharacter h = available.get(j);
                System.out.println((j+1)+". "+h.getName()+" ["+h.getFaction()+"] (HP:"+h.getCurrentHP()+"/"+h.getMaxHP()+", MP:"+h.getCurrentMP()+"/"+h.getMaxMP()+")");
            }
            int choice = -1;
            while (true) {
                try {
                    System.out.print("Choose hero " + (i+1) + ": ");
                    choice = Integer.parseInt(sc.nextLine()) - 1;
                    if (choice < 0 || choice >= available.size()) throw new Exception();
                    break;
                } catch (Exception e) { System.out.println("Invalid input!"); }
            }
            team.add(cloneHero(available.get(choice)));
            available.remove(choice);
        }
    }

    private void selectHeroesAI(ArrayList<BaseCharacter> team, int mode) {
        ArrayList<BaseCharacter> available = getAllHeroes();
        System.out.println("\n--- AI is choosing its heroes ---");
        for (int i=0;i<mode;i++) {
            try { Thread.sleep(600); } catch (InterruptedException ignored) {}
            int ch = rand.nextInt(available.size());
            BaseCharacter pick = cloneHero(available.get(ch));
            team.add(pick);
            available.remove(ch);
            System.out.println("🤖 AI selects: " + pick.getName()+" ["+pick.getFaction()+"]");
        }
        Utility.pause();
    }
    
    BaseCharacter selectSingleHeroForArcade() {
    ArrayList<BaseCharacter> available = getAllHeroes();
    for (int i = 0; i < available.size(); i++) {
        BaseCharacter h = available.get(i);
        System.out.println((i + 1) + ". " + h.getName() + " [" + h.getFaction() + "]");
    }
    int idx = -1;
    while (true) {
        try {
            System.out.print("Select your hero: ");
            idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx < 0 || idx >= available.size()) throw new Exception();
            break;
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
    return cloneHero(available.get(idx));
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
            System.out.println("\n========== ROUND " + round + " ==========");
            // tarot phase every 3 rounds
            if (round % 3 == 0) {
                System.out.println("\n💫 Tarot Phase!");
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
                if (isPvP || (!isPvP && team1==team1)) {
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

    int sel = -1;
    while (true) {
        // ---------- Dynamic skill menu ----------
        System.out.println("\nChoose action:");
        for (int i = 1; i <= 3; i++) {
            String skillName = hero.getSkillName(i);
            int mpCost = hero.getSkillMPCost(i);
            int cd = hero.getSkillCooldown(i);
            System.out.println(i + ". " + skillName + " (MP: " + mpCost + ", CD: " + cd + ")");
        }
        System.out.println("4. Basic Attack");

        try {
            System.out.print("Enter 1-4: ");
            sel = Integer.parseInt(sc.nextLine());
            if (sel < 1 || sel > 4) throw new Exception();

            if (sel == 4) break; // Basic attack always usable

            int skillIdx = sel - 1;
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
        int skillIdx = sel - 1;
        hero.useSkill(skillIdx, target, null, enemies);
    }
}

    // ---------- AI Turn ----------
    private void aiTurn(BaseCharacter enemy, ArrayList<BaseCharacter> playerTeam, int mode) {
        if (!enemy.isAlive()) return;
        // sleep check done by battle loop
        // choose target
        ArrayList<BaseCharacter> alive = new ArrayList<>();
        for (BaseCharacter p: playerTeam) if (p.isAlive()) alive.add(p);
        if (alive.isEmpty()) return;

        BaseCharacter target = alive.get(rand.nextInt(alive.size()));

        // Decide action: prefer usable skill (not on CD and has MP) else basic attack.
        int chosenAction = 4; // default basic attack
        // try to pick a skill
        List<Integer> candidates = Arrays.asList(0,1,2);
        Collections.shuffle(candidates);
        for (int si : candidates) {
            try {
                // naive check: see if has MP by trying typical costs (subclasses actually check MP)
                if (!enemy.skillOnCooldown(si)) {
                    chosenAction = si+1;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (chosenAction == 4) {
            // confusion possibility: 51% hit ally
            if (enemy.hasStatus("Confusion") && rand.nextInt(100) < 51) {
                // attack random ally of own team (simulate misfire)
                ArrayList<BaseCharacter> ownAlive = new ArrayList<>();
                // assume enemy belongs to team2 or team1; AI just attacks playerTeam normally; confusion for AI means attack its own allies - we skip complexity and just randomize target to playerTeam above
                System.out.println(enemy.getName()+" is confused and attacks an ally (simulated)!");
                enemy.basicAttack(target);
            } else {
                System.out.println("\n==================== AI TURN ====================");
                System.out.println(enemy.getName()+" attacks >>> "+target.getName()+" <<< !");
                enemy.basicAttack(target);
                System.out.println("================================================");
            }
        } else {
            System.out.println("\n==================== AI TURN ====================");
            enemy.useSkill(chosenAction-1, target, null, playerTeam);
            System.out.println("================================================");
        }
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
        for (BaseCharacter hero : team) factionCount.put(hero.getFaction(), factionCount.getOrDefault(hero.getFaction(),0)+1);
        for (String fac : factionCount.keySet()) {
            int count = factionCount.get(fac);
            if (count >= 2) {
                for (BaseCharacter hero : team) {
                    if (hero.getFaction().equals(fac)) {
                        int hpBuff = (int)(hero.getMaxHP() * (5 + rand.nextInt(11)) / 100.0);
                        int mpBuff = (int)(hero.getMaxMP() * (5 + rand.nextInt(11)) / 100.0);
                        hero.setCurrentHP(hero.getCurrentHP() + hpBuff);
                        hero.setCurrentMP(hero.getCurrentMP() + mpBuff);
                        System.out.println(hero.getName()+" receives Faction Buff! +"+hpBuff+" HP, +"+mpBuff+" MP.");
                    }
                }
            }
        }
    }
    public void startArcadeMode(BaseCharacter player) {
        ArrayList<BaseCharacter> enemyTeam = new ArrayList<>();
        int winCount = 0;
        System.out.println("\n--- Arcade Mode Start! ---");

    while (player.isAlive()) {
        enemyTeam.clear();
        BaseCharacter enemy = cloneHero(getAllHeroes().get(rand.nextInt(getAllHeroes().size())));
        enemyTeam.add(enemy);
        player.resetHPMP();

        System.out.println("\n--- New Enemy Approaches: " + enemy.getName() + " [" + enemy.getFaction() + "] ---");
        battleLoopArcade(player, enemyTeam);

        if (player.isAlive()) {
            winCount++;
            System.out.println("Enemy defeated! Total Wins: " + winCount);
            Utility.pause();
        }
    }

    System.out.println("\nPlayer defeated in Arcade Mode! Total Wins: " + winCount);
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