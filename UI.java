//A class to display texts , item icons ...

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {     

    GamePanel gamePanel ;
    Font font_40 ;
    Font font_30 ;
    Font font_80B ;
    BufferedImage keyImage ;
    public boolean messageOn = false ;
    public String message = "" ;
    int messageCounter = 0 ;
    public boolean gameFinished = false ;
    double playTime ;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel ;
        font_40 = new Font("Arial", Font.PLAIN, 40) ;
        font_30 = new Font("Arial", Font.PLAIN, 30) ;
        font_80B = new Font("Arial", Font.BOLD, 80) ;
        OBJ_Key objKey = new OBJ_Key() ;
        keyImage = objKey.image ;
    }

    public void showMessage(String text){
        messageOn = true ;
        message = text ;
    }

    public void draw(Graphics2D g2){

        if(gameFinished){

            g2.setFont(font_40);
            g2.setColor(Color.WHITE);
            String text = "you found the treasure !" ;
            // Obtenir la largeur du texte en pixels :
            int textWidth = g2.getFontMetrics().stringWidth(text);
            int x = gamePanel.screenWidth / 2 - textWidth / 2;
            int y = gamePanel.screenHeight / 2 - (gamePanel.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(font_40);
            g2.setColor(Color.WHITE);
            text = "your time is : " + dFormat.format(playTime) +" ! " ;
            // Obtenir la largeur du texte en pixels :
            textWidth = g2.getFontMetrics().stringWidth(text);
            x = gamePanel.screenWidth / 2 - textWidth / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(font_80B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations !" ;
            // Obtenir la largeur du texte en pixels :
            textWidth = g2.getFontMetrics().stringWidth(text);
            x = gamePanel.screenWidth / 2 - textWidth / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
            g2.drawString(text, x, y);

            gamePanel.gameThread = null ;  //stop the game 

        }else{

            //KEYS :
            g2.setFont(font_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gamePanel.tileSize/2,gamePanel.tileSize/2 , gamePanel.tileSize,gamePanel.tileSize, null) ;
            g2.drawString(" x " + gamePanel.player.nbKeys , 66, 64);

            //TIME :
            playTime += (double)1/60 ;
            g2.drawString("Time:"+dFormat.format(playTime), gamePanel.tileSize*11, 64);

            //MESSAGES : 
            if(messageOn){
                g2.setFont(font_30);
                g2.drawString(message, gamePanel.tileSize/2 , gamePanel.tileSize*5);
                messageCounter++ ;
                if(messageCounter > 60){     //the text disappears after 2 seconds 
                    messageCounter = 0 ;
                    messageOn = false ;
                }
            }

        }
    }
}
