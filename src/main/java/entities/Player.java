package entities;

import main.Game;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utils.HelpMethods.*;
import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animationTick, aniIndex, aniSpeed = 15;

    private int playerAction = IDLE;

    private boolean left, up, right, down, jump;

    private boolean moving = false, attacking = false;

    private float playerSpeed = 2.0f;

    private int[][] lvlData;

    private float xDrawOffset = 20 * Game.SCALE;
    private float yDrawOffset = 10 * Game.SCALE;

    private float airSpeed = 0f;

    private float gravity = 0.04f * Game.SCALE;

    private float jupmSpeed = -2.25f * Game.SCALE;

    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    private boolean inAir = false;





    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x ,y, 25 * Game.SCALE, 28 *Game.SCALE);
    }

    public void update(){
        updatePos();
        updateAniTick();
        setAnimation();

    }

    public void render(Graphics graphics){
        graphics.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),100, 80, null);
//        drawHitbox(graphics);
    }




    private void updateAniTick() {

        animationTick++;
        if(animationTick>= aniSpeed){
            animationTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
            }


        }
    }
    private void setAnimation() {
        int startAni = playerAction;

        if(moving){
            playerAction = RUNNING;
        }else {
            playerAction = IDLE;
        }
        if(inAir){
            if(airSpeed < 0){
                playerAction = JUMP;

            }else {
                playerAction = JUMP;
            }
        }

        if(attacking)
            playerAction = ATTACK_1;

        if(startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        animationTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;
        if(jump)
            jump();

        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if(left)
            xSpeed -=  playerSpeed;
        if(right )
            xSpeed += playerSpeed;

        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if(inAir){

            if(CanMoveHere(hitbox.x,  hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0)
                    restInAir();
                 else
                    airSpeed = fallSpeedAfterCollision;

                updateXPos(xSpeed);
            }

        }else
            updateXPos(xSpeed);


        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jupmSpeed;
    }

    private void restInAir() {
        inAir = false;
        airSpeed = 0;
    }


    private void updateXPos(float xSpeed) {
                if(CanMoveHere(hitbox.x +xSpeed, hitbox.y,  hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;

        }
                else {
                    hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
                }
    }

    private void loadAnimations() {



        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[8][8];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i*64, j*64, 60, 64);
                }

            }



    }

    public void loadLvlData(int [][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }


    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }
}
