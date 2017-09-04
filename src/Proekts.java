import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Proekts extends JPanel {
    JFrame mainFrame;
    JPanel panel = new JPanel(new GridBagLayout());
    JScrollPane scrollPane = new JScrollPane(panel);
    JButton button = new JButton("Загрузить csv");
    JComboBox nameCSV = creatJCombobox(); JComboBox id = creatJCombobox();
    JComboBox url = creatJCombobox(); JComboBox name1C = creatJCombobox();
    JComboBox type = creatJCombobox();JButton downloadButton = new JButton("Загрузить csv");
    JComboBox rove = new JComboBox();
    String[][] updateProektCSV;
    public Proekts(JFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        RS.addComponent(Proekts.this,new JLabel("импортировать названия проектов для 1С из csv"),new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(Proekts.this,button,new Rectangle(1,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(Proekts.this,new JPanel(),new Rectangle(2,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
        RS.addComponent(Proekts.this,scrollPane,new Rectangle(0,1,3,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:FinDep.db");
            Statement statement = connection.createStatement();
            String sql = "SELECT NameFromCSV FROM Proekts";
            ResultSet rs = statement.executeQuery(sql);
            int i = 0;
            while (rs.next()){
                RS.addComponent(panel,new OneProekt(rs.getString("NameFromCSV")),new Rectangle(0,i,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
                i++;
            }
            statement.close();
            connection.close();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(Proekts.this, "Ошибка заполнения списка проектов\n" +e);
        }
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                importDataOfProekts();
            }
        });


    }
    private void importDataOfProekts(){
        JDialog dialog = new JDialog();
        JPanel contentPanel = new JPanel(new GridBagLayout());

        rove.setPreferredSize(new Dimension(200,30));
        rove.addItem("1");rove.addItem("2");
        rove.setSelectedIndex(0);
        JTextField textField = new JTextField(";");
        textField.setPreferredSize(new Dimension(200,30));
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setTitle("Импорт данных по проектам из (csv)");
        dialog.setModal(true);

        RS.addComponent(contentPanel, new JLabel("Соотношение колонок csv файла к данным проектов"),new Rectangle(0,0,2,1),GridBagConstraints.CENTER,GridBagConstraints.NONE, new Insets(0,5,10,0));
        RS.addComponent(contentPanel,new JLabel("Полное название проекта с 1GP (Paymantix)"),new Rectangle(0,1,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,nameCSV,new Rectangle(1,1,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JLabel("ID Проекта"),new Rectangle(0,2,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,id,new Rectangle(1,2,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JLabel("Сайт проекта"),new Rectangle(0,3,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,url,new Rectangle(1,3,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JLabel("Название проекта 1С"),new Rectangle(0,4,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,name1C,new Rectangle(1,4,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JLabel("Тип проекта"),new Rectangle(0,5,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,type,new Rectangle(1,5,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JLabel("Данные со строки №"),new Rectangle(0,6,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,rove,new Rectangle(1,6,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));

        RS.addComponent(contentPanel,new JLabel("Разделитель значений: "),new Rectangle(0,7,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,textField,new Rectangle(1,7,2,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));

        RS.addComponent(contentPanel,new JPanel(),new Rectangle(0,8,2,1),GridBagConstraints.WEST,GridBagConstraints.BOTH, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,downloadButton,new Rectangle(0,9,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,new JPanel(),new Rectangle(1,9,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));

        downloadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    if (nameCSV.getSelectedIndex()==5){
                        JOptionPane.showMessageDialog(mainFrame,"Не выбрана колонка с именами проектов 1GP");
                    }else {
                        updateProektCSV = new ReadCSV().ReadCSV(RS.FifeChoose(mainFrame,"Загрузка CSV","","CSV","CSV")).clone();
                        udateProektsFromCSV(updateProektCSV);
                        dialog.dispose();
                        JOptionPane.showMessageDialog(mainFrame,"Данные проектов обновлены успешно");
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(mainFrame,"Ошибка загрузки файла\n"+e1);
                }
            }
        });


        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocation(mainFrame.getX()+100,mainFrame.getY()+100);
        dialog.setSize(450,400);
        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.revalidate();
        dialog.repaint();
    }
    private JComboBox creatJCombobox(){
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("1");comboBox.addItem("2");comboBox.addItem("3");
        comboBox.addItem("4");comboBox.addItem("5");comboBox.addItem("не определено");
        comboBox.setSelectedIndex(5);
        comboBox.setPreferredSize(new Dimension(200,30));

        return comboBox;
    }
    private void udateProektsFromCSV(String[][] mass){
        if (id.getSelectedIndex()!=5){
            ResultSet rs = RS.getNameFromCSVAll();
            try {
                while (rs.next()){
                    String nameCSVString =rs.getString("NameFromCSV");
                    for (int i=0;i<mass.length;i++){
                        if (nameCSVString.equals(mass[i][nameCSV.getSelectedIndex()])){
                            RS.setIDProekt(mass[i][id.getSelectedIndex()],nameCSVString,Proekts.this);
                        }
                    }

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainFrame,"Ошибка получения сверочной информации с БД\n"+e);
            }
        }
        if (url.getSelectedIndex()!=5){
            ResultSet rs = RS.getNameFromCSVAll();
            try {
                while (rs.next()){
                    String nameCSVString =rs.getString("NameFromCSV");
                    for (int i=0;i<mass.length;i++){
                        if (nameCSVString.equals(mass[i][nameCSV.getSelectedIndex()])){
                            RS.setNameOfProekt(mass[i][url.getSelectedIndex()],nameCSVString,Proekts.this);
                        }
                    }

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainFrame,"Ошибка получения сверочной информации с БД\n"+e);
            }
        }
        if (name1C.getSelectedIndex()!=5){
            ResultSet rs = RS.getNameFromCSVAll();
            try {
                while (rs.next()){
                    String nameCSVString =rs.getString("NameFromCSV");
                    for (int i=0;i<mass.length;i++){
                        if (nameCSVString.equals(mass[i][nameCSV.getSelectedIndex()])){
                            RS.setNameOfProekt1C(mass[i][name1C.getSelectedIndex()],nameCSVString,Proekts.this);
                        }
                    }

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainFrame,"Ошибка получения сверочной информации с БД\n"+e);
            }

        }
        if (type.getSelectedIndex()!=5){
            ResultSet rs = RS.getNameFromCSVAll();
            try {
                while (rs.next()){
                    String nameCSVString =rs.getString("NameFromCSV");
                    for (int i=0;i<mass.length;i++){
                        if (nameCSVString.equals(mass[i][nameCSV.getSelectedIndex()])){
                            RS.setTypeOfProject(mass[i][type.getSelectedIndex()],nameCSVString,Proekts.this);
                        }
                    }

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainFrame,"Ошибка получения сверочной информации с БД\n"+e);
            }
        }
    }
}
class OneProekt extends JPanel{
    JLabel nameFromCSV;
    JLabel idProekt;
    JLabel nameOfProekt;
    JLabel nameOfProekt1C;
    JLabel typeOfProject;
    JButton editButton = new JButton("Edit");
    Border etched = BorderFactory.createEtchedBorder();
    OneProekt(String nameFromCSV){
        this.nameFromCSV = new JLabel(nameFromCSV);
        this.nameFromCSV.setBorder(etched);
        this.nameFromCSV.setPreferredSize(new Dimension(300,30));
        idProekt = new JLabel(RS.getIDProekt(nameFromCSV,OneProekt.this));
        idProekt.setBorder(etched);
        idProekt.setPreferredSize(new Dimension(50,30));
        nameOfProekt = new JLabel(RS.getNameOfProekt(nameFromCSV,OneProekt.this));
        nameOfProekt.setBorder(etched);
        nameOfProekt.setPreferredSize(new Dimension(150,30));
        nameOfProekt1C = new JLabel(RS.getNameOfProekt1C(nameFromCSV,OneProekt.this));
        nameOfProekt1C.setBorder(etched);
        nameOfProekt1C.setPreferredSize(new Dimension(100,30));
        typeOfProject = new JLabel(RS.getTypeOfProject(nameFromCSV,OneProekt.this));
        typeOfProject.setBorder(etched);
        typeOfProject.setPreferredSize(new Dimension(50,30));

        RS.addComponent(OneProekt.this,this.nameFromCSV,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));
        RS.addComponent(OneProekt.this,idProekt,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));
        RS.addComponent(OneProekt.this,nameOfProekt,new Rectangle(1,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));
        RS.addComponent(OneProekt.this,nameOfProekt1C,new Rectangle(2,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));
        RS.addComponent(OneProekt.this,typeOfProject,new Rectangle(3,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));
        RS.addComponent(OneProekt.this,editButton,new Rectangle(4,0,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 5));

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new EditProekt(nameFromCSV,OneProekt.this);
            }
        });

    }
}
class EditProekt extends JFrame{
    JPanel panelVisual = new JPanel(new GridBagLayout());
    JTextField nameFromCSV;
    JTextField idProekt;
    JTextField nameOfProekt;
    JTextField nameOfProekt1C;
    JTextField typeOfProject;
    JButton saveButton = new JButton("Save");
    Border etched = BorderFactory.createEtchedBorder();
    EditProekt(String nameFromCSV, JPanel panel){
        setTitle(nameFromCSV);
        setLayout(new GridBagLayout());
        setVisible(true);
        setResizable(false);
        setMinimumSize(new Dimension(420,250));
        this.nameFromCSV = new JTextField(nameFromCSV);
        this.nameFromCSV.setEditable(false);
        this.nameFromCSV.setBorder(etched);
        this.nameFromCSV.setPreferredSize(new Dimension(300,30));
        idProekt = new JTextField(RS.getIDProekt(nameFromCSV,panel));
        idProekt.setBorder(etched);
        idProekt.setPreferredSize(new Dimension(300,30));
        nameOfProekt = new JTextField(RS.getNameOfProekt(nameFromCSV,panel));
        nameOfProekt.setBorder(etched);
        nameOfProekt.setPreferredSize(new Dimension(300,30));
        nameOfProekt1C = new JTextField(RS.getNameOfProekt1C(nameFromCSV,panel));
        nameOfProekt1C.setBorder(etched);
        nameOfProekt1C.setPreferredSize(new Dimension(300,30));
        typeOfProject = new JTextField(RS.getTypeOfProject(nameFromCSV,panel));
        typeOfProject.setBorder(etched);
        typeOfProject.setPreferredSize(new Dimension(300,30));

        RS.addComponent(EditProekt.this,panelVisual,new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        RS.addComponent(panelVisual,this.nameFromCSV,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE);
        RS.addComponent(panelVisual,new JLabel(""),new Rectangle(1,0,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,this.idProekt,new Rectangle(0,1,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,new JLabel("ID Проекта"),new Rectangle(1,1,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,this.nameOfProekt,new Rectangle(0,2,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,new JLabel("Сайт проекта"),new Rectangle(1,2,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,this.nameOfProekt1C,new Rectangle(0,3,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,new JLabel("Имя проекта 1С"),new Rectangle(1,3,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,this.typeOfProject,new Rectangle(0,4,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,new JLabel("Тип проекта"),new Rectangle(1,4,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,saveButton,new Rectangle(0,5,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(5, 0, 0, 0));
        RS.addComponent(panelVisual,new JLabel(""),new Rectangle(1,5,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(5, 0, 0, 0));

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setIDProekt(idProekt.getText(),nameFromCSV,panelVisual);
                RS.setNameOfProekt(nameOfProekt.getText(),nameFromCSV,panelVisual);
                RS.setNameOfProekt1C(nameOfProekt1C.getText(),nameFromCSV,panelVisual);
                RS.setTypeOfProject(typeOfProject.getText(),nameFromCSV,panelVisual);
                panel.removeAll();
                RS.addComponent(panel,new OneProekt(nameFromCSV),new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
                EditProekt.this.dispose();
            }
        });

    }
}
