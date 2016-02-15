/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Jeremy.CHAUT
 */
public class XMLManage {

    private Element root;
    private org.jdom.Document doc;
    private String parent, elmtId;
    private String filePath;
    private ArrayList field;
    private ArrayList[] arr;
    public static int RESSOURCE = 1;
    private int typeGestion = 0;
    private int lastID = 0;

    public XMLManage(String parent, String elmt) {
        root = new Element(parent);
        doc = new Document(root);
        elmtId = elmt;
        arr = null;
        field = null;
    }

    public void readFile(String fichier) throws Exception {
        SAXBuilder sxb = new SAXBuilder();
        if (typeGestion == XMLManage.RESSOURCE) {
            doc = sxb.build(getClass().getResourceAsStream(fichier));
        } else {
            doc = sxb.build(new File(fichier));
        }
        filePath = fichier;
        root = doc.getRootElement();
    }

    public void save() throws IOException {
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        File f = new File(filePath);
        if (!f.exists() && !f.isFile()) {
            if (!f.getAbsoluteFile().getParentFile().exists()) {
                f.getAbsoluteFile().getParentFile().mkdirs();
            }
            f.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(filePath);
        sortie.output(doc, fout);
        fout.close();
    }

    public void save(String file) throws IOException {
        filePath = file;
        save();
    }

    public void add(ArrayList elmt, ArrayList value, int id) {
        if (id == -1) {
            id = getNextId();
            lastID = (id > lastID) ? id : lastID;
        }
        field = elmt;
        Element param = new Element(elmtId);
        Attribute atID = new Attribute("id", "" + id);
        param.setAttribute(atID);
        root.addContent(param);
        if (elmt != null && value != null) {
            for (int i = 0; i < elmt.size(); i++) {
                Element e = new Element(tools.ToolString.sansAccents((String) elmt.get(i).toString().replaceAll(" ", "")));
                e.setText(value.get(i).toString());
                param.addContent(e);
            }
        }
    }

    public void add(String main, ArrayList elmt, ArrayList value) {
        Element param = new Element(main);
        root.addContent(param);
        if (elmt != null && value != null) {
            for (int i = 0; i < elmt.size(); i++) {
                Element e = new Element(tools.ToolString.sansAccents((String) elmt.get(i).toString().replaceAll(" ", "")));
                e.setText(value.get(i).toString());
                param.addContent(e);
            }
        }
    }

    public void add(Element main, ArrayList elmt, ArrayList value) {
        if (main != null) {
            if (elmt != null && value != null) {
                for (int i = 0; i < elmt.size(); i++) {
                    Element e = new Element(tools.ToolString.sansAccents((String) elmt.get(i).toString().replaceAll(" ", "")));
                    e.setText(value.get(i).toString());
                    main.addContent(e);
                }
            }
        }
    }

    public void add(Element main, String field, ArrayList elmt, ArrayList value) {
        if (main != null) {
            Element eField = new Element(field);
            main.addContent(eField);
            if (elmt != null && value != null) {
                for (int i = 0; i < elmt.size(); i++) {
                    Element e = new Element(tools.ToolString.sansAccents((String) elmt.get(i).toString().replaceAll(" ", "")));
                    e.setText(value.get(i).toString());
                    eField.addContent(e);
                }
            }
        }
    }

    public Element add(String parent, String main, ArrayList elmt, ArrayList value) {
        Element param = new Element(main);
        Element eMain = null;
        if (root.getName().equals(parent)) {
            eMain = root;
        } else {
            eMain = root.getChild(parent);
        }
        if (eMain == null) {
            eMain = new Element(parent);
            root.addContent(eMain);
        }
        eMain.addContent(param);
        if (elmt != null && value != null) {
            for (int i = 0; i < elmt.size(); i++) {
                Element e = new Element(tools.ToolString.sansAccents((String) elmt.get(i).toString().replaceAll(" ", "")));
                e.setText(value.get(i).toString());
                param.addContent(e);
            }
        }
        return param;
    }

    private Element retrieveLastElement(Element e, String parent) {
        List lst = e.getChildren();
        for (int i = (lst.size() - 1); i >= 0; i--) {
        }
        return null;
    }

    public void add(ArrayList elmt, ArrayList value) {
        add(elmt, value, -1);
    }

    public void del(int id) {
        del("" + id);
    }

    public void del(String id) {
        try {
            readFile(filePath);
            List listParam = root.getChildren(elmtId);
            Iterator i = listParam.iterator();
            while (i.hasNext()) {
                Element courant = (Element) i.next();
                if (id.equals(courant.getAttribute("id").getValue())) {
                    i.remove();
                    break;
                }
            }
            save();
        } catch (Exception ex) {
        }

    }

    public void loadData() {
        loadData(null, filePath);
    }

    public void loadData(ArrayList[] a) {
        loadData(a, filePath);
    }

    public void loadData(String path) {
        loadData(null, path);
    }

    public void loadData(ArrayList[] a, String path) {
        int id = 0;
        arr = a;
        try {
            filePath = path;
            readFile(filePath);
            List listParam = root.getChildren(elmtId);
            Iterator i = listParam.iterator();
            while (i.hasNext()) {
                Element courant = (Element) i.next();
                List lstField = courant.getChildren();
                if (arr == null) {
                    arr = new ArrayList[lstField.size() + 1];
                    for (int k = 0; k < arr.length; k++) {
                        arr[k] = new ArrayList();
                    }
                }
                id = tools.Tools.convertToInt(courant.getAttributeValue("id"));
                lastID = (id > lastID) ? id : lastID;
                for (int j = 0; j < lstField.size(); j++) {
                    Element e = (Element) lstField.get(j);
                    if (e.getChildren().size() > 0) {
                        ArrayList a1 = new ArrayList();
                        for (int k = 0; k < e.getChildren().size(); k++) {
                            Element e1 = (Element) e.getChildren().get(k);
                            a1.add(e1.getText());
                        }
                        arr[j].add(a1);
                    } else {
                        arr[j].add(e.getText().trim());
                    }
                }
                arr[lstField.size()].add(id);

            }
        } catch (Exception ex) {
        }
    }

    public int getNextId() {
        return lastID + 1;
    }

    public ArrayList[] getAllData() {
        return arr;
    }

    public static Object[][] getTransformData(ArrayList[] a) {
        Object[][] o = new Object[a[0].size()][a.length];
        for (int i = 0; i < a[0].size(); i++) {
            for (int j = 0; j < a.length; j++) {
                o[i][j] = a[j].get(i);
            }
        }
        return o;
    }

    public ArrayList<String> getData(int id) {
        if (arr != null && id >= 0) {
            ArrayList<String> aId = arr[arr.length - 1];
            int ind = aId.indexOf(id);
            ArrayList<String> a = new ArrayList<String>();
            for (int i = 0; i < arr.length; i++) {
                a.add(arr[i].get(ind).toString());
            }
            return a;
        } else {
            return null;
        }
    }

    public int getTypeGestion() {
        return typeGestion;
    }

    public void setTypeGestion(int typeGestion) {
        this.typeGestion = typeGestion;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
