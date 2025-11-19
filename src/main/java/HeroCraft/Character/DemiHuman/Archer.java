package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Archer extends BaseCharacter {
    public Archer() {
        super("Archer", "DemiHuman", 100, 120);

        skillNames[0] = "Piercing Shot";
        skillNames[1] = "Multi-Shot";
        skillNames[2] = "Eyesight";

        skillMPCosts[0] = 25;
        skillMPCosts[1] = 30;
        skillMPCosts[2] = 75;

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Piercing Shot
                if (skillOnCooldown(0)) { System.out.println("Piercing Shot is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 35 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " fires Piercing Shot at " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Multi-Shot
                if (skillOnCooldown(1)) { System.out.println("Multi-Shot is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 20 + rand.nextInt(11);
                    e.takeDamage(dmg);
                    System.out.println(name + " hits " + e.getName() + " with Multi-Shot for " + dmg + " damage!");
                }
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Eyesight
                if (skillOnCooldown(2)) { System.out.println("Eyesight is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                applyStatus("Mark", 6);
                System.out.println(name + " uses Eyesight, marking all enemies for extra damage for 6 turns!");
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}