package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Druid extends BaseCharacter {
    public Druid() {
        super("Druid", "Mystic", 95, 130);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=7;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Roots of Binding
                if (skillOnCooldown(0)) { System.out.println("Roots of Binding on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) { e.takeDamage(15 + rand.nextInt(6)); e.applyStatus("Rooted",2); }
                for (BaseCharacter a : allies) { a.applyStatus("TempHP10",1); }
                System.out.println(name+" casts Roots of Binding: damages enemies, roots them, allies gain Temp HP.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Iron Bark Armor
                if (skillOnCooldown(1)) { System.out.println("Iron Bark Armor on cooldown."); return; }
                int cost=15; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                target.applyStatus("Regen10",3);
                target.applyStatus("TempHP15",3);
                System.out.println(name+" grants Iron Bark to "+target.getName()+" for 3 turns.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Blessing of the Forest
                if (skillOnCooldown(2)) { System.out.println("Blessing of the Forest on cooldown."); return; }
                int cost=70; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter a : allies) {
                    a.heal(40);
                    // remove statuses: simple approach remove Poison/Burn/Bleeding etc
                    a.removeStatus("Poison"); a.removeStatus("Burn"); a.removeStatus("Bleeding");
                    a.applyStatus("TempHP10",1);
                    a.restoreMP(20);
                    System.out.println(name+" heals "+a.getName()+" and removes bad statuses.");
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
