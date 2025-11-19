package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Archer extends BaseCharacter {
    public Archer() {
        super("Archer", "Demi-Human", 95, 110);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=5;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Piercing Shot
                if (skillOnCooldown(0)) { System.out.println("Piercing Shot on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 45 + rand.nextInt(21); //45-65
                // ignore 50% defense: we don't model defense so just apply dmg
                target.takeDamage(dmg);
                System.out.println(name+" uses Piercing Shot on "+target.getName()+" for "+dmg+" (piercing).");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Multi-Shot
                if (skillOnCooldown(1)) { System.out.println("Multi-Shot on cooldown."); return; }
                int cost=30; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                // hits target and up to 2 adjacent: for simplicity pick up to 3 random enemies (unique)
                ArrayList<BaseCharacter> pool = new ArrayList<>();
                for (BaseCharacter e : enemies) if (e.isAlive()) pool.add(e);
                int hits = Math.min(3, pool.size());
                for (int i=0;i<hits;i++) {
                    BaseCharacter t = pool.remove(rand.nextInt(pool.size()));
                    int dmg = 28 + rand.nextInt(21); //28-48
                    t.takeDamage(dmg);
                    System.out.println(name+" hits "+t.getName()+" for "+dmg+" with Multi-Shot.");
                }
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Eyesight
                if (skillOnCooldown(2)) { System.out.println("Eyesight on cooldown."); return; }
                int cost=75; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                applyStatus("TrueSight",2);
                applyStatus("HalfMPCost",2); // we will interpret HalfMPCost in BattleManager to ignore for checks; here only status
                applyStatus("CritBoost",2);
                System.out.println(name+" activates Eyesight: True Sight +50% Crit and half skill MP cost for 2 turns.");
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}