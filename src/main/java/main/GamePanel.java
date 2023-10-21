package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int xdelta = 100, ydelta = 100;


    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xdelta+= value;
        //updates changes on the panel
        repaint();
    }

    public void changeYDelta(int value){
        this.ydelta+= value;
        //updates the changes on the panel
        repaint();
    }

    public void setRectPostition(int x, int y){
        this.xdelta = x;
        this.ydelta =y;
        repaint();

    }
    public void paintComponent(Graphics graphics){
        //calling the paintComponent method defined in the
        // Jpanel Class
        super.paintComponent(graphics);
        graphics.fillRect( xdelta, ydelta, 200, 50);
    }
}
