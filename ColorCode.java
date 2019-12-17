package Projet_JAVA_Sami_Khalifa;

public enum ColorCode {

    BLACK(0,0,0),
    WHITE(255,255,255),
    RED(255,0,0),
    GREEN(0,255,0),
    BLUE(0,0,255),

    //Some added colors

    GRAY(128,128,128),
    YELLOW(255,255,0),
    PURPLE(255,0,255),
    ORANGE(255,165,0); //#FFA500

    private int R,G,B;

    ColorCode(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
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
}

