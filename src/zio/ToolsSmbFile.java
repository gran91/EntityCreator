package zio;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

/**
 *
 * @author J?r?my Chaut
 */
public class ToolsSmbFile {

    public static String outCmd;

    public static SmbFile[] addFileToFile(SmbFile[] o1, SmbFile[] o2) {
        int n = 0;
        if (o1 == null && o2 != null) {
            return o2;
        } else if (o1 != null && o2 == null) {
            return o1;
        } else if (o1 == null && o2 == null) {
            return null;
        } else {
            SmbFile[] o = new SmbFile[o1.length + o2.length];
            for (int i = 0; i < o1.length; i++) {
                o[i] = o1[i];
                n = i + 1;
            }
            System.arraycopy(o2, 0, o, n, o2.length);
            return o;
        }
    }

    public static ArrayList<SmbFile> listFiles(String dir, String ext, ArrayList<SmbFile> list) throws SmbException, MalformedURLException {
        return listFiles(new SmbFile(dir), ext, list);
    }

    public static ArrayList<SmbFile> listFiles(SmbFile dir, String ext, ArrayList<SmbFile> list) throws SmbException {
        SmbFile listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    listFiles(listFile[i], ext, list);
                } else {
                    if (listFile[i].getName().endsWith(ext)) {
                        list.add(listFile[i]);
                    }
                }
            }
        }
        return list;
    }

    public static SmbFile[] listDirectory(String dir) throws MalformedURLException, SmbException {
        SmbFile fdir = new SmbFile(dir);
        SmbFile[] tabFile;

        if (fdir.exists() && fdir.isDirectory()) {
            SmbFile[] listFile = fdir.listFiles();
            int cptFile = 0;
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        cptFile++;
                    }
                }
            }

            int cpt1 = 0;
            tabFile = new SmbFile[cptFile];
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        tabFile[cpt1] = listFile[i];
                        cpt1++;
                    }
                }

                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        tabFile = addFileToFile(tabFile, listDirectory(listFile[i].getPath()));
                    }
                }
            }

        } else {
            return null;
        }
        return tabFile;
    }

    public static String readFile(SmbFile f) {
        try {
            if (f.exists()) {
                SmbFileInputStream in = new SmbFileInputStream(f);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                return new String(bytes);
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static boolean writeFile(SmbFile f, byte[] s) {
        SmbFileOutputStream fos;
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            f.setReadWrite();
            fos = new SmbFileOutputStream(f);
            fos.write(s);
            fos.flush();
            fos.close();
            if (f.exists()) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ToolsFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean writeFile(SmbFile f, String s) {
        return writeFile(f, s.getBytes());
    }

    public static File streamToFile(InputStream is, String name) {
        FileOutputStream fos;
        java.io.File f = new java.io.File(name);
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            f.setWritable(true);
            fos = new FileOutputStream(f);
            byte[] buf = new byte[3000];
            int read = 0;
            while ((read = is.read(buf)) > 0) {
                fos.write(buf, 0, read);
            }
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(ToolsFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return f;
    }

    public static String getExtension(File f) {
        return "." + f.getName().split("\\.")[f.getName().split("\\.").length - 1];
    }

    public static String getFormatSize(File f) {
        return getFormatSize(f.length());
    }

    public static String getFormatSize(long octet) {
        int cpt = 0;
        while (octet > 1024) {
            octet = octet / 1024;
            cpt++;
        }
        switch (cpt) {
            case 0:
                return octet + " o";
            case 1:
                return octet + " Ko";
            case 2:
                return octet + " Mo";
            case 3:
                return octet + " Go";
            case 4:
                return octet + " To";
            case 5:
                return octet + " Po";
            default:
                return octet + " o";
        }
    }
}
