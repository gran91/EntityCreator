package ui.entities;

import controler.AbstractControler;
import ui.entity.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import ui.panel.model.*;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import main.Ressource;
import model.entities.DefaultEntitiesModel;
import model.entities.IEntitiesModel;
import model.entity.IEntityModel;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import ui.popup.UIPopup;
import ui.table.UITable;
import ui.table.model.*;
import ui.tools.*;

/**
 *
 * @author Jeremy.CHAUT
 */
public class DefaultUITableEntities extends JPanel implements Observer, IUIEntities {
    
    protected PanelButton pButton = new PanelButton(PanelButton.BT_TABLE);
    protected DefaultTableModel tableModel;
    protected JXPanel pAll, pinfo;
    protected JXLabel linfo;
    protected GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected String title;
    protected Window parentContainer;
    protected int error = 0;
    public int id = 0, idParent = 0;
    protected DefaultEntitiesModel model;
    public static final int FRAME = 0;
    public static final int DISPOSE = 1;
    protected int containerType = 0;
    private JScrollPane s;
    private UITable table;
    protected IUIEntity viewEntity;
    
    public DefaultUITableEntities(DefaultEntitiesModel m, IUIEntity vEntity) {
        super(new BorderLayout(), true);
        model = m;
        tableModel = new DefaultTableModel(model.getData(), model.getColumnsHeader());
        viewEntity = vEntity;
        model.addObserver(this);
        model.notifyObservers();
    }
    
    public DefaultUITableEntities(DefaultEntitiesModel m, DefaultTableModel tabModel, IUIEntity vEntity) {
        super(new BorderLayout(), true);
        model = m;
        tableModel = tabModel;
        viewEntity = vEntity;
        model.addObserver(this);
        model.notifyObservers();
    }
    
    public void control(AbstractControler l) {
        pButton.bAdd.addActionListener(l);
        pButton.bDel.addActionListener(l);
        pButton.bMaj.addActionListener(l);
        table.addMouseListener(l);
    }
    
    public void load() {
        loadDefaut();
    }
    
    public void loadDefaut() {
        model.loadData();
        table = new UITable(model, tableModel);
        table.loadTable();
        s = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 200));
        table.getTableHeader().setReorderingAllowed(false);
        pinfo = new JXPanel(new FlowLayout(), true);
        if (!model.getEntities().isEmpty()) {
            linfo = new JXLabel(i18n.Language.getLabel(12) + ":" + model.getEntities().size());
        } else {
            linfo = new JXLabel();
        }
        linfo.setFont(new Font(this.getFont().getName(), Font.BOLD, 10));
        pinfo.add(linfo);
        pAll = new JXPanel(new BorderLayout(), true);
        pAll.add(pButton, BorderLayout.NORTH);
        this.add(pAll, BorderLayout.NORTH);
        this.add(s, BorderLayout.CENTER);
        this.add(pinfo, BorderLayout.SOUTH);
        
    }
    
    @Override
    public void loadData() {
        loadData(this.id);
    }
    
    public void loadData(int id) {
        loadDataDefaut(id);
    }
    
    public void loadDataDefaut(int id) {
        this.id = id;
    }
    
    public void close() {
        if (error == 0) {
            parentContainer.dispose();
        } else {
            JOptionPane.showMessageDialog(this, i18n.Language.getLabel(error));
        }
    }
    
    @Override
    public void show() {
        if (containerType == DefaultUIEntity.FRAME) {
            showInFrame();
        } else if (containerType == DefaultUIEntity.DIALOG) {
            showInDialog();
        }
    }
    
    @Override
    public void showInDialog() {
        parentContainer = UITools.createDialog(null, title, this);
    }
    
    @Override
    public void showInFrame() {
        parentContainer = UITools.createFrame(title, this);
        if (new File(getClass().getClassLoader().getResource(Ressource.imgLogo).getFile()).exists()) {
            parentContainer.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Ressource.imgLogo)).getImage());
        }
        ((JFrame) parentContainer).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void addMenu(JMenuBar m) {
        if (parentContainer != null) {
            JFrame frame = (JFrame) parentContainer;
            frame.setJMenuBar(m);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        loadData();
    }
    
    @Override
    public IUIEntity getViewEntity() {
        return viewEntity;
    }
    
    @Override
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
    @Override
    public UITable getTableView() {
        return table;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdParent() {
        return idParent;
    }
    
    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
    
    public Window getParentContainer() {
        return parentContainer;
    }
    
    public void setParentContainer(Window parentContainer) {
        this.parentContainer = parentContainer;
    }
    
    public int getContainerType() {
        return containerType;
    }
    
    public void setContainerType(int containerType) {
        this.containerType = containerType;
    }
    
    @Override
    public ArrayList<String> getData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IEntityModel getModelEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UIPopup getPopup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPopup(UIPopup p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadData(Object[][] o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addModelEntityToUIEntity(IEntityModel e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIdLink(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getIdLink() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyEntity(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setShowType(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IEntitiesModel getModel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTitle(java.lang.String t) {
        title=t;
    }

    @Override
    public String getTitle() {
        if(title==null){
            title=i18n.Language.getLabel(this.getModel().getModelEntity().getIdLng());
        }
        return title;
    }
    
    @Override
    public Window getWindow() {
        return parentContainer;
    }
}
