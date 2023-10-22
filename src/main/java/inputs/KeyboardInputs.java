package inputs;

import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;

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
              gamePanel.setDirection(UP);
              break;
          case  KeyEvent.VK_A:
             //Left
              gamePanel.setDirection(LEFT);
              break;
          case  KeyEvent.VK_S:
              //Down
              gamePanel.setDirection(DOWN);
              break;
          case  KeyEvent.VK_D:
              //Right
              gamePanel.setDirection(RIGHT);
              break;

      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){

            case  KeyEvent.VK_SPACE   :
            case  KeyEvent.VK_A:
            case  KeyEvent.VK_S:
            case  KeyEvent.VK_D:
                gamePanel.setMoving(false);

                break;

        }
    }
}
