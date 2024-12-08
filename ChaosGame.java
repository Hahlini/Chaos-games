import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ChaosGame{
    private int width;
    private int height;
    private int iterations;
    private double step;
    private String id;

    private boolean hasRequirements;
    private boolean TwoInRowNotNeighbor;
    private boolean NotLastCorner;
    private boolean NotOneAway;
    private boolean NotTwoAway;

    private ArrayList<int[]> corners = new ArrayList<>();

    public void addCorner(int x, int y){
        int[] corner = {x, y};
        corners.add(corner);
    }

    public void addMidpoints(){
        int size = corners.size();
        
        ArrayList<int[]> corners = new ArrayList<int[]>(this.corners);
        this.corners.clear();

        for (int i = 0; i < size; i++) {
            this.corners.add(corners.get(i));

            int index = (i + 1) % size; 
            int x = (corners.get(i)[0] + corners.get(index)[0]) / 2;
            int y = (corners.get(i)[1] + corners.get(index)[1]) / 2;
            addCorner(x, y);
        }
    }

    public void placeCorners(String type){
        String[] shape = type.split("\\+");

        switch (shape[0]) {
            case "Triangle":
                addCorner(width, height);
                addCorner(0, height);
                addCorner(width/2, 0);    
                break;
            case "Square":
                addCorner(0, 0);
                addCorner(width, 0);
                addCorner(width, height);
                addCorner(0, height);
                break;
            case "Pentagon":
                addCorner(width/5, height);
                addCorner(width * 4/5, height);
                addCorner(width * 99 / 100, height * 43 / 100);
                addCorner(width/2, height * 8 / 100);
                addCorner(width / 100, height * 43 / 100);
                break;
            case "Hexagon":
                addCorner(0, height/2);
                addCorner(width * 1 / 4, height * 7 / 100);
                addCorner(width * 3 / 4, height * 7 / 100);
                addCorner(width, height / 2);
                addCorner(width * 3 / 4, height * 93 / 100);
                addCorner(width * 1 / 4, height * 93 / 100);
                break;
            default:
                break;
        }

        if (shape.length > 1) {
            if (shape[1].equals("Midpoints")){
                addMidpoints();
            }            
        }
    }

    public ChaosGame(int height, int width, int iterations, double step, String type, String requirement) {
        this.height = height;
        this.width = width;
        this.iterations = iterations;
        this.step = step;
        this.hasRequirements = (requirement == "") ? false : true;
        this.id = type + "_" + (hasRequirements ? requirement + "_" : "");

        placeCorners(type);
        if (hasRequirements) prepRequirements(requirement);
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
    
    public int[] iteratePoint(int[] point){
        int[] corner = corners.get((int)(Math.random() * corners.size()));
        
        point[0] = (int)(point[0] + (corner[0]-point[0]) * step);
        point[1] = (int)(point[1] + (corner[1]-point[1]) * step); 
        
        return (point);
    }

    boolean twoInARow = false;
    int lastCorner;

    public int[] iteratePointReq(int[] point){
        int nCorners = corners.size();
        
        Boolean goal = false;
        int corner = (int)(Math.random() * nCorners);
        while(goal == false){
            corner = (int)(Math.random() * nCorners);
            if(twoInARow && TwoInRowNotNeighbor && (corner == (lastCorner + 1) % nCorners || (corner + 1) % nCorners == lastCorner)){
                corner = (int)(Math.random() * nCorners);
            } else if(NotLastCorner && corner ==lastCorner){
                corner = (int)(Math.random() * nCorners);
            } else if(NotOneAway && corner == (lastCorner + 1) % nCorners){
                corner = (int)(Math.random() * nCorners);
            } else if(NotTwoAway && corner == (lastCorner + 2) % nCorners){
                corner = (int)(Math.random() * nCorners);
            } else {
                goal = true;
            }
        }

        int[] target = corners.get(corner);
        twoInARow = lastCorner == corner;
        lastCorner = corner;
        point[0] = (int)(point[0] + (target[0]-point[0]) * step);
        point[1] = (int)(point[1] + (target[1]-point[1]) * step); 
        
        return (point);
    }

    public void prepRequirements(String requirement){
        String[] requirementList = requirement.split("-");

        for (String req : requirementList) {
            if(req.equals("TwoInRowNotNeighbor")){
                this.TwoInRowNotNeighbor = true;
            } 
            if(req.equals("NotLastCorner")) {
                this.NotLastCorner = true;
            }
            if(req.equals("NotOneAway")) {
                this.NotOneAway = true;
            } 
            if(req.equals("NotTwoAway")){
                this.NotTwoAway = true;
            }
        }
        
    }

    public void iterate(BufferedImage img, int color){
        int[] point = generatePoint();


        if (hasRequirements){
            for (int i = 0 ; i < iterations; i++){
                point = iteratePointReq(point);
                img.setRGB(point[0], point[1], color);
            }
        } else {
            for (int i = 0 ; i < iterations; i++){
                point = iteratePoint(point);
                img.setRGB(point[0], point[1], color);             
            }
        }
    }

    public void generateImage(){
        int a = 255;
        int r = 255;
        int g = 255; 
        int b = 0; 
        int color = (a<<24) | (r<<16) | (g<<8) | b; 

        BufferedImage img = null;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f = null;
        
        iterate(img, color);
    
        try{
            f = new File("images\\ChaosGameFractal_" + id + width + "x" + height + "-" + (int)(step*100) + ".png");
            ImageIO.write(img, "png", f);
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
        
    }


    public static void main(String args[]){

        
        //main
        
        ChaosGame[] preset = new ChaosGame[22];
        preset[0] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Triangle", "");
        preset[1] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Square+Midpoints", "");
        preset[2] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "NotLastCorner");
        preset[3] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "NotOneAway");
        preset[4] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "NotTwoAway");
        preset[5] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Square", "TwoInRowNotNeighbor");
        preset[6] = new ChaosGame(2000, 2000, 1000000, 1.0/1.61803398875, "Pentagon", "");
        preset[7] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "NotLastCorner");
        preset[8] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "NotOneAway");
        preset[9] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "NotTwoAway");
        preset[10] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Pentagon", "TwoInRowNotNeighbor");
        preset[11] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Hexagon", "");
        preset[11] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "");
        preset[12] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "NotLastCorner");
        preset[13] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "NotOneAway");
        preset[14] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Hexagon", "NotTwoAway");
        preset[15] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Square+Midpoints", "NotLastCorner-NotTwoAway");
        preset[16] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Hexagon", "NotLastCorner-NotTwoAway");
        preset[17] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Square+Midpoints", "NotLastCorner-TwoInRowNotNeighbor");
        preset[18] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Square+Midpoints", "NotLastCorner-NotOneAway");
        preset[19] = new ChaosGame(2000, 2000, 1000000, 2.0/3.0, "Triangle+Midpoints", "NotOneAway-NotTwoAway");
        preset[20] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Triangle+Midpoints", "NotOneAway-NotTwoAway");
        preset[21] = new ChaosGame(2000, 2000, 1000000, 1.0/2.0, "Triangle+Midpoints", "TwoInRowNotNeighbor");

        for (ChaosGame chaosGame : preset) {
            if (chaosGame != null) {
                chaosGame.generateImage();                
            }
        }
        
    }
       
}