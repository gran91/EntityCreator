/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.ArrayList;
import java.util.Observable;
import main.EntityCreator;
import main.Ressource;
import model.entity.DefaultCustomerEntityModel;
import model.entity.IEntityModel;
import zio.XMLManage;

/**
 *
 * @author JCHAUT
 */
public class DefaultEntitiesModel extends Observable implements IEntitiesModel {

    protected String path, file, library, sqltable;
    protected String customer;
    protected XMLManage xml;
    protected ArrayList<IEntityModel> entities = new ArrayList<IEntityModel>();
    protected Object[][] tableData;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    protected IEntityModel modelEntity;
    protected String[] header;

    public DefaultEntitiesModel(String cust, String f, XMLManage x, IEntityModel model) {
        xml = x;
        customer = cust;
        path = (!customer.equals("")) ? Ressource.pathCustDir + customer : "";
        file = f;
        modelEntity = model;
    }

    @Override
    public void loadData() {
        entities.clear();
        if (CURRENTDATA == Ressource.XMLDATA) {
            xml.loadData(path + file);
            ArrayList[] d = xml.getAllData();
            if (d != null) {
                entities = new ArrayList<IEntityModel>();
                tableData = new Object[d[d.length - 1].size()][d.length];
                for (int i = 0; i < d[d.length - 1].size(); i++) {
                    modelEntity = modelEntity.newInstance();
                    if (modelEntity != null) {
                        int n = Integer.parseInt(d[d.length - 1].get(i).toString());
                        if (modelEntity.getClass().getSuperclass().equals(DefaultCustomerEntityModel.class)) {
                            ((DefaultCustomerEntityModel) modelEntity).setCustomer(customer);
                        }
                        modelEntity.loadData(n);
                        entities.add(modelEntity);
                        tools.Tools.addDataToObject(tableData, modelEntity.getData(), i);
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
        return tableData;
    }

    @Override
    public ArrayList<IEntityModel> getEntities() {
        return entities;
    }

    @Override
    public void setEntities(ArrayList<IEntityModel> a) {
        this.entities = a;
    }

    @Override
    public void setFileName(String s) {
        file = s;
    }

    @Override
    public void addEntity() {
    }

    @Override
    public void delEntity(IEntityModel entity) {
        delEntity(entity.getIdLink(), entity.getId());
    }

    @Override
    public void delEntity(int idLink, int id) {
        if (CURRENTDATA == Ressource.XMLDATA) {
            xml.loadData(path + file);
            xml.del(id);
        }
        loadData();
    }

    public IEntityModel getModelEntity() {
        return modelEntity;
    }

    public void setModelEntity(IEntityModel modelEntity) {
        this.modelEntity = modelEntity;
    }

    @Override
    public String[] getColumnsHeader() {
        return header;
    }

    @Override
    public void setColumnsHeader(String[] h) {
        header = h;
    }

    @Override
    public String getLink() {
        return customer;
    }

    @Override
    public void setLink(String s) {
        customer = s;
    }

    @Override
    public String getNameEntity() {
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
}
