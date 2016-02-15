/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

/**
 *
 * @author JCHAUT
 */
public class ActionerModel {

    protected String label;
    protected String actionCmd;
    protected int idLng=-1;

    public ActionerModel(String l, String a) {
        label = l;
        actionCmd = a;
    }
    
    public ActionerModel(int l, String a) {
        label = i18n.Language.getLabel(l);
        actionCmd = a;
        idLng=l;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getActionCmd() {
        return actionCmd;
    }

    public void setActionCmd(String actionCmd) {
        this.actionCmd = actionCmd;
    }

    public int getIdLng() {
        return idLng;
    }

    public void setIdLng(int idLng) {
        this.idLng = idLng;
    }
}
