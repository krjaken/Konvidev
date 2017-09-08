import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Created by s.ivanov on 01.07.2017.
 */
public final class RS extends GridBagConstraints {
    private static LinkedHashSet<String> settingsList = new LinkedHashSet<String>();
    private static Timestamp timestamp;

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

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE Settings "+
                    "(TypeSettings CHAR(200) NOT NULL, "+
                    "Value CHAR(200) NOT NULL, "+
                    "DateUpdate LONG NOT NULL)";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Log "+
                    "(TimeStamp TIMESTAMP NOT NULL, "+
                    "URLCurr CHAR(200), "+
                    "CurrList BLOB, "+//тут надо хранить массив курсы скчаные с сайта
                    "ConverCurrList BLOB, "+//тут храним массив курсов конвертации
                    "FileCSV BLOB, "+//тут храним файл ЦСВ что мы загрузили
                    "PartnersGroup BLOB, "+//список групп продавцев
                    "PartnersSum BLOB, "+
                    "Result BLOB, "+
                    "ResultCSV BLOB)";
            statement.executeUpdate(sql);

            for (String s: settingsList){
                sql = "INSERT INTO Settings (TypeSettings,Value,DateUpdate) " +
                        "VALUES ('"+s+"', '0',0);";
                statement.executeUpdate(sql) ;
            }

            sql = "CREATE TABLE Proekts "+
                    "(NameFromCSV TEXT PRIMARY KEY, "+
                    "IDProekt TEXT, "+
                    "NameOfProekt TEXT, "+
                    "NameOfProekt1C TEXT, "+
                    "TypeOfProject TEXT)";
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {

        }
    }

    /*
    Геттеры и сетеры для БД таблица Proekts
     */

    public static void addNameFromCSV(String value, JPanel mainPane){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            if (value.contains("http")){
                String sql = "INSERT INTO Proekts (NameFromCSV,IDProekt,NameOfProekt,NameOfProekt1C,TypeOfProject) " +
                        "VALUES ('"+value+"', '-', '-', '-', '-');";
                statement.executeUpdate(sql) ;
                String tr = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
                RS.setNameOfProekt(tr,value,mainPane);

                tr = value.substring(value.indexOf('[') + 1, value.indexOf(']'));
                RS.setIDProekt(tr,value,mainPane);

            }

        } catch (SQLException e) {
        }
    }

    public static void setIDProekt(String value, String ident, JPanel mainPanel){
        String columnNameSet ="IDProekt";
        updateValueInDB("Proekts",columnNameSet,value,"NameFromCSV",ident,mainPanel);
    }

    public static void setNameOfProekt(String value, String ident, JPanel mainPanel){
        String columnNameSet ="NameOfProekt";
        updateValueInDB("Proekts",columnNameSet,value,"NameFromCSV",ident,mainPanel);
    }

    public static void setNameOfProekt1C(String value, String ident, JPanel mainPanel){
        String columnNameSet ="NameOfProekt1C";
        updateValueInDB("Proekts",columnNameSet,value,"NameFromCSV",ident,mainPanel);
    }

    public static void setTypeOfProject(String value, String ident, JPanel mainPanel){
        String columnNameSet ="TypeOfProject";
        updateValueInDB("Proekts",columnNameSet,value,"NameFromCSV",ident,mainPanel);
    }



    public static LinkedList<String> getNameFromCSVAll(){
        LinkedList<String> nameOfCSV = new LinkedList<String>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            ResultSet value = statement.executeQuery("SELECT NameFromCSV FROM Proekts");
            while (value.next()){
                nameOfCSV.add(value.getString("NameFromCSV"));
            }
            statement.close();
            connection.close();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(new JPanel(),"Ошибка получения данных с БД\n"+e);
        }
        return nameOfCSV;

    }

    public static String getIDProekt(String ident, JPanel mainPane){
        String columnName = "IDProekt";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Proekts", "NameFromCSV", ident, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }

    public static String getNameOfProekt(String ident, JPanel mainPane){
        String columnName = "NameOfProekt";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Proekts", "NameFromCSV", ident, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }

    public static String getNameOfProekt1C(String ident, JPanel mainPane){
        String columnName = "NameOfProekt1C";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Proekts", "NameFromCSV", ident, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }

    public static String getTypeOfProject(String ident, JPanel mainPane){
        String columnName = "TypeOfProject";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Proekts", "NameFromCSV", ident, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }




    /*
    Геттеры и сетеры для БД таблица Log
     */

    public static void setURLCurrLog(String value, JPanel mainPanel){
        String columnNameSet ="URLCurr";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static String getURLCurrLog(JPanel mainPane){
        String columnName = "URLCurr";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Log", "TimeStamp",timestamp, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }
    public static void setCurrListLog(String value, JPanel mainPanel){
        String columnNameSet = "CurrList";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getCurrListLog(JPanel mainPane){
        String columnName = "CurrList";
        String[][] result = new String[0][0];
        try {
            result = (String[][]) getValueFromDB(columnName, "Log", "TimeStamp",timestamp,mainPane);
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }
    public static void setConverCurrListLog(String value, JPanel mainPanel){
        String columnNameSet = "ConverCurrList";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getConverCurrListLog(Timestamp timestamp, JPanel mainPane){
        String columnName = "ConverCurrList";
        String result;
        try {
            result = (String) getValueFromDB(columnName, "Log", "TimeStamp",timestamp, mainPane);
        }catch (Exception e){
            result = "";
            JOptionPane.showMessageDialog(mainPane,"Непредвиденная ошибка БД\n"+e);
        }
        return result;
    }

    public static void updateFileLog(String filename, JPanel mainPane) {
        // update sql
        String updateSQL = "UPDATE Log "
                + "SET FileCSV = ? "
                + "WHERE TimeStamp = '"+timestamp+"'";

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);

            // set parameters
            pstmt.setBytes(1, readFile(filename));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPane,"Ошибка обновления файла лога\n"+e);
        }
    }

    public static Object getFileCSVLog(Timestamp timestamp, JPanel mainPane){
        String sql = "FileCSV";
        return "";
    }
    public static void setPartnersGroupLog(String[][] value, JPanel mainPanel){
        String columnNameSet ="PartnersGroup";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getPartnersGroupLog(Timestamp timestamp, JPanel mainPane){
        String sql = "PartnersGroup";
        return "";
    }
    public static void setPartnersSumLog(String value, JPanel mainPanel){
        String columnNameSet = "PartnersSum";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getPartnersSumLog(Timestamp timestamp, JPanel mainPane){
        String sql = "PartnersSum";
        return "";
    }
    public static void setResultLog(String value, JPanel mainPanel){
        String columnNameSet = "Result";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getResultLog(JPanel mainPane){
        String sql = "Result";
        return "";
    }
    public static void setResultCSVLog(String value, JPanel mainPanel){
        String columnNameSet = "ResultCSV";
        updateValueInDB("Log",columnNameSet,value,"TimeStamp",timestamp,mainPanel);
    }
    public static Object getResultCSVLog(Timestamp timestamp, JPanel mainPane){
        String sql = "ResultCSV";
        return "";
    }
    public static void addTimeStampLog(JPanel mainPane){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO Log (TimeStamp, URLCurr, CurrList, ConverCurrList, FileCSV, PartnersGroup, PartnersSum, Result, ResultCSV) " +
                    "VALUES ('"+timestamp+"', '-', '-', '-', '-', '-', '-', '-', '-');";
            statement.executeUpdate(sql) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Timestamp getCurrencyTimeStampLog(){
        return timestamp;
    }


    /*
    Геттеры и сетеры для БД таблица Settings
    */


    public static void setFeeParsentDB(String value, JPanel mainPanel){
        Double dt=0.00;
        try {
            dt = Double.parseDouble(value);
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
        if (dt<=0){
            JOptionPane.showMessageDialog(mainPanel,"Значение не может быть отрицательным или 0");
        }else if (dt>=100){
            JOptionPane.showMessageDialog(mainPanel,"Значение не может быть больше 100%");
        }else {
            String ident = "ParsentFee";
            updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
        }
    }
    public static void setURLforCurDB(String value, JPanel mainPanel){
        if (value.contains("http://")||value.contains("https://")){
            String ident = "URLCurrDownload";
            updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
        }else {
            JOptionPane.showMessageDialog(mainPanel,"Данные не являються ссылкой на сайт");
        }
    }
    public static String getFeeParsentDB(JPanel mainPane){
        //"0.12%"
        String sql ="ParsentFee";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }
    public static String getURLforCurDB(JPanel mainPane){
        //"http://www.cbr.ru/currency_base/daily.aspx?date_req=";
        String sql ="URLCurrDownload";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setTenancy(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "Tenancy";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }

    }
    public static String getTenancy(JPanel mainPane){
        //String result="1132.00";
        String sql ="Tenancy";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setTechSupport(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "TechSupport";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTechSupport(JPanel mainPane){
        //String result="1132.00";
        String sql ="TechSupport";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setKlientSupport(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "KlientSupport";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getKlientSupport(JPanel mainPane){
        //String result="1132.00";
        String sql ="KlientSupport";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setRiskM(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "RiskM";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getRiskM(JPanel mainPane){
        //String result="2264.00";
        String sql ="RiskM";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setAnalitik(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "Analitik";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getAnalitik(JPanel mainPane){
        //String result="100000.00";
        String sql ="Analitik";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setTesting(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "Testing";
                updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getTesting(JPanel mainPane){
        //String result="120000.00";
        String sql ="Testing";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }

    public static void setDevelopment(String value, JPanel mainPanel){
        try {
            double dt = Double.parseDouble(value);
            if (dt<0){
                JOptionPane.showMessageDialog(mainPanel,"Число не может быть отрицательным");
            }else {
                String ident = "Development";
                RS.updateValueInDB("Settings","Value",value,"TypeSettings",ident,mainPanel);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,"Значение не являеться числом");
        }
    }
    public static String getDevelopment(JPanel mainPane){
        //String result="422400.00";
        String sql ="Development";
        String result= (String) getValueFromDB("Value", "Settings", "TypeSettings",sql,mainPane);
        return result;
    }


    /*
    Методы добавляющие компоненты на компоненты на базе GridBagLayout, а также методы выбора файлов и директории для сохранения
     */

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
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle(dialog);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showOpenDialog(null);

        String string ="";
        //if (fileChooser.showOpenDialog(JFileChooser.APPROVE_OPTION)=="open")
        return fileChooser.getSelectedFile().getAbsolutePath();
    }
    public static String DirectoriChoose(String dialog, String dataSQL){
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





    public static void setSettingsList(String temp){
        settingsList.add(temp);
    }

    private static void updateValueInDB(String tabName,String columnNameSet,Object value,String columnID,Object ident, JPanel mainPanel){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE "+tabName+" SET "+columnNameSet+" = '"+value+"' WHERE "+columnID+" = '"+ident+"'");
            statement.close();
            connection.close();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel,"Ошибка добавления данных в БД\n"+e);
        }
    }

    private static Object getValueFromDB(String columnName, String tabName, String columnID,Object sql, JPanel mainPanel){
        String value;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT "+columnName+" FROM "+tabName+" WHERE "+columnID+" = '"+sql+"'");
            value = rs.getString(columnName);
            statement.close();
            connection.close();
        }catch (SQLException e) {
            value ="";
            JOptionPane.showMessageDialog(mainPanel,"Ошибка получения данных с БД\n"+e);
        }
        return value;
    }
    private static byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
}
