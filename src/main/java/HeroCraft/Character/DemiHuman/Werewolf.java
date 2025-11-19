package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Werewolf extends BaseCharacter {
    public Werewolf() {
        super("Werewolf", "DemiHuman", 120, 100);

        skillNames[0] = "Silverbolt Shot";
        skillNames[1] = "Moonfang Awakening";
        skillNames[2] = "Lunar Rampage";

        skillMPCosts[0] = 20;
        skillMPCosts[1] = 35;
        skillMPCosts[2] = 80;

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 4;
        skillMaxCooldowns[2] = 7;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Silverbolt Shot
                if (skillOnCooldown(0)) { System.out.println("Silverbolt Shot is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 30 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " fires Silverbolt Shot at " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Moonfang Awakening
                if (skillOnCooldown(1)) { System.out.println("Moonfang Awakening is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int dmg = 40 + rand.nextInt(21);
                target.takeDamage(dmg);
                applyStatus("Weaken", 2);
                System.out.println(name + " uses Moonfang Awakening on " + target.getName() + " for " + dmg + " damage and weakens them!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Lunar Rampage
                if (skillOnCooldown(2)) { System.out.println("Lunar Rampage is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 50 + rand.nextInt(31);
                    e.takeDamage(dmg);
                    System.out.println(name + " hits " + e.getName() + " with Lunar Rampage for " + dmg + " damage!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}