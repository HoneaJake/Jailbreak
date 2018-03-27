package jailbreak;

public class JailBreak {

    public static GameBoard GAME_BOARD;

    public static void main(String[] args){

        GAME_BOARD = new GameBoard();
        new Gui();

    }

    public static void restart(){

        GAME_BOARD = new GameBoard();

    }

}
