/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import model.entity.IEntityModel;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IEntitiesModel{

    public void setFileName(String s);

    public ArrayList<IEntityModel> getEntities();

    public void setEntities(ArrayList<IEntityModel> entities);

    public Object[][] getData();

    public void loadData() throws IOException;

    public void addEntity();

    public void delEntity(IEntityModel entity);

    public void delEntity(int idLink,int id);

    public String[] getColumnsHeader();

    public void setColumnsHeader(String[] h);

    public IEntityModel getModelEntity();

    public String getLink();

    public void setLink(String s);

    public String getNameEntity();

    public int getIdLink();

    public void setIdLink(int n);
}