package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Assassin extends BaseCharacter {
    public Assassin() {
        super("Assassin", "Human", 100, 120);

        skillNames[0] = "Shadow Strike";
        skillNames[1] = "Poison Dagger";
        skillNames[2] = "Blade Dance";

        skillMPCosts[0] = 20;
        skillMPCosts[1] = 25;
        skillMPCosts[2] = 60;

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Shadow Strike
                if (skillOnCooldown(0)) { System.out.println("Shadow Strike is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 25 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " strikes from shadows dealing " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Poison Dagger
                if (skillOnCooldown(1)) { System.out.println("Poison Dagger is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                target.applyStatus("Poison", 3);
                System.out.println(name + " poisons " + target.getName() + " for 3 turns!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Blade Dance
                if (skillOnCooldown(2)) { System.out.println("Blade Dance is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 35 + rand.nextInt(11);
                    e.takeDamage(dmg);
                    System.out.println(name + " strikes " + e.getName() + " with Blade Dance for " + dmg + " damage!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}