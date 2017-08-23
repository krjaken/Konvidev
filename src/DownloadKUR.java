import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

public class DownloadKUR {
    public String[][] DownloadKUR(String data, JPanel mainPane) throws IOException {
        Document doc = Jsoup.connect(RS.getURLforCurDB(mainPane)+data).get();
        RS.setTextLog(RS.getURLforCurDB(mainPane)+data);
        Elements tdElements = doc.getElementsByClass("data");

        String html = String.valueOf(tdElements);
        html = html.replaceAll(" ","");
        html = html.replaceAll("\n","");
        html = html.replace("<tableclass=\"data\"><tbody><tr><th>Цифр.код</th><th>Букв.код</th><th>Единиц</th><th>Валюта</th><th>Курс</th></tr><tr>","");
        html = html.replace("</tbody></table>","");
        html = html.replace(",",".");
        html = html.replace("</td>","");
        html = html.replace("</tr>","");
        String[] temp = html.split("<tr>");
        String[] temp1 = temp[0].split("<td>");
        String[][] kurr = new String[temp.length][temp1.length];
        for (int i=0;i<temp.length;i++){
            String[] ttt = temp[i].split("<td>");
            for (int t = 0; t<ttt.length;t++){
                kurr[i][t] = ttt[t];
            }
        }
        RS.setTextLog(kurr);
        return kurr;
    }
}
