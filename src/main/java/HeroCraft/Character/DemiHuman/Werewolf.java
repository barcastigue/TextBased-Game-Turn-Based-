package HeroCraft.Character.DemiHuman;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

public class Werewolf extends BaseCharacter {
    protected int bloodlustStacks = 0;
    protected boolean beastMode = false;

    public Werewolf() {
        super("Werewolf", "Demi-Human", 120, 90);
        skillMaxCooldowns[0]=2; skillMaxCooldowns[1]=6; skillMaxCooldowns[2]=8;
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Silverbolt Shot
                if (skillOnCooldown(0)) { System.out.println("Silverbolt Shot on cooldown."); return; }
                int cost=20; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                int dmg = 30 + rand.nextInt(11); //30-40
                if (target.getCurrentHP() < target.getMaxHP()*0.30) dmg += 15;
                target.takeDamage(dmg);
                System.out.println(name+" fires Silverbolt Shot for "+dmg+" dmg.");
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Moonfang Awakening
                if (skillOnCooldown(1)) { System.out.println("Moonfang Awakening on cooldown."); return; }
                int cost=35; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                applyStatus("Moonfang",3); // normal attacks become melee with +25% dmg and 10% lifesteal
                System.out.println(name+" awakens Moonfang: +25% damage and 10% lifesteal for 3 turns.");
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Lunar Rampage
                if (skillOnCooldown(2)) { System.out.println("Lunar Rampage on cooldown."); return; }
                int hpCost = (int)Math.ceil(maxHP * 0.10);
                setCurrentHP(currentHP - hpCost);
                applyStatus("Rampage",3); // +40% ATK and 30% DR for 3 turns
                System.out.println(name+" goes into Lunar Rampage sacrificing "+hpCost+" HP.");
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
