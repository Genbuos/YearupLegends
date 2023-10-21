package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel(){

    }

    public void paintComponent(Graphics graphics){
        //calling the paintComponent method defined in the
        // Jpanel Class
        super.paintComponent(graphics);
        graphics.fillRect(100, 100, 200, 50);
    }
}
