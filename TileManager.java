import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileManager {

    public GamePanel gamePanel ;
    Tile[] tiles ;
    int[][] mapTileNum ;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel ;
        this.tiles = new Tile[10] ;
        getTilesImages();
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow] ;
        loadMap();
    } 

    public void getTilesImages()
    {
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/wall.png"));
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/water.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap ()
    {
        try{
            InputStream is = getClass().getResourceAsStream("map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
            int col = 0 ;
            int row = 0 ;
            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow)
            {
                String line = br.readLine();
                while (col < gamePanel.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]) ;
                    mapTileNum[col][row] = num ;
                    col ++ ;
                }
                if (col == gamePanel.maxScreenCol){
                    col = 0 ;
                    row ++ ;
                }
            }
            br.close();
        }catch(Exception e)
        {

        }
    }

    public void draw(Graphics2D g2)
    {
        int col = 0 ;
        int row = 0 ;
        int x = 0 ;
        int y = 0 ; 
        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {

            int tileNum = mapTileNum[col][row] ;

            g2.drawImage(tiles[tileNum].image , x , y , gamePanel.tileSize, gamePanel.tileSize , null);
            col += 1 ; 
            x += gamePanel.tileSize ;
            if (col == gamePanel.maxScreenCol){
                col = 0 ; 
                x = 0 ;
                row += 1 ;
                y += gamePanel.tileSize ;
            }
        }
    }
    
}
