/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Ressource;
import model.entities.XMLEntitiesModel;
import org.jdom.Element;
import tools.AlphaComparator;
import zio.XMLManage;

/**
 *
 * @author Jeremy.CHAUT
 */
public class XMLEntityModel extends Observable implements IEntityModel {

    protected String path, file;
    protected XMLManage xml;
    protected int idlng;
    protected IEntityModel linkEntity;
    protected String nameLinkEntity;
    protected int viewLinkEntity;
    protected ArrayList<String> data;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    protected static final String LIBDATA = Ressource.conf.getConfig().getProperty(Ressource.DATA_LIB_PROP);
    protected String name;
    protected int id=-1, idLink=-1;
    protected Object[][] dataLink;
    protected ArrayList<String> lstElmtEntity = new ArrayList<String>();
    protected HashMap<Integer, Integer> fieldTableMap = new HashMap<Integer, Integer>();
    protected LinkedHashMap<Integer, ArrayList> fieldMap = new LinkedHashMap<Integer, ArrayList>();
    protected ArrayList<Integer> errorId = new ArrayList<Integer>();

    public XMLEntityModel(String entityName) {
        name = entityName;
        loadXMLModel();
    }

    private void loadXMLModel() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            XMLManage x = new XMLManage("entity", "information");
            try {
                x.readFile("xmlModel" + System.getProperty("file.separator") + name + ".xml");
                Element elmtInfo = x.getRoot().getChild("information");
                idlng = tools.Tools.convertToInt(elmtInfo.getChild("idlng").getText().toString());
                if (elmtInfo.getChild("linkentity") != null) {
                    nameLinkEntity = elmtInfo.getChild("linkentity").getText();
                    viewLinkEntity = 1;
                    if (!nameLinkEntity.isEmpty()) {
                        linkEntity = new XMLEntityModel(nameLinkEntity);
                        XMLEntitiesModel mLink = new XMLEntitiesModel(nameLinkEntity);
                        mLink.loadData();
                        if ((dataLink = mLink.getData()) != null) {
                            java.util.Arrays.sort(dataLink, new tools.AlphaComparator(0));
                        }
                    }
                }
                fieldMap.clear();
                lstElmtEntity.clear();
                Element elmtFields = x.getRoot().getChild("fields");
                String nam = "";
                Iterator i = elmtFields.getChildren().iterator();
                while (i.hasNext()) {
                    Element cur = (Element) i.next();
                    int ind = tools.Tools.convertToInt(cur.getAttributeValue("id"));
                    nam = cur.getChild("name").getText();
                    lstElmtEntity.add(nam);
                    ArrayList a = new ArrayList();
                    a.add(nam);
                    a.add(cur.getChild("idlng").getText());
                    a.add(cur.getChild("type").getText());
                    a.add(cur.getChild("length").getText());
                    a.add(cur.getChild("mandatory").getText());
                    a.add(cur.getChild("exist").getText());
                    a.add(cur.getChild("editable").getText());
                    a.add(cur.getChild("case").getText());
                    a.add(cur.getChild("table").getText());
                    if (cur.getChild("linkentity") != null) {
                        Element link = cur.getChild("linkentity");
                        ArrayList b = new ArrayList();
                        b.add(link.getChild("name").getText());
                        b.add(link.getChild("column").getText());
                        b.add(link.getChild("editable").getText());
                        a.add(b);
                        XMLEntitiesModel mLink = new XMLEntitiesModel(b.get(0).toString());
                        Object[][] dLink = mLink.getData();
                        if (dLink != null) {
                            java.util.Arrays.sort(dLink, new AlphaComparator(0));
                        }
                        b.add(dLink);
                        b.add(mLink);

                    }
                    fieldMap.put(ind, a);
                }
                xml = new XMLManage(getParentElmt(), getChildElmt());
            } catch (Exception ex) {
                Logger.getLogger(XMLEntityModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public HashMap<Integer, Integer> getFieldTable() {
        fieldTableMap.clear();
        if (nameLinkEntity != null && !nameLinkEntity.isEmpty()) {
            XMLEntityModel l = new XMLEntityModel(nameLinkEntity);
            fieldTableMap.put(-1, l.idlng);
        }
        for (Integer mapKey : fieldMap.keySet()) {
            ArrayList d = fieldMap.get(mapKey);
            if (d != null && d.get(8).toString().trim().equals("1")) {
                fieldTableMap.put(mapKey, tools.Tools.convertToInt(d.get(1).toString().trim()));
            }
        }
        return fieldTableMap;
    }

    @Override
    public XMLEntityModel newInstance() {
        try {
            XMLEntityModel temp = this.getClass().newInstance();
            temp.setPath(path);
            temp.setFile(file);
            temp.setXml(xml);
            return temp;
        } catch (InstantiationException ex) {
            Logger.getLogger(XMLEntityModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(XMLEntityModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void loadData(int id) {
        if (CURRENTDATA == Ressource.XMLDATA) {
            this.id = id;
            retrievePath();
            xml.loadData(path);
            data = xml.getData(id);
        }
    }

    private void retrievePath() {
        String sName = System.getProperty("file.separator") + name + ".xml";
        String s =sName;
        IEntityModel temp = this;
        if (linkEntity != null) {
            s = System.getProperty("file.separator") + nameLinkEntity + System.getProperty("file.separator")+linkEntity.getId()+ System.getProperty("file.separator");
            temp = linkEntity.getLinkEntity();
            while (temp != null) {
                s = System.getProperty("file.separator") + temp.getName() + System.getProperty("file.separator")+temp.getId()+ System.getProperty("file.separator") + s;
                temp =temp.getLinkEntity();
            }
            s+=sName;
        }
        path=LIBDATA+s;
    }

    public ArrayList[] getAllData() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            path = LIBDATA + System.getProperty("file.separator") + name + ".xml";
            if (!nameLinkEntity.isEmpty()) {
                path = LIBDATA + System.getProperty("file.separator") + nameLinkEntity + System.getProperty("file.separator") + idLink + System.getProperty("file.separator") + name + ".xml";
            }
            xml.loadData(path);
            return xml.getAllData();
        }
        return null;
    }

    @Override
    public void saveData() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            try {
                xml.save();
            } catch (IOException ex) {
                Logger.getLogger(XMLEntityModel.class.getName()).log(Level.SEVERE, null, ex);
                errorId.clear();
                errorId.add(0);
            }
        }
    }

    @Override
    public void setData(ArrayList<String> a) {
        data = a;
    }

    @Override
    public void setData() {
        data = new ArrayList<String>();
        data.add(name);
    }

    @Override
    public boolean add() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            if (linkEntity != null) {
                linkEntity.loadData(idLink);
            }
            if (idLink == -1) {
                path = LIBDATA + System.getProperty("file.separator") + name + ".xml";
            } else {
                path = LIBDATA + System.getProperty("file.separator") + nameLinkEntity + System.getProperty("file.separator") + idLink + System.getProperty("file.separator") + name + ".xml";
            }
            xml.setFilePath(path);
            xml.loadData();
            if (this.id == -1) {
                xml.add(lstElmtEntity, data);
            } else {
                xml.del(this.id);
                xml.add(lstElmtEntity, data, this.id);
            }
            saveData();
            setChanged();
            notifyObservers("add");
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getData() {
        return data;
    }

    @Override
    public boolean checkData(ArrayList<String> a) {
        errorId.clear();
        return checkDefaultData(a);
    }

    public boolean checkDefaultData(ArrayList<String> a) {
        if (a.get(0).equals("")) {
            errorId.add(0);
            return false;
        }
        return true;
    }

    public ArrayList<Integer> getErrorId() {
        return errorId;
    }

    public void setErrorId(ArrayList<Integer> errorId) {
        this.errorId = errorId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getLstElmtEntity() {
        return lstElmtEntity;
    }

    public void setLstElmtEntity(ArrayList<String> lstElmtEntity) {
        this.lstElmtEntity = lstElmtEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public XMLManage getXml() {
        return xml;
    }

    public void setXml(XMLManage xml) {
        this.xml = xml;
    }

    @Override
    public void clearData() {
        if (data != null) {
            data.clear();
        }
        id = 0;
        name = "";
    }

    public LinkedHashMap<Integer, ArrayList> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(LinkedHashMap<Integer, ArrayList> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public void setIdlng(int idlng) {
        this.idlng = idlng;
    }

    public String getNameLinkEntity() {
        return nameLinkEntity;
    }

    public void setNameLinkEntity(String nameLinkEntity) {
        this.nameLinkEntity = nameLinkEntity;
    }

    public String getParentElmt() {
        return getChildElmt() + "s";
    }

    public String getChildElmt() {
        if (name.length() > 3) {
            return name.substring(0, 3);
        }
        return name;
    }

    public int getIdLink() {
        return idLink;
    }

    public void setIdLink(int idLink) {
        this.idLink = idLink;
        if (linkEntity != null) {
            linkEntity.loadData(idLink);
        }
    }

    public int getViewLinkEntity() {
        return viewLinkEntity;
    }

    public void setViewLinkEntity(int idLinkEntity) {
        this.viewLinkEntity = idLinkEntity;
    }

    @Override
    public int getIdLng() {
        return idlng;
    }

    @Override
    public Object[][] getDataLink() {
        return dataLink;
    }

    public IEntityModel getLinkEntity() {
        return linkEntity;
    }

    public void setLinkEntity(IEntityModel linkEntity) {
        this.linkEntity = linkEntity;
    }

    public HashMap<Integer, Integer> getFieldTableMap() {
        return fieldTableMap;
    }

    public void setFieldTableMap(HashMap<Integer, Integer> fieldTableMap) {
        this.fieldTableMap = fieldTableMap;
    }

    @Override
    public void loadData() {
        loadData(id);
    }
}
