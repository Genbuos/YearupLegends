package entities;

import utils.LoadSave;

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

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update(){
        updatePos();
        updateAniTick();
        setAnimation();

    }

    public void render(Graphics graphics){
        BufferedImage currentFrame = animations[playerAction][aniIndex];
        graphics.drawImage(currentFrame, (int) x, (int) y,200, 300, null);

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

        if(left && !right){
            x-= playerSpeed;
            moving = true;
        } else if(right && !left){
            x+= playerSpeed;
            moving = true;
        }

        if(up && !down){
            y-=playerSpeed;
            moving = true;
        } else if (down && !up) {
            y+=playerSpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][];

        // Define animation data (number of frames and atlas images)
        AnimationData[] animationData = {
                new AnimationData(8, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_IDLE_ATLAS)),
                new AnimationData(8, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_RUN_ATLAS)),
                new AnimationData(4, LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATK1_ATLAS))
                // Add more animations as needed
        };

        for (int animationType = 0; animationType < animationData.length; animationType++) {
            int numFrames = animationData[animationType].numFrames;
            BufferedImage img = animationData[animationType].atlas;

            BufferedImage[] animationFrames = new BufferedImage[numFrames];

            int subimageWidth;
            if (animationType == ATTACK_1) {
                // Calculate subimage width based on the smaller size of the attack frames
                subimageWidth = img.getWidth() / 5; // Manually set the number of frames in the attack animation
            } else {
                // For other animations, use the standard frame width
                subimageWidth = img.getWidth() / numFrames;
            }

            int frameHeight = img.getHeight();

            for (int i = 0; i < numFrames; i++) {
                animationFrames[i] = img.getSubimage(i * subimageWidth, 0, subimageWidth, frameHeight);
                animationFrames[i] = normalizeFrameSize(animationFrames[i], subimageWidth, frameHeight, 0);
            }

            animations[animationType] = animationFrames;

            if (animationType == ATTACK_1) {
                // Separate and normalize the frames of the attack animation
                animationFrames = normalizeAttackAnimation(animationFrames, subimageWidth, frameHeight);
                animations[animationType] = animationFrames;
            }
        }
    }

    private BufferedImage[] normalizeAttackAnimation(BufferedImage[] attackFrames, int frameWidth, int frameHeight) {
        BufferedImage[] normalizedAttackFrames = new BufferedImage[attackFrames.length];

        for (int i = 0; i < attackFrames.length; i++) {
            normalizedAttackFrames[i] = normalizeFrameSize(attackFrames[i], frameWidth, frameHeight, 0);
        }

        return normalizedAttackFrames;
    }
    private int calculateAnchorX(BufferedImage frame) {
        // Implement your logic to calculate the anchor point's X coordinate
        // This could involve detecting transparent pixels, etc.
        // For example, if your anchor point is in the center of the frame, you can use frame.getWidth() / 2.
        return frame.getWidth() / 2;
    }

    // Normalize the frame size and position the anchor point
    private BufferedImage normalizeFrameSize(BufferedImage frame, int targetWidth, int targetHeight, int anchorX) {
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        int x = (targetWidth - frame.getWidth()) / 2; // Center the frame horizontally
        int y = targetHeight - frame.getHeight(); // Position the frame at the bottom

        g.drawImage(frame, x, y, null);
        g.dispose();

        return result;
    }
    private static class AnimationData {
        int numFrames;
        BufferedImage atlas;

        public AnimationData(int numFrames, BufferedImage atlas) {
            this.numFrames = numFrames;
            this.atlas = atlas;
        }
    }


    // Helper method to map animation type to the corresponding atlas
    private String getAtlasForAnimation(int animationType) {
        switch (animationType) {
            case IDLE:
                return LoadSave.PLAYER_IDLE_ATLAS;
            case RUNNING:
                return LoadSave.PLAYER_RUN_ATLAS;
            case ATTACK_1:
                return LoadSave.PLAYER_ATK1_ATLAS;
            // Add cases for other animation types if needed
            default:
                return ""; // Handle invalid cases
        }
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
