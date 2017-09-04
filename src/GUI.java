import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class GUI extends JFrame {
    public JPanel panel = new JPanel(new GridBagLayout());
    private JTabbedPane jTabbedPane = new JTabbedPane();

    Settings settings = new Settings();
    Proekts proekts = new Proekts(GUI.this);
    PP pp = new PP(GUI.this);
    Log log = new Log(GUI.this);

    public GUI() throws ParseException {

        setTitle("PP");
        setLocation(100,100);

        jTabbedPane.addTab("PP",pp);
        jTabbedPane.add("Данные проектов",proekts);
        jTabbedPane.addTab("Log",log);
        jTabbedPane.addTab("Settings",settings);
        RS.addComponent(panel,jTabbedPane,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
        getContentPane().setLayout(new GridBagLayout());
        RS.addComponent(getContentPane(),panel,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
        setPreferredSize(new Dimension(800,800));
        setMinimumSize(new Dimension(800,800));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}