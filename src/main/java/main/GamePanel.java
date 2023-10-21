package main;
import java.awt.Color;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xdelta = 100, ydelta = 100;
    private float xDir =1f, yDir =1f;

    private Color color = new Color(150, 20, 90);

    private Random random;
    private int frames =0;

    private long lastCheck = 0;

    public GamePanel(){
        random = new Random();
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

    //this is a recursive function
    public void paintComponent(Graphics graphics){
        //calling the paintComponent method defined in the
        // Jpanel Class
        super.paintComponent(graphics);

        updateRectangle();
        graphics.setColor(color);

        graphics.fillRect(((int) xdelta), (int) ydelta, 200, 50);


    }

    private void updateRectangle(){
        xdelta+= xDir;
        if(xdelta > 400 || xdelta < 0){
            //reverses direction
            xDir*=-1;
            color = getRandColor();
        }



        ydelta+= yDir;
        if(ydelta > 400 || ydelta < 0){
            yDir*=-1;
            color = getRandColor();
        }

    }

    private Color getRandColor(){
        int r = random.nextInt(255);
        int b = random.nextInt(255);
        int g = random.nextInt(255);
        return new Color(r,g,b);
    }

}
