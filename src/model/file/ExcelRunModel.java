/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.file;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import zio.Excel;

/**
 *
 * @author JCHAUT
 */
public class ExcelRunModel extends AbstractFileModel implements IFileRunModel {

    private String filePath;
    private File file;
    private Object[][] excelData;
    private int idSheet;

    public ExcelRunModel() {
        filePath = "";
        excelData = null;
        idSheet = 0;
    }

    public void setFileName(String path) {
        File f = new File(path);
        if (f.exists() && f.isFile() && f.canRead()) {
            filePath = path;
            file = f;
        } else {
            filePath = "";
            file = null;
        }
    }

    @Override
    public void loadData(int linebeg, int colbeg, int lineend, int colend) throws IOException {
        if (file != null) {
            try {
                Workbook wrk = Workbook.getWorkbook(file);
                Sheet sheet = wrk.getSheet(idSheet);
                if (sheet != null) {
                    excelData = Excel.readXlsTable(sheet, linebeg, colbeg, lineend, colend);
                } else {
                    excelData = null;
                }
            } catch (BiffException ex) {
                excelData = null;
                Logger.getLogger(ExcelRunModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void launchData() {
     
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Object[][] getData() {
        return excelData;
    }

    public void setData(Object[][] excelData) {
        this.excelData = excelData;
    }

    public int getIdSheet() {
        return idSheet;
    }

    public void setIdSheet(int idSheet) {
        this.idSheet = idSheet;
    }
}
