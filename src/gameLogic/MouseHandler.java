package gameLogic;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseListener {
    public boolean leftClickPressed;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.leftClickPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.leftClickPressed = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    

}
