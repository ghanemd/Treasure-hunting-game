import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

    GamePanel gamePanel ;
    KeyHandler keyHandler ;

    public Player (GamePanel gamePanel , KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel ;
        this.keyHandler = keyHandler ;
        setDefaultValues();
        getImagePlayer();
        direction = "down" ;
    }

    public void setDefaultValues()
    {
        this.x = 100 ;
        this.y = 100 ;
        this.speed = 4 ;
    }

    public void getImagePlayer ()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_right_2.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()   
    {
        if (keyHandler.upPressed){
            direction = "up" ;
            this.y -= speed ;
        }
        if (keyHandler.downPressed){
            direction = "down" ;
            this.y += speed ;
        }
        if (keyHandler.leftPressed){
            direction = "left" ;
            this.x -= speed ;
        }
        if(keyHandler.rightPressed){
            direction = "right" ;
            this.x += speed ;
        }
        spriteCounter++ ;
        if (spriteCounter > 50)
        {
            if (spriteNumber == 1){
                spriteNumber = 2 ;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1 ;
            }
        }
    }

    public void draw (Graphics2D g2)
    {
        BufferedImage image = null ;
        switch (direction) {
            case "up":
                if(spriteNumber == 1){
                    image = up1 ;
                }
                if (spriteNumber == 2){
                    image = up2 ;
                }
                break;
            case "down":
                if (spriteNumber == 1){
                    image = down1 ; 
                }
                if (spriteNumber == 2){
                    image = down2 ; 
                }
                break ;
            case "left" :
                if(spriteNumber == 1){
                    image = left1 ;
                }
                if (spriteNumber == 2){
                    image = left2 ;
                }
                break ;
            case "right" :
                if (spriteNumber == 1){
                    image = right1 ;
                }
                if (spriteNumber == 2){
                    image = right2 ;
                }
                break ;
        }
        g2.drawImage(image,x,y,gamePanel.tileSize,gamePanel.tileSize,null);
    }


}
