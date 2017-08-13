import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {
    private JLabel jlURLkukrs = new JLabel();
    private JLabel jlPersentFEE = new JLabel();
    private JButton jbSaveURL = new JButton("Save URL");
    private JButton jbFEE = new JButton("Save % FEE");
    public Settings() {
        setLayout(new GridBagLayout());
        RS.addComponent(Settings.this,jbFEE,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
    }
}
