package entities;

import main.Game;
import utils.LoadSave;
import static utils.HelpMethods.CanMoveHere;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animationTick, aniIndex, aniSpeed = 20;

    private int playerAction = IDLE;

    private boolean left, up, right, down;

    private boolean moving = false, attacking = false;

    private float playerSpeed = 2.0f;

    private int[][] lvlData;

    private float xDrawOffSet = 21 * Game.SCALE;
    private float yDrawOffSet = 4 * Game.SCALE;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20*Game.SCALE, 28* Game.SCALE);
    }

    public void update(){
        updatePos();

        updateAniTick();
        setAnimation();

    }

    public void render(Graphics graphics){
        BufferedImage currentFrame = animations[playerAction][aniIndex];
        int scaledWidth = width * 2; // Modify this factor to adjust the player's size
        int scaledHeight = height * 2; // Modify this factor to adjust the player's size
        graphics.drawImage(currentFrame, (int) (x - xDrawOffSet) - 45 , (int) (y - yDrawOffSet) - 30 , scaledWidth , scaledHeight , null);
        drawHitbox(graphics);

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
        if(!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed =0;

        if(left && !right){
            xSpeed = -playerSpeed;

        } else if(right && !left){
            xSpeed = playerSpeed;

        }

        if(up && !down){
            ySpeed = -playerSpeed;

        } else if (down && !up) {
            ySpeed =playerSpeed;

        }

//        if(CanMoveHere(x+ xSpeed, y+ ySpeed, width, height, lvlData )){
//            this.x += xSpeed;
//            this.y += ySpeed;
//            moving = true;
//        }


        if(CanMoveHere(hitbox.x+ xSpeed, hitbox.y+ ySpeed, hitbox.width, hitbox.height, lvlData )){
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            x += xSpeed;
            y += ySpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][];

        // Define animation data in a more structured way
        AnimationData[] animationData = {
                new AnimationData(8, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_IDLE_ATLAS), IDLE),
                new AnimationData(8, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_RUN_ATLAS), RUNNING),
                new AnimationData(5, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATK1_ATLAS), ATTACK_1)
                // Add more animations as needed
        };

        for (int animationType = 0; animationType < animationData.length; animationType++) {
            AnimationData data = animationData[animationType];
            animations[animationType] = loadAndNormalizeAnimation(data);
        }
    }

    private BufferedImage[] loadAndNormalizeAnimation(AnimationData data) {
        int numFrames = data.numFrames;
        BufferedImage img = data.atlas;

        int subimageWidth = (data.animationType == ATTACK_1) ? img.getWidth() / 5 : img.getWidth() / numFrames;
        int frameHeight = img.getHeight();

        BufferedImage[] animationFrames = new BufferedImage[numFrames];

        for (int i = 0; i < numFrames; i++) {
            animationFrames[i] = img.getSubimage(i * subimageWidth, 0, subimageWidth, frameHeight);
            System.out.println(subimageWidth + frameHeight);
            animationFrames[i] = normalizeFrameSize(animationFrames[i], subimageWidth, frameHeight, 0);
        }

        if (data.animationType == ATTACK_1) {
            animationFrames = normalizeAttackAnimation(animationFrames, subimageWidth, frameHeight);
        }

        return animationFrames;
    }

    private static class AnimationData {
        int numFrames;
        BufferedImage atlas;
        int animationType; // Store the animation type

        public AnimationData(int numFrames, BufferedImage atlas, int animationType) {
            this.numFrames = numFrames;
            this.atlas = atlas;
            this.animationType = animationType;
        }
    }
    private BufferedImage[] normalizeAttackAnimation(BufferedImage[] attackFrames, int frameWidth, int frameHeight) {
        BufferedImage[] normalizedAttackFrames = new BufferedImage[attackFrames.length];

        for (int i = 0; i < attackFrames.length; i++) {
            normalizedAttackFrames[i] = normalizeFrameSize(attackFrames[i], frameWidth, frameHeight, 0);
        }

        return normalizedAttackFrames;
    }


    // Helper method to map animation type to the corresponding atlas

    private BufferedImage normalizeFrameSize(BufferedImage frame, int targetWidth, int targetHeight, int anchorX) {
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        int x = (targetWidth - frame.getWidth()) ; // Center the frame horizontally
        int y = targetHeight - frame.getHeight() ; // Position the frame at the bottom

        g.drawImage(frame, x, y, null);
        g.dispose();

        return result;
    }

    public void loadLvlData(int [][] lvlData){
    this.lvlData = lvlData;
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
}
