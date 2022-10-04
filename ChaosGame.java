import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ChaosGame{
    private int width;
    private int height;
    private int itterations;
    private int nCorners;
    private double step;

    private int[] corner1 = {0, 0};
    private int[] corner2 = {0, 0};
    private int[] corner3 = {0, 0};
    private int[] corner4 = {0, 0};
    private int[] corner5 = {0, 0};
    private int[] corner6 = {0, 0};
    private int[] corner7 = {0, 0};
    private int[] corner8 = {0, 0};

    public ChaosGame(int height, int width, int itterations, double step, String type) {
        this.height = height;
        this.width = width;
        this.itterations = itterations;
        this.step = step;

        if(type == "Triangle"){
            nCorners = 3;
            int[] corner1 = {width, height};
            int[] corner2 = {0, height};
            int[] corner3 = {width/2, 0};

            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
        } else if (type == "Square +m") {
            nCorners = 8;
            int[] corner1 = {0, 0};
            int[] corner2 = {width, 0};
            int[] corner3 = {0, height};
            int[] corner4 = {width, height};
            int[] corner5 = {width/2, 0};
            int[] corner6 = {0, height/2};
            int[] corner7 = {width/2, height};
            int[] corner8 = {width, height/2};

            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
            this.corner4 = corner4;
            this.corner5 = corner5;
            this.corner6 = corner6;
            this.corner7 = corner7;
            this.corner8 = corner8;
        } else if (type == "Pentagon"){
            nCorners = 5;
            int[] corner1 = {width/5, 0};
            int[] corner2 = {width - width/5, 0};
            int[] corner3 = {0, height / 5 * 3};
            int[] corner4 = {width, height / 5 * 3};
            int[] corner5 = {width/2, height};

            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
            this.corner4 = corner4;
            this.corner5 = corner5;
        } else {
            throw new IllegalArgumentException("Ogiltig typ");
        }
    }

    public int[] generatePoint(){
        int max = width;
        int min = 0;
        int range = max - min + 1;
 
        int x = (int)(Math.random() * range) + min;
        int y = (int)(Math.random() * range) + min;

        int[] list = {x, y};
        return(list);
    }
    
    public int[] itteratePoint(int[] point){
        int x = point[0];
        int y = point[1];

        int corner = (int)(Math.random() * nCorners) + 1;

        int newX = 0;
        int newY = 0;
        if (corner == 1){
            newX = (int)(x + (corner1[0]-x)*step);
            newY = (int)(y + (corner1[1]-y)*step);
        } else if (corner == 2){
            newX = (int)(x + (corner2[0]-x)*step);
            newY = (int)(y + (corner2[1]-y)*step);
        } else if (corner == 3){
            newX = (int)(x + (corner3[0]-x)*step);
            newY = (int)(y + (corner3[1]-y)*step);
        } else if (corner == 4){
            newX = (int)(x + (corner4[0]-x)*step);
            newY = (int)(y + (corner4[1]-y)*step);
        } else if (corner == 5){
            newX = (int)(x + (corner5[0]-x)*step);
            newY = (int)(y + (corner5[1]-y)*step);
        } else if (corner == 6){
            newX = (int)(x + (corner6[0]-x)*step);
            newY = (int)(y + (corner6[1]-y)*step);
        } else if (corner == 7){
            newX = (int)(x + (corner7[0]-x)*step);
            newY = (int)(y + (corner7[1]-y)*step);
        } else if (corner == 8){
            newX = (int)(x + (corner8[0]-x)*step);
            newY = (int)(y + (corner8[1]-y)*step);
        }
           
        int[] newPoint = {newX, newY} ;
        return (newPoint);
    }

    public static void main(String args[]){
        ChaosGame[] preset = new ChaosGame[3];
        preset[0] = new ChaosGame(3500, 4000, 10000000, 1.0/2.0, "Triangle");
        preset[1] = new ChaosGame(4000, 4000, 10000000, 2.0/3.0, "Square +m");
        preset[2] = new ChaosGame(4000, 4000, 10000000, 1.0/1.61803398875, "Pentagon");

        int a = 255;
        int r = 255;
        int g = 255; 
        int b = 0; 
        int p = (a<<24) | (r<<16) | (g<<8) | b; 

        for (ChaosGame n : preset) {
            BufferedImage img = null;
            img = new BufferedImage(n.width, n.height, BufferedImage.TYPE_INT_ARGB);
            File f = null;
            int[] point = n.generatePoint();

            for (int i = 0 ; i < n.itterations; i++){
                point = n.itteratePoint(point);
                img.setRGB(point[0], point[1], p);             
            }
        
            try
            {
                f = new File("C:\\Users\\Johan Hahlin\\code\\java\\Fractals\\images\\ChaosGameFractal" + n.nCorners + "-" + n.width + "x" + n.height + ".png");
                ImageIO.write(img, "png", f);
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
}