package ui.entities;

import controler.AbstractControler;
import controler.entity.XMLControlerEntity;
import java.awt.*;
import ui.entity.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import main.Ressource;
import model.entities.IEntitiesModel;
import model.entities.XMLEntitiesModel;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import tabpane.JMyTabbedPane;
import ui.list.JComboboxXML;
import ui.popup.UIPopup;
import ui.table.UITable;
import ui.table.model.*;
import ui.tools.*;

/**
 *
 * @author Jeremy.CHAUT
 */
public class XMLUILink extends JPanel implements Observer, IUIEntities {

    protected JXPanel pAll;
    protected JLabel lLink;
    protected JComboboxXML cLink;
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
    protected String nameEntity;
    protected ArrayList<AbstractControler> listControler;
    protected int uitype = 0;
    private JXTaskPaneContainer taskcontainer = new JXTaskPaneContainer();
    JMyTabbedPane tab = new JMyTabbedPane(false, false);

    public XMLUILink(XMLEntitiesModel m, AbstractControler ent) {
        super(new BorderLayout(), true);
        model = m;
        listControler = new ArrayList<>();
        listControler.add(ent);
        model.addObserver(this);
        model.notifyObservers();
    }

    public XMLUILink(XMLEntitiesModel m, ArrayList<AbstractControler> lstEnt) {
        super(new BorderLayout(), true);
        model = m;
        listControler = lstEnt;
        model.addObserver(this);
        model.notifyObservers();
    }

    public XMLUILink(XMLEntitiesModel m, AbstractControler ent, int type) {
        super(new BorderLayout(), true);
        model = m;
        listControler = new ArrayList<>();
        listControler.add(ent);
        uitype = type;
        model.addObserver(this);
        model.notifyObservers();
    }

    public XMLUILink(XMLEntitiesModel m, ArrayList<AbstractControler> lstEnt, int type) {
        super(new BorderLayout(), true);
        model = m;
        listControler = lstEnt;
        uitype = type;
        model.addObserver(this);
        model.notifyObservers();
    }

    public void control(AbstractControler l) {
        cLink.control(l);
//        for (int i = 0; i < listEntity.size(); i++) {
//            listEntity.get(i).control(l);
//        }
    }

    public void load() {
        loadDefaut();
    }

    public void loadDefaut() {
        lLink = new JLabel(i18n.Language.getLabel(model.getModelEntity().getIdLng()));
        cLink = new JComboboxXML(model);
        //cLink.setAction("changeLink");
        cLink.load();

        JPanel p = new JPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lLink, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(cLink, gbc);

        if (uitype == 0) {
            for (int i = 0; i < listControler.size(); i++) {
                JXTaskPane tTask = new JXTaskPane();
                tTask.setTitle(listControler.get(i).getView().getTitle());
                tTask.add((JPanel) listControler.get(i).getView());
                if (i != 0) {
                    tTask.setCollapsed(true);
                } else {
                    tTask.setCollapsed(false);
                }
                taskcontainer.add(tTask);
                listControler.get(i).getView().setContainerType(-1);
            }
            s = new JScrollPane(taskcontainer);
            this.add(s, BorderLayout.CENTER);
        } else {
            for (int i = 0; i < listControler.size(); i++) {
                tab.add(i18n.Language.getLabel(listControler.get(i).getView().getModelEntity().getIdLng()), (JPanel) listControler.get(i).getView());
                listControler.get(i).getView().setContainerType(-1);
            }
            this.add(tab, BorderLayout.CENTER);
        }
        this.add(p, BorderLayout.NORTH);
    }

    @Override
    public void loadData() {
        int n = cLink.getSelectedIndex();
        cLink.loadData();
        cLink.setSelectedIndex(n);
        for (int i = 0; i < listControler.size(); i++) {
            listControler.get(i).getView().setId(-1);
            listControler.get(i).getView().getModel().setIdLink(cLink.getId());
            listControler.get(i).getView().loadData();
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

    public Container getContainer() {
        if (uitype == 0) {
            return taskcontainer;
        } else if (uitype == 1) {
            return tab;
        }
        return null;
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
        if (cLink != null) {
            if (cLink.getSelectedIndex() != 0) {
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

    public int getUitype() {
        return uitype;
    }

    public void setUitype(int uitype) {
        this.uitype = uitype;
    }
    
    @Override
    public Window getWindow() {
        return parentContainer;
    }

    public JComboboxXML getcLink() {
        return cLink;
    }

    public void setcLink(JComboboxXML cLink) {
        this.cLink = cLink;
    }
}
