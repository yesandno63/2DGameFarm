package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler KeyH;

    //Nombre de sprite par spriteSheet
    int RunframeCount = 6;
    int IdleframeCount = 4;

    public Player(GamePanel gp, KeyHandler KeyH) {
        this.gp = gp;
        this.KeyH = KeyH;

        animationFrames = new HashMap<>();

        setDefaultValues();
        loadAnimationFrames();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        int framesWidth = 64;
        int framesHeight = 64;

        framesDown = new BufferedImage[RunframeCount];
        framesUp = new BufferedImage[RunframeCount];
        framesLeft = new BufferedImage[RunframeCount];
        framesRight = new BufferedImage[RunframeCount];

        framesIdleDown = new BufferedImage[IdleframeCount];
        framesIdleUp = new BufferedImage[IdleframeCount];
        framesIdleLeft = new BufferedImage[IdleframeCount];
        framesIdleRight = new BufferedImage[IdleframeCount];

        BufferedImage runDown = null;
        BufferedImage runUp = null;
        BufferedImage runLeft = null;
        BufferedImage runRight = null;

        BufferedImage IdleDown = null;
        BufferedImage IdleUp = null;
        BufferedImage IdleLeft = null;
        BufferedImage IdleRight = null;

        try{
            runDown = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Run_Base/Run_Down-Sheet.png"));
            runUp = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Run_Base/Run_Up-Sheet.png"));
            runLeft = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Run_Base/Run_Left-Sheet.png"));
            runRight = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Run_Base/Run_Right-Sheet.png"));

            IdleDown = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Idle_Base/Idle_Down-Sheet.png"));
            IdleUp = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Idle_Base/Idle_Up-Sheet.png"));
            IdleLeft = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Idle_Base/Idle_Left.png"));
            IdleRight = ImageIO.read(getClass().getResourceAsStream("/res/TileSheet/Entities/Characters/Body_A/Animations/Idle_Base/Idle_Right-Sheet.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

        // subimage = extrait un rectangle dans l'image Run
        for(int i = 0; i < RunframeCount; i++){
            framesDown[i] = runDown.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesUp[i] = runUp.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesLeft[i] = runLeft.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesRight[i] = runRight.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
        }

        // subimage = extrait un rectangle dans l'image Idle
        for (int i = 0; i < IdleframeCount; i++) {
            framesIdleDown[i] = IdleDown.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesIdleUp[i] = IdleUp.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesIdleLeft[i] = IdleLeft.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
            framesIdleRight[i] = IdleRight.getSubimage(i * framesWidth, 0, framesWidth, framesHeight);
        }
    }

    public void loadAnimationFrames(){
        animationFrames.put("up", RunframeCount);
        animationFrames.put("down", RunframeCount);
        animationFrames.put("left", RunframeCount);
        animationFrames.put("right", RunframeCount);

        animationFrames.put("UpIdle", IdleframeCount);
        animationFrames.put("DownIdle", IdleframeCount);
        animationFrames.put("LeftIdle", IdleframeCount);
        animationFrames.put("RightIdle", IdleframeCount);

    }

    public void update() {

        boolean moving = false;

        //----Mouvement----
        if(KeyH.upPressed){
            setDirection("up");
            y -= speed;
            moving = true;
        }
        else if(KeyH.downPressed){
            setDirection("down");
            y += speed;
            moving = true;
        }
        else if(KeyH.leftPressed){
            setDirection("left");
            x -= speed;
            moving = true;
        }
        else if(KeyH.rightPressed){
            setDirection("right");
            x += speed;
            moving = true;
        }

        //----Animation----
        spriteCounter++;

        if(moving){
            if (spriteCounter > 10) {
                spriteCounter = 0;
                int maxFrames = animationFrames.get(direction);
                spriteNum = (spriteNum + 1) % maxFrames;
            }
        }
        else {
            //Passage Idle
            switch (direction) {
                case "up":
                    setDirection("UpIdle");
                    break;
                case "down":
                    setDirection("DownIdle");
                    break;
                case "left":
                    setDirection("LeftIdle");
                    break;
                case "right":
                    setDirection("RightIdle");
                    break;
            }

            spriteCounter++;
            if (spriteCounter > 20) {
                spriteCounter = 0;
                int maxFrames = animationFrames.get(direction);
                spriteNum = (spriteNum + 1) % maxFrames;
            }
        }
    }

    public void setDirection(String newDirection) {
        if (!direction.equals(newDirection)) {
            direction = newDirection;
            spriteNum = 0;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = framesUp[spriteNum];
                break;
            case "down":
                image = framesDown[spriteNum];
                break;
            case "left":
                image = framesLeft[spriteNum];
                break;
            case "right":
                image = framesRight[spriteNum];
                break;
            case "UpIdle":
                image = framesIdleUp[spriteNum];
                break;
            case "DownIdle":
                image = framesIdleDown[spriteNum];
                break;
            case "LeftIdle":
                image = framesIdleLeft[spriteNum];
                break;
            case "RightIdle":
                image = framesIdleRight[spriteNum];
        }
        g2.drawImage(image, x, y, gp.tileSize*scalePlayer, gp.tileSize*scalePlayer, null);
    }

}
