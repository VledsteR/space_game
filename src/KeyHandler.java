import java.awt.event.*;

public class KeyHandler implements KeyListener{

    public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, spacePressed = false, enterPressed = false, escPressed = false, shiftkeyHeld = false;
    public boolean pressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W ) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
            pressed = false;
        }
        if (code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
            pressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE)
        {
            escPressed = true;
            pressed = false;
        }
        if (code == KeyEvent.VK_SHIFT)
        {
            shiftkeyHeld = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE)
        {
            escPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT)
        {
            shiftkeyHeld = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
}





