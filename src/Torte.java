import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Torte extends JPanel {

    Double[] values = {600.0, 175.0, 150.0, 50.0, 100.0, 125.0};
    Map<String, Double> valuesProekts =new HashMap<String, Double>();
    Double totalValue = calcTotalValue();
    int xC = 40;
    public int yC = 60;
    Color colors[] = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.MAGENTA,
            Color.PINK
    };
    public int cD = 200;

    public Torte(String group, String csvStart) {
        parsStringToValues(values, group);
        setLayout(new GridBagLayout());

    }

    public Double[] parsStringToValues(Double[] values, String data){


        return values;
    }

    public Double calcTotalValue() {
        Double buffer = Double.valueOf(0);
        for (int i = 0; i < values.length; i++) {
            buffer += values[i];
        }
        return buffer;
    }

    @Override
    public void paint(Graphics g) {
        int start  = 0;
        int steps = values.length;
        int stepSize = 0;
        for (int i = 1; i < steps; i++) {
            stepSize = getStepSize(values[i], totalValue);
            g.setColor(colors[i - 1]);
            g.fillArc(xC, yC, cD, cD, start, stepSize);
            start += stepSize;
        }
    }

    private int getStepSize(Double val, Double total) {
        return (int) ((360 * val) / total);
    }
}