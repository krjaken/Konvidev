import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.LinkedHashSet;

/**
 * Created by s.ivanov on 01.07.2017.
 */
public final class RS extends GridBagConstraints {
    final static JTextArea textField = new JTextArea();
    private static LinkedHashSet<String> settingsList = new LinkedHashSet<String>();

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
        settingsList.add("URLCurrDownload");
        settingsList.add("ParsentFee");
        settingsList.add("Tenancy");
        settingsList.add("TechSupport");
        settingsList.add("KlientSupport");
        settingsList.add("RiskM");
        settingsList.add("Analitik");
        settingsList.add("Testing");
        settingsList.add("Development");
        textField.setLayout(new GridBagLayout());
        textField.append("История операций по расчету данных Пеймантикс\n");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE Settings "+
                    "(TypeSettings CHAR(200) NOT NULL, "+
                    "Value CHAR(200) NOT NULL, "+
                    "DateUpdate LONG NOT NULL)";
            statement.executeUpdate(sql);
            // сюда создать БД для хранения лога в виду файлов и массивов

            for (String s: settingsList){
                sql = "INSERT INTO Settings (TypeSettings,Value,DateUpdate) " +
                        "VALUES ('"+s+"', '0',0);";
                statement.executeUpdate(sql) ;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {

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
        //сюда пишем заполнения лога
    }

    public static void setFeeParsentDB(String temp, JPanel mainPanel){
        Double dt=0.00;
        try {
            dt = Double.parseDouble(temp);
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
        if (dt<=0){
            JOptionPane.showMessageDialog(mainPanel,"Значение не может быть отрицательным или 0");
        }else if (dt>=100){
            JOptionPane.showMessageDialog(mainPanel,"Значение не может быть больше 100%");
        }else {
            String sql = "ParsentFee";
            RS.addValueInDB(sql,temp,mainPanel);
        }
    }
    public static void setURLforCurDB(String temp, JPanel mainPanel){
        if (temp.contains("http://")||temp.contains("https://")){
            String sql = "URLCurrDownload";
            RS.addValueInDB(sql,temp,mainPanel);
        }else {
            JOptionPane.showMessageDialog(mainPanel,"Данные не являються ссылкой на сайт");
        }
    }
    public static String getFeeParsentDB(JPanel mainPane){
        //"0.12%"
        String sql ="ParsentFee";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }
    public static String getURLforCurDB(JPanel mainPane){
        //"http://www.cbr.ru/currency_base/daily.aspx?date_req=";
        String sql ="URLCurrDownload";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setTenancy(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "Tenancy";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }

    }
    public static String getTenancy(JPanel mainPane){
        //String result="1132.00";
        String sql ="Tenancy";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setTechSupport(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "TechSupport";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTechSupport(JPanel mainPane){
        //String result="1132.00";
        String sql ="TechSupport";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setKlientSupport(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "KlientSupport";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getKlientSupport(JPanel mainPane){
        //String result="1132.00";
        String sql ="KlientSupport";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setRiskM(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "RiskM";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getRiskM(JPanel mainPane){
        //String result="2264.00";
        String sql ="RiskM";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setAnalitik(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "Analitik";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getAnalitik(JPanel mainPane){
        //String result="100000.00";
        String sql ="Analitik";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setTesting(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "Testing";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTesting(JPanel mainPane){
        //String result="120000.00";
        String sql ="Testing";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }

    public static void setDevelopment(String temp, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(temp);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String sql = "Development";
                RS.addValueInDB(sql,temp,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getDevelopment(JPanel mainPane){
        //String result="422400.00";
        String sql ="Development";
        String result=getValueFromDB(sql,mainPane);
        return result;
    }
    public static void setSettingsList(String temp){
        settingsList.add(temp);
    }

    private static void addValueInDB(String sql,String value, JPanel mainPanel){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Settings SET Value = '"+value+"' WHERE TypeSettings = '"+sql+"'");
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(mainPanel,"Данные успешно обновлены");
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel,"Ошибка добавления данных в БД\n"+e);
        }
    }

    private static String getValueFromDB(String sql, JPanel mainPanel){
        String value;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Value FROM Settings WHERE TypeSettings = '"+sql+"'");
            value = rs.getString("Value");
            statement.close();
            connection.close();
        }catch (SQLException e) {
            value ="";
            JOptionPane.showMessageDialog(mainPanel,"Ошибка получения данных с БД\n"+e);
        }
        return value;
    }
}
