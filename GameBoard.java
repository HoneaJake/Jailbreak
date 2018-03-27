package jailbreak;

import jailbreak.piece.AbstractGamePiece;
import jailbreak.piece.pieces.KingpinPiece;
import jailbreak.piece.pieces.OutlawPiece;
import jailbreak.piece.pieces.PossePiece;

import java.util.ArrayList;

public class GameBoard {

    public ArrayList<AbstractGamePiece> pieces = new ArrayList<>();

    public boolean outlawTurn = false, gameOver = false;
    public AbstractGamePiece.Type winner;

    public GameBoard() {

        KingpinPiece kingpinPiece = new KingpinPiece();

        pieces.add(kingpinPiece.setLocation(4,4));

        OutlawPiece outlawPiece = new OutlawPiece();

        pieces.add(outlawPiece.setLocation(4,6));
        pieces.add(outlawPiece.setLocation(4,5));
        pieces.add(outlawPiece.setLocation(4,3));
        pieces.add(outlawPiece.setLocation(4,2));

        pieces.add(outlawPiece.setLocation(2,4));
        pieces.add(outlawPiece.setLocation(3,4));
        pieces.add(outlawPiece.setLocation(5,4));
        pieces.add(outlawPiece.setLocation(6,4));

        PossePiece possePiece = new PossePiece();

        pieces.add(possePiece.setLocation(0,3));
        pieces.add(possePiece.setLocation(0,4));
        pieces.add(possePiece.setLocation(1,4));
        pieces.add(possePiece.setLocation(0,5));

        pieces.add(possePiece.setLocation(8,3));
        pieces.add(possePiece.setLocation(8,4));
        pieces.add(possePiece.setLocation(7,4));
        pieces.add(possePiece.setLocation(8,5));

        pieces.add(possePiece.setLocation(3,0));
        pieces.add(possePiece.setLocation(4,0));
        pieces.add(possePiece.setLocation(4,1));
        pieces.add(possePiece.setLocation(5,0));

        pieces.add(possePiece.setLocation(3,8));
        pieces.add(possePiece.setLocation(4,8));
        pieces.add(possePiece.setLocation(4,7));
        pieces.add(possePiece.setLocation(5,8));

    }

    public AbstractGamePiece getPieceAt(int x, int y) {

        for (AbstractGamePiece piece : pieces)
            if (piece.x == x && piece.y == y)
                return piece;

        return null;

    }

    public int getLeft(AbstractGamePiece.Type team){

        int left = 0;

        for(AbstractGamePiece piece : pieces)
            if(piece.getType() == team)
                left ++;

        return left;
    }

}
