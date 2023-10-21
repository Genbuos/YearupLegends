package inputs;

import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()){

          case  KeyEvent.VK_SPACE   :
              //Up
              gamePanel.changeYDelta(-5);
              break;
          case  KeyEvent.VK_A:
             //Left
              gamePanel.changeXDelta(-5);
              break;
          case  KeyEvent.VK_S:
              //Down
              gamePanel.changeYDelta(5);
              break;
          case  KeyEvent.VK_D:
              //Right
              gamePanel.changeXDelta(5);
              break;

      }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
