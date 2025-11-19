package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Monk extends BaseCharacter {
    public Monk() {
        super("Monk", "Human", 110, 120);

        skillNames[0] = "Power Punch";
        skillNames[1] = "Rising Uppercut";
        skillNames[2] = "Flying Kick";

        skillMPCosts[0] = 15;
        skillMPCosts[1] = 20;
        skillMPCosts[2] = 60;

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 5;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Power Punch
                if (skillOnCooldown(0)) { System.out.println("Power Punch is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 20 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " hits " + target.getName() + " with Power Punch for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Rising Uppercut
                if (skillOnCooldown(1)) { System.out.println("Rising Uppercut is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int dmg = 30 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " uses Rising Uppercut on " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Flying Kick
                if (skillOnCooldown(2)) { System.out.println("Flying Kick is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                int dmg = 50 + rand.nextInt(21);
                target.takeDamage(dmg);
                System.out.println(name + " delivers Flying Kick to " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}