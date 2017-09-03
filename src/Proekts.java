import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Proekts extends JPanel {
    JPanel panel = new JPanel(new GridBagLayout());
    JScrollPane scrollPane = new JScrollPane(panel);
    public Proekts(){
        setLayout(new GridBagLayout());
        RS.addComponent(Proekts.this,scrollPane,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
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
