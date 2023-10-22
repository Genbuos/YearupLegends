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

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xdelta = 100, ydelta = 100;

    private BufferedImage img;

    private BufferedImage[][] animations;

    private int animationTick, aniIndex, aniSpeed = 15;

    private int playerAction = IDLE;

    private int playerDirection = -1;

    private boolean moving = false;




    public GamePanel(){

        mouseInputs = new MouseInputs(this);
        importImg();

        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
            }

        }

    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try{
            if(is != null)
                img = ImageIO.read(is);


        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private void setPanelSize() {
        //images are 32 by 32 px
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

  public void setDirection(int direction){
    this.playerDirection = direction;

    moving = true;
  }

    public void setMoving(boolean moving){
        this.moving = moving;
    }
    private void updateAniTick() {

        animationTick++;
        if(animationTick>= aniSpeed){
            animationTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }
    private void setAnimation() {
        if(moving){
            playerAction = RUNNING;
        }else {
            playerAction = IDLE;
        }
    }

    private void updatePos() {
        if(moving){
            switch (playerDirection){
                case LEFT:
                    xdelta -= 5;
                    break;
                case UP:
                    ydelta -= 5;
                    break;
                case RIGHT:
                    xdelta+= 5;
                    break;
                case DOWN:
                    ydelta += 5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics graphics){
        //calling the paintComponent method defined in the
        // Jpanel Class
        super.paintComponent(graphics);
        updateAniTick();

        setAnimation();
        updatePos();
        graphics.drawImage(animations[playerAction][aniIndex], (int) xdelta, (int) ydelta,256, 160, null);




    }



}
