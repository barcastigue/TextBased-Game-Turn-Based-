package HeroCraft.Character.Human;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Monk extends BaseCharacter {
    public Monk() {
        super("Monk", "Human", 100, 80);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Power Punch
                if (skillOnCooldown(0)) { System.out.println("Power Punch on cooldown."); return; }
                int cost=15; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg=35;
                boolean stun = rand.nextInt(100) < 25;
                if (stun) target.applyStatus("Stun",1);
                // restore 5 MP on "critical" - simulate 10% crit
                boolean crit = rand.nextInt(100) < 10;
                if (crit) { restoreMP(5); System.out.println("Critical! Monk restores 5 MP."); }
                target.takeDamage(dmg);
                System.out.println(name+" uses Power Punch on "+target.getName()+" for "+dmg+" damage." + (stun ? " Stunned!" : ""));
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Rising Uppercut
                if (skillOnCooldown(1)) { System.out.println("Rising Uppercut on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg=45;
                target.takeDamage(dmg);
                target.applyStatus("Bleeding", 2);
                System.out.println(name+" uses Rising Uppercut for "+dmg+" damage and inflicts Bleeding.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Flying Kick
                if (skillOnCooldown(2)) { System.out.println("Flying Kick on cooldown."); return; }
                int cost=60; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) {
                    if (!e.isAlive()) continue;
                    int dmg = 30;
                    if (e.hasStatus("Stun") || e.hasStatus("Bleeding")) dmg = (int)(dmg * 1.25);
                    e.takeDamage(dmg);
                    System.out.println(name+" hits "+e.getName()+" for "+dmg+" with Flying Kick.");
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}