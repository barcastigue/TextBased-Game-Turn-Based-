package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Priest extends BaseCharacter {
    public Priest() {
        super("Priest", "Human", 90, 140);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=8;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Holy Light
                if (skillOnCooldown(0)) { System.out.println("Holy Light on cooldown."); return; }
                int cost=30; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int heal = 30;
                if (target.getCurrentHP() < target.getMaxHP()/2) heal = 40;
                target.heal(heal);
                System.out.println(name+" casts Holy Light on "+target.getName()+" for "+heal+" HP.");
                if (target.getCurrentHP() == target.getMaxHP()) {
                    target.restoreMP(15);
                    System.out.println(target.getName()+" was healed to full and restores 15 MP!");
                }
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Divine Retribution
                if (skillOnCooldown(1)) { System.out.println("Divine Retribution on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                target.applyStatus("Marked", 2); // takes 15 holy damage when attacking (handled in BattleManager)
                System.out.println(name+" marks "+target.getName()+" for Divine Retribution (2 turns).");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Divine Grace
                if (skillOnCooldown(2)) { System.out.println("Divine Grace on cooldown."); return; }
                int cost = (int)Math.ceil(maxMP * 0.60);
                if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter a : allies) {
                    int heal = (int)Math.floor(a.getMaxHP() * 0.50);
                    a.heal(heal);
                    a.applyStatus("Barrier30", 1); // barrier for 30 HP simulated as status
                    a.restoreMP(25);
                    System.out.println(name+" heals "+a.getName()+" for "+heal+" HP, grants 30 barrier and 25 MP.");
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
