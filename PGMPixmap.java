package Projet_JAVA_Sami_Khalifa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PGMPixmap extends Pixmap {

    public PGMPixmap(String file, Integer size) {
        super(file, size);
    }

    public PGMPixmap(Pixmap pixmap) {
        super(pixmap);
    }

    private PGMPixmap(ArtPixel[][] artPixel, Integer size,String ligne1,String ligne2, String ligne3,int li,int col){
        super(artPixel,size,ligne1,ligne2,ligne3,li,col);
    }

    @Override
    public PGMPixmap doSomethingSpecial() {
        ArtPixel[][] artParam = getArtPixels();
        ArtPixel[][] newArtixel = new ArtPixel[artParam.length][artParam[0].length];
        for (int i = 0; i < artParam.length; i++) {
            for (int j = 0; j < artParam[0].length; j++) {
                newArtixel[i][j] = artParam[i][j];
                newArtixel[i][j].setColor( inverseColor( artParam[i][j].getColor() ) );
            }
        }
        return new PGMPixmap(newArtixel,getSize(),getLigne1(),getLigne2(),getLigne3(),getLigneArtPixel(),getColArtPixel() ) ;
    }

    private Color inverseColor(Color c){

        int max = Integer.parseInt(getLigne3());
        double newR = max - c.getR()  * max / 255 ;
        int newGris = (int)newR *255 / max;
        return new Color(newGris,newGris,newGris);

    }

    @Override
    public Pixmap getConcat(Pixmap p){
        System.out.println("Nouveau Pixmap PGM crée ! ");
        return new PGMPixmap(ConcatArtpixel(p) ,getSize(),getLigne1(),getLigne2(),getLigne3(),getLigneArtPixel(),p.getColArtPixel() );
    }

    @Override
    public void LireFichier(String fichier) {
        try {
            File fichierPGM = new File("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\Pixmap\\"+fichier);
            Scanner lecteur = new Scanner( fichierPGM );

            setLigne1(lecteur.nextLine() ); // Type de fichiers
            setLigne2(lecteur.nextLine() ); // Dimension du fichier
            setLigne3(lecteur.nextLine() ); //la valeur maximale des couleurs

            setColArtPixel(Integer.parseInt(getLigne2().substring(0,getLigne2().indexOf(' '))) ) ;
            setLigneArtPixel(Integer.parseInt(getLigne2().substring(getLigne2().indexOf(' ')+1)));

            ArtPixel[][] artPixel = new ArtPixel[getLigneArtPixel() / getSize()][getColArtPixel() / getSize()];

            //System.out.println("type : "+typeFichier+" -- dim : "+dimensions);
            //System.out.println("li : "+lignes+" -- col : "+colonnes+" -- gris : "+longueurGris);

            int ligneActu = 0; //va représenter la ligne actuelle comme un compteur
            while ( lecteur.hasNextLine() ) {

                String ligneActuelle = lecteur.nextLine();

                int coldispo = 0; //lignes disponibles en evitant les espaces

                for (int i = 0; i < ligneActuelle.length(); i++) { //le 'i' represente les caractères de chaque ligne

                    char val = ligneActuelle.charAt(i);

                    if(val != ' '){
                        Color c;

                        if(val == '0'){ //j'utilises les 2 methodes de création couleur
                            c = new Color("black");
                        }
                        else{

                            if(tryParseInt( getLigne3() )){ //on verifie que le fichier recu en parametre est bien de type pbm donc où la valeur du gris est spécifiée
                                double grisdouble = ( Double.parseDouble(val+"") / Integer.parseInt( getLigne3() ) * 255);
                                int gris = (int) grisdouble ;
                                c = new Color(gris,gris,gris);
                            }else{
                                int gris = Integer.parseInt(val+"");
                                c = new Color(gris,gris,gris);
                                //System.out.println("Probleme generation couleur gris");
                            }
                            //System.out.println("Gris : "+c.getColorname());
                        }

                        artPixel[ligneActu/ getSize()][coldispo/ getSize()] = new ArtPixel(c,getSize());
                        coldispo++;
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
            FileWriter Ecriturefichier = new FileWriter("C:\\Users\\mklfs\\IdeaProjects\\Hola\\src\\Projet_JAVA_Sami_Khalifa\\"+filename+".pgm");

            setLigne2(getColArtPixel() +" "+ getLigneArtPixel()) ;

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
                        int couleurGris;

                        if(!getLigne1().equals("P1")){
                            double grisdouble = ( Double.parseDouble(getArtPixels()[i][j].getColor().getR()+"") * Integer.parseInt( getLigne3() ) /255 );
                            couleurGris = (int) grisdouble ;
                        }else{
                            if(getArtPixels()[i][j].getColor().getColorname().equals("WHITE")){ //si ce n'est pas noir ce sera blanc
                                couleurGris = 0;
                            }else{
                                couleurGris = 1;
                            }
                        }
                        for (int l = 0; l < getSize(); l++) {
                            Ecriturefichier.write( couleurGris+" ");
                        }
                    }
                    Ecriturefichier.write("\n");
                }
            }
            System.out.println("Fichier : "+filename+".pgm"+" bien ecrit ! ");
            Ecriturefichier.close();
        }

        catch (IOException e) {
            System.out.println("Erreur écriture Fichier ! (Verifier le chemin ?) ");
            e.printStackTrace();
        }

    }
}
