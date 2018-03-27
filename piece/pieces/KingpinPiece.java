package jailbreak.piece.pieces;

import jailbreak.JailBreak;
import jailbreak.piece.GamePiece;
import jailbreak.piece.MovablePiece;

import java.awt.*;
import java.util.ArrayList;

@GamePiece.Info(
        abbreviation = "K",
        type = GamePiece.Type.OUTLAW
)
public class KingpinPiece extends MovablePiece {

    private final ArrayList<Point> winLocations = new ArrayList<>();

    public KingpinPiece() {

        winLocations.add(new Point(1, 0));
        winLocations.add(new Point(2, 0));
        winLocations.add(new Point(0, 1));
        winLocations.add(new Point(0, 2));

        winLocations.add(new Point(1, 8));
        winLocations.add(new Point(2, 8));
        winLocations.add(new Point(0, 7));
        winLocations.add(new Point(0, 6));

        winLocations.add(new Point(8, 8));
        winLocations.add(new Point(8, 7));
        winLocations.add(new Point(8, 6));
        winLocations.add(new Point(8, 7));
        winLocations.add(new Point(8, 6));

        winLocations.add(new Point(8, 1));
        winLocations.add(new Point(8, 2));
        winLocations.add(new Point(7, 0));
        winLocations.add(new Point(6, 0));

    }

    @Override
    public boolean isCaptured() {

        GamePiece[] surroundingPieces = {JailBreak.GAME_BOARD.getPieceAt(x - 1, y), JailBreak.GAME_BOARD.getPieceAt(x + 1, y), JailBreak.GAME_BOARD.getPieceAt(x, y - 1), JailBreak.GAME_BOARD.getPieceAt(x, y + 1)};

        for (GamePiece piece : surroundingPieces)
            if ((piece != null && piece.getType() == getType()) || (piece == null))
                return false;

        return true;

    }

    public boolean hasEscaped() {

        return winLocations.contains(new Point(x, y));

    }

}
