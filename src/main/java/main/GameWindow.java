package main;

import javax.swing.*;

public class GameWindow{
    private JFrame jFrame;
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        //spawns window in the center
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        //fits the size of the window to the preferred size for its
        // component.
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
