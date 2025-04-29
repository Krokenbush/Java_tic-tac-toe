package game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tournament {

    public static int playGame(int n, int m, int k, int p, boolean rhomb, Player pl1, Player pl2) {
        final Game game = new Game(false, pl1, pl2);
        int result;
        do {
            result = game.play(new TicTacToeBoard(n, m, k, p, rhomb)); // :NOTE: возможно, более просто решение: получить эталонную доску при создании доски и далее перед каждой игрой просто создавать ее копию
            System.out.println(pl1.getName() + " X " + pl2.getName() + " Game result: " + result);
        } while (result == -1 || result == 0);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите количество участников турнира: "); 
        int count = sc.nextInt();
        System.out.println("Введите размеры поля: ");
        int n = sc.nextInt();
        int m = sc.nextInt();
        System.out.println("Введите k: ");
        int k = sc.nextInt();
        System.out.println("Введите p: ");
        int p = sc.nextInt();
        System.out.println("Введите \"true\" для игры в ромбе, иначу \"false\": ");
        boolean rhomb = sc.nextBoolean();
        sc.close();

        RandomPlayer[] upper = new RandomPlayer[count];
        RandomPlayer[] down = new RandomPlayer[count];
        RandomPlayer[] list = new RandomPlayer[count];

        int upIdx = 0;
        int downIdx = 0; 
        
        for (int i = 0; i < count; ++i) {
            upper[i] = new RandomPlayer(n, m);
            upper[i].setName("Player" + (i + 1));
            list[i] = upper[i];
            upIdx++;
        } 

        Random rnd = ThreadLocalRandom.current();
  
        for (int i = upIdx - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
        
            RandomPlayer temp = upper[i];
            upper[i] = upper[j];
            upper[j] = temp;
        }

        int nowPlace = 1;
        while (upIdx > 1 || downIdx > 1) {
            if (downIdx > 1) {
                for (int i = 0; i < downIdx / 2; ++i) {
                    int result = playGame(n, m, k, p, rhomb, down[i], down[downIdx - i - 1]);
                    if (result == 2) {
                        RandomPlayer temp = down[i];
                        down[i] = down[downIdx - i - 1];
                        down[downIdx - i - 1] = temp;
                    }
                }
                for (int i = (downIdx / 2 + downIdx % 2); i < downIdx; ++i) {
                    down[i].setPlace(nowPlace);
                }
                downIdx = downIdx / 2 + downIdx % 2;
            }
            nowPlace++;
            if (upIdx > 1) {
                for (int i = 0; i < upIdx / 2; ++i) { 
                    int result = playGame(n, m, k, p, rhomb, upper[i], upper[upIdx - i - 1]);
                    if (result == 2) {
                        RandomPlayer temp = upper[i];
                        upper[i] = upper[upIdx - i - 1];
                        upper[upIdx - i - 1] = temp;
                    }
                }
                for (int i = (upIdx / 2 + upIdx % 2); i < upIdx; ++i) {
                    down[downIdx++] = upper[i];
                }
                upIdx = upIdx / 2 + upIdx % 2;
            }
            
        }

        int result = playGame(n, m, k, p, rhomb, upper[0], down[0]);

        if (result == 1) {
            down[0].setPlace(nowPlace++);
            upper[0].setPlace(nowPlace);
        } else {
            upper[0].setPlace(nowPlace++);
            down[0].setPlace(nowPlace);
        }

        for (int i = 0; i < count; ++i) {
            System.out.println(list[i].getName() + " : " + (nowPlace - list[i].getPlace() + 1));
        }
    }
}
