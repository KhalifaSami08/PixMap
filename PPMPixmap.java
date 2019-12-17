package Projet_JAVA_Sami_Khalifa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PPMPixmap extends Pixmap{

    public PPMPixmap(String file, Integer size) {
        super(file, size);
    }

    public PPMPixmap(Pixmap pixmap) {
        super(pixmap);
    }

    private PPMPixmap(ArtPixel[][] artPixel,Integer size,String ligne1,String ligne2, String ligne3,int li,int col){
        super(artPixel,size,ligne1,ligne2,ligne3,li,col);
    }

    @Override
    public Pixmap getConcat(Pixmap p){
        System.out.println("Nouveau Pixmap PPM crée ! ");
        return new PPMPixmap(ConcatArtpixel(p) , getSize(),getLigne1(),getLigne2(),getLigne3(),getLigneArtPixel(),p.getColArtPixel() );
    }

    @Override
    public void LireFichier(String fichier) {
        try {
            File fichierPPM = new File("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\Pixmap\\"+fichier);
            Scanner lecteur = new Scanner( fichierPPM );

            setLigne1(lecteur.nextLine() ); // Type de fichiers
            setLigne2(lecteur.nextLine() ); // Dimension du fichier
            setLigne3(lecteur.nextLine() ); //la valeur maximale des couleurs

            setColArtPixel(Integer.parseInt(getLigne2().substring(0,getLigne2().indexOf(' '))) ) ;
            setLigneArtPixel(Integer.parseInt(getLigne2().substring(getLigne2().indexOf(' ')+1)) );

            ArtPixel[][] artPixel = new ArtPixel[getLigneArtPixel() / getSize()][getColArtPixel() / getSize()];

            //System.out.println("type : "+typeFichier+" -- dim : "+dimensions);
            //System.out.println("li : "+lignes+" -- col : "+colonnes+" -- couleur max : "+coulMax);

            int ligne= 0;
            while ( lecteur.hasNextLine() ) {

                String lignecourante = lecteur.nextLine();
                String[] sansEsp = lignecourante.split("\\s+");
                int col = 0;
                for (int i = 0; i < sansEsp.length - 2; i++) {
                    if(i%3 == 0){
                        int couleurMax;
                        int r;
                        int g;
                        int b;
                        if(tryParseInt(getLigne3())){ //pour les conversions
                            couleurMax = Integer.parseInt(getLigne3());
                            r = Integer.parseInt(sansEsp[i]) / couleurMax * 255;
                            g = Integer.parseInt(sansEsp[i+1]) / couleurMax * 255;
                            b = Integer.parseInt(sansEsp[i+2]) / couleurMax * 255;
                        }else{
                            r = Integer.parseInt(sansEsp[i]) ;
                            g = Integer.parseInt(sansEsp[i+1]);
                            b = Integer.parseInt(sansEsp[i+2]);
                        }
                        artPixel[ligne / getSize()][col / getSize()] = new ArtPixel(new Color(r,g,b),getSize());
                        col++;
                    }
                }
                ligne++;
            }
            System.out.println("Fichier : "+getFile()+" bien lu ! ");
            setArtPixels( artPixel ) ;
            lecteur.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Erreur Lecture Fichier.");
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(String filename){

        try {
            FileWriter Ecriturefichier = new FileWriter("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\"+filename+".ppm");

            setLigne2(getColArtPixel() +" "+ getLigneArtPixel() );

            Ecriturefichier.write(getLigne1()+"\n");
            Ecriturefichier.write(getLigne2()+"\n");
            Ecriturefichier.write(getLigne3()+"\n");

            //System.out.println(getSize());
            //System.out.println(getArtPixels().length+" : "+getArtPixels()[0].length);
            //System.out.println(getLigne1());
            //System.out.println(getLigne2());
            //System.out.println(getLigne3());

            for (int i = 0; i < getArtPixels().length; i++) {
                for (int k = 0; k < getSize(); k++) {
                    for (int j = 0; j < getArtPixels()[0].length; j++) {
                        for (int l = 0; l < getSize(); l++) {

                            if(getLigne1().equals("P1")){
                                int couleurGris;
                                if(getArtPixels()[i][j].getColor().getColorname().equals("WHITE")){ //si ce n'est pas noir ce sera blanc
                                    couleurGris = 0;
                                }else{
                                    couleurGris = 1;
                                }
                                Ecriturefichier.write(couleurGris+"\t");

                            }else if( getLigne1().equals("P2") ){

                                double grisdouble = ( Double.parseDouble(getArtPixels()[i][j].getColor().getR()+"") * Integer.parseInt( getLigne3() ) /255 );
                                int couleurGris = (int) grisdouble ;
                                Ecriturefichier.write(couleurGris+"\t");

                            }else{
                                Ecriturefichier.write(getArtPixels()[i][j].getColor().getR()+" "+getArtPixels()[i][j].getColor().getG()+" "+getArtPixels()[i][j].getColor().getB()+"\t");
                            }
                        }
                    }
                    Ecriturefichier.write("\n");
                }
            }
            System.out.println("Fichier : "+filename+".ppm"+" bien ecrit ! ");
            Ecriturefichier.close();
        }

        catch (IOException e) {
            System.out.println("Erreur écriture Fichier ! (Verifier le chemin ?) ");
            e.printStackTrace();
        }

    }

}
