import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class ReadCSV {
    public String[][] ReadCSV(String csvFile) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String[][]mass;

            int row=0;
            int cells=0;
            int celsRes=0;
            String line = null;
            Scanner scanner = null;
//узнаём размер массива
            while ((line = reader.readLine()) != null) {// считываем построчно

                scanner = new Scanner(line);
                scanner.useDelimiter(";");

                while (scanner.hasNext()) {
                    scanner.next();
                    celsRes++;
                }
                if (cells<celsRes){cells=celsRes;}

                row++;
                celsRes=0;
            }
//закрываем наш ридер
            reader.close();
            mass= new String[row][cells];

// заполнить масив считаными данными
            BufferedReader reader2 = new BufferedReader(new FileReader(csvFile));
            int i=0;
            int j=0;
            Scanner scanner2 = null;
            while ((line = reader2.readLine()) != null&&i<row) {

                scanner2 = new Scanner(line);

                while (scanner2.hasNext() && j < cells) {
                    String[] data = line.split(";");
                    for (int r=0;r<data.length;r++){
                        if (data[r]==null){
                            mass[i][j] = "";
                        }
                        mass[i][j] = data[r];
                        mass[i][j]=mass[i][j].replaceAll("\"", "").replaceAll(" ","").replaceAll(",",".");
                        if (j==1){
                            RS.addNameFromCSV(mass[i][j],new JPanel());
                        }
                        j++;
                    }
                }
                i++;
                j = 0;

            }
            reader2.close();

        RS.updateFileLog(csvFile,new JPanel());


        return mass;
    }
}