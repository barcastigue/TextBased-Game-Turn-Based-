/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HeroCraft.Character;

/**
 * @author DELL
 */
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

    protected List<StatusEffect> activeStatuses = new ArrayList<>();

    protected static class StatusEffect {
        String name;
        int duration;

        StatusEffect(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }
    }

    public BaseCharacter(String name, String faction, int hp, int mp) {
        this.name = name;
        this.faction = faction;
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxMP = mp;
        this.currentMP = mp;
    }

    public boolean hasStatus(String statusName) {
        for (StatusEffect s : activeStatuses) {
            if (s.name.equals(statusName) && s.duration > 0) return true;
        }
        return false;
    }

    public void applyStatus(String statusName, int duration) {
        activeStatuses.add(new StatusEffect(statusName, duration));
    }

    public void reduceStatusDuration() {
        for (StatusEffect s : activeStatuses) {
            s.duration--;
        }
        activeStatuses.removeIf(s -> s.duration <= 0);
    }

    public void basicAttack(BaseCharacter target) {
        if (this.hasStatus("Silence")) {
            System.out.println(this.name + " is silenced and cannot attack!");
            return;
        }
        Random rand = new Random();
        int minDamage = 10;
        int maxDamage = 30;
    int damage = rand.nextInt(maxDamage - minDamage + 1) + minDamage;
        if (target.hasStatus("Vulnerable")) damage = (int)(damage * 1.3);
        if (this.hasStatus("Weaken")) damage = (int)(damage * 0.7);

        target.takeDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void takeDamage(int dmg) {
        currentHP -= dmg;
        if (currentHP < 0) currentHP = 0;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public String getName() { return name; }
    public String getFaction() { return faction; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentMP() { return currentMP; }
    public int getMaxMP() { return maxMP; }

    public void setCurrentHP(int hp) {
        this.currentHP = Math.min(hp, this.maxHP);
        if (this.currentHP < 0) this.currentHP = 0;
    }

    public void setCurrentMP(int mp) {
        this.currentMP = Math.min(mp, this.maxMP);
        if (this.currentMP < 0) this.currentMP = 0;
    }
}