package HeroCraft.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseCharacter {
    protected String name;
    protected String faction;
    protected int maxHP;
    protected int currentHP;
    protected int maxMP;
    protected int currentMP;

    protected Random rand = new Random();

    public void resetHPMP() {
        this.currentMP = this.maxMP;
    }

    // Status system
    protected static class StatusEffect {
        public String name;
        public int remainingTurns;
        public int magnitude;

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

    // Skill system
    protected String[] skillNames = new String[] {"Skill 1", "Skill 2", "Skill 3"};
    protected int[] skillCooldowns = new int[3];    // remaining cooldown
    protected int[] skillMaxCooldowns = new int[3]; // max cooldown set by subclass
    protected int[] skillMPCosts = new int[3];      // MP cost per skill

    public BaseCharacter(String name, String faction, int hp, int mp) {
        this.name = name;
        this.faction = faction;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxMP = mp;
        this.currentMP = mp;

        // Initialize skills arrays
        for (int i = 0; i < 3; i++) {
            skillCooldowns[i] = 0;
            skillMaxCooldowns[i] = 0;
            skillMPCosts[i] = 0;
        }
    }

    // ---------- Combat ----------
    public void basicAttack(BaseCharacter target) {
        if (hasStatus("Silence")) { System.out.println(this.name + " is silenced and cannot attack!"); return; }
        if (hasStatus("Sleep")) { System.out.println(this.name + " is asleep and cannot attack!"); return; }

        int min = 8, max = 15;
        int damage = rand.nextInt(max - min + 1) + min;

        if (hasStatus("Weaken")) damage = (int) Math.floor(damage * 0.7);
        if (target.hasStatus("Vulnerable")) damage = (int) Math.floor(damage * 1.3);

        target.takeDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void takeDamage(int dmg) {
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

    // ---------- Status ----------
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

    public void reduceStatusDuration() {
        for (int i = statuses.size() - 1; i >= 0; i--) {
            StatusEffect s = statuses.get(i);
            s.remainingTurns--;
            if (s.remainingTurns <= 0) {
                System.out.println(this.name + " is no longer affected by " + s.name + ".");
                statuses.remove(i);
            }
        }
    }

    // ---------- Cooldown & MP ----------
    public boolean skillOnCooldown(int skillIndex) {
        return skillCooldowns[skillIndex] > 0;
    }

    public void setCooldown(int skillIndex, int cd) {
        skillCooldowns[skillIndex] = cd;
        skillMaxCooldowns[skillIndex] = cd;
    }

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

    // ---------- Skill info ----------
    public int getSkillMPCost(int oneBasedIndex) {
        if (oneBasedIndex <= 0 || oneBasedIndex > 3) return 0;
        return skillMPCosts[oneBasedIndex - 1];
    }

    public int getSkillCooldown(int oneBasedIndex) {
        if (oneBasedIndex <= 0 || oneBasedIndex > 3) return 0;
        return skillCooldowns[oneBasedIndex - 1];
    }

    public String getSkillName(int oneBasedIndex) {
        if (oneBasedIndex <= 0 || oneBasedIndex > 3) return "Unknown Skill";
        return skillNames[oneBasedIndex - 1];
    }

    // ---------- Abstract ----------
    public abstract void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies);

    // ---------- Getters & Setters ----------
    public String getName() { return name; }
    public String getFaction() { return faction; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentMP() { return currentMP; }
    public int getMaxMP() { return maxMP; }
    public void setCurrentHP(int hp) { this.currentHP = Math.min(hp, maxHP); if (this.currentHP < 0) this.currentHP = 0; }
    public void setCurrentMP(int mp) { this.currentMP = Math.min(mp, maxMP); if (this.currentMP < 0) this.currentMP = 0; }
}