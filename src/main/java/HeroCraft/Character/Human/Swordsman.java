package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

/**
 * Swordsman
 * Skills:
 * 1) Wounded Strike — 15–30 dmg; +25% if HP < 50%; MP20; CD 2
 * 2) Grievous Guard — Guard (30% dmg reduction) 2 turns + heal self 10–30 HP; MP25; CD3
 * 3) Curse of the Undying — Deals 70 True Damage, costs 30% current HP; on kill regain 20% Max HP + +10 STR (we simulate STR as extra damage) for 2 turns.
 */
public class Swordsman extends BaseCharacter {
    public Swordsman() {
        super("Swordsman", "Human", 110, 80);
        // set skill cooldowns (max cooldowns)
        setCooldown(0, 0); // will be set when used
        setCooldown(1, 0);
        setCooldown(2, 0);
        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch (skillIndex) {
            case 0 -> { // Wounded Strike
                if (skillOnCooldown(0)) { System.out.println("Wounded Strike is on cooldown."); return; }
                int cost = 20;
                if (!hasMP(cost)) { System.out.println("Not enough MP."); return; }
                spendMP(cost);
                int damage = 15 + rand.nextInt(16); // 15-30
                if (currentHP < maxHP/2) damage = (int) Math.floor(damage * 1.25);
                target.takeDamage(damage);
                System.out.println(name + " uses Wounded Strike on " + target.getName() + " for " + damage + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Grievous Guard
                if (skillOnCooldown(1)) { System.out.println("Grievous Guard is on cooldown."); return; }
                int cost = 25;
                if (!hasMP(cost)) { System.out.println("Not enough MP."); return; }
                spendMP(cost);
                int heal = 10 + rand.nextInt(21); // 10-30
                heal(heal);
                // apply Guard status which we'll interpret in BattleManager if needed (30% damage reduction)
                applyStatus("Guard", 2);
                System.out.println(name + " uses Grievous Guard, heals " + heal + " HP and gains Guard for 2 turns.");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Curse of the Undying
                if (skillOnCooldown(2)) { System.out.println("Curse of the Undying is on cooldown."); return; }
                int hpCost = (int) Math.floor(currentHP * 0.30);
                if (hpCost <= 0) { System.out.println("Not enough HP to use this."); return; }
                // costs 30% current HP instead of MP
                setCurrentHP(currentHP - hpCost);
                int damage = 70; // true damage: ignore statuses/defense
                target.takeDamage(damage);
                System.out.println(name + " uses Curse of the Undying on " + target.getName() + " for " + damage + " TRUE damage! (Cost: " + hpCost + " HP)");
                if (!target.isAlive()) {
                    int restore = (int) Math.floor(maxHP * 0.20);
                    heal(restore);
                    // simulate +10 STR by giving Vulnerable-like extra damage boost on self
                    applyStatus("UnholyMight", 2); // we'll interpret this in BattleManager by adding flat bonus for basic attack/skills
                    System.out.println(name + " killed target and regains " + restore + " HP and gains UnholyMight (+10 damage) for 2 turns.");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill index.");
        }
    }
}
