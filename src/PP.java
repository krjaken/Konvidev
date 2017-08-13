import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Set;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class PP extends JPanel {
    MaskFormatter mf1 = new MaskFormatter("##.##.####");
    JFormattedTextField DateK = new JFormattedTextField(mf1);
    private JPanel jpKur = new JPanel(new GridBagLayout());
    private JPanel jpDate = new JPanel(new GridBagLayout());
    private JPanel jpFirstDate = new JPanel(new GridBagLayout());
    private JPanel jpBasikDate = new JPanel(new GridBagLayout());
    private JButton jbDownloadCSV = new JButton("Download CSV");
    private JButton jbUpdateKur = new JButton("Загрузить курсы");
    private JButton jbOpering = new JButton("Обработать");
    private JButton jbDate= new JButton();
    private JPanel jpDownload = new JPanel(new GridBagLayout());
    private JPanel jpUpdate = new JPanel(new GridBagLayout());
    private JPanel jpOpering = new JPanel(new GridBagLayout());
    String[][] konvidwvCSV;
    private JLabel jlDateKurr = new JLabel();
    public Set<String> listProdavzov=new HashSet<String>();
    public Map<Object, JChekbox> mapLogic = new HashMap<Object, JChekbox>();

    JPanel jpSkroll = new JPanel(new GridBagLayout());
    JScrollPane jScrollPane = new JScrollPane(jpSkroll, VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JLabel jlKurr = new JLabel("Вал. Учета: ");
    JComboBox jcKURR = new JComboBox();
    String[][] ActualKurrKonvers;

    public PP(JFrame mainFrame) throws ParseException {
        setLayout(new GridBagLayout());
        Border etched = BorderFactory.createEtchedBorder();
        Border titled3 = BorderFactory.createTitledBorder(etched, "Доп. информация");
        Border titled = BorderFactory.createTitledBorder(etched, "Данные обработки");
        jpKur.setBorder(titled3);
        jpDate.setBorder(titled);
        jpFirstDate.setBorder(etched);
        DateK.setMinimumSize(new Dimension(80,28));
        DateK.setBorder(etched);
        jpFirstDate.setPreferredSize(new Dimension(350,33));
        jpKur.setPreferredSize(new Dimension(175,500));
        jpDate.setPreferredSize(new Dimension(400,500));
        jpDownload.setPreferredSize(new Dimension(30,30));
        jbDownloadCSV.setPreferredSize(new Dimension(70,30));
        jpUpdate.setPreferredSize(new Dimension(30,30));
        jbUpdateKur.setPreferredSize(new Dimension(70,30));
        jpUpdate.add(new JLabel(new ImageIcon("imgNOT.gif")));
        jpDownload.add(new JLabel(new ImageIcon("imgNOT.gif")));
        jpOpering.add(new JLabel(new ImageIcon("imgNOT.gif")));
        jbDownloadCSV.setVisible(false);
        jpDownload.setVisible(false);
        jbOpering.setVisible(false);
        jpOpering.setVisible(false);


        jbDownloadCSV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    konvidwvCSV = new ReadCSV().ReadCSV(RS.FifeChoose(mainFrame,"Загрузка CSV","","CSV","CSV")).clone();
                    SelectSellers(konvidwvCSV);

                    jbOpering.setVisible(true);
                    jpOpering.setVisible(true);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(mainFrame,"Ошибка загрузки файла");
                }
                jpDownload.removeAll();
                jpDownload.add(new JLabel(new ImageIcon("imgOK.gif")));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        jbUpdateKur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    Visual(new DownloadKUR().DownloadKUR(DateK.getText()),mainFrame);
                    jpUpdate.removeAll();
                    jpUpdate.add(new JLabel(new ImageIcon("imgOK.gif")));
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    jbDownloadCSV.setVisible(true);
                    jpDownload.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jbOpering.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Object o:listProdavzov)
                    System.out.println(mapLogic.get(o).isSelected());
                Invoice(mainFrame);
            }
        });

        RS.addComponent(jpKur,jScrollPane,new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        RS.addComponent(jpFirstDate,new JPanel(),new Rectangle(0,0,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL);
        RS.addComponent(jpFirstDate,DateK,new Rectangle(1,0,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE);
        RS.addComponent(jpFirstDate,jpUpdate,new Rectangle(2,0,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE);
        RS.addComponent(jpFirstDate,jbUpdateKur,new Rectangle(3,0,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE);
        RS.addComponent(jpFirstDate,jpDownload,new Rectangle(4,0,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE);
        RS.addComponent(jpFirstDate,jbDownloadCSV,new Rectangle(5,0,1,1),GridBagConstraints.EAST,GridBagConstraints.NONE);
        RS.addComponent(jpDate,jpFirstDate,new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
        RS.addComponent(jpDate,jpBasikDate,new Rectangle(0,1,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        RS.addComponent(PP.this,jpDate,new Rectangle(0,0,1,1),GridBagConstraints.WEST,GridBagConstraints.BOTH);
        RS.addComponent(PP.this,jpKur,new Rectangle(1,0,1,1),GridBagConstraints.EAST,GridBagConstraints.VERTICAL);

    }
    public void Visual(String[][] kurr, JFrame mainFrame) {
        for (int i = 0; i < kurr.length; i++) {
            jcKURR.addItem(kurr[i][2]);
        }
        jcKURR.setSelectedIndex(11);
        jcKURR.addItem("RUB");
        jcKURR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpSkroll.removeAll();
                Visual2(kurr,mainFrame);
            }
        });

        Visual2(kurr,mainFrame);
    }
    private void Visual2(String[][] kurr, JFrame mainFrame){
        RS.addComponent(jpSkroll, jlKurr, new Rectangle(0, 0, 1, 1), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        RS.addComponent(jpSkroll, jcKURR, new Rectangle(1, 0, 2, 1), GridBagConstraints.WEST, GridBagConstraints.NONE);
        ActualKurrKonvers= new String[kurr.length+1][3];
        ActualKurrKonvers[ActualKurrKonvers.length-1][0]="RUB";
        for (int i=0;i<kurr.length;i++){
            if (kurr[i][2].equals(jcKURR.getSelectedItem())){
                ActualKurrKonvers[ActualKurrKonvers.length-1][1]=kurr[i][3];
                ActualKurrKonvers[ActualKurrKonvers.length-1][2]=String.valueOf(Double.parseDouble(kurr[i][3].replaceAll(",","."))/Double.parseDouble(kurr[i][5].replaceAll(",",".")));
            }
        }

        for (int i=0;i<kurr.length;i++){
            RS.addComponent(jpSkroll,new JLabel(kurr[i][2]+" :"),new Rectangle(0,i+1,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
            RS.addComponent(jpSkroll,new JLabel(kurr[i][3]),new Rectangle(1,i+1,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
            if (jcKURR.getSelectedItem().equals("RUB")){
                RS.addComponent(jpSkroll,new JLabel(kurr[i][5]),new Rectangle(2,i+1,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 30));
                ActualKurrKonvers[i][0]=kurr[i][2];
                ActualKurrKonvers[i][1]=kurr[i][3];
                ActualKurrKonvers[i][2]=kurr[i][5].replaceAll(",",".");
            }else {
                String konver = String.valueOf((Double.parseDouble(kurr[i][5])/Double.parseDouble(kurr[jcKURR.getSelectedIndex()][5])));
                RS.addComponent(jpSkroll,new JLabel(konver),new Rectangle(2,i+1,1,1),GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 30));
                ActualKurrKonvers[i][0]=kurr[i][2];
                ActualKurrKonvers[i][1]=kurr[i][3];
                ActualKurrKonvers[i][2]=konver.replaceAll(",",".");
            }
        }
        RS.addComponent(jpSkroll,new JPanel(),new Rectangle(0,kurr.length+1,3,1),GridBagConstraints.SOUTH,GridBagConstraints.VERTICAL);
        mainFrame.revalidate();
        mainFrame.repaint();

        RS.getTextLog("Актуальный курс конвертации валют к выбраной валюте "+jcKURR.getSelectedItem()+"\n");
        RS.getTextLog(ActualKurrKonvers);
    }
    private void SelectSellers(String[][] konvidwvCSV){
        RS.addComponent(jpBasikDate,new JLabel("Список продавцев для обработки"),new Rectangle(0,0,3,1),GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0, 40, 30, 0));

        for (int i =0;i<konvidwvCSV.length;i++){
            if((konvidwvCSV[i][0].equals(""))||konvidwvCSV[i][0].equals("Total")||konvidwvCSV[i][0].equals("Продавец") )continue;
            listProdavzov.add(konvidwvCSV[i][0]);
        }

        int num=1;
        for (Object o: listProdavzov) {
            mapLogic.put(o,new JChekbox(String.valueOf(o)));
            RS.addComponent(jpBasikDate,new JLabel(String.valueOf(o)),new Rectangle(0,num,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 10, 20, 30));
            RS.addComponent(jpBasikDate,mapLogic.get(o),new Rectangle(1,num,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 10, 20, 30));//сюда надо добавить класс с чекбоксом
            RS.addComponent(jpBasikDate,new JPanel(),new Rectangle(2,num,1,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(0, 10, 20, 30));

            num++;

        }

        RS.addComponent(jpBasikDate,jbOpering,new Rectangle(0,num,1,1),GridBagConstraints.WEST,GridBagConstraints.NONE);
        RS.addComponent(jpBasikDate,new JPanel(),new Rectangle(1,num,2,1),GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL);
        RS.addComponent(jpBasikDate,new JPanel(),new Rectangle(0,num+1,3,1),GridBagConstraints.EAST,GridBagConstraints.BOTH);

    }
    private void Invoice(JFrame mainFrame){
        String[] header = {"Услуги 1gamepay","","",""};
        String[][] sumInKur;
        String[][] invoice;
        JTable jtInvoice;
        int io=0;
        for (Object o: listProdavzov){
            if (mapLogic.get(o).isSelected()){
                io++;
            }
        }
        String[][] TotalCSV = new String[io+2][konvidwvCSV[0].length-1];

        for (int tt = 0;tt<TotalCSV.length;tt++){
            for (int ttt=0; ttt<TotalCSV[0].length;ttt++){
                if (tt==0){
                    TotalCSV[0][ttt]= konvidwvCSV[1][ttt+1];
                }else {
                    TotalCSV[tt][ttt]= "0";
                }
            }
        }

        io=1;
        for (Object o: listProdavzov){
            if (mapLogic.get(o).isSelected()){
                TotalCSV[io][0]= String.valueOf(o);
                io++;
            }
        }
        konvidwvCSV[konvidwvCSV.length-1][0]="Total";
        TotalCSV[io][0]="Total";


        for (int i=1; i<konvidwvCSV[0].length;i++){
            for (int t=0;t<konvidwvCSV.length;t++){
                if (konvidwvCSV[t][i].equals("")){
                    continue;
                }else if ((konvidwvCSV[1][i].contains("In.")||konvidwvCSV[1][i].contains("Out."))&&konvidwvCSV[t][0]!="Total"){
                    for (int zz=1;zz<TotalCSV.length;zz++) {
                        if (TotalCSV[zz][0].equals(konvidwvCSV[t][0])) {
                            TotalCSV[zz][i-1] = String.valueOf(Double.parseDouble(TotalCSV[zz][i-1])+Double.parseDouble(konvidwvCSV[t][i]));
                        }
                    }
                }else if(konvidwvCSV[t][0].equals("Total")){
                    TotalCSV[TotalCSV.length-1][i-1] = konvidwvCSV[t][i];
                }
            }
        }


        Set<String> listCurr=new HashSet<String>();
        for (int ttt=1; ttt<TotalCSV[0].length;ttt++){
            TotalCSV[0][ttt]= TotalCSV[0][ttt].replaceAll("In.","").replaceAll("Out.","");
            listCurr.add(TotalCSV[0][ttt]);
        }

        for (int i=0;i<TotalCSV.length;i++){
            for (int y=0;y<TotalCSV[0].length;y++){
                System.out.print(TotalCSV[i][y]+"    ");
            }
            System.out.println("");
        }

        int oi=1;
        for (Object o: listCurr){
            oi++;
        }

        sumInKur= new String[TotalCSV.length][oi];

        oi=1;
        for (Object o: listCurr){
            sumInKur[0][oi]=String.valueOf(o);
            oi++;
        }


        for (int r=1;r<TotalCSV.length;r++){
            sumInKur[r][0]=TotalCSV[r][0];
        }

        for (int i=1;i<sumInKur.length;i++){
            for (int y=1;y<sumInKur[0].length;y++){
                sumInKur[i][y]="0";
            }
        }

        for (int i=1;i<sumInKur.length;i++){
            for (int y=1;y<sumInKur[0].length;y++){
                for (int t=1;t<TotalCSV[0].length;t++){
                    if (sumInKur[0][y].contains(TotalCSV[0][t])){
                        sumInKur[i][y]=String.valueOf(Double.parseDouble(sumInKur[i][y])+Double.parseDouble(TotalCSV[i][t]));
                    }
                }

            }
        }

        for (int i=0;i<sumInKur.length;i++){
            for (int y=0;y<sumInKur[0].length;y++){
                System.out.print(sumInKur[i][y]+"    ");
            }
            System.out.println("");
        }



//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime (new Date(DateK.getText())); // Date date
//        System.out.println (calendar.get (Calendar.MONTH));

        invoice= new String[sumInKur.length+19][4];
        invoice[0][0] = "Invoice ";//+calendar.get (Calendar.MONTH-1)+calendar.get (Calendar.YEAR);
        invoice[1][0] = "Invoice number: ";
        invoice[2][0] = "Billing period: ";//+ calendar.getActualMinimum(Calendar.DAY_OF_MONTH)+"-"+calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        invoice[3][0] = "WL: 1GamePay";
        invoice[4][0] = "Currenncy: "+ jcKURR.getSelectedItem();
        invoice[5][0] = "Merchants: ";
        for (int i=1;i<sumInKur.length;i++){
            invoice[5][0] = invoice[5][0]+"\n"+sumInKur[i][0];
        }
        invoice[6][0] = "Услуги 1gamePay";

        for (int i=1;i<sumInKur.length;i++){
            invoice[6 + i][0] = invoice[2][0];
            invoice[6 + i][1] = sumInKur[i][0];
            invoice[6 + i][2] = "0";
            invoice[6 + i][3] = String.valueOf(jcKURR.getSelectedItem());
        }
        int i = 1;

        for (; i < sumInKur.length-1; i++) {
            for (int t = 1; t < sumInKur[0].length; t++) {
                    if (jcKURR.getSelectedItem().equals(sumInKur[0][t])) {
                        invoice[6 + i][2] = String.valueOf(Pars(invoice[6 + i][2]) + Pars(sumInKur[i][t]));
                    }else {
                        for (int ui=0;ui<ActualKurrKonvers.length;ui++) {
                        if (ActualKurrKonvers[ui][0].equals(sumInKur[0][t])) {
                            Double dt = Pars(invoice[6 + i][2]) + (Pars(sumInKur[i][t]) * (Pars(ActualKurrKonvers[ui][2])/ Pars(ActualKurrKonvers[ui][1])));
                            System.out.println(dt);
                            invoice[6 + i][2] = String.valueOf(dt);
                        }}
                    }
            }
            invoice[6 + i][2]=new DecimalFormat("##,###,###,##0.0000").format(Pars(invoice[6 + i][2]));
        }
        i=i++;
        invoice[6+i][2]="0";
        for (int p=1;p<i;p++){
            invoice[6+i][2]=String.valueOf(Pars(invoice[6+i][2])+Pars(invoice[6+p][2]));
        }
        invoice[6 + i][2]=new DecimalFormat("##,###,###,##0.0000").format(Pars(invoice[6 + i][2]));

        invoice[7+i][2] = new DecimalFormat("##,###,###,##0.0000").format(Pars(invoice[7+i][2]));

        invoice[7+i][0]="Тариф, более 7М EUR"; invoice[7+i][2]="0.12%";
        invoice[8+i][0]="Лицензия"; invoice[8+i][2]=new DecimalFormat("##########0.00").format(((Pars(invoice[6 + i][2])/100*0.12 )));
        invoice[9+i][0]="Аренда оборудования"; invoice[9+i][2]="1 132.00";
        invoice[10+i][0]="Тех.сопровождение"; invoice[10+i][2]="1 132.00";
        invoice[11+i][0]="Тех.поддержка клиентов"; invoice[11+i][2]="1 132.00";
        invoice[12+i][0]="Риск-менеджмент"; invoice[12+i][2]="2 264.00";
        invoice[13+i][0]="Итого";
        invoice[13+i][2]="0.00";
        for (int tr=0;tr<5;tr++){
            invoice[13+i][2]=new DecimalFormat("##########0.00").format(Pars(invoice[13+i][2])+Pars(invoice[8+i+tr][2]));
        }
        invoice[14+i][1]="Аналитика";
        invoice[15+i][1]="Тестировщик";
        invoice[16+i][1]="Разработчики";

        for (int m = 0; m <ActualKurrKonvers.length;m++){
            if (ActualKurrKonvers[m][0].equals("RUB")){
                invoice[14+i][2]=new DecimalFormat("##########0.00").format(((100000/Pars(ActualKurrKonvers[m][1])*Pars(ActualKurrKonvers[m][2]))));
                invoice[15+i][2]=new DecimalFormat("##########0.00").format(((120000/Pars(ActualKurrKonvers[m][1])*Pars(ActualKurrKonvers[m][2]))));
                invoice[16+i][2]=new DecimalFormat("##########0.00").format((((211200+211200)/Pars(ActualKurrKonvers[m][1])*Pars(ActualKurrKonvers[m][2]))));
            }
        }

        invoice[17+i][2]=new DecimalFormat("##########0.00").format(Pars(invoice[14+i][2])+Pars(invoice[15+i][2])+Pars(invoice[16+i][2]));
        invoice[18+i][0]="ИТОГО к оплате"; invoice[18+i][2]=new DecimalFormat("##,###,###,##0.00").format(Pars(invoice[17+i][2])+Pars(invoice[13+i][2]));



        jtInvoice = new JTable(invoice,new String[]{"","","",""});


        jpBasikDate.removeAll();
        RS.addComponent(jpBasikDate,jtInvoice,new Rectangle(0,0,1,1),GridBagConstraints.CENTER,GridBagConstraints.BOTH);
        mainFrame.revalidate();
        mainFrame.repaint();



    }

    private double Pars(String temp){
        double td=0;
        try{
            String tem =temp.replaceAll(",",".").replaceAll(" ","").replace(String.valueOf((char) 160),"");
            BigDecimal bigDecimal = new BigDecimal(tem);
            td =bigDecimal.doubleValue();

        }catch (Exception e){
            td=0;
        }
        return td;
    }
}
class JChekbox extends JCheckBoxMenuItem{
    private final String title;
    public JChekbox(String title){
       this.title=title;
       setText(title);
       setSelected(true);

    }
}

