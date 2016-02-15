/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import main.Ressource;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;

/**
 *
 * @author JCHAUT
 */
public class XMLEntitiesModel extends Observable implements IEntitiesModel {

    protected String path, file, library, sqltable;
    protected int idLink = -1;
    protected Object[][] tableData;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    protected static final String LIBDATA = Ressource.conf.getConfig().getProperty(Ressource.DATA_LIB_PROP);
    protected XMLEntityModel modelEntity;
    protected XMLEntitiesModel linkEntity;
    protected HashMap<Integer, Integer> fieldTableMap;
    protected String[] headerLink, header;
    protected String nameModel;
    protected File fLinkEntity;
    protected ArrayList<String> listPath = new ArrayList<>();
    protected HashMap<Integer, ArrayList[]> dataMap = new HashMap<Integer, ArrayList[]>();
    protected boolean dataWithLink = false;

    public XMLEntitiesModel(String name) {
        nameModel = name;
        modelEntity = new XMLEntityModel(nameModel);
        fieldTableMap = modelEntity.getFieldTable();
        createHeader();
    }

    private void createHeader() {
        if (fieldTableMap.keySet().contains(-1)) {
            headerLink = new String[fieldTableMap.size() + 2];
            headerLink[0] = "IDLink";
            header = new String[fieldTableMap.size()];
            dataWithLink = true;
        } else {
            headerLink = new String[fieldTableMap.size() + 1];
            header = new String[fieldTableMap.size() + 1];
        }

        int i = 0;
        for (Integer mapKey : fieldTableMap.keySet()) {
            Integer d = fieldTableMap.get(mapKey);
            if (mapKey != -1) {
                if (dataWithLink) {
                    headerLink[i + 2] = i18n.Language.getLabel(d);
                } else {
                    headerLink[i] = i18n.Language.getLabel(d);
                }
                header[i] = i18n.Language.getLabel(d);
            } else {
                headerLink[1] = i18n.Language.getLabel(d);
            }
            i++;
        }
        headerLink[headerLink.length - 1] = "ID";
        header[header.length - 1] = "ID";
    }

    @Override
    public void loadData() {
        dataMap.clear();
        int nbLine = 0;
        if (CURRENTDATA == Ressource.XMLDATA) {
            listPath.clear();
            path = LIBDATA + System.getProperty("file.separator") + nameModel + ".xml";
            linkEntity = null;
            if (!modelEntity.getNameLinkEntity().isEmpty()) {
                fLinkEntity = new File(LIBDATA + System.getProperty("file.separator") + modelEntity.getNameLinkEntity());
                linkEntity = new XMLEntitiesModel(modelEntity.getNameLinkEntity());
                if (!fLinkEntity.isDirectory() || !fLinkEntity.exists()) {
                    fLinkEntity.mkdirs();
                }
                linkEntity.loadData();
                dataWithLink = (idLink == -1);
                for (Integer mapKey : linkEntity.getDataMap().keySet()) {
                    ArrayList[] d = linkEntity.getDataMap().get(mapKey);
                    if (d != null) {
                        for (int i = 0; i < d[d.length - 1].size(); i++) {
                            int id = tools.Tools.convertToInt(d[d.length - 1].get(i).toString().trim());
                            if (idLink == -1 || idLink == id) {
                                path = fLinkEntity.getAbsolutePath() + System.getProperty("file.separator") + id + System.getProperty("file.separator") + nameModel + ".xml";
                                modelEntity.getXml().loadData(path);
                                if (modelEntity.getXml().getAllData() != null) {
                                    dataMap.put(id, modelEntity.getXml().getAllData());
                                    nbLine += modelEntity.getXml().getAllData()[modelEntity.getXml().getAllData().length - 1].size();
                                }
                            }
                        }
                    }
                }
                if (dataWithLink) {
                    tableData = new Object[nbLine][fieldTableMap.size() + 2];
                } else {
                    tableData = new Object[nbLine][fieldTableMap.size()];
                }
                int lin = 0;
                int init = 0;
                for (Integer mapKey : dataMap.keySet()) {
                    ArrayList[] d = dataMap.get(mapKey);
                    for (int i = 0; i < d[0].size(); i++) {
                        if (dataWithLink) {
                            init = 2;
                            XMLEntityModel l = new XMLEntityModel(modelEntity.getNameLinkEntity());
                            l.loadData(mapKey);
                            tableData[lin][0] = mapKey;
                            tableData[lin][1] = l.getData().get(0);
                        }
                        int col = 0;
                        for (int j = 0; j < d.length; j++) {
                            if (fieldTableMap.containsKey(j)) {
                                if (modelEntity.getFieldMap().get(j).get(modelEntity.getFieldMap().get(j).size() - 1) instanceof ArrayList) {
                                    ArrayList a = (ArrayList) modelEntity.getFieldMap().get(j).get(modelEntity.getFieldMap().get(j).size() - 1);
                                    XMLEntityModel l = new XMLEntityModel(a.get(0).toString());
                                    l.loadData(tools.Tools.convertToInt(d[j].get(i).toString().trim()));
                                    if (l.getData() != null) {
                                        tableData[lin][col + init] = l.getData().get(tools.Tools.convertToInt(a.get(1).toString().trim()));
                                    } else {
                                        tableData[lin][col + init] = d[j].get(i);
                                    }
                                } else {
                                    tableData[lin][col + init] = d[j].get(i);
                                }
                                col++;
                            }
                        }
                        tableData[lin][tableData[lin].length - 1] = d[d.length - 1].get(i);
                        lin++;
                    }
                }

            } else {
                listPath.add(path);
                modelEntity.getXml().loadData(path);
                dataMap.put(-1, modelEntity.getXml().getAllData());
                if (modelEntity.getXml().getAllData() != null) {
                    nbLine = modelEntity.getXml().getAllData()[modelEntity.getXml().getAllData().length - 1].size();
                    tableData = new Object[nbLine][modelEntity.getXml().getAllData().length];
                    for (Integer mapKey : dataMap.keySet()) {
                        ArrayList[] d = dataMap.get(mapKey);
                        for (int i = 0; i < d[0].size(); i++) {
                            tableData[i][0] = mapKey;
                            for (int j = 0; j < d.length; j++) {
                                tableData[i][j] = d[j].get(i);
                            }
                        }
                    }
                }
            }

        }
    }

    public void setData(Object[][] a) {
        this.tableData = a;
    }

    @Override
    public Object[][] getData() {
        if (tableData == null) {
            loadData();
        }
        return tableData;
    }

    @Override
    public void setFileName(String s) {
        file = s;
    }

    @Override
    public void addEntity() {
        //modelEntity = new XMLEntityModel(nameModel);
        modelEntity.setIdLink(getIdLink());
        modelEntity.setId(-1);
    }

    @Override
    public void delEntity(IEntityModel entity) {
        delEntity(entity.getIdLink(), entity.getId());
    }

    @Override
    public void delEntity(int idLink, int id) {
        if (CURRENTDATA == Ressource.XMLDATA) {
            modelEntity.setIdLink(idLink);
            modelEntity.loadData(id);
            modelEntity.getXml().del(id);
            setChanged();
            notifyObservers("del");
        }
        loadData();
    }

    public IEntityModel getModelEntity() {
        return modelEntity;
    }

    public void setModelEntity(IEntityModel modelEntity) {
        this.modelEntity = (XMLEntityModel) modelEntity;
    }

    @Override
    public String[] getColumnsHeader() {
        return (dataWithLink) ? headerLink : header;
    }

    @Override
    public void setColumnsHeader(String[] h) {
        header = h;
    }

    public HashMap<Integer, ArrayList[]> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<Integer, ArrayList[]> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String getLink() {
        return modelEntity.getNameLinkEntity();
    }

    @Override
    public void setLink(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<IEntityModel> getEntities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEntities(ArrayList<IEntityModel> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNameEntity() {
        return nameModel;
    }

    public boolean isDataWithLink() {
        return dataWithLink;
    }

    public void setDataWithLink(boolean dataWithLink) {
        this.dataWithLink = dataWithLink;
    }

    @Override
    public int getIdLink() {
        return idLink;
    }

    @Override
    public void setIdLink(int n) {
        idLink = n;
    }
}
