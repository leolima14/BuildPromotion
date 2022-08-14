package FranGames.BuildPromotion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Jogos {
    

    public List<String> getJogos() throws FileNotFoundException, IOException{
        List<String> j = new ArrayList<>();
        try {
            File jogos = new File("C:/Users/Unknown/Documents/GitHub/BuildPromotion/jogos.xls");
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(jogos));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                String jogo = row.getCell(0).getStringCellValue();
                j.add(jogo);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return j;
        
    }
}
