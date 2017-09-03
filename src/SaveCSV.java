import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveCSV {
    public SaveCSV(String adress, String file, String charset){
        try(FileOutputStream fos=new FileOutputStream(adress+"//paymantix.csv"))
        {
            // перевод строки в байты
            byte[] buffer = file.getBytes();
            fos.write(buffer, 0, buffer.length);
            JOptionPane.showMessageDialog(new JPanel(),"Файл сохранен успешно");
            RS.setResultCSVLog(file,new JPanel());
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(new JPanel(),"Сохранение не успешно\n"+ex);
        }
    }
}
