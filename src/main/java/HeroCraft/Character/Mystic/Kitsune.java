package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Kitsune extends BaseCharacter {
    public Kitsune() {
        super("Kitsune", "Mystic", 88, 150);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=4; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Foxfire Eruption
                if (skillOnCooldown(0)) { System.out.println("Foxfire Eruption on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 20 + rand.nextInt(11);
                target.takeDamage(dmg);
                target.applyStatus("Burn",3); // 8 dmg/turn handled in BaseCharacter
                System.out.println(name+" casts Foxfire Eruption on "+target.getName()+" for "+dmg+" damage and Burn.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Spirit Mirage
                if (skillOnCooldown(1)) { System.out.println("Spirit Mirage on cooldown."); return; }
                int cost=30; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                applyStatus("Clone",3); // simplified clone status
                System.out.println(name+" summons a Clone for 3 turns. If it survives, Kitsune regains 15 MP.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Soul Silence
                if (skillOnCooldown(2)) { System.out.println("Soul Silence on cooldown."); return; }
                int cost=60; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                target.takeDamage(55 + rand.nextInt(11)); //55-65
                target.applyStatus("Silence",2);
                System.out.println(name+" uses Soul Silence on "+target.getName()+" for heavy spirit damage and Silence.");
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
