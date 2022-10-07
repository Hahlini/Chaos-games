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
    private int lastCorner;
    private String requirements;
    private String id;
    private boolean hasRequirements;
    private int[] corner0 = {0, 0};
    private int[] corner1 = {0, 0};
    private int[] corner2 = {0, 0};
    private int[] corner3 = {0, 0};
    private int[] corner4 = {0, 0};
    private int[] corner5 = {0, 0};
    private int[] corner6 = {0, 0};
    private int[] corner7 = {0, 0};


    public ChaosGame(int height, int width, int itterations, double step, String type, String requirements) {
        this.height = height;
        this.width = width;
        this.itterations = itterations;
        this.step = step;
        this.requirements = requirements;
        this.hasRequirements = (requirements == "") ? false : true;
        this.id = type + "_" + requirements + "_";


        if(type == "Triangle"){
            nCorners = 3;
            int[] corner0 = {width, height};
            int[] corner1 = {0, height};
            int[] corner2 = {width/2, 0};

            this.corner0 = corner0;
            this.corner1 = corner1;
            this.corner2 = corner2;
        } else if (type == "Square"){
            nCorners = 4;
            int[] corner0 = {0, 0};
            int[] corner1 = {width, 0};
            int[] corner2 = {width, height};
            int[] corner3 = {0, height};
            

            this.corner0 = corner0;
            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
        } else if (type == "Square+Midpoints") {
            nCorners = 8;
            int[] corner0 = {0, 0};
            int[] corner1 = {width, 0};
            int[] corner2 = {0, height};
            int[] corner3 = {width, height};
            int[] corner4 = {width/2, 0};
            int[] corner5 = {0, height/2};
            int[] corner6 = {width/2, height};
            int[] corner7 = {width, height/2};

            this.corner0 = corner0;
            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
            this.corner4 = corner4;
            this.corner5 = corner5;
            this.corner6 = corner6;
            this.corner7 = corner7;
        } else if (type == "Pentagon"){
            nCorners = 5;
            int[] corner0 = {width/5, height};
            int[] corner1 = {width * 4 / 5, height};
            int[] corner2 = {width * 99 / 100, height * 43 / 100};
            int[] corner3 = {width/2, height * 8 / 100};
            int[] corner4 = {width / 100, height * 43 / 100};

            this.corner0 = corner0;
            this.corner1 = corner1;
            this.corner2 = corner2;
            this.corner3 = corner3;
            this.corner4 = corner4;
        } else if (type == "Hexagon"){
            nCorners = 6;
            int[] corner0 = {0, height/2};
            int[] corner1 = {width * 1 / 4, height * 7 / 100};
            int[] corner2 = {width * 3 / 4, height * 7 / 100};
            int[] corner3 = {width, height / 2};
            int[] corner4 = {width * 3 / 4, height * 93 / 100};
            int[] corner5 = {width * 1 / 4, height * 93 / 100};

            this.corner0 = corner0;
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

        int corner = (int)(Math.random() * nCorners);

        int newX = 0;
        int newY = 0;
        if (corner == 0){
            newX = (int)(x + (corner0[0]-x)*step);
            newY = (int)(y + (corner0[1]-y)*step);
        } else if (corner == 1){
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
        } 
           
        int[] newPoint = {newX, newY} ;
        return (newPoint);
    }

    boolean twoInARow = false;

    public int[] itteratePoint(int[] point, String requirements){
        int x = point[0];
        int y = point[1];

        int corner = (int)(Math.random() * nCorners);
        
        if (twoInARow){
            while (corner == (lastCorner + 1) % nCorners || (corner + 1) % nCorners == lastCorner)
            corner = (int)(Math.random() * nCorners);
        }

        if (requirements == "NotLastCorner"){
            corner = (lastCorner + (int)(Math.random() * (nCorners-1) + 1)) % nCorners;
        } else if (requirements == "Not1Away"){
            corner = (lastCorner + (int)(Math.random() * (nCorners-1))) % nCorners;
        } else if (requirements == "Not2Away"){
            corner = (lastCorner + (int)(Math.random() * (nCorners-1))+3) % nCorners;
        }

        this.twoInARow = false;
        
        int newX = 0;
        int newY = 0;

        

        if (requirements == "2InRowNotNeighbour" && corner == lastCorner){
            this.twoInARow = true;
        }
        if (corner == 0){
            newX = (int)(x + (corner0[0]-x)*step);
            newY = (int)(y + (corner0[1]-y)*step);
        } else if (corner == 1){
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
        } 
           
        int[] newPoint = {newX, newY} ;
        lastCorner = corner;
        return (newPoint);

        
    }


    public static void main(String args[]){
        ChaosGame[] preset = new ChaosGame[16];
        preset[0] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Triangle", "");
        preset[1] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Square+Midpoints", "");
        preset[2] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "NotLastCorner");
        preset[3] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "Not1Away");
        preset[4] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "Not2Away");
        preset[5] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "2InRowNotNeighbour");
        preset[6] = new ChaosGame(2000, 2000, 1000000, 1.0/1.61803398875, "Pentagon", "");
        preset[7] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "NotLastCorner");
        preset[8] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "Not1Away");
        preset[9] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "Not2Away");
        preset[10] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "2InRowNotNeighbour");
        preset[11] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Hexagon", "");
        preset[12] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "NotLastCorner");
        preset[13] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "Not1Away");
        preset[14] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "Not2Away");
        preset[15] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Triangle", "2InRowNotNeighbour");

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

            if (n.hasRequirements){
                for (int i = 0 ; i < n.itterations; i++){
                    point = n.itteratePoint(point, n.requirements);
                    img.setRGB(point[0], point[1], p);
                }
            } else {
            for (int i = 0 ; i < n.itterations; i++){
                point = n.itteratePoint(point);
                img.setRGB(point[0], point[1], p);             
                }
            }
        
            try
            {
                f = new File("images\\ChaosGameFractal_" + n.id + n.width + "x" + n.height + ".png");
                ImageIO.write(img, "png", f);
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
}