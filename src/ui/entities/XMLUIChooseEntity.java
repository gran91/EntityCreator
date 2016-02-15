package ui.entities;

import controler.AbstractControler;
import controler.entity.XMLControlerEntity;
import java.awt.*;
import ui.entity.*;
import ui.panel.model.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import main.Ressource;
import model.entities.IEntitiesModel;
import model.entities.XMLEntitiesModel;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;
import org.jdesktop.swingx.JXPanel;
import ui.list.JComboboxXML;
import ui.popup.UIPopup;
import ui.table.UITable;
import ui.table.model.*;
import ui.tools.*;

/**
 *
 * @author Jeremy.CHAUT
 */
public class XMLUIChooseEntity extends JPanel implements Observer, IUIEntities {

    protected PanelButton pButton = new PanelButton(PanelButton.BT_OK_CANCEL_APPLY);
    protected JXPanel pAll;
    protected JLabel lEntity;
    protected JComboboxXML cEntity;
    protected GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected String title;
    protected Window parentContainer;
    protected int error = 0;
    public int id = 0, idParent = 0;
    protected XMLEntitiesModel model;
    public static final int FRAME = 0;
    public static final int DISPOSE = 1;
    protected int containerType = 0;
    private JScrollPane s;
    protected IUIEntity viewEntity;
    protected UIPopup popup;
    protected Object[][] dataLink;

    public XMLUIChooseEntity(XMLEntitiesModel m) {
        super(new BorderLayout(), true);
        model = m;
        model.addObserver(this);
        model.notifyObservers();
    }

    public void control(AbstractControler l) {
        cEntity.control(l);
    }

    public void load() {
        loadDefaut();
    }

    public void loadDefaut() {
        lEntity = new JLabel(i18n.Language.getLabel(model.getModelEntity().getIdLng()));
        cEntity = new JComboboxXML(model);
        cEntity.setAction("change");
        cEntity.load();

        viewEntity = new XMLUIEntity((XMLEntityModel)model.getModelEntity());
        viewEntity.load();
        ((XMLUIEntity) viewEntity).removeButton();

        JPanel p = new JPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lEntity, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(cEntity, gbc);

        s = new JScrollPane(((XMLUIEntity) viewEntity));

        this.add(p, BorderLayout.NORTH);
        this.add(s, BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        if (cEntity != null) {
            int n = cEntity.getSelectedIndex();
            cEntity.setIdLink(model.getIdLink());
            model.loadData();
            cEntity.loadData();
            cEntity.setSelectedIndex(n);
            model.getModelEntity().setIdLink(cEntity.getIdLink());
            model.getModelEntity().setId(cEntity.getId());
            model.getModelEntity().loadData(cEntity.getId());
            viewEntity.setId(model.getModelEntity().getId());
            viewEntity.loadData();
        }
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
    public UITable getTableView() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (cEntity != null) {
            cEntity.setId(id);
        }
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
        return model.getModelEntity();
    }

    @Override
    public UIPopup getPopup() {
        return popup;
    }

    @Override
    public void setPopup(UIPopup p) {
        popup = p;
    }

    @Override
    public void loadData(Object[][] o) {
    }

    @Override
    public void addModelEntityToUIEntity(IEntityModel e) {
        viewEntity = new XMLUIEntity((XMLEntityModel) e);
    }

    @Override
    public void setIdLink(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getIdLink() {
        if (cEntity != null) {
            if (cEntity.getSelectedIndex() != 0) {
                for (int i = 0; i < dataLink.length; i++) {
//                    if (dataLink[i][0].toString().equals(cEntity.getSelectedItem().toString())) {
//                        return tools.Tools.convertToInt(dataLink[i][dataLink[i].length - 1].toString().trim());
//                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void delEntity() {
    }

    @Override
    public void modifyEntity(int type) {
    }

    @Override
    public void setShowType(int type) {

    }

    @Override
    public IEntitiesModel getModel() {
        return model;
    }

    @Override
    public void addEntity() {
        model.addEntity();
        viewEntity = new XMLUIEntity((XMLEntityModel)model.getModelEntity());
        new XMLControlerEntity(viewEntity);
    }

    @Override
    public DefaultTableModel getTableModel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTitle(java.lang.String t) {
        title = t;
    }

    @Override
    public String getTitle() {
        if (title == null) {
            title = i18n.Language.getLabel(this.getModel().getModelEntity().getIdLng());
        }
        return title;
    }

    @Override
    public Window getWindow() {
        return parentContainer;
    }

    public JComboboxXML getcEntity() {
        return cEntity;
    }

    public void setcEntity(JComboboxXML cEntity) {
        this.cEntity = cEntity;
    }
}
