package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Minotaur extends BaseCharacter {
    public Minotaur() {
        super("Minotaur", "Demi-Human", 150, 60);
        skillMaxCooldowns[0]=3; skillMaxCooldowns[1]=4; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Taunting Roar
                if (skillOnCooldown(0)) { System.out.println("Taunting Roar on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) e.applyStatus("Taunt",1);
                applyStatus("DamageReduce20",1);
                System.out.println(name+" uses Taunting Roar: taunts enemies and gains 20% DR for 1 turn.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Blood Offering
                if (skillOnCooldown(1)) { System.out.println("Blood Offering on cooldown."); return; }
                int cost=25; if(hasMP(cost)) {
                    spendMP(cost);
                    for (BaseCharacter a: allies) a.restoreMP(30);
                    System.out.println(name+" restores 30 MP to allies.");
                } else {
                    // not enough mp: pay hp instead
                    int hpCost = 20;
                    setCurrentHP(currentHP - hpCost);
                    for (BaseCharacter a: allies) a.restoreMP(30);
                    System.out.println(name+" had low MP, pays 20 HP to restore allies' MP.");
                }
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Earthshatter
                if (skillOnCooldown(2)) { System.out.println("Earthshatter on cooldown."); return; }
                int cost=60; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) {
                    int dmg=35;
                    e.takeDamage(dmg);
                    e.applyStatus("Vulnerable",2);
                    System.out.println(name+" Earthshatters "+e.getName()+" for "+dmg+" and makes them Vulnerable.");
                }
                applyStatus("DamageReduce50",2); // self gains 50% DR for short time
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
