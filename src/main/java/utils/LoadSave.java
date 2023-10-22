package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static BufferedImage GetSpriteAtlas(String fileName){

        try(InputStream is = LoadSave.class.getResourceAsStream("/" + fileName )){

                if(is != null){
                    return ImageIO.read(is);
                } else {
                    System.err.println("Resource not found");
                }


        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Failed to read: " + fileName);
        }
        return null;
    }

    public static int[][] GetLevelData(){
        int[][] lvldata = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                lvldata[j][i] = value;
            }
        }
        return lvldata;
    }
}
