package Projet_JAVA_Sami_Khalifa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Pixmap {

    private String file;
    private Integer Size;

    private Pixmap pixmap;
    private ArtPixel[][] artPixels;

    private String ligne1;
    private String ligne2;
    private int ligneArtPixel;
    private int colArtPixel;
    private String ligne3;

    public Pixmap(String file, Integer size) {
        this.file = file;
        this.Size = size;
        LireFichier(getFile());
    }

    public Pixmap(Pixmap pixmap) {
        this.artPixels = pixmap.getArtPixels();
        this.setSize(pixmap.getSize());
        this.ligne1 = pixmap.getLigne1();
        this.ligne2 = pixmap.getLigne2();
        this.ligne3 = pixmap.getLigne3();
        this.ligneArtPixel = pixmap.getLigneArtPixel();
        this.colArtPixel = pixmap.getColArtPixel();
    }

    // 3e Constructeur pour crée un Pixmap grace a la concatnation
    public Pixmap(ArtPixel[][] artPixel,Integer size,String ligne1,String ligne2, String ligne3,int li,int col){
        this.artPixels = artPixel;
        this.Size = size;
        this.ligne1 = ligne1;
        this.ligne2 = ligne2;
        this.ligne3 = ligne3;
        this.ligneArtPixel = li;
        this.colArtPixel = col;
    }

    //Getters et Setters

    public String getLigne1() {
        return ligne1;
    }

    public void setLigne1(String ligne1) {
        this.ligne1 = ligne1;
    }

    public String getLigne2() {
        return ligne2;
    }

    public void setLigne2(String ligne2) {
        this.ligne2 = ligne2;
    }

    public int getLigneArtPixel() {
        return ligneArtPixel;
    }

    public void setLigneArtPixel(int ligneArtPixel) {
        this.ligneArtPixel = ligneArtPixel;
    }

    public int getColArtPixel() {
        return colArtPixel;
    }

    public void setColArtPixel(int colArtPixel) {
        this.colArtPixel = colArtPixel;
    }

    public String getLigne3() {
        return ligne3;
    }

    public void setLigne3(String ligne3) {
        this.ligne3 = ligne3;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getSize() {
        return Size;
    }

    public void setSize(Integer size) {
        this.Size = size;
    }

    public Pixmap getPixmap() {
        return pixmap;
    }

    public void setPixmap(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    public ArtPixel[][] getArtPixels() {
        return artPixels;
    }

    public void setArtPixels(ArtPixel[][] artPixels) {
        this.artPixels = artPixels;
    }

    public void setPixelColor(Integer xPos,Integer yPos,Color color){
        this.artPixels[xPos][yPos].setColor(color);
    }

    //Methodes A GENERER

    public void resize(double valeur){
        int newvaleur = (int)( getSize() * valeur );
        setSize( newvaleur );
        for (int i = 0; i < getArtPixels().length; i++) {
            for (int j = 0; j < getArtPixels()[0].length; j++) {
                this.artPixels[i][j].setSize(newvaleur);
            }
        }
        setLigneArtPixel((int)(getLigneArtPixel() * valeur));
        setColArtPixel((int)(getColArtPixel() * valeur));
    }

    public void insert(String position,Pixmap p){

        ArtPixel[][] insertArtPixel = p.getArtPixels();
        ArtPixel[][] thisArtPixel = getArtPixels();

        if(insertArtPixel.length > thisArtPixel.length || insertArtPixel[0].length > thisArtPixel[0].length){
            throw new RuntimeException("L'image a inserer est trop grande ! ");
        }else{

            //on initialise notre nouvelArtPiwel aux dimensions de l'artpixel actuel
            ArtPixel[][] newArt = new ArtPixel[thisArtPixel.length][thisArtPixel[0].length];
            for (int i = 0; i < thisArtPixel.length; i++) {
                for (int j = 0; j < thisArtPixel[0].length; j++) {
                    newArt[i][j] = thisArtPixel[i][j];
                }
            }

            int longueur_original = thisArtPixel.length; //on assigne nos valeurs
            int largeur_original = thisArtPixel[0].length; //on assigne nos valeurs
            int nouvellelongueur = insertArtPixel.length; //on assigne nos valeurs
            int nouvellelargeur = insertArtPixel[0].length; //on assigne nos valeurs

           //System.out.println(
           //        longueur_original+" : "+
           //        largeur_original+" : "+
           //        nouvellelongueur+" : "+
           //        nouvellelargeur );

            switch (position){

                case "top-left" :
                    for (int i = 0 ; i < nouvellelongueur; i++) {
                        for (int j = 0 ; j < nouvellelargeur; j++) {
                            //System.out.println("TOP-LEFT : "+i+" : "+j);
                            newArt[i][j] = insertArtPixel[i][j];
                        }
                    }
                    break;
                case "top-right" :
                    for (int i = 0 ; i < nouvellelongueur; i++) {
                        int k = 0; // Pour representer la colonne de l'artPixel
                        for (int j = largeur_original-nouvellelargeur ; j < largeur_original; j++) { //on commence a partir de la droite
                            //System.out.println("TOP-RIGHT : "+i+" : "+j+" : "+k);
                            newArt[i][j] = insertArtPixel[i][k];
                            k++;
                        }
                    }
                    break;
                case "center" :
                    int moitieligne = longueur_original / 2 - nouvellelongueur /2 ;
                    int moitiecol = largeur_original / 2 - nouvellelargeur /2 ;

                    //System.out.println("moitier : "+moitieligne+" : "+moitiecol);

                    int l = 0;
                    for (int i = moitieligne ; i < moitieligne + nouvellelongueur ; i++) {
                        int k = 0;
                        for (int j = moitiecol ; j < moitiecol + nouvellelargeur ; j++) {
                            //System.out.println("CENTER : "+i+" : "+j+" : "+l+" : "+k);
                            newArt[i][j] = insertArtPixel[l][k];
                            k++;
                        }
                        l++;
                    }
                    break;
                case "bottom-left" :
                    l = 0;
                    for (int i = longueur_original - nouvellelongueur ; i < longueur_original; i++) {
                        for (int j = 0 ; j < nouvellelargeur; j++) {
                            //System.out.println("BOTTOM-LEFT : "+i+" : "+j+" : "+l);
                            newArt[i][j] = insertArtPixel[l][j] ;
                        }
                        l++;
                    }
                    break;
                case "bottom-right" :
                    l = 0;
                    for (int i = longueur_original - nouvellelongueur ; i < longueur_original; i++) {
                        int k = 0;
                        for (int j = largeur_original - nouvellelargeur ; j < largeur_original; j++) {
                            //System.out.println("BOTTOM-RIGHT : "+i+" : "+j+" : "+k);
                            newArt[i][j] = insertArtPixel[l][k];
                            k++;
                        }
                        l++;
                    }
                    break;
                default:
                    System.out.println("Erreur METHODE INSERT");
                    break;
            }
            setArtPixels(newArt);
        }}

    //va servir pour verifier si la 3e ligne est un commentaire ou des dimensions (utile si on rapelles plusieurs fois la meme methode avec des fihiers differents
    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //Va servir pour aider la methode getConcat
    public ArtPixel[][] ConcatArtpixel(Pixmap p){ //je crée cette methode pour simplifier la concaténation des Pixmap

        ArtPixel[][] artActuel = getArtPixels();   //je récupere le ArtPixel en cours
        ArtPixel[][] artParam = p.getArtPixels(); //je récupere le ArtPixel recu en paramétre

        if(artActuel.length != artParam.length){    //je compare les lignes respecives
            throw new RuntimeException("Les dimensions ne sont pas compatibles avec la concaténation, Réesayez ! ");
        }else{

            int newSizeL = artActuel.length ; //on garde le même nombre de ligne
            int newSizeC = artActuel[0].length + artParam[0].length;
            //System.out.println("newsizeL : "+newSizeL+" C : "+newSizeC+" - "+artActuel[0].length+" "+artParam[0].length);

            ArtPixel[][] newartPixel = new ArtPixel[newSizeL][newSizeC]; //je crée l'ArtPixel que je vais utiliser

            for (int i = 0; i < newartPixel.length; i++) { //on remplis en premier avec l'artPixel recu en Parametre

                int r;
                int g;
                int b;

                for (int j = 0; j < artParam[0].length; j++) {
                    r = artParam[i][j].getColor().getR();
                    g = artParam[i][j].getColor().getG();
                    b = artParam[i][j].getColor().getB();
                    newartPixel[i][j] = new ArtPixel(new Color(r,g,b),getSize());
                }
                for (int j = artParam[0].length; j < newartPixel[0].length; j++) {
                    r = artActuel[i][j - artParam[0].length].getColor().getR();
                    g = artActuel[i][j - artParam[0].length].getColor().getG();
                    b = artActuel[i][j - artParam[0].length].getColor().getB();
                    newartPixel[i][j] = new ArtPixel(new Color(r,g,b),getSize());
                }
            }
            return newartPixel;
        }
    }

    //Quelques methodes Abstraites

    public abstract Pixmap getConcat(Pixmap p);

    public abstract void LireFichier(String fichier);

    public abstract void writeToFile(String filename);

}
