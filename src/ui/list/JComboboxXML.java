package ui.list;

import controler.AbstractControler;
import controler.entity.XMLControlerEntity;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import main.Ressource;
import model.entities.IEntitiesModel;
import model.entities.XMLEntitiesModel;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;
import org.jdesktop.swingx.JXButton;
import ui.entities.IUIEntities;
import ui.entity.IUIEntity;
import ui.entity.XMLUIEntity;
import ui.popup.UIPopup;
import ui.table.UITable;
import ui.table.model.DefaultTableModel;
import ui.tools.AutoComboBox;

/**
 *
 * @author chautj
 */
public class JComboboxXML extends JPanel implements IUIEntities {

    private AutoComboBox c;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    private JXButton bAdd = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgAdd))));
    private GridBagConstraints gbc;
    protected Object[][] data;
    protected int id = -1;
    protected int idLink = -1;
    protected int idColumnEntity = 0;
    protected boolean isEditable = false;
    public IEntitiesModel model;
    protected XMLUIEntity viewEntity;
    protected String action = "change";

    public JComboboxXML(IEntitiesModel m) {
        super(new GridBagLayout(), true);
        model = m;
    }

    public JComboboxXML(String m) {
        super(new GridBagLayout(), true);
        model = new XMLEntitiesModel(m);
    }

    public void removeButton() {
        this.remove(bAdd);
        this.revalidate();
        this.repaint();
    }

    public AutoComboBox getCombobox() {
        return c;
    }

    public void setCombobox(AutoComboBox c) {
        this.c = c;
    }

    public JXButton getButton() {
        return bAdd;
    }

    public void setButton(JXButton bAj) {
        this.bAdd = bAj;
    }

    public void setSelectedIndex(int n) {
        if (c != null) {
            c.setSelectedIndex(n);
        }
    }

    public int getSelectedIndex() {
        if (c != null) {
            return c.getSelectedIndex();
        }
        return -1;
    }

    public void setEnabled(boolean b) {
        setButtonEnabled(b);
        if (c != null) {
            c.setEnabled(b);
        }
    }

    public void setButtonEnabled(boolean b) {
        bAdd.setEnabled(b);
    }

    @Override
    public void load() {
        if (c == null) {
            c = new AutoComboBox(new ArrayList());
            c.setActionCommand(action);
        }
        bAdd.setActionCommand("add");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(0, 0, 0, 0);
        this.add(c, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(0, 5, 0, 0);
        this.add(bAdd, gbc);
    }

    @Override
    public void loadData() {
        if (model instanceof XMLEntitiesModel) {
            ((XMLEntitiesModel) model).loadData();
        }
        if (model.getModelEntity() != null) {
            model.getModelEntity().setId(getId());
        }
        loadData(model.getData());
    }

    @Override
    public void loadData(Object[][] o) {
        model.setIdLink(idLink);
        data = o;//model.getData();
        if (data != null) {
            int idCol = idColumnEntity;
            if (model.getModelEntity() != null) {
                if (!model.getModelEntity().getNameLinkEntity().isEmpty() && idLink == -1) {
                    idCol = idCol + 2;
                }
            }
            java.util.Arrays.sort(data, new tools.AlphaComparator(idCol));
            ArrayList a = new ArrayList();
            a.add("");
            Object[] ob = tools.Tools.extractColumn(data, idCol);
            if (ob != null) {
                a.addAll(new ArrayList(Arrays.asList(ob)));
            }
            if (c == null) {
                c = new AutoComboBox(a);
            } else {
                c.setDataList(a);
            }
            if (id == -1) {
                c.setSelectedIndex(0);
            } else {
                ArrayList b = new ArrayList(Arrays.asList(tools.Tools.extractColumn(data, data[0].length - 1)));
//                if ((id = b.indexOf(id)) != -1) {
//                    c.setSelectedIndex(id + 1);
//                } else {
//                    c.setSelectedIndex(0);
//                }
                int n = 0;
                if ((n = b.indexOf(id)) != -1) {
                    c.setSelectedIndex(n + 1);
                } else {
                    c.setSelectedIndex(0);
                }
            }
            c.setActionCommand(action);
        }
    }

    @Override
    public void control(AbstractControler controler) {
        c.addActionListener(controler);
        bAdd.addActionListener(controler);
    }

    @Override
    public void addEntity() {
        model.addEntity();
        if (model instanceof XMLEntitiesModel) {
            viewEntity = new XMLUIEntity((XMLEntityModel) model.getModelEntity());
            new XMLControlerEntity(viewEntity);
        }
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
    public IEntityModel getModelEntity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> getData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showInDialog() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showInFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setContainerType(int containerType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIdLink(int id) {
        this.idLink = id;
    }

    @Override
    public int getIdLink() {
        if (model instanceof XMLEntitiesModel) {
            if (((XMLEntitiesModel) model).isDataWithLink() && c.getSelectedIndex() > 0) {
                idLink = tools.Tools.convertToInt(data[c.getSelectedIndex() - 1][0].toString().trim());
            }
        }
        return idLink;
    }

    @Override
    public void setId(int id) {
        this.id = id;
        loadData();
    }

    @Override
    public int getId() {
        if (id <= 0) {
            if (c.getSelectedIndex() <= 0) {
                id = -1;
            } else {
                id = tools.Tools.convertToInt(data[c.getSelectedIndex() - 1][data[0].length - 1].toString().trim());
            }
        }
        //model.getModelEntity().setId(id);
        return id;
    }

    @Override
    public void setShowType(int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IUIEntity getViewEntity() {
        return viewEntity;
    }

    @Override
    public void addModelEntityToUIEntity(IEntityModel e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UITable getTableView() {
//DELETE
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DefaultTableModel getTableModel() {
//DELETE
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
    public IEntitiesModel getModel() {
        return model;
    }

    public int getIdColumnEntity() {
        return idColumnEntity;
    }

    public void setIdColumnEntity(int idColumnEntity) {
        this.idColumnEntity = idColumnEntity;
    }

    public boolean isIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void setTitle(java.lang.String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Window getWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
