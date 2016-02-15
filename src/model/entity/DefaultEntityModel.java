/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.EntityCreator;
import main.Ressource;
import zio.XMLManage;

/**
 *
 * @author Jeremy.CHAUT
 */
public class DefaultEntityModel extends Observable implements IEntityModel {

    protected String path, file, library, sqltable;
    protected XMLManage xml;
    protected ArrayList<String> data;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    protected String name;
    protected int id;
    protected String[] lstElmtEntity = Ressource.defaultElmt;
    protected ArrayList<Integer> errorId = new ArrayList<Integer>();

    public DefaultEntityModel() {
    }

    public DefaultEntityModel(String p, String f, XMLManage x) {
        path = p;
        file = f;
        xml = x;
    }

    public DefaultEntityModel(String f, XMLManage x) {
        path = "";
        file = f;
        xml = x;
    }

    @Override
    public DefaultEntityModel newInstance() {
        try {
            DefaultEntityModel temp=this.getClass().newInstance();
            temp.setPath(path);
            temp.setFile(file);
            temp.setXml(xml);
            return temp;
        } catch (InstantiationException ex) {
            Logger.getLogger(DefaultEntityModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DefaultEntityModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void loadData(int id) {
        if (CURRENTDATA == Ressource.XMLDATA) {
            this.id = id;
            xml.loadData(path + file);
            data = xml.getData(id);
            name = data.get(0);
        }
    }

    @Override
    public void saveData() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            try {
                xml.save();
            } catch (IOException ex) {
                Logger.getLogger(DefaultEntityModel.class.getName()).log(Level.SEVERE, null, ex);
                errorId.clear();
                errorId.add(0);
            }
        }
    }

    @Override
    public void setData(ArrayList<String> a) {
        data = a;
        if (data != null) {
            name = a.get(0);
        }
    }

    @Override
    public void setData() {
        data = new ArrayList<String>();
        data.add(name);
    }

    @Override
    public boolean add() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            if (this.id == 0) {
                xml.add(tools.Tools.convertArrayToArrayList(lstElmtEntity, null), data);
            } else {
                xml.del(this.id);
                xml.add(tools.Tools.convertArrayToArrayList(lstElmtEntity, null), data, this.id);
            }
            saveData();
            //reloadParentCombo();
            //reloadParentList();
                /*
             * if (panelTable != null) { panelTable.getTable().initMyTable(); }
             */
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

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String[] getLstElmtEntity() {
        return lstElmtEntity;
    }

    public void setLstElmtEntity(String[] lstElmtEntity) {
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

    public String getSQLTable() {
        return sqltable;
    }

    public void setSQLTable(String table) {
        this.sqltable = table;
    }

    public XMLManage getXml() {
        return xml;
    }

    public void setXml(XMLManage xml) {
        this.xml = xml;
    }

    @Override
    public void clearData() {
        data.clear();
        id = 0;
        name = "";
    }

    @Override
    public int getIdLng() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getIdLink() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIdLink(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[][] getDataLink() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNameLinkEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNameLinkEntity(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LinkedHashMap<Integer, ArrayList> getFieldMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IEntityModel getLinkEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLinkEntity(IEntityModel linkEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadData() {
        loadData(id);
    }
}
