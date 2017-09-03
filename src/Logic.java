import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Logic {
    String[][] paymantix;
    String[][] csvOut;
    String[][] conCurr;
    Double totalSumm;
    Set<String> listProekts = new HashSet<String>();
    JCheckBox Okrugl = new JCheckBox();
    JTextField decliment = new JTextField(";");
    JComboBox coding = new JComboBox();
    Map<Object,JChekbox> mapProekts;

    JTextField postavList = new JTextField("1GamePay EUR");
    JTextField statRasList = new JTextField("Услуги процессинга");
    JTextField typePostList = new JTextField("На расходы");
    JTextField kommentList = new JTextField("услуги процессинга для внешних / внутренних проектов");
    String selectedCurr="";


    public Logic(JFrame mainFrame, String[][] paymantix, Map<Object,JChekbox> mapProekts, Double totalSumm){
        this.paymantix = paymantix;
        this.totalSumm =totalSumm;
        this.mapProekts = mapProekts;
        createDialog(mainFrame);
        calc();
    }
    public void calc(){
        int i=0;
        for (Map.Entry<Object, JChekbox> entry : mapProekts.entrySet()) {
            if (mapProekts.get(entry.getKey()).isSelected()){
                for (int tt=0;tt<paymantix.length;tt++){
                    if (paymantix[tt][0].equals(entry.getKey().toString())){
                        listProekts.add(paymantix[tt][1]);
                        i++;
                    }
                }
            }
        }

        int tri=1;
        csvOut = new String[i+1][7];
        csvOut[0][0] = "сумма";
        csvOut[0][1] = "Валюта";
        csvOut[0][2] = "Поразделение";
        csvOut[0][3] = "Поставщик";
        csvOut[0][4] = "Статья расходов";
        csvOut[0][5] = "Вид поступления";
        csvOut[0][6] = "Комментарий";
        for (String nameFromCSV: listProekts) {
            csvOut[tri][0] = summProektInOut(nameFromCSV);
            csvOut[tri][1] = "EUR";
            csvOut[tri][2] = RS.getNameOfProekt1C(nameFromCSV,new JPanel());
            csvOut[tri][3] = postavList.getText();
            csvOut[tri][4] = statRasList.getText();
            csvOut[tri][5] = typePostList.getText();
            csvOut[tri][6] = kommentList.getText();
            tri++;
        }
        Double totalINOUT=0.0;
        for (int op =1; op<csvOut.length;op++){
            totalINOUT= totalINOUT+ Double.parseDouble(csvOut[op][0]);
        }

        for (int op =1; op<csvOut.length;op++){
            csvOut[op][0]=String.valueOf(Double.parseDouble(csvOut[op][0])/totalINOUT*totalSumm);
            System.out.print(csvOut[op][0]+"; "+csvOut[op][1]+"; "+csvOut[op][2]+"; "+csvOut[op][3]+"; "+csvOut[op][4]+"; "+csvOut[op][5]+"; "+csvOut[op][6]);
            System.out.println("");
        }
    }




    public String summProektInOut(String nameFromCSV){
        String converCurr = (String) RS.getConverCurrListLog(RS.getCurrencyTimeStampLog(), new JPanel());
        String[] temp = converCurr.split("\n");
        String[] strings = temp[0].split(";");
        conCurr = new String[temp.length][strings.length];
        int ir=0;
        for (String tr : temp){
            String[] ss = tr.split(";");
            for (int iu=0;iu<ss.length;iu++){
                conCurr[ir][iu] = ss[iu];
            }
            ir++;
        }


        Double summ = 0.0;
        for (int i = 2; i < paymantix.length; i++){
            for (int y = 2; y < paymantix[0].length; y++){
                if (paymantix[i][1].equals(nameFromCSV)){
                    for (int z=0;z<conCurr.length;z++){
                        if (paymantix[1][y].contains(conCurr[z][0])){
                            summ = (summ + (Double.parseDouble(paymantix[i][y])/Double.parseDouble(conCurr[z][1]))*Double.parseDouble(conCurr[z][2]));
                        }
                    }
                }
            }
        }
        return String.valueOf(summ);
    }
    private void createDialog(JFrame frame)
    {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setTitle("Формирование (csv)");
        dialog.setModal(true);
        JButton saveButton = new JButton("Сохранить (csv)");
        JPanel contentPanel = new JPanel(new GridBagLayout());
        Okrugl.setSelected(true);
        decliment.setPreferredSize(new Dimension(30,30));
        decliment.setHorizontalAlignment(JTextField.CENTER);
        coding.addItem("UTF-8");coding.addItem("Unicode");coding.addItem("ANSI");

        coding.setPreferredSize(new Dimension(340,30));
        postavList.setPreferredSize(new Dimension(340,30));
        statRasList.setPreferredSize(new Dimension(340,30));
        typePostList.setPreferredSize(new Dimension(340,30));
        kommentList.setPreferredSize(new Dimension(340,30));

        RS.addComponent(contentPanel,new JLabel("Округлять значения"),new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel,Okrugl,new Rectangle(1,0,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JLabel("Значений разделитель :"),new Rectangle(0,1,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, decliment,new Rectangle(1,1,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JLabel("Кодировка текста :"),new Rectangle(0,2,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, coding,new Rectangle(1,2,5,1),GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,5,0,0));

        RS.addComponent(contentPanel, new JLabel("Поставщик"),new Rectangle(0,3,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, postavList,new Rectangle(1,3,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JLabel("Статья расходов"),new Rectangle(0,4,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, statRasList,new Rectangle(1,4,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JLabel("Вид поступления"),new Rectangle(0,5,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, typePostList,new Rectangle(1,5,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JLabel("Комментарий"),new Rectangle(0,6,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, kommentList,new Rectangle(1,6,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(0,5,0,0));


        RS.addComponent(contentPanel,new JPanel(),new Rectangle(0,7,2,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        RS.addComponent(contentPanel, saveButton,new Rectangle(0,8,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,5,0,0));
        RS.addComponent(contentPanel, new JPanel(),new Rectangle(1,8,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL, new Insets(0,5,0,0));
        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocation(frame.getX()+100,frame.getY()+100);
        dialog.setSize(500,400);
        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.revalidate();
        dialog.repaint();

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
    }
}
