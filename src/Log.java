import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Log extends JPanel {
    Map<JLabel, Boolean> list = new HashMap<JLabel, Boolean>();

    public Log(JFrame mainFrame) {
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        RS.addComponent(Log.this, new JScrollPane(panel), new Rectangle(0, 0, 1, 1), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0));
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Log";
            ResultSet rs = statement.executeQuery(sql);
            int num = 0;

            JLabel URL = new JLabel("URL");
            JLabel CurrList = new JLabel("Курсы");
            JLabel ConverCurrList = new JLabel("Курсы конверт");
            JLabel FileCSV = new JLabel("Исходник");
            JLabel PartnersGroup = new JLabel("Гр. Продавцы");
            JLabel PartnersSum = new JLabel("Гр. Суммы");
            JLabel Result = new JLabel("Результат");
            JLabel ResultCSV = new JLabel("Результат в csv");

            while (rs.next()) {
                Timestamp timeStamp = rs.getTimestamp("TimeStamp");

                list.put(URL, rs.getString("URLCurr").equals("-"));
                list.put(CurrList, rs.getString("CurrList").equals("-"));
                list.put(ConverCurrList, rs.getString("ConverCurrList").equals("-"));
                list.put(FileCSV, rs.getString("FileCSV").equals("-"));
                list.put(PartnersGroup, rs.getString("PartnersGroup").equals("-"));
                list.put(PartnersSum, rs.getString("PartnersSum").equals("-"));
                list.put(Result, rs.getString("Result").equals("-"));
                list.put(ResultCSV, rs.getString("ResultCSV").equals("-"));

                RS.addComponent(panel, new JLabelLog(list, timeStamp), new Rectangle(0, num, 1, 1), GridBagConstraints.NORTH, GridBagConstraints.BOTH);
                num++;

                list.clear();
            }
            RS.addComponent(panel, new JPanel(), new Rectangle(0, num, 1, 1), GridBagConstraints.CENTER, GridBagConstraints.BOTH);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    class JLabelLog extends JLabel {
        public JLabelLog(Map<JLabel, Boolean> list, Timestamp timestamp) {
            JButton button = new JButton("Подробней");
            Border etched = BorderFactory.createEtchedBorder();
            Border titled3 = BorderFactory.createTitledBorder(etched, String.valueOf(timestamp));
            JLabelLog.this.setBorder(titled3);
            JLabelLog.this.setPreferredSize(new Dimension(750, 130));
            JLabelLog.this.setLayout(new GridBagLayout());
            JLabelLog.this.setVisible(true);
            JLabel label = new JLabel();
            label.setLayout(new GridBagLayout());
            label.setBorder(etched);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        new CreateLogWindow(timestamp);
                    } catch (Exception ee) {
                        System.out.println(ee);
                    }
                }
            });

            RS.addComponent(JLabelLog.this, button, new Rectangle(0, 0, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE);
            RS.addComponent(JLabelLog.this, label, new Rectangle(1, 0, 1, 1), GridBagConstraints.WEST, GridBagConstraints.BOTH);

            int i = 0;
            int y = 0;
            for (Map.Entry<JLabel, Boolean> entry : list.entrySet()) {
                if (entry.getValue()) {
                    RS.addComponent(label, new JLabel(entry.getKey().getText()), new Rectangle(i, y, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE);
                    i++;
                    RS.addComponent(label, new JLabel(new ImageIcon("imgNOT.gif")), new Rectangle(i, y, 1, 1), GridBagConstraints.EAST, GridBagConstraints.NONE);
                    i++;
                    System.out.println("true");
                } else {
                    RS.addComponent(label, new JLabel(entry.getKey().getText()), new Rectangle(i, y, 1, 1), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
                    i++;
                    RS.addComponent(label, new JLabel(new ImageIcon("imgOK.gif")), new Rectangle(i, y, 1, 1), GridBagConstraints.EAST, GridBagConstraints.NONE);
                    i++;
                    System.out.println("folse");
                }
                if (i == 4) {
                    i = 0;
                    y++;
                }
            }
        }

        class CreateLogWindow {
            public CreateLogWindow(Timestamp timestamp) {
                JPanel panelDate = new JPanel(new GridBagLayout());
                Border etched = BorderFactory.createEtchedBorder();
                panelDate.setBorder(etched);
                Map<String,String> mapDate = new HashMap<String, String>();

                String URL = "";
                String CurrList = "";
                String ConverCurrList = "";
                String FileCSVrs = "";
                String PartnersGroup = "";
                String PartnersSum = "";
                String Result = "";
                String ResultCSV = "";

                try {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * FROM Log WHERE TimeStamp = '"+timestamp+"'";
                    ResultSet rs = statement.executeQuery(sql);
                    System.out.println(timestamp);
                    URL = rs.getString("URLCurr");
                    CurrList = rs.getString("CurrList");
                    mapDate.put("Курсы валют на дату",CurrList);
                    ConverCurrList = rs.getString("ConverCurrList");
                    mapDate.put("Курсы ковертации на дату",ConverCurrList);
                    FileCSVrs = rs.getString("FileCSV");
                    mapDate.put("Исходный файл (csv)",FileCSVrs);
                    PartnersGroup = rs.getString("PartnersGroup");
                    mapDate.put("Информация по продавцам",PartnersGroup);
                    PartnersSum = rs.getString("PartnersSum");
                    mapDate.put("Информация по продавцам +Сумма",PartnersSum);
                    Result = rs.getString("Result");
                    mapDate.put("Invoice ",Result);
                    ResultCSV = rs.getString("ResultCSV");
                    mapDate.put("Файл для 1С (csv)",ResultCSV);
                    System.out.println(URL);
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }

                JPanel panel2 = new JPanel();

                int i=0;
                for (Map.Entry<String, String> entry : mapDate.entrySet()) {
                    RS.addComponent(panelDate, new JLabel(entry.getKey()), new Rectangle(0, i, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 10, 30));
                    RS.addComponent(panelDate, new JButton("Показать"), new Rectangle(1, i, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 10, 30));
                    RS.addComponent(panelDate, new JButton("Скачать"), new Rectangle(2, i, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 10, 30));
                    RS.addComponent(panelDate, new JLabel(), new Rectangle(3, i, 1, 1), GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL);
                    i++;
                }


                RS.addComponent(panelDate,panel2, new Rectangle(0, i, 4, 1), GridBagConstraints.NORTH, GridBagConstraints.BOTH);

                final String loadString = "Сайт загрузки курса";

                String html = "<html>" +
                        "<head>" +
                        "<meta charset='utf-8'>" +
                        "<title>Тег А</title>" +
                        "</head>" +
                        "<body>" +
                        "<p><a href=\"" + URL + "\">Сайт загрузки курса </a></p>" +
                        "</body>" +
                        "</html>";
                final JLabel link = new JLabel(html);
                String finalURL = URL;

                link.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        link.setBorder(new EmptyBorder(2, 2, 0, 0));
                        Desktop desktop;
                        if (Desktop.isDesktopSupported()) {
                            desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                                // launch browser
                                URI uri;
                                try {
                                    uri = new URI(finalURL);
                                    desktop.browse(uri);
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                } catch (URISyntaxException use) {
                                    use.printStackTrace();
                                }
                            }
                        }
                    }

                    public void mouseEntered(MouseEvent e) {
                        link.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    public void mouseExited(MouseEvent e) {
                        link.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                    public void mouseReleased(MouseEvent e) {
                        link.setBorder(new EmptyBorder(0, 0, 0, 0));
                    }
                });

                JFrame frame = new JFrame("Истрия операций за:  " + String.valueOf(timestamp));
                JPanel panel = new JPanel(new GridBagLayout());
                frame.setMinimumSize(new Dimension(800, 800));
                frame.setVisible(true);
                frame.setLayout(new GridBagLayout());
                RS.addComponent(frame, panel, new Rectangle(0, 0, 1, 1), GridBagConstraints.CENTER, GridBagConstraints.BOTH);
                RS.addComponent(panel, link, new Rectangle(0, 0, 1, 1), GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 15, 0));
                RS.addComponent(panel, new JPanel(), new Rectangle(1, 0, 1, 1), GridBagConstraints.EAST, GridBagConstraints.NONE);
                RS.addComponent(panel, new JPanel(), new Rectangle(2, 0, 1, 1), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
                RS.addComponent(panel, panelDate, new Rectangle(0, 1, 3, 1), GridBagConstraints.SOUTH, GridBagConstraints.BOTH);

            }
        }
    }
}