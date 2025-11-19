package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Minotaur extends BaseCharacter {
    public Minotaur() {
        super("Minotaur", "DemiHuman", 140, 80);

        skillNames[0] = "Taunting Roar";
        skillNames[1] = "Blood Offering";
        skillNames[2] = "Earthshatter";

        skillMPCosts[0] = 20;
        skillMPCosts[1] = 25;
        skillMPCosts[2] = 60;

        skillMaxCooldowns[0] = 3;
        skillMaxCooldowns[1] = 4;
        skillMaxCooldowns[2] = 5;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Taunting Roar
                if (skillOnCooldown(0)) { System.out.println("Taunting Roar is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                target.applyStatus("Taunt", 2);
                System.out.println(name + " uses Taunting Roar! " + target.getName() + " is forced to target Minotaur.");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Blood Offering
                if (skillOnCooldown(1)) { System.out.println("Blood Offering is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                target.takeDamage(30);
                heal(20);
                System.out.println(name + " uses Blood Offering on " + target.getName() + " for 30 damage and heals 20 HP!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Earthshatter
                if (skillOnCooldown(2)) { System.out.println("Earthshatter is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 40 + rand.nextInt(21);
                    e.takeDamage(dmg);
                    System.out.println(name + " hits " + e.getName() + " with Earthshatter for " + dmg + " damage!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}