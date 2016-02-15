package zio;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author J?r?my Chaut
 */
public class ToolsFile {

    public static String outCmd;

    public static File[] addFileToFile(File[] o1, File[] o2) {
        int n = 0;
        if (o1 == null && o2 != null) {
            return o2;
        } else if (o1 != null && o2 == null) {
            return o1;
        } else if (o1 == null && o2 == null) {
            return null;
        } else {
            File[] o = new File[o1.length + o2.length];
            for (int i = 0; i < o1.length; i++) {
                o[i] = o1[i];
                n = i + 1;
            }
            System.arraycopy(o2, 0, o, n, o2.length);
            return o;
        }
    }

    public static File[] fileChoice(Component c, String t, ArrayList arrFiltre) {
        File[] tabFile = null;
        String desc = "";
        FiltreExtensible filtre = new FiltreExtensible("");
        if (arrFiltre != null && !arrFiltre.isEmpty()) {
            desc = i18n.Language.getLabel(34) + "(";
            for (int i = 0; i < arrFiltre.size(); i++) {
                filtre.addExtension(arrFiltre.get(i).toString());
                desc += arrFiltre.get(i).toString() + ",";
            }
        } else {
            desc = i18n.Language.getLabel(34) + "...";
        }
        filtre.setDescription(desc);
        JFileChooser jfc;
        if (t != null) {
            jfc = new JFileChooser(t);
        } else {
            jfc = new JFileChooser();
        }
        jfc.addChoosableFileFilter(filtre);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogType(JFileChooser.APPROVE_OPTION);
        if (jfc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
            tabFile = jfc.getSelectedFiles();
        }
        return tabFile;
    }

    public static String downloadFile(Component c, String ext) {
        String desc = "";
        FiltreExtensible filtre = new FiltreExtensible("");
        if (ext != null && !ext.equals("")) {
            desc = i18n.Language.getLabel(34) + "(";
            filtre.addExtension(ext);
            desc += ext + ")";

        }
        filtre.setDescription(desc);
        JFileChooser jfc = new JFileChooser("");
        jfc.addChoosableFileFilter(filtre);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogType(JFileChooser.APPROVE_OPTION);
        if (jfc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile().getPath();
        }
        return null;
    }

    public static File directoryChoice(Component c, JTextField t) {
        File dir = null;
        JFileChooser jfc = new JFileChooser(t.getText());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogType(JFileChooser.APPROVE_OPTION);
        if (jfc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
            dir = jfc.getSelectedFile();
            t.setText(dir.getPath());
        }
        return dir;
    }

    public static boolean copier(File source, File destination) {
        boolean resultat = false;
        // Declaration des flux
        java.io.FileInputStream sourceFile = null;
        java.io.FileOutputStream destinationFile = null;
        try {
            // Cr??ation du fichier :
            destination.createNewFile();
            // Ouverture des flux
            sourceFile =
                    new java.io.FileInputStream(source);
            destinationFile =
                    new java.io.FileOutputStream(destination);
            // Lecture par segment de 0.5Mo
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
// Copie r??ussie

            resultat = true;
        } catch (java.io.FileNotFoundException f) {
        } catch (java.io.IOException e) {
        } finally {
            // Quoi qu'il arrive, on ferme les flux
            try {
                sourceFile.close();
            } catch (Exception e) {
            }
            try {
                destinationFile.close();
            } catch (Exception e) {
            }
        }
        return (resultat);
    }

    public static boolean deplacer(File source, File destination) {
        if (!destination.exists()) {
            // On essaye avec renameTo
            boolean result = source.renameTo(destination);
            if (!result) {
                // On essaye de copier
                result = true;
                result &=
                        copier(source, destination);
                result &=
                        source.delete();
            }

            return (result);

        } else {
            // Si le fichier destination existe, on annule ...
            return (false);
        }
    }

    public static ArrayList<File> listFiles(String dir, String ext, ArrayList<File> list) {
        return listFiles(new File(dir), ext, list);
    }

    public static ArrayList<File> listFiles(File dir, String ext, ArrayList<File> list) {
        File listFile[] = dir.listFiles();
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

    public static File[] listDirectory(String dir) {
        File fdir = new File(dir);
        File[] tabFile;

        if (fdir.exists() && fdir.isDirectory()) {
            File[] listFile = fdir.listFiles();
            int cptFile = 0;
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        cptFile++;
                    }
                }
            }

            int cpt1 = 0;
            tabFile = new File[cptFile];
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        tabFile[cpt1] = listFile[i];
                        cpt1++;
                    }
                }

                for (int i = 0; i < listFile.length; i++) {
                    if (listFile[i].isDirectory()) {
                        tabFile = addFileToFile(tabFile, listDirectory(listFile[i].getAbsolutePath()));
                    }
                }
            }

        } else {
            return null;
        }
        return tabFile;
    }

    public static String imageToFile(Image image, String fileType) throws IOException {
        File imageDir = new File("temp");
        if (!imageDir.exists() || !imageDir.isDirectory()) {
            imageDir.mkdir();
        }
        String imageFileName = fileType + ".jpg";
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        File imageFile = new File(imageDir, imageFileName);
        ImageIO.write(bufferedImage, fileType, imageFile);
        return imageFile.getPath();
    }

    public static String readFile(File f) {
        try {
            if (f.exists()) {
                FileInputStream in = new FileInputStream(f);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                return new String(bytes);
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static boolean writeFile(File f, byte[] s) {
        FileOutputStream fos;
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            f.setWritable(true);
            fos = new FileOutputStream(f);
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

    public static boolean writeFile(File f, String s) {
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

    public static String getSimpleName(File f) {
        int index = -1;
        return (f.getName() != null) ? ((index = f.getName().lastIndexOf(".")) == -1) ? f.getName() : f.getName().substring(0, f.getName().lastIndexOf('.')) : "";
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

    public static void recursifDelete(File path) throws IOException {
        if (!path.exists()) {
            throw new IOException(
                    "File not found '" + path.getAbsolutePath() + "'");
        }
        if (path.isDirectory()) {
            File[] children = path.listFiles();
            for (int i = 0; children != null && i < children.length; i++) {
                recursifDelete(children[i]);
            }
            if (!path.delete()) {
                throw new IOException(
                        "No delete path '" + path.getAbsolutePath() + "'");
            }
        } else if (!path.delete()) {
            throw new IOException(
                    "No delete file '" + path.getAbsolutePath() + "'");
        }
    }

    public static String getFreeDriveLetter() {
        File[] froot = File.listRoots();
        int cpt = froot.length - 1;
        char c = 'Z';
        while (cpt >= 0) {
            if (froot[cpt].getAbsolutePath().charAt(0) == c) {
                cpt--;
                c--;
            } else {
                return "" + c;
            }
        }
        return "";
    }

    public static String mountNetworkDrive(String letter, String path, String login, String mdp) {
        String driveLetter = (letter.equals("")) ? getFreeDriveLetter() : letter;
        try {
            unmountNetworkDrive(letter);
            outCmd = "";
            String[] param = {"cmd", "/c", "net use", driveLetter + ":", path, "/USER:" + login, mdp};

            final Process process = Runtime.getRuntime().exec(param);
            Thread errProcess = new Thread() {

                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                outCmd += line + "\n";
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };

            errProcess.start();

            while (errProcess.isAlive()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }

            return driveLetter;
        } catch (IOException ex) {
            Logger.getLogger(ToolsFile.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public static boolean unmountNetworkDrive(String letter) {
        try {
            outCmd = "";
            String[] param = {"cmd", "/c", "net use", letter + ":", "/delete"};
            final Process process = Runtime.getRuntime().exec(param);
            Thread errProcess = new Thread() {

                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                outCmd += line + "\n";
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };

            errProcess.start();

            while (errProcess.isAlive()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }

            return true;
        } catch (IOException ex) {
            Logger.getLogger(ToolsFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
