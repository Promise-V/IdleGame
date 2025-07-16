import java.sql.SQLOutput;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Player newplayer = new Player();
        newplayer.setBalance(200);
        LemonadeStand lmnstnd = new LemonadeStand();
        newplayer.buy(lmnstnd);
        System.out.println(lmnstnd.getRewardTime());
        System.out.println(lmnstnd.getLevel());
        System.out.println(newplayer.getBalance());
        System.out.println(lmnstnd.getProfit());
        System.out.println(newplayer.ownedInvestments);
        GameEngine engine = new GameEngine(newplayer);
        engine.start();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Investment24/7 where investments happen round the clock! \n" +
                                "1.BUY LEMONADE STAND\n" +
                                "2.CHECK BALANCE \n" +
                                "3.INCREMENT LEVEL \n"
        );
        System.out.println(newplayer.statTracker.getTotalEarnings());

    }
}
