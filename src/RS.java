import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;

/**
 * Created by s.ivanov on 01.07.2017.
 */
public final class RS extends GridBagConstraints {

    final static LinkedHashSet<String> HashSetGroupAC = new LinkedHashSet<String>();
    final static LinkedHashSet<String> HashSetGroup1C = new LinkedHashSet<String>();
    final static JTextArea textField = new JTextArea();

    public static void getTextLog(String temp){
        textField.append("\""+temp+"\""+";\n");
    }
    public static void getTextLog(String[][] mass){
        for (int i=0;i<mass.length;i++){
            for (int t=0;t<mass[0].length;t++){
                textField.append("\""+mass[i][t]+"\"; ");
            }
            textField.append("\n");
        }
    }
    public static void getTextLog(Double dt){

    }


    static {
        aaa();
    }

    public static void aaa() {
        textField.setLayout(new GridBagLayout());
        textField.append("История операций по расчету данных Пеймантикс\n");
//        Calendar calendar = new GregorianCalendar();
//        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy");
//
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDepMap.db");
//            Statement statement = connection.createStatement();
//            String sql = "SELECT groupAC from accentpay";
//            String sql1 = "SELECT group1C from accentpay";
//            ResultSet temp1 = statement.executeQuery(sql);
//
//            while(temp1.next()) {
//                HashSetGroupAC.add(temp1.getString(1));
//            }
//            ResultSet temp2 = statement.executeQuery(sql1);
//            while(temp2.next()) {
//                HashSetGroup1C.add(temp2.getString(1));
//            }
//            connection.close();
//        } catch (SQLException e) {
//            JOptionPane.showInputDialog("Ошибка работы с массивом: "+e);
//        }
    }

    public static void addComponent(Container main, JComponent component, Rectangle location, int anchor, int fill) {
        addComponent(main, component, location, anchor, fill, new Insets(0, 0, 0, 0));
    }

    public static void addComponent(Container main, JComponent component, Rectangle location, int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = location.x;
        gbc.gridy = location.y;
        gbc.gridwidth = location.width;
        gbc.gridheight = location.height;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = (fill == GridBagConstraints.BOTH || fill == GridBagConstraints.HORIZONTAL) ? 1.0 : 0.0;
        gbc.weighty = (fill == GridBagConstraints.BOTH || fill == GridBagConstraints.VERTICAL) ? 1.0 : 0.0;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = insets;
        main.add(component, gbc);
    }

    public static String FifeChoose(JFrame frame, String dialog, String currentDir, String fileFilter, String ff){

        JFileChooser fileChooser = new JFileChooser();
        try {
            fileChooser.setCurrentDirectory(new File(currentDir));
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame,"Ошибка выбора файла: "+dialog+"\n Ошибка: "+e);
        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileFilter,ff);
        fileChooser.setDialogTitle(dialog);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showOpenDialog(null);
        //fileChooser.setFileFilter(filter);
        String string ="";
        //if (fileChooser.showOpenDialog(JFileChooser.APPROVE_OPTION)=="open")
        return fileChooser.getSelectedFile().getAbsolutePath();
    }
    public String DirectoriChoose(String dialog, String dataSQL){
        String temp ="";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\12\\Desktop\\конвидев"));
        fileChooser.setDialogTitle(dialog);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);

        temp ="";
        //if (fileChooser.showOpenDialog(JFileChooser.APPROVE_OPTION)=="open")

        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    public static void UpdateDB(JFrame frame, String[][] transMap){
        System.out.println("данные пришли на обновление ДБ");
        //String temp = "UPDATE accentpay SET nameOfColumn = "+transMap[i][1]+", group1C = 'sasasa' WHERE id = 1;";
        try {
            //Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDepMap.db");
            Statement statement = connection.createStatement();
            System.out.println("Данные направлены на обновление!");
            String sql = "DELETE FROM accentpay;\nREINDEX accentpay;\nVACUUM;";
            System.out.println("Данные очищены");
            statement.executeUpdate(sql);
            for(int i=1;i<transMap.length ;i++) {
                if (transMap[0][1].equals("nameOfColumn") && transMap[0][2].equals("numberColumn") && transMap[0][3].equals("tipeOfTransaktion") && transMap[0][4].equals("groupAC") && transMap[0][5].equals("group1C") && transMap[0][6].equals("group1CTransitFrom") && transMap[0][7].equals("group1CTransitFrom1") && transMap[0][8].equals("balans1C")) {
                    sql = "INSERT INTO accentpay (nameOfColumn, numberColumn, tipeOfTransaktion, groupAC, group1C, group1CTransitFrom, group1CTransitFrom1, balans1C)\nVALUES (\"" + transMap[i][1] + "\", \"" + transMap[i][2] + "\", \"" + transMap[i][3] + "\", \"" + transMap[i][4] + "\", \"" + transMap[i][5] + "\", \"" + transMap[i][6] + "\", \"" + transMap[i][7] + "\", \"" + transMap[i][8] + "\" );";
                    statement.executeUpdate(sql);
                    System.out.println("Данные обновлены успешно!");
                }
            }
            connection.close();
            JOptionPane.showMessageDialog(frame,"База данный обновлена успешно");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame,"База данных не обновлена, ошибка:\n"+e);
        }

    }


}
