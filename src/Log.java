import javax.swing.*;
import java.awt.*;

public class Log extends JPanel {
    private JScrollPane scrollPane=new JScrollPane(RS.textField);
    public Log(){
        setLayout(new GridBagLayout());
        RS.addComponent(Log.this,scrollPane,new Rectangle(0,0,1,1),GridBagConstraints.EAST,GridBagConstraints.BOTH);

    }
}
