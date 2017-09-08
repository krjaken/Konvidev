import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ModuleXLS {
    JPanel visualPane = new JPanel(new GridBagLayout());
    JPanel menuPane = new JPanel(new GridBagLayout());
    JTabbedPane jTabbedPane = new JTabbedPane();
    JButton sameButton = new JButton("same button");

    public ModuleXLS(){
        Border etched = BorderFactory.createEtchedBorder();
        menuPane.setBorder(etched);
        menuPane.setPreferredSize(new Dimension(1200,35));
        String[][] trtrt = new String[2][2];
        trtrt[0][0] = "00";trtrt[0][1] = "01";
        trtrt[1][0] = "10";trtrt[1][1] = "11";
        JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(1200,600));
        dialog.setTitle("MobuleXLS");
        dialog.setModal(true);
        new TabbXLS(jTabbedPane,"name1",trtrt);
        RS.addComponent(menuPane,sameButton,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE);
        RS.addComponent(menuPane,new JPanel(),new Rectangle(1,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);

        RS.addComponent(visualPane,menuPane,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1));
        RS.addComponent(visualPane,jTabbedPane,new Rectangle(0,1,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        dialog.add(visualPane);
        dialog.pack();
        dialog.setVisible(true);
    }
}
class TabbXLS{
    public TabbXLS(JTabbedPane jTabbedPane, String nameOfTab, String[][] data){
        Border etched = BorderFactory.createEtchedBorder();
        JTable visualTable = new JTable(new DefaultTableModel(50,100));
        visualTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(visualTable);
        scrollPane.setBorder(etched);

        jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        jTabbedPane.addTab(nameOfTab,scrollPane);
    }
}
