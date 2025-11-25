package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Summoner extends BaseCharacter {
    public Summoner() {
        super("Summoner", "Mystic", 300, 550);

        skillNames[0] = "Call of the Shard";
        skillNames[1] = "Grimoire Pact";
        skillNames[2] = "Call of the Grimoire";

        skillMPCosts[0] = 25;
        skillMPCosts[1] = 30;
        skillMPCosts[2] = 90;

        skillMaxCooldowns[0] = 3;
        skillMaxCooldowns[1] = 4;
        skillMaxCooldowns[2] = 7;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Call of the Shard
                if (skillOnCooldown(0)) { System.out.println("Call of the Shard is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 30 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " summons a shard to hit " + target.getName() + " for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Grimoire Pact
                if (skillOnCooldown(1)) { System.out.println("Grimoire Pact is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int dmg = 35 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " strikes " + target.getName() + " with Grimoire Pact for " + dmg + " damage!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // Call of the Grimoire
                if (skillOnCooldown(2)) { System.out.println("Call of the Grimoire is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 50 + rand.nextInt(31);
                    e.takeDamage(dmg);
                    System.out.println(name + " hits " + e.getName() + " with Call of the Grimoire for " + dmg + " damage!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}