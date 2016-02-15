package i18n;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Ressource;

/**
 *
 * @author Jérémy Chaut
 */
public class Language {

    public static boolean createLanguageFile(String lngCode) {
        zio.XMLManage xml = new zio.XMLManage("lang", "label");
        try {
            xml.save(Ressource.XML_LNG + System.getProperty("file.separator") + "language" + lngCode.toUpperCase().trim() + ".xml");
        } catch (IOException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static boolean setLabel(int id, String lng, String label) {
        zio.XMLManage xml = new zio.XMLManage("lang", "label");
        xml.loadData(Ressource.XML_LNG + System.getProperty("file.separator") + "language" + lng.toUpperCase().trim() + ".xml");
        xml.del(id);

        ArrayList<String> aElmt = new ArrayList<String>();
        aElmt.add("nom");

        ArrayList<String> aData = new ArrayList<String>();
        aData.add(label);
        xml.add(aElmt, aData, id);
        try {
            xml.save(Ressource.XML_LNG + System.getProperty("file.separator") + "language" + lng.toUpperCase().trim() + ".xml");
        } catch (IOException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static boolean removeLabel(int id, String lng) {
        zio.XMLManage xml = new zio.XMLManage("lang", "label");
        xml.loadData(Ressource.XML_LNG + System.getProperty("file.separator") + "language" + lng.toUpperCase().trim() + ".xml");
        xml.del(id);
        try {
            xml.save(Ressource.XML_LNG + System.getProperty("file.separator") + "language" + lng.toUpperCase().trim() + ".xml");
        } catch (IOException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static boolean removeLabel(String id, String lng) {
        int ind = -1;
        try {
            ind = Integer.parseInt(id.trim());
        } catch (Exception e) {
            ind = -1;
        }
        return removeLabel(ind, lng);
    }

    public static boolean setLabel(String id, String lng, String label) {
        int ind = -1;
        try {
            ind = Integer.parseInt(id.trim());
        } catch (Exception e) {
            ind = -1;
        }
        return setLabel(ind, lng, label);
    }

    public static String getLabel(String file, int id, String lang) {
        zio.XMLManage xml = new zio.XMLManage("lang", "label");
        //xml.setTypeGestion(zio.XMLManage.RESSOURCE);
        File f = new File(Ressource.XML_LNG);
        if (!f.exists()) {
            f.mkdirs();
        }
        xml.loadData(Ressource.XML_LNG + System.getProperty("file.separator") + file);
        if (xml.getAllData() != null) {
            ArrayList[] arr = xml.getAllData();
            String s = "";
            if (arr[1].indexOf(id)
                    != -1) {
                s = (String) arr[0].get(arr[1].indexOf(id));
            }
            return s;
        } else if (xml.getAllData() == null && !file.equals("languageEN.xml")) {
            return getLabel("languageEN.xml", id, lang);
        }
        return "";
    }

    public static int getIdLabel(String s) {
        return getIdLabel(s, getLanguageCode());
    }

    public static int getIdLabel(String s, String lang) {
        int id = -1;
        Object[][] allLng = getAllLabel(lang);
        ArrayList listName = new ArrayList(Arrays.asList(tools.Tools.extractColumn(allLng, 0)));
        ArrayList listId = new ArrayList(Arrays.asList(tools.Tools.extractColumn(allLng, 1)));
        id = listName.indexOf(s);
        return tools.Tools.convertToInt(listId.get(id).toString());
    }

    public static Object[][] getAllLabel(String lang) {
        String file = "language" + lang.toUpperCase().trim() + ".xml";
        zio.XMLManage xml = new zio.XMLManage("lang", "label");
        //xml.setTypeGestion(zio.XMLManage.RESSOURCE);
        xml.loadData(Ressource.XML_LNG + System.getProperty("file.separator") + file);
        Object[][] s = null;
        if (xml.getAllData() != null) {
            ArrayList[] arr = xml.getAllData();
            s = new Object[arr[0].size()][2];
            for (int i = 0; i < s.length; i++) {
                s[i][0] = arr[0].get(i).toString();
                s[i][1] = Integer.parseInt(arr[1].get(i).toString().trim());
            }
            return s;
        } else if (xml.getAllData() == null && !file.equals("languageEN.xml")) {
            return getAllLabel(lang);
        }
        return s;
    }

    public static Object[][] getAllLabel() {
        return getAllLabel(getLanguageCode());
    }

    public static String getLabel(String file, int id) {
        return getLabel(file, id, getLanguageCode());
    }

    public static String getLabel(int id) {
        return getLabel("language" + getLanguageCode().toUpperCase() + ".xml", id, getLanguageCode());
    }

    public static String getLabel(int id, String lng) {
        return getLabel("language" + lng.toUpperCase() + ".xml", id, lng);
    }

    public static ArrayList<String> traduce(ArrayList<Integer> data, String lng) {
        ArrayList<String> a = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            a.add(getLabel(data.get(i), lng));
        }
        return a;
    }

    public static ArrayList<String> traduce(int[] data, String lng) {
        ArrayList<String> a = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            a.add(getLabel(data[i], lng));
        }
        return a;
    }

    public static ArrayList<String> traduce(ArrayList<Integer> data) {
        return traduce(data, getLanguageCode());
    }

    public static ArrayList<String> traduce(int[] data) {
        return traduce(data, getLanguageCode());
    }

    public static String getLanguageCode() {
        String lang = Ressource.conf.getConfig().getProperty("config.lang");
        if (lang == null) {
            Ressource.conf.setConfig("config.lang", Locale.getDefault().getLanguage());
            lang = Locale.getDefault().getLanguage();
        }
        if (lang.equals("")) {
            lang = System.getProperty("user.language");
            Ressource.conf.setConfig("config.lang", System.getProperty("user.language"));
        }
        return lang;
    }
}
