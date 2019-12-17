package Projet_JAVA_Sami_Khalifa;
import java.util.HashMap;
import static java.lang.Integer.parseInt;

public class Color {

    private String Colorname;
    private int R,G,B;

    public Color(String colorname) {
        Colorname = colorname.toUpperCase(); //On vérifie que la valeur entrée est en Majuscule
        VerifName();
    }

    public Color(int r, int g, int b) {
       this.R = r;
       this.G = g;
       this.B = b;
       VerifRGB(r,g,b);
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }

    public String getColorname() {
        return Colorname;
    }

    private void VerifName(){

        boolean finded = false;

        for (int i = 0; i < ColorCode.values().length; i++) {

            if(ColorCode.values()[i].name().equals(Colorname)){
                finded = true;
                R = ColorCode.values()[i].getR();
                G = ColorCode.values()[i].getG();
                B = ColorCode.values()[i].getB();
                //System.out.println("Couleur trouvée par nom ! "+" R = "+R+" && G = "+G+" && B = "+B);
            }
        }

        if(!finded)
            System.out.println("Couleur non Trouvée , Réesayez SVP ! ");

    }

    private void VerifRGB(int r,int g,int b){

        for (int i = 0; i < ColorCode.values().length; i++) {

            if( ColorCode.values()[i].getR() == r &&
                ColorCode.values()[i].getG() == g &&
                ColorCode.values()[i].getB() == b )
            {
                Colorname = ColorCode.values()[i].name();
                //System.out.println("Couleur trouvée par RGB ! " +Colorname+" a la position : "+i);
            }
        }

    }
}
