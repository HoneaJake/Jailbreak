package jailbreak;

import jailbreak.piece.GamePiece;
import jailbreak.piece.MovablePiece;
import jailbreak.piece.pieces.KingpinPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public class Gui extends JFrame {

    public static final int SCALE = 75;

    private Point selected = new Point(-1, -1),
            mouseLoc = new Point(0, 0);

    public Gui() {

        setTitle("JailBreak");
        setSize(SCALE * 9 + 5, SCALE * 9 + 28);

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(JailBreak.GAME_BOARD.gameOver) {

                    JailBreak.restart();
                    return;

                }

                for (GamePiece piece : JailBreak.GAME_BOARD.pieces)
                    if (piece.isClicked(e.getX(), e.getY() - 15)) {

                        if (JailBreak.GAME_BOARD.outlawTurn && piece.getType() == GamePiece.Type.POSSE)
                            continue;
                        else if (!JailBreak.GAME_BOARD.outlawTurn && piece.getType() == GamePiece.Type.OUTLAW)
                            continue;

                        selected = new Point(piece.x, piece.y);
                        mouseLoc = new Point(e.getX(), e.getY() - 15);
                        repaint();
                        return;
                    }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(JailBreak.GAME_BOARD.gameOver)
                    return;

                MovablePiece piece = (MovablePiece) JailBreak.GAME_BOARD.getPieceAt(selected.x, selected.y);

                if (piece == null) {

                    selected = new Point(-1, -1);
                    return;

                }

                if (piece.x == e.getX() / SCALE && piece.y == (e.getY() - 15) / SCALE) {

                    selected = new Point(-1, -1);
                    mouseLoc = new Point(e.getX(), e.getY() - 15);
                    repaint();
                    return;

                }

                if (piece.canMove(e.getX() / SCALE, (e.getY() - 15) / SCALE)) {

                    piece.x = e.getX() / SCALE;
                    piece.y = (e.getY() - 15) / SCALE;

                    JailBreak.GAME_BOARD.outlawTurn = !JailBreak.GAME_BOARD.outlawTurn;

                }

                selected = new Point(-1, -1);
                mouseLoc = new Point(e.getX(), e.getY() - 15);
                repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if(JailBreak.GAME_BOARD.gameOver)
                    return;

                mouseLoc = new Point(e.getX(), e.getY() - 15);
                repaint();

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        setContentPane(new JPanel() {
            @Override
            public void paint(Graphics g) {

                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                boolean offsetColor = false;

                for (int x = 0; x < 9; x++)
                    for (int y = 0; y < 9; y++) {

                        g.setColor(offsetColor ? new Color(198, 158, 47) : new Color(252, 224, 146));
                        g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);

                        if (MovablePiece.FORBIDDEN.contains(new Point(x, y)) && JailBreak.GAME_BOARD.getPieceAt(x,y) == null) {

                            g.setColor(Color.BLACK);
                            g.drawLine(x * SCALE, y * SCALE, (x + 1) * SCALE, (y + 1) * SCALE);
                            g.drawLine(x * SCALE, (y + 1) * SCALE, (x + 1) * SCALE, y * SCALE);

                        }

                        offsetColor = !offsetColor;

                    }

                checkGameLogic();

                for (GamePiece piece : JailBreak.GAME_BOARD.pieces)
                    ((MovablePiece) piece).drawPiece(g, selected);

                if (JailBreak.GAME_BOARD.gameOver) {

                    g.setColor(new Color(120, 120, 120, 210));
                    g.fillRect(SCALE * 2, SCALE * 3, SCALE * 5, SCALE * 3);

                    g.setColor(new Color(255, 255, 255));
                    g.setFont(new Font("arial", 0, 25));

                    String text = "Team " + JailBreak.GAME_BOARD.winner.toString().toLowerCase().replace("_", " ") + " won!";

                    g.drawString(text, getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, getHeight() / 2);
                    g.drawString("Click to restart", getWidth() / 2 - g.getFontMetrics().stringWidth("Click to restart") / 2, getHeight() / 2 + 20);

                    return;

                }

                if (selected.x != -1) {

                    try {

                        GamePiece piece = JailBreak.GAME_BOARD.getPieceAt(selected.x, selected.y);

                        g.setColor(piece.getType().getColor());
                        int ovalScale = (int) (SCALE * 0.8);

                        g.fillOval(mouseLoc.x - SCALE / 2, mouseLoc.y - SCALE / 2, ovalScale, ovalScale);
                        g.setColor(Color.BLACK);
                        g.drawOval(mouseLoc.x - SCALE / 2, mouseLoc.y - SCALE / 2, ovalScale, ovalScale);

                        g.setFont(new Font("arial", 0, 18));
                        g.setColor(Color.BLACK);
                        g.drawString(piece.getAbbreviation(), mouseLoc.x - g.getFontMetrics().stringWidth(piece.getAbbreviation()), mouseLoc.y);

                    } catch (Exception e) {


                    }

                }

            }

        });


        while (true) {

            repaint();

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public void checkGameLogic() {

        Iterator<GamePiece> pieces = JailBreak.GAME_BOARD.pieces.iterator();

        while (pieces.hasNext()) {

            MovablePiece piece = (MovablePiece) pieces.next();

            if (piece.isCaptured()) {

                pieces.remove();

                if (piece instanceof KingpinPiece) {

                    JailBreak.GAME_BOARD.winner = GamePiece.Type.POSSE;
                    JailBreak.GAME_BOARD.gameOver = true;

                }

            }

            if (piece instanceof KingpinPiece) {

                if (((KingpinPiece) piece).hasEscaped()) {

                    JailBreak.GAME_BOARD.winner = GamePiece.Type.OUTLAW;
                    JailBreak.GAME_BOARD.gameOver = true;

                }

            }

        }

        if (!JailBreak.GAME_BOARD.gameOver) {

            if (JailBreak.GAME_BOARD.getLeft(GamePiece.Type.POSSE) == 0) {

                JailBreak.GAME_BOARD.winner = GamePiece.Type.OUTLAW;
                JailBreak.GAME_BOARD.gameOver = true;

            } else if (JailBreak.GAME_BOARD.getLeft(GamePiece.Type.OUTLAW) == 0) {

                JailBreak.GAME_BOARD.winner = GamePiece.Type.POSSE;
                JailBreak.GAME_BOARD.gameOver = true;

            }

        }

    }

}
