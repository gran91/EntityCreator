/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import main.Ressource;

/**
 *
 * @author Jeremy.CHAUT
 */
public class DefaultCustomerEntityModel extends DefaultEntityModel implements IEntityModel {

    protected String customer;

    public DefaultCustomerEntityModel(String cust) {
        customer = cust;
        path = Ressource.pathCustDir + cust;
        library = cust;
    }

    public DefaultCustomerEntityModel() {
        customer = "";
        path = Ressource.pathCustDir;
        library = "";
    }
    
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String cust) {
        this.customer = cust;
        this.path = Ressource.pathCustDir + cust;
        this.library = cust;
    }
    
    @Override
    public void setData(ArrayList<String> a) {
        data = a;
        if (data != null) {
            customer = a.get(0);
            name = a.get(1);
        }
    }

    @Override
    public void setData() {
        data = new ArrayList<String>();
        data.add(customer);
        data.add(name);
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
    public void loadData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
