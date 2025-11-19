package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Kitsune extends BaseCharacter {
    public Kitsune() {
        super("Kitsune", "Mystic", 95, 140);

        skillNames[0] = "Foxfire Eruption";
        skillNames[1] = "Spirit Mirage";
        skillNames[2] = "Soul Silence";

        skillMPCosts[0] = 25;
        skillMPCosts[1] = 30;
        skillMPCosts[2] = 60;

        skillMaxCooldowns[0] = 3;
        skillMaxCooldowns[1] = 5;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Foxfire Eruption
                if (skillOnCooldown(0)) { System.out.println("Foxfire Eruption is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 35 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " hits " + target.getName() + " with Foxfire Eruption for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Spirit Mirage
                if (skillOnCooldown(1)) { System.out.println("Spirit Mirage is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                applyStatus("Evade", 2);
                System.out.println(name + " uses Spirit Mirage to become evasive for 2 turns!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Soul Silence
                if (skillOnCooldown(2)) { System.out.println("Soul Silence is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                target.applyStatus("Silence", 2);
                System.out.println(name + " casts Soul Silence on " + target.getName() + "!");
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}