package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Gunner extends BaseCharacter {
    public Gunner() {
        super("Gunner", "Demi-Human", 92, 130);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=6;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Fateful Shot
                if (skillOnCooldown(0)) { System.out.println("Fateful Shot on cooldown."); return; }
                int cost=15; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int roll = rand.nextInt(100);
                if (roll < 50) {
                    int dmg = 20 + rand.nextInt(11); //20-30
                    target.takeDamage(dmg);
                    System.out.println(name+" fires Fateful Shot for "+dmg+" damage.");
                } else if (roll < 80) {
                    int dmg = 50;
                    target.takeDamage(dmg);
                    System.out.println(name+" scores a critical Fateful Shot for "+dmg+" damage!");
                } else {
                    restoreMP(10);
                    System.out.println(name+" misses but refunds 10 MP.");
                }
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Hecate's Blessing
                if (skillOnCooldown(1)) { System.out.println("Hecate's Blessing on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int r = rand.nextInt(100);
                if (r < 33) {
                    applyStatus("DodgeBuff",2);
                    System.out.println(name+" grants Dodge (50%) to team for 2 turns.");
                } else if (r < 66) {
                    for (BaseCharacter a: allies) a.applyStatus("Barrier40",2);
                    System.out.println(name+" grants 40 HP Barrier to allies.");
                } else {
                    for (BaseCharacter a: allies) a.applyStatus("Regen20",2);
                    System.out.println(name+" grants Regen 20 HP/turn to allies.");
                }
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Chaos Recoil
                if (skillOnCooldown(2)) { System.out.println("Chaos Recoil on cooldown."); return; }
                int cost=80; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                for (BaseCharacter e : enemies) {
                    int dmg = 30;
                    e.takeDamage(dmg);
                    int r = rand.nextInt(100);
                    if (r < 70) {
                        e.applyStatus("CurseTrue",2); // interpret as 20% MaxHP true damage later
                        System.out.println(name+" hits "+e.getName()+" and curses (True damage over time).");
                    } else {
                        e.applyStatus("Weaken",2); // -15% STR interpreted as Weaken
                        System.out.println(name+" hits "+e.getName()+" and weakens them.");
                    }
                }
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
