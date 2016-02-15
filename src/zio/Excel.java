package zio;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * *********************************************************************
 * Définition d'un Excel en écriture
 *
 * @author Jérémy Chaut
 * *********************************************************************
 */
public class Excel {

    private DateFormat dateFormat;
    private Date date;
    private WritableFont fontHeader, fontLine;
    public static WritableCellFormat titleFormat, headerFormat, lineFormat;
    public static WritableCellFormat JUST_BOTTOM_MEDIUM, BOTTOM_MEDIUM;
    public static WritableCellFormat JUST_LEFT_MEDIUM, LEFT_MEDIUM;
    public static WritableCellFormat JUST_RIGHT_MEDIUM, RIGHT_MEDIUM;
    public static WritableCellFormat JUST_TOP_MEDIUM, TOP_MEDIUM;
    public static WritableCellFormat JUST_LEFT_BOTTOM_CORNER_MEDIUM, LEFT_CORNER_BOTTOM_MEDIUM;
    public static WritableCellFormat JUST_LEFT_TOP_CORNER_MEDIUM, LEFT_CORNER_TOP_MEDIUM;
    public static WritableCellFormat JUST_RIGHT_BOTTOM_CORNER_MEDIUM, RIGHT_CORNER_BOTTOM_MEDIUM;
    public static WritableCellFormat JUST_RIGHT_TOP_CORNER_MEDIUM, RIGHT_CORNER_TOP_MEDIUM;
    public static WritableCellFormat LEFT_RIGHT_MEDIUM, LEFT_RIGHT_TOP_MEDIUM, LEFT_RIGHT_BOTTOM_MEDIUM;
    public static int XLS_TABLE_HORIZONTAL = 0;
    public static int XLS_TABLE_VERTICAL = 1;
    public WritableWorkbook writeWrk;
    public Workbook wrk;

    /**
     * *********************************************************************
     * Constructeur de la classe Excel
     * *********************************************************************
     */
    public Excel() {
        date = new Date();
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        fontHeader = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        fontLine = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
        titleFormat = new WritableCellFormat(fontHeader);
        headerFormat = new WritableCellFormat(fontHeader);
        lineFormat = new WritableCellFormat(fontLine);

        TOP_MEDIUM = new WritableCellFormat();
        BOTTOM_MEDIUM = new WritableCellFormat();
        LEFT_MEDIUM = new WritableCellFormat();
        RIGHT_MEDIUM = new WritableCellFormat();

        RIGHT_CORNER_TOP_MEDIUM = new WritableCellFormat();
        RIGHT_CORNER_BOTTOM_MEDIUM = new WritableCellFormat();
        LEFT_CORNER_TOP_MEDIUM = new WritableCellFormat();
        LEFT_CORNER_BOTTOM_MEDIUM = new WritableCellFormat();

        LEFT_RIGHT_MEDIUM = new WritableCellFormat();
        LEFT_RIGHT_TOP_MEDIUM = new WritableCellFormat();
        LEFT_RIGHT_BOTTOM_MEDIUM = new WritableCellFormat();

        try {
            titleFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            titleFormat.setBackground(Colour.BLUE_GREY);
            titleFormat.setAlignment(Alignment.CENTRE);
            headerFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            lineFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            LEFT_RIGHT_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_RIGHT_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
            LEFT_RIGHT_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);

            LEFT_RIGHT_TOP_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_RIGHT_TOP_MEDIUM.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
            LEFT_RIGHT_TOP_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
            LEFT_RIGHT_TOP_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);

            LEFT_RIGHT_BOTTOM_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_RIGHT_BOTTOM_MEDIUM.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
            LEFT_RIGHT_BOTTOM_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
            LEFT_RIGHT_BOTTOM_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);

            TOP_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            TOP_MEDIUM.setBorder(Border.TOP, BorderLineStyle.MEDIUM);

            BOTTOM_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            BOTTOM_MEDIUM.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);

            LEFT_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);

            RIGHT_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            RIGHT_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);

            LEFT_CORNER_TOP_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_CORNER_TOP_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
            LEFT_CORNER_TOP_MEDIUM.setBorder(Border.TOP, BorderLineStyle.MEDIUM);

            LEFT_CORNER_BOTTOM_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            LEFT_CORNER_BOTTOM_MEDIUM.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
            LEFT_CORNER_BOTTOM_MEDIUM.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);

            RIGHT_CORNER_TOP_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            RIGHT_CORNER_TOP_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
            RIGHT_CORNER_TOP_MEDIUM.setBorder(Border.TOP, BorderLineStyle.MEDIUM);

            RIGHT_CORNER_BOTTOM_MEDIUM.setBorder(Border.ALL, BorderLineStyle.THIN);
            RIGHT_CORNER_BOTTOM_MEDIUM.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
            RIGHT_CORNER_BOTTOM_MEDIUM.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);

        } catch (WriteException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public WritableWorkbook createExcelFile(String path) {
        try {
            writeWrk = Workbook.createWorkbook(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writeWrk;
    }

    public Workbook openExcelFile(String path) throws BiffException, IOException {
        return openExcelFile(new File(path));

    }

    public Workbook openExcelFile(File f) throws BiffException, IOException {
        wrk = null;
        if (f.exists() && f.isFile() && f.canRead()) {
            wrk = Workbook.getWorkbook(f);
        }
        return wrk;
    }

    public WritableSheet addSheet(String name, WritableWorkbook wrk) {
        return wrk.createSheet(name, wrk.getSheets().length);
    }

    public WritableSheet addSheet(String name, WritableWorkbook wrk, int index) {
        return wrk.createSheet(name, index);
    }

    public static void sheetAutoFitColumns(WritableSheet sheet) {
        for (int i = 0; i < sheet.getColumns(); i++) {
            Cell[] cells = sheet.getColumn(i);
            int longestStrLen = -1;

            if (cells.length == 0) {
                continue;
            }

            /*
             * Find the widest cell in the column.
             */
            for (int j = 0; j < cells.length; j++) {
                if (cells[j].getContents().length() > longestStrLen) {
                    String str = cells[j].getContents();
                    if (str == null || str.isEmpty()) {
                        continue;
                    }
                    longestStrLen = str.trim().length();
                }
            }

            /*
             * If not found, skip the column.
             */
            if (longestStrLen == -1) {
                continue;
            }

            /*
             * If wider than the max width, crop width
             */
            if (longestStrLen > 255) {
                longestStrLen = 255;
            }

            CellView cv = sheet.getColumnView(i);
            cv.setSize(longestStrLen * 256 + 100); /*
             * Every character is 256 units wide, so scale it.
             */

            sheet.setColumnView(i, cv);
        }
    }

    public static void addCell(WritableSheet sheet,
            Border border,
            BorderLineStyle borderLineStyle,
            int col, int row, String value) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setBorder(border, borderLineStyle);
        Label label = new Label(col, row, value, cellFormat);
        sheet.addCell(label);
    }

    public static void addCell(WritableSheet sheet, WritableCellFormat cellFormat,
            int col, int row, String value) throws WriteException {
        Label label = new Label(col, row, value, cellFormat);
        sheet.addCell(label);
    }

    public void writeXlsTable(WritableSheet sheet, String header, ArrayList<String> lbl, ArrayList<ArrayList<String>> data, int col, int line, int direction) throws WriteException, IOException {
        if (direction == XLS_TABLE_HORIZONTAL) {
            int colTemp = col;
            if (!header.equals("")) {
                sheet.mergeCells(col, line, col + lbl.size() - 1, line);
                Label titleLbl = new Label(col, line, header, titleFormat);
                sheet.addCell(titleLbl);
                line++;
            }
            for (int i = 0; i < lbl.size(); i++) {
                Label headerSheet = new Label(colTemp, line, lbl.get(i).toString(), headerFormat);
                sheet.addCell(headerSheet);
                colTemp++;
            }
            line++;

            colTemp = col;
            for (int j = 0; j < data.size(); j++) {
                ArrayList<String> a = data.get(j);
                for (int k = 0; k < a.size(); k++) {
                    Label lineSheet = null;
                    if (j == data.size() - 1) {
                        lineSheet = new Label(colTemp, line, a.get(k), LEFT_RIGHT_BOTTOM_MEDIUM);
                    } else {
                        lineSheet = new Label(colTemp, line, a.get(k), LEFT_RIGHT_MEDIUM);
                    }
                    sheet.addCell(lineSheet);
                    colTemp++;
                }
                colTemp = col;
                line++;
            }
        }
        if (direction == XLS_TABLE_VERTICAL) {
            int lineTemp = line;
            if (!header.equals("")) {
                sheet.mergeCells(col, line, col + data.size(), line);
                Label titleLbl = new Label(col, line, header, titleFormat);
                sheet.addCell(titleLbl);
                line++;
            }
            lineTemp = line;
            for (int i = 0; i < lbl.size(); i++) {
                Label headerSheet = null;
                if (i == 0) {
                    headerSheet = new Label(col, lineTemp, lbl.get(i).toString(), LEFT_RIGHT_TOP_MEDIUM);
                } else if (i == lbl.size() - 1) {
                    headerSheet = new Label(col, lineTemp, lbl.get(i).toString(), LEFT_RIGHT_BOTTOM_MEDIUM);
                } else {
                    headerSheet = new Label(col, lineTemp, lbl.get(i).toString(), LEFT_RIGHT_MEDIUM);
                }
                sheet.addCell(headerSheet);
                lineTemp++;
            }

            col++;
            lineTemp = line;
            for (int j = 0; j < data.size(); j++) {
                ArrayList<String> a = data.get(j);
                for (int k = 0; k < a.size(); k++) {
                    Label lineSheet = null;
                    if (k == 0) {
                        lineSheet = new Label(col, lineTemp, a.get(k), RIGHT_CORNER_TOP_MEDIUM);
                    } else if (k == a.size() - 1) {
                        lineSheet = new Label(col, lineTemp, a.get(k), RIGHT_CORNER_BOTTOM_MEDIUM);
                    } else {
                        lineSheet = new Label(col, lineTemp, a.get(k), RIGHT_MEDIUM);
                    }
                    sheet.addCell(lineSheet);
                    lineTemp++;
                }
                col++;
            }
        }
    }

    public void writeXlsTable(WritableSheet sheet, String header, ArrayList<String> lbl, ArrayList<ArrayList<String>> data, int col, int line) throws WriteException, IOException {
        writeXlsTable(sheet, header, lbl, data, col, line, XLS_TABLE_HORIZONTAL);
    }

    public void writeXlsTable(WritableSheet sheet, String header, ArrayList<String> lbl, ArrayList<ArrayList<String>> data, int col) throws WriteException, IOException {
        writeXlsTable(sheet, header, lbl, data, col, 0, XLS_TABLE_HORIZONTAL);
    }

    public void writeXlsTable(WritableSheet sheet, String header, ArrayList<String> lbl, ArrayList<ArrayList<String>> data) throws WriteException, IOException {
        writeXlsTable(sheet, header, lbl, data, 0, 0, XLS_TABLE_HORIZONTAL);
    }

    public boolean addDefaultSheet(WritableWorkbook workbook, String name, ArrayList header, ArrayList headerComment, Object[][] data) {
        try {
            WritableSheet sheet = workbook.createSheet(name, workbook.getSheets().length);
            for (int i = 0; i < header.size(); i++) {
                Label headerSheet = new Label(i, 0, header.get(i).toString(), headerFormat);
                if (headerComment != null && headerComment.size() >= i) {
                    WritableCellFeatures comment = new WritableCellFeatures();
                    comment.setComment(headerComment.get(i).toString());
                    headerSheet.setCellFeatures(comment);
                }
                sheet.addCell(headerSheet);
            }

            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < header.size(); k++) {
                    Label line = new Label(k, j + 1, data[j][k].toString(), lineFormat);
                    sheet.addCell(line);
                }
            }
            workbook.write();
            workbook.close();
            return true;
        } catch (RowsExceededException e1) {
            e1.printStackTrace();
        } catch (WriteException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object[][] readXlsTable(Sheet sheet, int linebeg, int colbeg, int lineend, int colend) {
        ArrayList<Cell[]> a = new ArrayList<Cell[]>();
        int nbline = 0;
        if (colend == -1) {
            colend = sheet.getColumns();
        }
        for (int i = colbeg; i < colend; i++) {
            Cell[] tabCell = sheet.getColumn(i);
            if (tabCell.length > nbline) {
                nbline = tabCell.length;
            }
            a.add(tabCell);
        }
        Object[][] o = new Object[nbline][a.size()];
        int cptLine = 0;
        int cptCol = 0;
        for (int j = 0; j < a.size(); j++) {
            cptLine = 0;
            for (int k = linebeg; k < nbline; k++) {
                o[cptLine][cptCol] = a.get(j)[k].getContents();
                cptLine++;
            }
            cptCol++;
        }
        return o;
    }
}
