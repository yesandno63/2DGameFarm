package entity;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Entity {

    public int x, y;
    public int speed;

    public BufferedImage[] framesDown, framesUp, framesLeft, framesRight, framesIdleUp, framesIdleDown, framesIdleLeft, framesIdleRight;
    public String direction;
    int scalePlayer = 3;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Map<String, Integer> animationFrames;
}
