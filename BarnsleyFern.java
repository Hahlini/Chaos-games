import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/* 


w	a	b	c	d	e	f	p	Portion generated
ƒ1	0	0	0	0.16	0	0	0.01	Stem
ƒ2	0.85	0.04	−0.04	0.85	0	1.60	0.85	Successively smaller leaflets
ƒ3	0.20	−0.26	0.23	0.22	0	1.60	0.07	Largest left-hand leaflet
ƒ4	−0.15	0.28	0.26	0.24	0	0.44	0.07	Largest right-hand leaflet
*/


public class BarnsleyFern {
    Random instanceRandom = new Random();
    private int width;
    private int height;
    private int itterations;

    public BarnsleyFern(int width, int height, int itterations){
        this.width = width;
        this.height = height;
        this.itterations = itterations;


    }

    public double[] generatePoint(){
        double xValue = instanceRandom.nextDouble(7) - 3;
        double yValue = instanceRandom.nextDouble(10);

        double[] result = {xValue, yValue};
        return (result);
    }

    public double[] itteratePoint(double[] point){
        int random = instanceRandom.nextInt(100)+1;
        double[] newPoint = new double[2];

        if (random == 1){
            newPoint[0] = 0;
            newPoint[1] = 0.16 * point[1];
        } else if (random <= 86){
            newPoint[0] = 0.85*point[0] + 0.04*point[1];
            newPoint[1] = -0.04*point[0] + 0.85 * point[1] + 1.6;
        } else if (random <= 92){
            newPoint[0] = 0.2*point[0] - 0.26*point[1];
            newPoint[1] = 0.23*point[0] + 0.22*point[1] + 1.6;
        } else if (random <= 100){
            newPoint[0] = -0.15*point[0] + 0.28*point[1];
            newPoint[1] = 0.26*point[0] + 0.24*point[1] + 0.44;
        }
        return(newPoint);
    }


    public static void main(String args[]){

        BarnsleyFern barnsleyFern = new BarnsleyFern(2000, 2000, 10000000);

        int a = 255;
        int r = 0;
        int g = 255; 
        int b = 0; 
        int p = (a<<24) | (r<<16) | (g<<8) | b; 

        BufferedImage img = null;
        img = new BufferedImage(barnsleyFern.width, barnsleyFern.height, BufferedImage.TYPE_INT_ARGB);
        File f = null;

        double[] point = barnsleyFern.generatePoint();

        for (int i = 0; i < barnsleyFern.itterations; i++){
            point = barnsleyFern.itteratePoint(point);
            img.setRGB((int)((point[0] + 3.0) * (2000 / 7)) , (int)(point[1] * 200), p); 
        }


        try
            {
                f = new File("images\\BarnsleyFern.png");
                ImageIO.write(img, "png", f);
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }

    }


}