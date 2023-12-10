package gameLogic;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseListener {
    private boolean leftClickPressed, rightClickPressed;
    private MouseEvent leftClickEvent, rightClickEvent;

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClickEvent = e;
            case MouseEvent.BUTTON3 -> this.rightClickEvent = e;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClickPressed = true;
            case MouseEvent.BUTTON3 -> this.rightClickPressed = true;
        }

        System.out.println(e.toString());
        System.out.println(String.format(
                "Clicked at x: %s, %s",
                e.getX(),
                e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClickPressed = false;
            case MouseEvent.BUTTON3 -> this.rightClickPressed = false;
        }

        System.out.println(String.format(
                "Unclicked at x: %s, %s",
                e.getX(),
                e.getY()));
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public boolean isLeftClickPressed() {
        return this.leftClickPressed;
    }

    public boolean isRightClickPressed() {
        return this.rightClickPressed;
    }

    public MouseEvent getLeftClickEvent() {
        return this.leftClickEvent;
    }

    public MouseEvent getRightClickEvent() {
        return this.rightClickEvent;
    }
}
