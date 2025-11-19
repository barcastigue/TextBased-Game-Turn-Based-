package HeroCraft.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BaseCharacter - common fields and utilities for all heroes.
 */
public abstract class BaseCharacter {
    protected String name;
    protected String faction;
    protected int maxHP;
    protected int currentHP;
    protected int maxMP;
    protected int currentMP;

    protected Random rand = new Random();

    // Status system (list-based)
    protected static class StatusEffect {
        public String name;
        public int remainingTurns;
        public int magnitude; // for percent or numeric value if needed

        public StatusEffect(String name, int turns) {
            this(name, turns, 0);
        }

        public StatusEffect(String name, int turns, int magnitude) {
            this.name = name;
            this.remainingTurns = turns;
            this.magnitude = magnitude;
        }
    }

    protected List<StatusEffect> statuses = new ArrayList<>();

    // Skill cooldowns: index 0..2 = skill1..skill3
    protected int[] skillCooldowns = new int[3];   // current remaining cooldown
    protected int[] skillMaxCooldowns = new int[3];// filled by subclass constructors

    public BaseCharacter(String name, String faction, int hp, int mp) {
        this.name = name;
        this.faction = faction;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxMP = mp;
        this.currentMP = mp;
        this.skillCooldowns[0] = this.skillCooldowns[1] = this.skillCooldowns[2] = 0;
    }

    // ---------- Combat core ----------
    // Basic attack is option 4 in UI
    public void basicAttack(BaseCharacter target) {
        if (hasStatus("Silence")) {
            System.out.println(this.name + " is silenced and cannot attack!");
            return;
        }
        if (hasStatus("Sleep")) {
            System.out.println(this.name + " is asleep and cannot attack!");
            return;
        }

        int min = 8, max = 15;
        int damage = rand.nextInt(max - min + 1) + min;

        // Weaken reduces outgoing damage by 30%
        if (hasStatus("Weaken")) damage = (int) Math.floor(damage * 0.7);

        // Vulnerable on the target increases damage taken by 30%
        if (target.hasStatus("Vulnerable")) damage = (int) Math.floor(damage * 1.3);

        // print and apply
        target.takeDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void takeDamage(int dmg) {
        // no defense modeled — could add defense later
        currentHP -= dmg;
        if (currentHP < 0) currentHP = 0;
    }

    public void heal(int amount) {
        currentHP += amount;
        if (currentHP > maxHP) currentHP = maxHP;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    // ---------- Status methods ----------
    public void applyStatus(String name, int turns) {
        if (name == null || turns <= 0) return;
        statuses.add(new StatusEffect(name, turns));
        System.out.println(this.name + " is afflicted with " + name + " for " + turns + " turn(s).");
    }

    public boolean hasStatus(String name) {
        for (StatusEffect s : statuses) {
            if (s.name.equals(name) && s.remainingTurns > 0) return true;
        }
        return false;
    }

    public void removeStatus(String name) {
        statuses.removeIf(s -> s.name.equals(name));
    }

    // Called at end of character's turn to decrement durations and handle ticks
    // Some statuses like Poison/Burn do periodic damage; handle here or in skill effects
    public void reduceStatusDuration() {
        for (int i = statuses.size() - 1; i >= 0; i--) {
            StatusEffect s = statuses.get(i);
            // apply periodic effects
            if (s.name.equals("Poison")) {
                int dot = 10;
                takeDamage(dot);
                System.out.println(this.name + " takes " + dot + " Poison damage.");
            } else if (s.name.equals("Burn")) {
                int dot = 8;
                takeDamage(dot);
                System.out.println(this.name + " takes " + dot + " Burn damage.");
            } else if (s.name.equals("Bleeding")) {
                int dot = 8;
                takeDamage(dot);
                System.out.println(this.name + " bleeds for " + dot + " damage.");
            }
            s.remainingTurns--;
            if (s.remainingTurns <= 0) {
                System.out.println(this.name + " is no longer affected by " + s.name + ".");
                statuses.remove(i);
            }
        }
    }

    // ---------- Cooldown and MP helpers ----------
    public boolean skillOnCooldown(int skillIndex) {
        return skillCooldowns[skillIndex] > 0;
    }

    public void setCooldown(int skillIndex, int cd) {
        skillCooldowns[skillIndex] = cd;
        skillMaxCooldowns[skillIndex] = cd;
    }

    // reduce cooldown by 1 at end of turn (call hero.reduceCooldowns())
    public void reduceCooldowns() {
        for (int i = 0; i < skillCooldowns.length; i++) {
            if (skillCooldowns[i] > 0) skillCooldowns[i]--;
        }
    }

    public boolean hasMP(int cost) {
        return currentMP >= cost;
    }

    public void spendMP(int cost) {
        currentMP -= cost;
        if (currentMP < 0) currentMP = 0;
    }

    public void restoreMP(int amt) {
        currentMP += amt;
        if (currentMP > maxMP) currentMP = maxMP;
    }

    public abstract void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies);

    // Getters & setters
    public String getName() { return name; }
    public String getFaction() { return faction; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentMP() { return currentMP; }
    public int getMaxMP() { return maxMP; }

    public void setCurrentHP(int hp) { this.currentHP = Math.min(hp, maxHP); if (this.currentHP < 0) this.currentHP = 0; }
    public void setCurrentMP(int mp) { this.currentMP = Math.min(mp, maxMP); if (this.currentMP < 0) this.currentMP = 0; }
    // inside BaseCharacter, add this field and accessor methods

    protected String[] skillNames = new String[] {"Skill 1", "Skill 2", "Skill 3"};

    public String getSkillName(int oneBasedIndex) {
        if (oneBasedIndex <= 0 || oneBasedIndex > skillNames.length) {
            return "Unknown Skill";
        }
        return skillNames[oneBasedIndex - 1];
    }
}