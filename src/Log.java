import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Log extends JPanel {
    public Log(JFrame mainFrame){
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panel);
        RS.addComponent(Log.this,scrollPane,new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0));
        Map<Timestamp, JButtonLog> mapLog = new HashMap<Timestamp, JButtonLog>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Log";
            ResultSet rs = statement.executeQuery(sql);
            int num=0;

            Map<JLabel,Boolean> list = new HashMap<JLabel, Boolean>();
            JLabel URL= new JLabel("URL");
            JLabel CurrList = new JLabel("Курсы");
            JLabel ConverCurrList = new JLabel("Курсы конверт");
            JLabel FileCSV = new JLabel("Исходник");
            JLabel PartnersGroup = new JLabel("Гр. Продавцы");
            JLabel PartnersSum = new JLabel("Гр. Суммы");
            JLabel Result = new JLabel("Результат");
            JLabel ResultCSV = new JLabel("Результат в csv");

            while (rs.next()){
                Timestamp timeStamp = rs.getTimestamp("TimeStamp");

                list.put(URL,rs.getString("URLCurr").equals("-"));
                list.put(CurrList,rs.getString("CurrList").equals("-"));
                list.put(ConverCurrList,rs.getString("ConverCurrList").equals("-"));
                list.put(FileCSV,rs.getString("FileCSV").equals("-"));
                list.put(PartnersGroup,rs.getString("PartnersGroup").equals("-"));
                list.put(PartnersSum,rs.getString("PartnersSum").equals("-"));
                list.put(Result,rs.getString("Result").equals("-"));
                list.put(ResultCSV,rs.getString("ResultCSV").equals("-"));

                mapLog.put(timeStamp,new JButtonLog(timeStamp));
                JLabel label = new JLabel();
                Border etched = BorderFactory.createEtchedBorder();
                Border titled3 = BorderFactory.createTitledBorder(etched, String.valueOf(timeStamp));
                label.setBorder(titled3);
                label.setLayout(new GridBagLayout());
                label.setPreferredSize(new Dimension(700,60));
                RS.addComponent(panel,label,new Rectangle(0,num,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
                RS.addComponent(label,mapLog.get(timeStamp),new Rectangle(0,num,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE);
                RS.addComponent(label,new JLabelLog(list),new Rectangle(1,num,1,1),GridBagConstraints.EAST,GridBagConstraints.BOTH);
                num++;
                addListner(mapLog.get(timeStamp), rs.getString("URLCurr"), rs.getString("CurrList"),rs.getString("ConverCurrList"),
                        rs.getString("FileCSV"), rs.getString("PartnersGroup"), rs.getString("PartnersSum"), rs.getString("Result"), rs.getString("ResultCSV"));
           list.clear();
            }
            RS.addComponent(panel,new JPanel(),new Rectangle(0,num,1,1),GridBagConstraints.EAST,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0));

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void addListner(JButton button, String URL,String CurrList,String ConverCurrList,String FileCSV,String PartnersGroup,String PartnersSum,String Result,String ResultCSV){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    createLogWindow(URL,CurrList,ConverCurrList,FileCSV,PartnersGroup,PartnersSum,Result,ResultCSV);

                } catch (Exception ee) {
                    System.out.println(ee);
                }
            }
        });
    }
    private void createLogWindow(String URL, String CurrList, String ConverCurrList, String FileCSV, String PartnersGroup, String PartnersSum, String Result, String ResultCSV){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800,800));
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        RS.addComponent(frame,panel,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
    }
}
class JButtonLog extends JButton{
    private String title;
    public JButtonLog(Timestamp timestamp){
        this.title= String.valueOf(timestamp);
        setText("Подробней");
    }
}
class JLabelLog extends JPanel{
    public JLabelLog(Map<JLabel,Boolean> list){
        JLabelLog.this.setLayout(new GridBagLayout());
        JLabelLog.this.setVisible(true);
        Border etched = BorderFactory.createEtchedBorder();
        JLabelLog.this.setBorder(etched);
        int y=0;
        int i= 0;
        for (Map.Entry<JLabel, Boolean> entry : list.entrySet()) {
            entry.getKey().setVisible(true);
            System.out.println(entry.getKey().getText());
                if (entry.getValue()) {
                    RS.addComponent(JLabelLog.this, new JLabel(entry.getKey().getText()), new Rectangle(i, y, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE);
                    i++;
                    RS.addComponent(JLabelLog.this, new JLabel(new ImageIcon("imgNOT.gif")), new Rectangle(i, y, 1, 1), GridBagConstraints.EAST, GridBagConstraints.NONE);
                    i++;
                    System.out.println("true");
                } else {
                    RS.addComponent(JLabelLog.this, new JLabel(entry.getKey().getText()), new Rectangle(i, y, 1, 1), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
                    i++;
                    RS.addComponent(JLabelLog.this, new JLabel(new ImageIcon("imgOK.gif")), new Rectangle(i, y, 1, 1), GridBagConstraints.EAST, GridBagConstraints.NONE);
                    i++;
                    System.out.println("folse");
                }
                if (i==4){
                    y++;
                    i=0;
                }
        }

    }
}

