package Projet_JAVA_Sami_Khalifa;

public class Main {
    public static void main(String[] args) {
        Integer ART_PIXEL_SIZE = 40;

        //Color operations

        Color blue = new Color("blue");
        Color orange = new Color("orange");
        Color red = new Color(255, 0, 0);

        //ArtPixel operations

        ArtPixel myPixel1 = new ArtPixel(red, ART_PIXEL_SIZE);
        ArtPixel myPixel2 = new ArtPixel(blue, ART_PIXEL_SIZE);

        myPixel1.setColor(orange);
        myPixel1.setSize(ART_PIXEL_SIZE*2);

        //Pixmap operations

        Pixmap myMap1 = new PBMPixmap("example.pbm", ART_PIXEL_SIZE);
        Pixmap myMap2 = new PGMPixmap("example.pgm", ART_PIXEL_SIZE);
        Pixmap myMap3 = new PPMPixmap("example.ppm", ART_PIXEL_SIZE);

        Pixmap myMap12 = new PGMPixmap(myMap1);
        Pixmap myMap13 = new PPMPixmap(myMap1);
        Pixmap myMap23 = new PPMPixmap(myMap2);

        Integer xPos = 0;
        Integer yPos = 0;

        Color black = new Color(0,0,0);
        Color green = new Color(0,255,0);

        myMap1.setPixelColor(xPos, yPos, black); //top left pixel set to black
        myMap2.setPixelColor(xPos, yPos, black);
        myMap3.setPixelColor(xPos, yPos, black);

        myMap1.setPixelColor(xPos, yPos, green); //top left pixel set to green
        myMap2.setPixelColor(xPos, yPos, green);
        myMap3.setPixelColor(xPos, yPos, green);

        myMap1.resize(2); //multiply dimensions of artPixels by two
        myMap2.resize(0.5); //divide dimensions of artPixels by two

        Pixmap emot1 = new PPMPixmap("emot1.ppm", ART_PIXEL_SIZE);
        Pixmap emot2 = new PGMPixmap("emot2.pgm", ART_PIXEL_SIZE);

        myMap3.insert("center", emot1);
        myMap3.insert("top-right", emot2);

        Pixmap myMap4 = myMap3.getConcat(myMap1); //do not modify myMap3

        myMap1.writeToFile("myMap1");
        myMap2.writeToFile("myMap2");
        myMap3.writeToFile("myMap3");
        myMap4.writeToFile("myMap4");

        myMap12.writeToFile("myMap12");
        myMap13.writeToFile("myMap13");
        myMap23.writeToFile("myMap23");
    }
}
