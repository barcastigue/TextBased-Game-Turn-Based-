package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;

public class GameManager {

    public static void main(String[] args) {
        new MainMenu().display();
    }

    public static void startGame() {
        BattleManager battle = new BattleManager();

        int mode = Utility.getValidatedInt("Select Game Mode [1] PvP, [2] PvAI: ", 1, 2);
        int battleChoice = Utility.getValidatedInt("Select Battle Type [1] 1v1, [2] 3v3: ", 1, 2);
        int battleType = (battleChoice == 1) ? 1 : 3;

        if (mode == 1) {
            battle.startPvPBattle(battleType);
        } else {
            battle.startPvAIBattle(battleType);
        }

        if (playAgain()) startGame();
    }

    public static void startArcadeMode() {
        BattleManager battle = new BattleManager();
        BaseCharacter selectedHero = battle.selectSingleHeroForArcade();
        battle.startArcadeMode(selectedHero);
    }

    private static boolean playAgain() {
        String input = Utility.getValidatedString("Do you want to play again? (Y/N): ", "Y", "N");
        return input.equalsIgnoreCase("Y");
    }
}