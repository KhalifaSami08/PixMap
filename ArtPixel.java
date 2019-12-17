package Projet_JAVA_Sami_Khalifa;

public class ArtPixel {

    private Color Color;
    private Integer Size;

    public ArtPixel(Color col, Integer art_Pixel_Size) {
        this.Color = col;
        this.Size = art_Pixel_Size;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color color) {
        this.Color = color;
    }

    public Integer getSize() {
        return Size;
    }

    public void setSize(Integer size) {
        this.Size = size;
    }

}
