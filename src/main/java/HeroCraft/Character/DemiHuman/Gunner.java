package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Gunner extends BaseCharacter {
    public Gunner() {
        super("Gunner", "DemiHuman", 95, 120);

        skillNames[0] = "Fateful Shot";
        skillNames[1] = "Hecate's Blessing";
        skillNames[2] = "Chaos Recoil";

        skillMPCosts[0] = 15;
        skillMPCosts[1] = 20;
        skillMPCosts[2] = 80;

        skillMaxCooldowns[0] = 0; // No cooldown
        skillMaxCooldowns[1] = 4;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Fateful Shot
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 25 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " fires Fateful Shot at " + target.getName() + " for " + dmg + " damage!");
            }
            case 1 -> { // Hecate's Blessing
                if (skillOnCooldown(1)) { System.out.println("Hecate's Blessing is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int buff = 20 + rand.nextInt(11);
                heal(buff);
                System.out.println(name + " uses Hecate's Blessing to heal for " + buff + " HP!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Chaos Recoil
                if (skillOnCooldown(2)) { System.out.println("Chaos Recoil is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                int dmg = 50 + rand.nextInt(21);
                target.takeDamage(dmg);
                heal(20); // self-heal
                System.out.println(name + " uses Chaos Recoil on " + target.getName() + " for " + dmg + " damage and heals self 20 HP!");
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}