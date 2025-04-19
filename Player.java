import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

    GamePanel gamePanel ;
    KeyHandler keyHandler ;

    public final int screenX ;
    public final int screenY ;

    public Player (GamePanel gamePanel , KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel ;
        this.keyHandler = keyHandler ;

        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2) ;
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2) ;

        solidArea = new Rectangle();    //rectangle a bit smaller than the player 
        solidArea.x = 8 ;
        solidArea.y = 16 ;
        solidArea.width = 32 ;
        solidArea.height = 32 ;

        setDefaultValues();
        getImagePlayer();
        direction = "down" ;
    }

    public void setDefaultValues()
    {
        this.worldX = gamePanel.tileSize * 23 ;
        this.worldY = gamePanel.tileSize * 21  ;
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
        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            if (keyHandler.upPressed){
                direction = "up" ;
            }
            if (keyHandler.downPressed){
                direction = "down" ;
            }
            if (keyHandler.leftPressed){
                direction = "left" ;
            }
            if(keyHandler.rightPressed){
                direction = "right" ;
            }

            // CHECK TILE COLLISION
            collisionOn = false ;
            gamePanel.collisionChecker.checkTile(this);

            // IF THERE IS NO COLLISION , THE PLAYER CAN MOVE 

            if (!collisionOn){
                switch (direction) {
                    case "up" :  this.worldY -= speed ;  break ;
                    case "down" : this.worldY += speed ;  break ;
                    case "left" :  this.worldX -= speed ;  break ;
                    case "right" :  this.worldX += speed ;  break ;
                    default:  break;
                }
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
        g2.drawImage(image,screenX, screenY,gamePanel.tileSize,gamePanel.tileSize,null);
    }


}
