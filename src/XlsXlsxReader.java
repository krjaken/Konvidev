import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

public class XlsXlsxReader {
    private int rows = 0;
    private int cells = 0;
    private String[][] mas;
    public String[][] XlsXlsxReader(String adres) throws IOException {

        if (adres.contains(".xlsx")) {
           mas = xlsx(adres);
        }else if (adres.contains(".xls")){
           mas = xls(adres);
        }
        return mas;
    }

    private String[][] xls(String adres){

        return new String[2][];
    }

    private String[][] xlsx(String adres) throws IOException {
        Workbook wb = new XSSFWorkbook(new FileInputStream(adres));
        DataFormatter formatter = new DataFormatter(Locale.forLanguageTag("YYYY.MM.DD"));
        Sheet sheet1 = wb.getSheetAt(0);

        rows = sheet1.getLastRowNum()+1;
        cells = sheet1.getColumnWidth(1);
//            for (Row row1 : sheet1) {
//                for (Cell ignored : row1) cells++;
//            }
//            cells = cells / rows;
        mas = new String[rows][cells];
        int tempNbC = 0;
        for (Row row : sheet1) {
            int tempNbR = 0;
            for (Cell cell : row) {

                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                String text = formatter.formatCellValue(cell);
                mas[tempNbC][tempNbR] = text;

                // Alternatively, get the value and format it yourself
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        mas[tempNbC][tempNbR] = cell.getRichStringCellValue().getString();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            mas[tempNbC][tempNbR] = cell.toString();
                        } else {
                            Double d = cell.getNumericCellValue();
                            mas[tempNbC][tempNbR] = Double.toString(d);
                            //System.out.println(Double.toString(d));
                        }
                        break;
                    case BOOLEAN:
                        mas[tempNbC][tempNbR] = String.valueOf(cell.getBooleanCellValue());
                        //System.out.println(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case FORMULA:
                        //mas[tempNbC][tempNbR] = cell.getCellFormula();
                        if (DateUtil.isCellDateFormatted(cell)) {
                            mas[tempNbC][tempNbR] = cell.getDateCellValue().toString();
                        } else {
                            Double d = cell.getNumericCellValue();
                            mas[tempNbC][tempNbR] = Double.toString(d);
                            //System.out.println(Double.toString(d));
                        }
                        break;
                    case BLANK:
                        mas[tempNbC][tempNbR] = "";
                        break;
                    default:
                        mas[tempNbC][tempNbR] = "default";
                }
                tempNbR++;
            }
            tempNbC++;
        }
        return mas;
    }
}
