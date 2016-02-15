package ui.entity;

import controler.AbstractControler;
import ui.panel.model.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import main.Ressource;
import model.entity.DefaultEntityModel;
import model.entity.IEntityModel;
import ui.tools.LimitedTextField;
import ui.tools.*;
import ui.utils.JComboboxAdd;

/**
 *
 * @author Jeremy.CHAUT
 */
public class DefaultUIEntity extends JPanel implements Observer, IUIEntity {

    protected PanelButton pButton = new PanelButton(PanelButton.BT_OK_CANCEL_APPLY);
    protected GridBagConstraints gbc;
    protected JLabel lname = new JLabel(i18n.Language.getLabel(11));
    protected LimitedTextField tname = new LimitedTextField(50, LimitedTextField.ALPHA);
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected String title;
    protected Window parentContainer;
    protected int error = 0;
    public int id = 0, idParent = 0;
    protected JComboboxAdd parentCombo;
    protected JList parentList;
    protected DefaultEntityModel model;
    public static final int NONE = -1;
    public static final int FRAME = 0;
    public static final int DIALOG = 1;
    protected int containerType = 0;
    public ArrayList<String> data;

    public DefaultUIEntity(DefaultEntityModel m) {
        super(new GridBagLayout(), true);
        model = m;
        model.addObserver(this);
        model.notifyObservers();
    }

    @Override
    public void control(AbstractControler l) {
        pButton.bApply.addActionListener(l);
        pButton.bOk.addActionListener(l);
        pButton.bCancel.addActionListener(l);

    }

    public void load() {
        loadDefaut();
    }

    public void loadDefaut() {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        this.add(lname, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        tname.setColumns(20);
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        this.add(tname, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = java.awt.GridBagConstraints.LINE_END;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        this.add(pButton, gbc);
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
        data = this.model.getData();
        if (data != null && !data.isEmpty()) {
            tname.setText(data.get(0));
        }
    }

    protected void reloadParentCombo() {
        if (parentCombo != null) {
//            parentCombo.loadDataCombobox(tools.Tools.convertArrayListToArray(xml.getAllData()[0], Ressource.oBlk));
        }
    }

    protected void reloadParentList() {
        if (parentList != null) {
//            xml.loadData(pathFile + fileName);
//            ArrayList[] vall = xml.getAllData();
//            if (vall == null) {
//                vdata = new Vector();
//                vid = new Vector();
//            } else {
//                vdata = new Vector(vall[0]);
//                vid = new Vector(vall[vall.length - 1]);
//            }
//            parentList.setListData(new Vector(xml.getAllData()[0]));
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

    /*
     * public void addMenu() { addMenu(new MenuBarDefault(this)); }
     */
    public void addMenu(JMenuBar m) {
        if (parentContainer != null) {
            JFrame frame = (JFrame) parentContainer;
            frame.setJMenuBar(m);
        }
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

    public JComboboxAdd getParentCombo() {
        return parentCombo;
    }

    public void setParentCombo(JComboboxAdd parentCombo) {
        this.parentCombo = parentCombo;
    }

    public Window getParentContainer() {
        return parentContainer;
    }

    public void setParentContainer(Window parentContainer) {
        this.parentContainer = parentContainer;
    }

    public JLabel getLname() {
        return lname;
    }

    public void setLname(JLabel lname) {
        this.lname = lname;
    }

    public JList getParentList() {
        return parentList;
    }

    public void setParentList(JList parentList) {
        this.parentList = parentList;
    }

    public int getContainerType() {
        return containerType;
    }

    public void setContainerType(int containerType) {
        this.containerType = containerType;
    }

    @Override
    public void update(Observable o, Object arg) {
        loadData();
    }

    @Override
    public ArrayList<String> getData() {
        ArrayList<String> a = new ArrayList<String>();
        a.add(tname.getText());
        return a;
    }

    @Override
    public IEntityModel getModelEntity() {
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
    public void setShowType(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTitle(java.lang.String t) {
        title = t;
    }

    @Override
    public String getTitle() {
        if (title == null) {
            title = i18n.Language.getLabel(this.getModelEntity().getIdLng());
        }
        return title;
    }
    
    @Override
    public Window getWindow() {
        return parentContainer;
    }
}
