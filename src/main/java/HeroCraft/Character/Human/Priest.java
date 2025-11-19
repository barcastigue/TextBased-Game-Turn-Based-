package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Priest extends BaseCharacter {
    public Priest() {
        super("Priest", "Human", 90, 150);

        skillNames[0] = "Holy Light";
        skillNames[1] = "Divine Retribution";
        skillNames[2] = "Divine Grace";

        skillMPCosts[0] = 30;
        skillMPCosts[1] = 25;
        skillMPCosts[2] = 0; // uses % Max MP

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Holy Light
                if (skillOnCooldown(0)) { System.out.println("Holy Light is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int heal = 25 + rand.nextInt(16);
                target.heal(heal);
                System.out.println(name + " heals " + target.getName() + " for " + heal + " HP!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Divine Retribution
                if (skillOnCooldown(1)) { System.out.println("Divine Retribution is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int dmg = 30 + rand.nextInt(21);
                target.takeDamage(dmg);
                System.out.println(name + " uses Divine Retribution on " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Divine Grace
                if (skillOnCooldown(2)) { System.out.println("Divine Grace is on cooldown."); return; }
                int restoreMP = (int)(maxMP * 0.6);
                restoreMP(restoreMP);
                System.out.println(name + " restores " + restoreMP + " MP using Divine Grace!");
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}