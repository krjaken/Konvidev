import javax.swing.*;
import java.awt.*;

public class Torte extends JPanel {
    static Integer[] values = {600, 175, 150, 50, 100, 125};
    public Torte() {
        int totalValue = calcTotalValue();
        int xC = 40;
        int yC = 60;
        Color colors[] = {
                Color.RED,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
                Color.MAGENTA,
                Color.PINK
        };
        int cD = 200;
        int windowWidth = 400;
        int windowHeight = 400;
}
    public static int calcTotalValue() {
        int buffer = 0;
        for (int i = 0; i < values.length; i++) {
            buffer += values[i];
        }
        return buffer;
    }
}