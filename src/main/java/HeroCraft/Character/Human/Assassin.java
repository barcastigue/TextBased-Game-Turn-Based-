package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Assassin extends BaseCharacter {
    public Assassin() {
        super("Assassin", "Human", 85, 100);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Shadow Strike
                if (skillOnCooldown(0)) { System.out.println("Shadow Strike on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 20 + rand.nextInt(6); // 20-25
                // simulate +25% crit chance: roll crit
                boolean crit = rand.nextInt(100) < 25;
                if (crit) { dmg = dmg * 2; System.out.println("Critical!"); }
                target.takeDamage(dmg);
                System.out.println(name+" uses Shadow Strike on "+target.getName()+" for "+dmg+" damage.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Poison Dagger
                if (skillOnCooldown(1)) { System.out.println("Poison Dagger on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg=5+rand.nextInt(6); //5-10
                target.takeDamage(dmg);
                target.applyStatus("Poison", 3); // 3 turns of poison (10/turn handled in BaseCharacter)
                System.out.println(name+" throws Poison Dagger for "+dmg+" dmg and inflicts Poison.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Blade Dance
                if (skillOnCooldown(2)) { System.out.println("Blade Dance on cooldown."); return; }
                int cost=60; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                if (enemies == null || enemies.size() == 0) { System.out.println("No enemies."); return; }
                for (int i=0;i<3;i++) {
                    BaseCharacter t = enemies.get(rand.nextInt(enemies.size()));
                    int dmg = 25 + rand.nextInt(10); //25-34
                    t.takeDamage(dmg);
                    System.out.println(name+" strikes "+t.getName()+" for "+dmg+" damage.");
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
