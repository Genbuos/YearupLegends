package main;
import java.awt.Color;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xdelta = 100, ydelta = 100;

    private BufferedImage img, subImg;





    public GamePanel(){

        mouseInputs = new MouseInputs(this);
        importImg();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {

        try(InputStream is = getClass().getResourceAsStream("/player_sprites.png")){
            if(is != null)
                img = ImageIO.read(is);


        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void setPanelSize() {
        //images are 32 by 32 px
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
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



        if(img != null) {
            subImg = img.getSubimage(1*64, 8*40, 64, 40);
            graphics.drawImage(subImg, (int) xdelta, (int) ydelta,128, 80, null);

        }else {
            graphics.setColor(Color.red);
            graphics.drawString("Image not loaded.", 10, 10);
        }


    }





}
