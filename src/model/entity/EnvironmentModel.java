/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.util.ArrayList;
import main.Ressource;
import zio.XMLManage;

/**
 *
 * @author Jeremy.CHAUT
 */
public class EnvironmentModel extends DefaultCustomerEntityModel implements IEntityModel {

    private int typeServer;
    private String m3Version, m3beVersion, fixVersion, description;
    private String loginServerView, passServerView;
    private String hostM3BE, portM3BE, loginM3BE, passM3BE;
    private String loginMNE, passMNE;
    private String pathM3BE, pathMNE;
    private String namedb, typedb, hostdb, portdb, logindb, passdb;
    private ArrayList<String>[] listType;

    public EnvironmentModel() {
        super();
        init();
    }

    public EnvironmentModel(String cust) {
        super(cust);
        init();
    }

    private void init() {
        loadTypeServer();
        xml = Ressource.xmlEnv;
        lstElmtEntity = Ressource.envElmt;
        file = Ressource.pathEnvFile;
        sqltable = "Environnement";

    }

    public void loadTypeServer() {
        XMLManage xmltype = Ressource.xmlSys;
        xmltype.loadData(Ressource.pathSysFile);
        listType = xmltype.getAllData();
    }

    @Override
    public void loadData(int id) {
        super.loadData(id);
        typeServer = 0;
        try {
            typeServer = Integer.parseInt(data.get(1));
        } catch (NumberFormatException ex) {
            typeServer = 0;
        }
        hostM3BE = data.get(2);
        portM3BE = data.get(3);
        loginM3BE = data.get(4);
        passM3BE = data.get(5);
        pathMNE = data.get(6);
        loginMNE = data.get(7);
        passMNE = data.get(8);
    }

    @Override
    public void clearData() {
        super.clearData();
        typeServer = 0;
        hostM3BE = "";
        portM3BE = "";
        loginM3BE = "";
        passM3BE = "";
        pathMNE = "";
        loginMNE = "";
        passMNE = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFixVersion() {
        return fixVersion;
    }

    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }

    public String getHostdb() {
        return hostdb;
    }

    public void setHostdb(String hostdb) {
        this.hostdb = hostdb;
    }

    public String getLoginM3BE() {
        return loginM3BE;
    }

    public void setLoginM3BE(String loginM3BE) {
        this.loginM3BE = loginM3BE;
    }

    public String getLoginMNE() {
        return loginMNE;
    }

    public void setLoginMNE(String loginMNE) {
        this.loginMNE = loginMNE;
    }

    public String getLoginServerView() {
        return loginServerView;
    }

    public void setLoginServerView(String loginServerView) {
        this.loginServerView = loginServerView;
    }

    public String getLogindb() {
        return logindb;
    }

    public void setLogindb(String logindb) {
        this.logindb = logindb;
    }

    public String getM3Version() {
        return m3Version;
    }

    public void setM3Version(String m3Version) {
        this.m3Version = m3Version;
    }

    public String getM3beVersion() {
        return m3beVersion;
    }

    public void setM3beVersion(String m3beVersion) {
        this.m3beVersion = m3beVersion;
    }

    public String getNamedb() {
        return namedb;
    }

    public void setNamedb(String namedb) {
        this.namedb = namedb;
    }

    public String getPassM3BE() {
        return passM3BE;
    }

    public void setPassM3BE(String passM3BE) {
        this.passM3BE = passM3BE;
    }

    public String getPassMNE() {
        return passMNE;
    }

    public void setPassMNE(String passMNE) {
        this.passMNE = passMNE;
    }

    public String getPassServerView() {
        return passServerView;
    }

    public void setPassServerView(String passServerView) {
        this.passServerView = passServerView;
    }

    public String getPassdb() {
        return passdb;
    }

    public void setPassdb(String passdb) {
        this.passdb = passdb;
    }

    public String getPathM3BE() {
        return pathM3BE;
    }

    public void setPathM3BE(String pathM3BE) {
        this.pathM3BE = pathM3BE;
    }

    public String getPathMNE() {
        return pathMNE;
    }

    public void setPathMNE(String pathMNE) {
        this.pathMNE = pathMNE;
    }

    public String getPortdb() {
        return portdb;
    }

    public void setPortdb(String portdb) {
        this.portdb = portdb;
    }

    public int getTypeServer() {
        return typeServer;
    }

    public String getTypeServerName() {
        return listType[0].get(listType[listType.length - 1].indexOf(typeServer));
    }

    public void setTypeServer(int typeServer) {
        this.typeServer = typeServer;
    }

    public String getTypedb() {
        return typedb;
    }

    public void setTypedb(String typedb) {
        this.typedb = typedb;
    }

    public String getHostM3BE() {
        return hostM3BE;
    }

    public void setHostM3BE(String hostM3BE) {
        this.hostM3BE = hostM3BE;
    }

    public String getPortM3BE() {
        return portM3BE;
    }

    public void setPortM3BE(String portM3BE) {
        this.portM3BE = portM3BE;
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
    public IEntityModel getLinkEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLinkEntity(IEntityModel linkEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
