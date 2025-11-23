package HeroCraft.Core;

import HeroCraft.Character.BaseCharacter;

public class GameManager {

    public static void main(String[] args) {
        new MainMenu().display();
    }

    public static void startGame() {
        BattleManager battle = new BattleManager();

        printModeSelectionUI();
        int mode = Utility.getValidatedInt(" » ", 1, 2);

        printBattleTypeSelectionUI();
        int battleChoice = Utility.getValidatedInt(" » ", 1, 2);
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
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║        Play Again? (Y/N)          ║");
        System.out.println("╚═══════════════════════════════════╝");
        String input = Utility.getValidatedString(" Enter choice: ", "Y", "N");
        return input.equalsIgnoreCase("Y");
    }
    private static void printModeSelectionUI() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                 SELECT GAME MODE                 ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  [1] » Player vs Player (PvP)                    ║");
        System.out.println("║  [2] » Player vs AI (PvAI)                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
    private static void printBattleTypeSelectionUI() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  SELECT BATTLE TYPE              ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  [1] » 1 vs 1 Battle                             ║");
        System.out.println("║  [2] » 3 vs 3 Battle                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}