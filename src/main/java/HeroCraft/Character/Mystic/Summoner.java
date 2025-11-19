package HeroCraft.Character.Mystic;

import HeroCraft.Character.BaseCharacter;
import java.util.ArrayList;

/**
 * Summoner can summon Minions (Shardling or Aegis).
 * Summons are represented as BaseCharacter Minion inner class here for simplicity.
 */
public class Summoner extends BaseCharacter {

    public Summoner() {
        super("Summoner", "Mystic", 90, 120);
        skillMaxCooldowns[0]=3; skillMaxCooldowns[1]=3; skillMaxCooldowns[2]=999; // ultimate long cooldown
    }

    // Simple Minion inner class
    public static class Minion extends BaseCharacter {
        public Minion(String name, int hp, int mp) { super(name, "Summon", hp, mp); }
        @Override public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
            // minion logic: basic attack only
            if (enemies != null && enemies.size() > 0) {
                BaseCharacter t = enemies.get(0);
                int dmg = 10;
                t.takeDamage(dmg);
                System.out.println(this.name + " attacks " + t.getName() + " for " + dmg);
            }
        }
    }

    @Override
    public void useSkill(int skillIndex, BaseCharacter target, ArrayList<BaseCharacter> allies, ArrayList<BaseCharacter> enemies) {
        switch(skillIndex) {
            case 0 -> { // Call of the Shard
                if (skillOnCooldown(0)) { System.out.println("Call of the Shard on cooldown."); return; }
                int cost=25; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                // randomly summon Shardling or Aegis: we just apply a status to indicate summon presence
                int r = rand.nextInt(100);
                if (r < 60) {
                    applyStatus("SummonShard",3);
                    System.out.println(name+" summons a Shardling (40 HP) for 3 turns.");
                } else {
                    applyStatus("SummonAegis",3);
                    System.out.println(name+" summons an Aegis (60 HP Taunt) for 3 turns.");
                }
                skillCooldowns[0]=skillMaxCooldowns[0];
            }
            case 1 -> { // Grimoire Pact
                if (skillOnCooldown(1)) { System.out.println("Grimoire Pact on cooldown."); return; }
                int cost=30; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                // heals active summon (simulated by removing statuses)
                System.out.println(name+" heals active summon and grants Empower.");
                applyStatus("SummonEmpower",2);
                skillCooldowns[1]=skillMaxCooldowns[1];
            }
            case 2 -> { // Call of the Grimoire
                if (skillOnCooldown(2)) { System.out.println("Call of the Grimoire on cooldown."); return; }
                int cost=90; if(!hasMP(cost)){System.out.println("Not enough MP.");return;}
                spendMP(cost);
                applyStatus("ChaosBehemoth",999); // permanent for the match (simulated)
                System.out.println(name+" summons Chaos Behemoth (permanent).");
                skillCooldowns[2]=skillMaxCooldowns[2];
            }
            default -> System.out.println("Invalid skill.");
        }
    }
}
