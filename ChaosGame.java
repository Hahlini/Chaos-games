import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ChaosGame{
        private int width = 4000;
        private int height = 4000;

        //triangle
        /* 
        double step = 1.0/2.0;
        int nCorners = 3;
        private int[] corner1 = {width, height};
        private int[] corner2 = {0, height};
        private int[] corner3 = {width/2, 0};
        private int[] corner4 = {};
        private int[] corner5 = {};
        private int[] corner6 = {};
        private int[] corner7 = {};
        private int[] corner8 = {};

        private int triangleFunction1(int x){
            return (2*x);
        }
        private int triangleFunction2(int x){
            return (-2*x);
        }
        public int[] generateTrianglePoint(){
            int max = width;
            int min = 0;
            int range = max - min + 1;
 
            int x = (int)(Math.random() * range) + min;
            int y = (int)(Math.random() * range) + min;

            System.out.println(x + ":" + y);

            if(y < triangleFunction1(x) && y < triangleFunction2(x)){
                int[] list = {x, y};
                return (list);
            } else {
                return(generateTrianglePoint());
            }
            
        }
        */

        //square with midpoints
        /*
        private double step = 2.0/3.0;
        private int nCorners = 8;
        private int[] corner1 = {0, 0};
        private int[] corner2 = {width, 0};
        private int[] corner3 = {0, height};
        private int[] corner4 = {width, height};
        private int[] corner5 = {width/2, 0};
        private int[] corner6 = {0, height/2};
        private int[] corner7 = {width/2, height};
        private int[] corner8 = {width, height/2};
        */

        //pentagon
        private int nCorners = 5;
        private double step = 1.0/1.61803398875;
        private int[] corner1 = {width/5, 0};
        private int[] corner2 = {width - width/5, 0};
        private int[] corner3 = {0, height / 5 * 3};
        private int[] corner4 = {width, height / 5 * 3};
        private int[] corner5 = {width/2, height};
        private int[] corner6 = {};
        private int[] corner7 = {};
        private int[] corner8 = {};

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

            ChaosGame chaosgame = new ChaosGame();
  
            // Create buffered image object
            BufferedImage img = null;
            img = new BufferedImage(chaosgame.width, chaosgame.height, BufferedImage.TYPE_INT_ARGB);
  
            // file object
            File f = null;

            int a = 255;
            int r = 255;
            int g = 255; 
            int b = 0; 
            int p = (a<<24) | (r<<16) | (g<<8) | b; 

            int[] point = chaosgame.generatePoint();

            for (int i = 0 ; i < 1000000; i++){
                point = chaosgame.itteratePoint(point);
                img.setRGB(point[0], point[1], p);             
            }

            try
            {
                f = new File("C:\\Users\\Johan Hahlin\\code\\java\\Fractals\\images\\fractal.png");
                ImageIO.write(img, "png", f);
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    
    
}