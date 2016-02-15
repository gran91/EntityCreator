/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IEntityModel {

    public void saveData();

    public void loadData(int id);

    public void loadData();

    public ArrayList<String> getData();

    public boolean checkData(ArrayList<String> a);

    public boolean add();

    public void setData();

    public void setData(ArrayList<String> a);

    public LinkedHashMap<Integer, ArrayList> getFieldMap();

    public IEntityModel newInstance();

    public void clearData();

    public int getId();

    public int getIdLink();

    public void setId(int n);

    public void setIdLink(int n);

    public Object[][] getDataLink();

    public int getIdLng();

    public String getNameLinkEntity();

    public void setNameLinkEntity(String s);

    public IEntityModel getLinkEntity();

    public void setLinkEntity(IEntityModel linkEntity);

    public String getName();
}
