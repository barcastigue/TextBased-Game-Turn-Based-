package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Elementalist extends BaseCharacter {
    public Elementalist() {
        super("Elementalist", "Mystic", 85, 140);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Magma Surge (line) - simulate as single target with burn chance
                if (skillOnCooldown(0)) { System.out.println("Magma Surge on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 30 + rand.nextInt(11); //30-40
                target.takeDamage(dmg);
                if (rand.nextInt(100) < 25) target.applyStatus("Burn",3);
                System.out.println(name+" casts Magma Surge on "+target.getName()+" for "+dmg+" and may Burn.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Tempest Torrent
                if (skillOnCooldown(1)) { System.out.println("Tempest Torrent on cooldown."); return; }
                int cost=30; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 35 + rand.nextInt(11);
                target.takeDamage(dmg);
                if (rand.nextInt(100) < 30) target.applyStatus("Soaked",2);
                System.out.println(name+" casts Tempest Torrent for "+dmg+" damage.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // All-Elemental Cataclysm
                if (skillOnCooldown(2)) { System.out.println("All-Elemental Cataclysm on cooldown."); return; }
                int cost=85; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) {
                    int dmg = 60;
                    e.takeDamage(dmg);
                    int pick = rand.nextInt(4);
                    switch(pick) {
                        case 0 -> e.applyStatus("Burn",3);
                        case 1 -> e.applyStatus("Soaked",3);
                        case 2 -> e.applyStatus("Weaken",2);
                        default -> e.applyStatus("Disoriented",2);
                    }
                    System.out.println(name+" hits "+e.getName()+" for "+dmg+" and inflicts random debuff.");
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
