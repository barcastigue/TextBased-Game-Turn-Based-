package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Druid extends BaseCharacter {
    public Druid() {
        super("Druid", "Mystic", 350, 480);

        skillNames[0] = "Roots of Binding";
        skillNames[1] = "Iron Bark Armor";
        skillNames[2] = "Blessing of the Forest";

        skillMPCosts[0] = 20;
        skillMPCosts[1] = 15;
        skillMPCosts[2] = 70;

        skillMaxCooldowns[0] = 3;
        skillMaxCooldowns[1] = 2;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Roots of Binding
                if (skillOnCooldown(0)) { System.out.println("Roots of Binding is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                target.applyStatus("Rooted", 2);
                System.out.println(name + " casts Roots of Binding! " + target.getName() + " is rooted!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Iron Bark Armor
                if (skillOnCooldown(1)) { System.out.println("Iron Bark Armor is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                applyStatus("Shield", 2);
                System.out.println(name + " uses Iron Bark Armor and gains a shield for 2 turns!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Blessing of the Forest
                if (skillOnCooldown(2)) { System.out.println("Blessing of the Forest is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter a : allies) if (a.isAlive()) {
                    int healAmt = 30 + rand.nextInt(21);
                    a.heal(healAmt);
                    System.out.println(name + " heals " + a.getName() + " for " + healAmt + " HP with Blessing of the Forest!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}