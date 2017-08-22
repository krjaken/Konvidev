import com.sun.xml.internal.bind.v2.runtime.reflect.opt.MethodAccessor_Long;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Settings extends JPanel {
    private JLabel jlURLkukrs = new JLabel();
    private JLabel jlPersentFEE = new JLabel();
    private JLabel jlTenancy = new JLabel();
    private JLabel jlTechSupport = new JLabel();
    private JLabel jlKlientSupport = new JLabel();
    private JLabel jlRiskM = new JLabel();
    private JLabel jlAnalitik = new JLabel();
    private JLabel jlTesting = new JLabel();
    private JLabel jlDevelopment = new JLabel();

    private JButton jbSaveURL = new JButton("Изменить URL загрузки курсов");
    private JButton jbFEE = new JButton("Изменить % FEE");
    private JButton jbTenancy = new JButton("Изменить стоимость аренды оборудования (руб)");
    private JButton jbTechSupport = new JButton("Изменить стоимость Техподдержки (руб)");
    private JButton jbKlientSupport = new JButton("Изменить стоимость Клиентской поддержки (руб)");
    private JButton jbRiskM = new JButton("Изменить стоимость Риск менеджмента (руб)");
    private JButton jbAnalitik = new JButton("Изменить стоимость Аналитики (руб)");
    private JButton jbTesting = new JButton("Изменить стоимость тестирования (руб)");
    private JButton jbDevelopment = new JButton("Изменить стоимость разработки (руб)");

    public Settings() throws ParseException {
        setLayout(new GridBagLayout());
        jlURLkukrs.setText(RS.getURLforCurDB());
        jlPersentFEE.setText(RS.getFeeParsentDB());
        jlTenancy.setText(RS.getTenancy());
        jlTechSupport.setText(RS.getTechSupport());
        jlKlientSupport.setText(RS.getKlientSupport());
        jlRiskM.setText(RS.getRiskM());
        jlAnalitik.setText(RS.getAnalitik());
        jlTesting.setText(RS.getTesting());
        jlDevelopment.setText(RS.getDevelopment());

        ADDObjINSettings(jlURLkukrs,jbSaveURL);
        ADDObjINSettings(jlPersentFEE,jbFEE);
        ADDObjINSettings(jlTenancy,jbTenancy);
        ADDObjINSettings(jlTechSupport,jbTechSupport);
        ADDObjINSettings(jlKlientSupport,jbKlientSupport);
        ADDObjINSettings(jlRiskM,jbRiskM);
        ADDObjINSettings(jlAnalitik,jbAnalitik);
        ADDObjINSettings(jlTesting,jbTesting);
        ADDObjINSettings(jlDevelopment,jbDevelopment);
        RS.addComponent(Settings.this,new JPanel(),new Rectangle(0,9,2,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
        jbSaveURL.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setURLforCurDB(JOptionPane.showInputDialog("Введите новый адрес с которого будет производиться загрузка курсов"),Settings.this);
                jlURLkukrs.setText(RS.getURLforCurDB());
            }
        });
        jbFEE.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setFeeParsentDB(JOptionPane.showInputDialog("Введите % комиссии по договору"),Settings.this);
                jlPersentFEE.setText(RS.getFeeParsentDB());
            }
        });
        jbTenancy.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setTenancy(JOptionPane.showInputDialog("Введите стоимость аренды оборудования (руб)"),Settings.this);
                jlTenancy.setText(RS.getTenancy());
            }
        });
        jbTechSupport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setTechSupport(JOptionPane.showInputDialog("Введите стоимость технической поддержки (руб)"),Settings.this);
                jlTechSupport.setText(RS.getTechSupport());
            }
        });
        jbKlientSupport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setKlientSupport(JOptionPane.showInputDialog("Введите стоимость клиентской поддуржки (руб)"),Settings.this);
                jlKlientSupport.setText(RS.getKlientSupport());
            }
        });
        jbRiskM.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setRiskM(JOptionPane.showInputDialog("Введите стоимость Риск менеджмент(руб)"),Settings.this);
                jlRiskM.setText(RS.getRiskM());
            }
        });
        jbAnalitik.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setAnalitik(JOptionPane.showInputDialog("Введите стоимость аналитических расходов (руб)"),Settings.this);
                jlAnalitik.setText(RS.getAnalitik());
            }
        });
        jbTesting.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setTesting(JOptionPane.showInputDialog("Введите стоимость тестирования (руб)"),Settings.this);
                jlTesting.setText(RS.getTesting());
            }
        });
        jbDevelopment.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                RS.setDevelopment(JOptionPane.showInputDialog("Введите стоимость разработки (руб)"),Settings.this);
                jlDevelopment.setText(RS.getDevelopment());
            }
        });
    }
    int i=0;
    private void ADDObjINSettings(JLabel label, JButton button){
        Border etched = BorderFactory.createEtchedBorder();
        label.setPreferredSize(new Dimension(150,30));
        button.setPreferredSize(new Dimension(70,30));
        label.setBorder(etched);
        RS.addComponent(Settings.this,label,new Rectangle(0,i,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
        RS.addComponent(Settings.this,button,new Rectangle(1,i,1,1),GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
        i++;
    }
}
