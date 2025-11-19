package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Elementalist extends BaseCharacter {
    public Elementalist() {
        super("Elementalist", "Mystic", 100, 140);

        skillNames[0] = "Magma Surge";
        skillNames[1] = "Tempest Torrent";
        skillNames[2] = "All-Elemental Cataclysm";

        skillMPCosts[0] = 25;
        skillMPCosts[1] = 30;
        skillMPCosts[2] = 85;

        skillMaxCooldowns[0] = 2;
        skillMaxCooldowns[1] = 3;
        skillMaxCooldowns[2] = 6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Magma Surge
                if (skillOnCooldown(0)) { System.out.println("Magma Surge is on cooldown."); return; }
                if (!hasMP(skillMPCosts[0])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[0]);
                int dmg = 35 + rand.nextInt(11);
                target.takeDamage(dmg);
                System.out.println(name + " hits " + target.getName() + " with Magma Surge for " + dmg + " damage!");
                skillCooldowns[0] = skillMaxCooldowns[0];
            }
            case 1 -> { // Tempest Torrent
                if (skillOnCooldown(1)) { System.out.println("Tempest Torrent is on cooldown."); return; }
                if (!hasMP(skillMPCosts[1])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[1]);
                int dmg = 40 + rand.nextInt(16);
                target.takeDamage(dmg);
                System.out.println(name + " hits " + target.getName() + " with Tempest Torrent for " + dmg + " damage!");
                skillCooldowns[1] = skillMaxCooldowns[1];
            }
            case 2 -> { // All-Elemental Cataclysm
                if (skillOnCooldown(2)) { System.out.println("All-Elemental Cataclysm is on cooldown."); return; }
                if (!hasMP(skillMPCosts[2])) { System.out.println("Not enough MP."); return; }
                spendMP(skillMPCosts[2]);
                for (BaseCharacter e : enemies) if (e.isAlive()) {
                    int dmg = 50 + rand.nextInt(31);
                    e.takeDamage(dmg);
                    System.out.println(name + " hits " + e.getName() + " with All-Elemental Cataclysm for " + dmg + " damage!");
                }
                skillCooldowns[2] = skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
