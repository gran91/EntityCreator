/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chautj
 */
public class FlatFile {

    private String ext;
    private String sep;
    private BufferedWriter bw;
    private FileWriter fw;
    public static String[] CSV = {"csv", ";"};
    public static String[] AMERICAN_CSV = {"csv", ","};
    public static String[] TABULATION = {"txt", "\t"};
    public static String[] NONE = {"txt", ""};

    public FlatFile(String[] type) {
        ext = type[0];
        sep = type[1];
    }

    public void createFile(String path, Object[] data) {
        createFile(path, null, tools.Tools.convertSObjectToMObject(data));
    }

    public void createFile(String path, Object[][] data) {
        createFile(path, null, data);
    }

    public void createFile(String path, ArrayList<String> header, Object[][] data) {
        try {
            fw = new FileWriter(path + "." + ext);
            bw = new BufferedWriter(fw);
            String s = "";
            if (header != null) {
                for (int k = 0; k < header.size(); k++) {
                    if (k != header.size() - 1) {
                        s += header.get(k).toString() + sep;
                    } else {
                        s += header.get(k).toString();
                    }
                }
                bw.write(s);
                bw.newLine();
                bw.flush();
            }

            for (int i = 0; i < data.length; i++) {
                s = "";
                for (int j = 0; j < data[i].length; j++) {
                    if (i != data.length - 1) {
                        s += data[i][j] + sep;
                    } else {
                        s += data[i][j];
                    }
                }
                bw.write(s);
                if (i != data.length - 1) {
                    bw.newLine();
                    bw.flush();
                }
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
