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
public class XMLUITableEntities extends JPanel implements Observer, IUIEntities {

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
    protected XMLEntitiesModel model;
    protected JLabel lLink;
    protected AutoComboBox cLink;
    protected int containerType = 0;
    private JScrollPane s;
    private UITable table;
    protected IUIEntity viewEntity;
    protected UIPopup popup;
    protected Object[][] dataLink;

    public XMLUITableEntities(XMLEntitiesModel m) {
        super(new BorderLayout(), true);
        model = m;
        model.addObserver(this);
        model.notifyObservers();
    }

    public XMLUITableEntities(XMLEntitiesModel m, DefaultTableModel tabModel) {
        super(new BorderLayout(), true);
        model = m;
        tableModel = tabModel;
        model.addObserver(this);
        model.notifyObservers();
    }

    public void control(AbstractControler l) {
        pButton.bAdd.addActionListener(l);
        pButton.bDel.addActionListener(l);
        pButton.bMaj.addActionListener(l);
        if (cLink != null) {
            cLink.addActionListener(l);
        }
        table.addMouseListener(l);
        if (table.getPopup() != null) {
            table.getPopup().setControler(l);
        }
    }

    public void load() {
        loadDefaut();
    }

    public void loadDefaut() {
        tableModel = new DefaultTableModel(model.getData(), model.getColumnsHeader());
        table = new UITable(model, tableModel);
        if (popup != null) {
            popup.loadElementPopUp();
            table.setPopup(popup);
        }
        table.loadTable();
        s = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 200));
        table.getTableHeader().setReorderingAllowed(false);
        pinfo = new JXPanel(new FlowLayout(), true);
        linfo = new JXLabel();
        linfo.setFont(new Font(this.getFont().getName(), Font.BOLD, 10));
        setInfo();
        pinfo.add(linfo);
        pAll = new JXPanel(new BorderLayout(), true);
        if (!model.getLink().isEmpty()) {
            XMLEntitiesModel lEntity = new XMLEntitiesModel(model.getLink());
            lEntity.loadData();
            ArrayList a = new ArrayList();
            a.add("");
//            for (Integer mapKey : lEntity.getDataMap().keySet()) {
//                a.addAll(((ArrayList[]) lEntity.getDataMap().get(mapKey))[0]);
//            }

            dataLink = lEntity.getData();
            if (dataLink != null) {
                java.util.Arrays.sort(dataLink, new tools.AlphaComparator(0));
                a.addAll(new ArrayList(Arrays.asList(tools.Tools.extractColumn(dataLink, 0))));
            }
            cLink = new AutoComboBox(a);
            lLink = new JLabel(i18n.Language.getLabel(lEntity.getModelEntity().getIdLng()));

            cLink.setActionCommand("changeLink");
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
            pAll.add(p, BorderLayout.NORTH);
        }
        pAll.add(pButton, BorderLayout.CENTER);
        this.add(pAll, BorderLayout.NORTH);
        this.add(s, BorderLayout.CENTER);
        this.add(pinfo, BorderLayout.SOUTH);

    }

    @Override
    public void loadData() {
        model.loadData();
        loadData(model.getData());
    }

    public void setInfo() {
        if (model.getData() != null && model.getData().length > 0) {
            linfo.setText(i18n.Language.getLabel(12) + ":" + model.getData().length);
        } else {
            linfo.setText("");
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
        tableModel.setData(formatData(o));
        tableModel.setColumns(model.getColumnsHeader());
        tableModel.fireTableDataChanged();
        tableModel.fireTableStructureChanged();
        table.formatColumns();
        setInfo();
    }

    private Object[][] formatData(Object[][] o) {
        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                Object[] o1 = o[i];
                for (int j = 0; j < o1.length - 1; j++) {
                    if (model.getModelEntity().getFieldMap().get(j).size() > 9) {
                        ArrayList link = (ArrayList) model.getModelEntity().getFieldMap().get(j).get(model.getModelEntity().getFieldMap().get(j).size() - 1);
                        Object[][] datal = (Object[][]) link.get(3);
                        Object[] colLinkId = tools.Tools.extractColumn(datal, datal[0].length - 1);
                        int idlink = -1;
                        if ((idlink = Arrays.asList(colLinkId).indexOf(tools.Tools.convertToInt(o1[j].toString().trim()))) != -1) {
                            o1[j] = tools.Tools.extractColumn(datal, 0)[idlink];
                        }

                    }
                }
                o[i] = o1;
            }
        }
        return o;
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
                    if (dataLink[i][0].toString().equals(cLink.getSelectedItem().toString())) {
                        return tools.Tools.convertToInt(dataLink[i][dataLink[i].length - 1].toString().trim());
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void delEntity() {
        int[] tab_sel_rows = table.getSelectedRows();
        int nb_sel_rows = table.getSelectedRowCount();

        if (nb_sel_rows == 0) {
            //mytable.getPanelParent().setInfo(i18n.Language.getLabel(13), Color.RED);
        } else {
            //confirmation de la suppression
            int reponse = JOptionPane.showOptionDialog(
                    table,
                    i18n.Language.getLabel(41),
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    new ImageIcon("img/warning.gif"),
                    null,
                    null);
            //si oui
            if (reponse == JOptionPane.OK_OPTION) {
                for (int i = 0; i < nb_sel_rows; i++) {
                    int id = tab_sel_rows[i];
                    if (id != -1) {
                        try {
                            int idReal = Integer.parseInt(table.getValueAt(id, table.getColumnCount() - 1).toString());
                            int idLink = -1;
                            if (model.getIdLink() == -1 && !model.getModelEntity().getNameLinkEntity().isEmpty()) {
                                idLink = Integer.parseInt(table.getValueAt(id, 0).toString());
                            }
                            model.delEntity(idLink, idReal);
                            //table.getModelTable().removeRow(id);
                        } catch (NumberFormatException ex) {
                        }
                    }
                }
            }
            //reinitialise la table
            //mytable.initMyTable();
        }
    }

    @Override
    public void modifyEntity(int type) {
        int n = -1;
        IEntityModel m = null;
        if (table.getSelectedRow() != -1 && table.getSelectedColumn() != -1) {
            m = new XMLEntityModel(model.getNameEntity());
            if (!model.getLink().isEmpty()) {
                if (model.getIdLink() == -1) {
                    n = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                } else {
                    n = model.getIdLink();
                }
                m.setIdLink(n);
            }
            n = (Integer) table.getValueAt(table.getSelectedRow(), table.getColumnCount() - 1);
            m.loadData(n);
            model.setModelEntity(m);
        }
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
