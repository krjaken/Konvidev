import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.*;

/**
 * Created by s.ivanov on 01.07.2017.
 */
public final class RS extends GridBagConstraints {
    final static JTextArea textField = new JTextArea();

    public static void setTextLog(String temp){
        textField.append("\""+temp+"\""+";\n");
    }
    public static void setTextLog(String[][] mass){
        for (int i=0;i<mass.length;i++){
            for (int t=0;t<mass[0].length;t++){
                textField.append("\""+mass[i][t]+"\"; ");
            }
            textField.append("\n");
        }
    }


    static {
        aaa();
    }

    public static void aaa(){
        textField.setLayout(new GridBagLayout());
        textField.append("История операций по расчету данных Пеймантикс\n");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            //String sql = "SELECT groupAC from accentpay";
            connection.close();
        } catch (SQLException e) {

            String url = "jdbc:sqlite:C:/sqlite/db/FinDep.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e11) {
                System.out.println(e11.getMessage());
            }
        }
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

    public static void setTextLog(Double dt){

    }

    public static void setFeeParsentDB(String temp, JPanel mainPanel){

    }
    public static void setURLforCurDB(String temp, JPanel mainPanel){

    }
    public static String getFeeParsentDB(){
        String result="0.12%";

        return result;
    }
    public static String getURLforCurDB(){
        String result="http://www.cbr.ru/currency_base/daily.aspx?date_req=";

        return result;
    }

    public static void setTenancy(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }

    }
    public static String getTenancy(){
        String result="1132.00";
        return result;
    }

    public static void setTechSupport(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTechSupport(){
        String result="1132.00";

        return result;
    }

    public static void setKlientSupport(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getKlientSupport(){
        String result="1132.00";

        return result;
    }

    public static void setRiskM(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getRiskM(){
        String result="2264.00";

        return result;
    }

    public static void setAnalitik(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getAnalitik(){
        String result="100000.00";

        return result;
    }

    public static void setTesting(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTesting(){
        String result="120000.00";

        return result;
    }

    public static void setDevelopment(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                //this code add date to DB
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getDevelopment(){
        String result="422400.00";

        return result;
    }

}
