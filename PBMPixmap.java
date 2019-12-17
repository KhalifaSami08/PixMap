package Projet_JAVA_Sami_Khalifa;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PBMPixmap extends Pixmap {

    public PBMPixmap(String file, Integer size) {
        super(file, size);
    }

    public PBMPixmap(Pixmap pixmap) {
        super(pixmap);
    }

    private PBMPixmap(ArtPixel[][] artPixel,Integer size,String ligne1,String ligne2, String ligne3,int li,int col){
        super(artPixel,size,ligne1,ligne2,ligne3,li,col);
    }

    @Override
    public Pixmap getConcat(Pixmap p){
        System.out.println("Nouveau Pixmap PBM crée ! ");
        return new PBMPixmap(ConcatArtpixel(p) ,getSize(),getLigne1(),getLigne2(),getLigne3(),getLigneArtPixel(),p.getColArtPixel() ); //j'utilises ici ma methode ArtPixel
    }

    @Override
    public void LireFichier(String fichier){ // le fichier varie si on appeles la methode a partir d'un fichier ou d'un pixmap existant
        try {
            File fichierPBM = new File("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\Pixmap\\"+fichier);
            Scanner lecteur = new Scanner( fichierPBM );

            setLigne1(lecteur.nextLine() ); // Type de fichiers
            setLigne2(lecteur.nextLine() ); // Dimension du fichier
            setLigne3(lecteur.nextLine() ); //la valeur maximale des couleurs

            setColArtPixel(Integer.parseInt(getLigne2().substring(0,getLigne2().indexOf(' '))) ) ;
            setLigneArtPixel(Integer.parseInt(getLigne2().substring(getLigne2().indexOf(' ')+1)));

            ArtPixel[][] artPixel = new ArtPixel[getLigneArtPixel() / getSize()][getColArtPixel() / getSize()]; //je crée un nouveau Artpixel

            int ligneActu = 0; //ligne actuelle
            while ( lecteur.hasNextLine() ) {

                String ligneActuelle = lecteur.nextLine();
                int coldispo = 0; //lignes disponibles en evitant les espaces

                for (int i = 0; i < ligneActuelle.length(); i++) { //le 'i' represente les caractères de chaque ligne
                    Color c;
                    char val = ligneActuelle.charAt(i);

                    if(val != ' '){ //on veut sauter les espaces

                        if(val == '0'){ //si ce n'est pas noir ce sera blanc
                            c = new Color("white");
                        }else{
                            c = new Color("black");
                        }
                        //System.out.println("li : "+ligneActu+" col : "+coldispo+" val : "+val);
                        artPixel[ligneActu/ getSize()][coldispo/ getSize()] = new ArtPixel( c,getSize() );
                        coldispo++ ;
                    }
                }
                ligneActu++;
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
            FileWriter Ecriturefichier = new FileWriter("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\"+filename+".pbm");

            setLigne2(getColArtPixel() +" "+ getLigneArtPixel()) ;

            Ecriturefichier.write(getLigne1()+"\n");
            Ecriturefichier.write(getLigne2()+"\n");
            Ecriturefichier.write(getLigne3()+"\n");

            //System.out.println(getSize());
            //System.out.println(getArtPixels().length+" : "+getArtPixels()[0].length);
            //System.out.println(getLigne1());
            //System.out.println(getLigne2());
            //System.out.println(getLigne3());

            for (int longueurFichier = 0; longueurFichier < getArtPixels().length; longueurFichier++) {
                for (int longueurArtPixel = 0; longueurArtPixel < getSize(); longueurArtPixel++) {
                    for (int largeurFichier = 0; largeurFichier < getArtPixels()[0].length ; largeurFichier++) {
                        int blackorwhite;

                        if(getArtPixels()[longueurFichier][largeurFichier].getColor().getColorname().equals("WHITE")){ //si ce n'est pas noir ce sera blanc
                            blackorwhite = 0;
                        }else{
                            blackorwhite = 1;
                        }
                        for (int largeurArtPixel = 0; largeurArtPixel < getSize(); largeurArtPixel++) {
                            Ecriturefichier.write(blackorwhite+" ");
                        }
                    }
                    Ecriturefichier.write("\n");
                }
            }
            System.out.println("Fichier : "+filename+".pbm"+" bien ecrit ! ");
            Ecriturefichier.close();
        }
        catch (IOException e) {
            System.out.println("Erreur écriture Fichier ! (Verifier le chemin ?) ");
            e.printStackTrace();
        }
    }
}
