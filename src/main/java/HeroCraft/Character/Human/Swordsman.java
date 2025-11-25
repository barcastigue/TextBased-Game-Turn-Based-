package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Swordsman extends BaseCharacter {
    public Swordsman() {
        super("Swordsman", "Human", 510, 315);

        skillNames[0] = "Wounded Strike";
        skillNames[1] = "Grievous Guard";
        skillNames[2] = "Curse of the Undying";

        skillMPCosts[0] = 20;
        skillMPCosts[1] = 25;
        skillMPCosts[2] = 0; // uses HP instead

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }


    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Wounded Strike
                if (skillOnCooldown(0)) { System.out.println("Wounded Strike is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 30 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " uses Wounded Strike on " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Grievous Guard
                if (skillOnCooldown(1)) { System.out.println("Grievous Guard is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                applyStatus("Guard", 3);
                System.out.println(name + " uses Grievous Guard and reduces incoming damage for 3 turns!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Curse of the Undying
                if (skillOnCooldown(2)) { System.out.println("Curse of the Undying is on cooldown."); return; }
                int trueDamage = 70 + rand.nextInt(31); // 70-100 true damage
                int targetHPBefore = target.getCurrentHP();
                target.setCurrentHP(target.getCurrentHP() - trueDamage); // bypass normal takeDamage
                if (target.getCurrentHP() < 0) target.setCurrentHP(0);
                System.out.println(name + " uses Curse of the Undying on " + target.getName() + " for " + trueDamage + " true damage!");

                if (targetHPBefore > 0 && target.getCurrentHP() == 0) {
                    int healAmount = (int)(maxHP * 0.2);
                    heal(healAmount);
                    System.out.println(name + " has slain " + target.getName() + " and restores " + healAmount + " HP!");
                    applyStatus("Fury", 3); // strikes hit harder for 3 turns
                    System.out.println(name + "'s strikes hit harder for the next 3 turns!");
                }

                skillCooldowns[2] = skillMaxCooldowns[2];
            }
        default -> System.out.println("Invalid skill.");
        }
    }
}