package gameLogic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    // left click events
    private boolean leftClickPressed, leftClickDrag;
    private MouseEvent leftClickPressedEvent, leftClickDragEvent;

    // right click events
    private boolean rightClickPressed;
    private MouseEvent rightClickPressedEvent;

    public boolean isLeftClickPressed() {
        return this.leftClickPressed;
    }

    public MouseEvent getLeftClickPressedEvent() {
        return this.leftClickPressedEvent;
    }

    public boolean isLeftClickDrag() {
        return this.leftClickDrag;
    }

    public MouseEvent getLeftClickDragEvent() {
        return this.leftClickDragEvent;
    }

    public boolean isRightClickPressed() {
        return this.rightClickPressed;
    }

    public MouseEvent getRightClickPressedEvent() {
        return this.rightClickPressedEvent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClickPressedEvent = e;
            case MouseEvent.BUTTON3 -> this.rightClickPressedEvent = e;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // does not continue if mouse drag is not on the left button
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        this.leftClickDrag = true;
        this.leftClickDragEvent = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> this.leftClickPressed = true;
            case MouseEvent.BUTTON3 -> this.rightClickPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> {
                this.leftClickPressed = false;
                this.leftClickDrag = false;
            }
            case MouseEvent.BUTTON3 -> this.rightClickPressed = false;
        }
    }
}
/**
 * @Override
 *           public void mouseClicked(MouseEvent e) {
 *           switch (e.getButton()) {
 *           case MouseEvent.BUTTON1 -> this.leftClickEvent = e;
 *           case MouseEvent.BUTTON3 -> this.rightClickEvent = e;
 *           }
 *           }
 * 
 * @Override
 *           public void mouseEntered(MouseEvent e) {
 * 
 *           }
 * 
 * @Override
 *           public void mousePressed(MouseEvent e) {
 *           switch (e.getButton()) {
 *           case MouseEvent.BUTTON1 -> this.leftClickPressed = true;
 *           case MouseEvent.BUTTON3 -> this.rightClickPressed = true;
 *           }
 * 
 *           System.out.println(e.toString());
 *           System.out.println(String.format(
 *           "Clicked at x: %s, %s",
 *           e.getX(),
 *           e.getY()));
 *           }
 * 
 * @Override
 *           public void mouseReleased(MouseEvent e) {
 *           switch (e.getButton()) {
 *           case MouseEvent.BUTTON1 -> this.leftClickPressed = false;
 *           case MouseEvent.BUTTON3 -> this.rightClickPressed = false;
 *           }
 * 
 *           System.out.println(String.format(
 *           "Unclicked at x: %s, %s",
 *           e.getX(),
 *           e.getY()));
 *           }
 * 
 * @Override
 *           public void mouseExited(MouseEvent e) {
 *           }
 * 
 *           public boolean isLeftClickPressed() {
 *           return this.leftClickPressed;
 *           }
 * 
 *           public boolean isRightClickPressed() {
 *           return this.rightClickPressed;
 *           }
 * 
 *           public MouseEvent getLeftClickEvent() {
 *           return this.leftClickEvent;
 *           }
 * 
 *           public MouseEvent getRightClickEvent() {
 *           return this.rightClickEvent;
 *           }
 */