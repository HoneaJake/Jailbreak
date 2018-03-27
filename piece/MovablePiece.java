package jailbreak.piece;

import jailbreak.Gui;
import jailbreak.JailBreak;

import java.awt.*;
import java.util.ArrayList;

public class MovablePiece extends AbstractGamePiece {

    public static final ArrayList<Point> FORBIDDEN = new ArrayList<>();

    static {

        FORBIDDEN.add(new Point(0, 0));
        FORBIDDEN.add(new Point(8, 0));
        FORBIDDEN.add(new Point(8, 8));
        FORBIDDEN.add(new Point(0, 8));
        FORBIDDEN.add(new Point(4, 4));

    }

    public boolean canMove(int targetX, int targetY) {

        if ((x != targetX && y != targetY) || JailBreak.GAME_BOARD.getPieceAt(targetX, targetY) != null)
            return false;

        if (FORBIDDEN.contains(new Point(targetX, targetY)))
            return false;

        for (int x = Math.min(targetX, this.x); x < Math.max(targetX, this.x); x++)
            if (JailBreak.GAME_BOARD.getPieceAt(x, y) != null && !isAtSameSpot((MovablePiece) JailBreak.GAME_BOARD.getPieceAt(x, y)))
                return false;


        for (int y = Math.min(targetY, this.y); y < Math.max(targetY, this.y); y++)
            if (JailBreak.GAME_BOARD.getPieceAt(x, y) != null && !isAtSameSpot((MovablePiece) JailBreak.GAME_BOARD.getPieceAt(x, y)))
                return false;

        return true;

    }

    public boolean isCaptured() {

        AbstractGamePiece[] horizontalPieces = {JailBreak.GAME_BOARD.getPieceAt(x - 1, y), JailBreak.GAME_BOARD.getPieceAt(x + 1, y)};

        boolean horizontalCapture, verticalCapture;
        horizontalCapture = verticalCapture = true;

        for (AbstractGamePiece piece : horizontalPieces)
            if (piece != null && piece.getType() == getType())
                horizontalCapture = false;
            else if (piece == null)
                horizontalCapture = false;

        AbstractGamePiece[] verticalPieces = {JailBreak.GAME_BOARD.getPieceAt(x, y - 1), JailBreak.GAME_BOARD.getPieceAt(x, y + 1)};

        for (AbstractGamePiece piece : verticalPieces)
            if (piece != null && piece.getType() == getType())
                verticalCapture = false;
            else if (piece == null)
                verticalCapture = false;

        return horizontalCapture || verticalCapture;

    }

    public void drawPiece(Graphics graphics, Point selected) {

        if (selected.x == x && selected.y == y)
            return;

        int scale = Gui.SCALE;

        graphics.setColor(getType().getColor());
        int ovalScale = (int) (scale * 0.8);

        graphics.fillOval(x * scale + (int) (scale * 0.1), y * scale + (int) (scale * 0.1), ovalScale, ovalScale);

        if (JailBreak.GAME_BOARD.outlawTurn && getType() == Type.OUTLAW || !JailBreak.GAME_BOARD.outlawTurn && getType() == Type.POSSE) {

            graphics.setColor(Color.BLACK);

            graphics.drawOval(x * scale + (int) (scale * 0.1), y * scale + (int) (scale * 0.1), ovalScale, ovalScale);

        }

        graphics.setFont(new Font("arial", 0, 18));
        graphics.setColor(Color.BLACK);
        graphics.drawString(getAbbreviation(), x * scale + (int) (scale * 0.425), y * scale + (int) (scale * 0.575));

    }

    public boolean isAtSameSpot(MovablePiece piece) {

        return this.x == piece.x && this.y == piece.y;

    }

}
