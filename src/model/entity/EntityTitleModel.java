/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import model.entities.IEntitiesModel;
import model.entities.XMLEntitiesModel;

/**
 *
 * @author JCHAUT
 */
public class EntityTitleModel {
    
    protected String nameEntity;
    protected IEntitiesModel entity;
    protected String title;
    protected int id = -1;
    protected int idLng = -1;
    
    public EntityTitleModel(String s, String e) {
        title = s;
        nameEntity = e;
        entity = new XMLEntitiesModel(nameEntity);
    }
    
    public EntityTitleModel(String s, IEntitiesModel e) {
        title = s;
        entity = e;        
        nameEntity = e.getNameEntity();
    }
    
    public EntityTitleModel(int s, IEntitiesModel e) {
        title = i18n.Language.getLabel(s);
        entity = e;        
        nameEntity = e.getNameEntity();
        idLng=s;
    }
    
    public EntityTitleModel(int s, String e) {
        title = i18n.Language.getLabel(s);
        nameEntity = e;
        entity = new XMLEntitiesModel(nameEntity);
        idLng=s;
    }
    
    public IEntitiesModel getEntity() {
        return entity;
    }
    
    public void setEntity(IEntitiesModel entity) {
        this.entity = entity;
    }
    
    public String getNameEntity() {
        return nameEntity;
    }
    
    public void setNameEntity(String entity) {
        this.nameEntity = entity;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
        entity.getModelEntity().setId(1);
    }

    public int getIdLng() {
        return idLng;
    }

    public void setIdLng(int idLng) {
        this.idLng = idLng;
    }
}
