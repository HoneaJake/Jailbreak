package jailbreak.piece;

import jailbreak.GameBoard;
import jailbreak.Gui;
import jailbreak.JailBreak;

import java.awt.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AbstractGamePiece implements Cloneable {

    public int x, y;

    public Type getType() {

        return getClass().getAnnotation(Info.class).type();

    }

    public String getAbbreviation() {

        return getClass().getAnnotation(Info.class).abbreviation();

    }

    public boolean isClicked(int mouseX, int mouseY){

        return mouseX >= x * Gui.SCALE && mouseX <= (x + 1) * Gui.SCALE && mouseY >= y * Gui.SCALE && mouseY <= (y + 1) * Gui.SCALE;

    }

    public AbstractGamePiece setLocation(int x, int y) {

        this.x = x;
        this.y = y;

        try {
            return (AbstractGamePiece) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {

        String abbreviation();

        Type type();

    }

    public enum Type {

        POSSE(new Color(140, 138, 127)),
        OUTLAW(new Color(244, 244, 244));

        private Color color;

        Type(Color color){

            this.color = color;

        }

        public Color getColor() {

            return color;

        }
    }

}
