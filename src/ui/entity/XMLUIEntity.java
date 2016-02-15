package ui.entity;

import controler.AbstractControler;
import controler.entities.DefaultControlerEntities;
import ui.panel.model.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.io.File;
import java.util.*;
import javax.swing.*;
import main.Ressource;
import model.entities.XMLEntitiesModel;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;
import tools.Tools;
import ui.list.JComboboxXML;
import ui.tools.*;

/**
 *
 * @author Jeremy.CHAUT
 */
public class XMLUIEntity extends JPanel implements IUIEntity, Observer {

    protected JLabel lLink;
    protected AutoComboBox cLink;
    protected PanelButton pButton = new PanelButton(PanelButton.BT_OK_CANCEL_APPLY);
    protected GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected String title;
    protected Window parentContainer;
    protected int error = 0;
    public int id = 0;
    protected IEntityModel model;
    public static final int FRAME = 0;
    public static final int DIALOG = 1;
    protected int containerType = 0;
    public ArrayList<String> data;
    public JComponent[] tabComp;
    protected LinkedHashMap<Integer, ArrayList> map;
    protected int showType = 0;
    protected AbstractControler controler;
    protected boolean activeButton = true;

    public XMLUIEntity(IEntityModel m) {
        super(new GridBagLayout(), true);
        model = m;
        ((XMLEntityModel) model).addObserver(this);
    }

    @Override
    public void control(AbstractControler l) {
        controler = l;
        pButton.bApply.addActionListener(controler);
        pButton.bOk.addActionListener(controler);
        pButton.bCancel.addActionListener(controler);
        if (cLink != null) {
            cLink.setActionCommand("changelink");
            cLink.addActionListener(controler);
        }
    }

    @Override
    public void load() {
        loadDefaut();
    }

    public void loadDefaut() {
        int y = 0;
        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        if (model.getNameLinkEntity() != null && !model.getNameLinkEntity().isEmpty()) {
            lLink = new JLabel(i18n.Language.getLabel(new XMLEntityModel(model.getNameLinkEntity()).getIdLng()));
            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.weightx = 0.2;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
            this.add(lLink, gbc);
            ArrayList<String> a = new ArrayList<>();
            a.add("");
            if (model.getDataLink() != null) {
                java.util.Arrays.sort(model.getDataLink(), new tools.AlphaComparator(0));
                a.addAll(new ArrayList(Arrays.asList(tools.Tools.extractColumn(model.getDataLink(), 0))));
            }
            cLink = new AutoComboBox(a);
            gbc.gridx = 1;
            gbc.weightx = 0.8;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(cLink, gbc);
            y++;
        }
        map = model.getFieldMap();
        tabComp = new JComponent[map.size()];
        for (Integer i : map.keySet()) {
            ArrayList fieldInfo = map.get(i);
            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.weightx = 0.2;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
            this.add(new JLabel(i18n.Language.getLabel(Tools.convertToInt(fieldInfo.get(1).toString().trim()))), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.8;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            boolean insert = false;
            int type = Tools.convertToInt(fieldInfo.get(2).toString().trim());
            if (fieldInfo.get(fieldInfo.size() - 1) instanceof ArrayList) {
                ArrayList a = (ArrayList) fieldInfo.get(fieldInfo.size() - 1);
                if (a.get(a.size() - 1) != null) {
                    JComboboxXML t = new JComboboxXML((XMLEntitiesModel) a.get(a.size() - 1));
                    t.setAction("");
                    DefaultControlerEntities c = new DefaultControlerEntities(t);
                    tabComp[i] = t;
                    this.add(t, gbc);
                    insert = true;
                }
            }

            if (!insert) {
                if (type == Ressource.FILE || type == Ressource.DIRECTORY) {
                    tabComp[i] = new JTextFieldFileChooser(type);
                    this.add(tabComp[i], gbc);
                } else if (type == Ressource.BOOLEAN) {
                    tabComp[i] = new JCheckBox();
                    this.add(tabComp[i], gbc);
                } else {
                    LimitedTextField t = new LimitedTextField(Tools.convertToInt(fieldInfo.get(3).toString().trim()), Tools.convertToInt(fieldInfo.get(2).toString().trim()));
                    tabComp[i] = t;
                    this.add(t, gbc);
                }
            }
            y++;
        }
        y++;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 3;
        gbc.anchor = java.awt.GridBagConstraints.LINE_END;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        if (activeButton) {
            this.add(pButton, gbc);
        }
    }

    @Override
    public void loadData() {
        loadData(this.id);
    }

    public void loadData(int id) {
        data = this.model.getData();
        map = model.getFieldMap();
        if (model.getIdLink() != -1 && model.getDataLink() != null) {
            for (int i = 0; i < model.getDataLink().length; i++) {
                if ((int) model.getDataLink()[i][model.getDataLink()[i].length - 1] == model.getIdLink()) {
                    cLink.setSelectedItem(model.getDataLink()[i][0]);
                    cLink.setEnabled(false);
                    break;
                }
            }
        }
        if (data != null && !data.isEmpty()) {
            for (int i = 0; i < tabComp.length; i++) {
                if (tabComp[i] instanceof LimitedTextField) {
                    ((LimitedTextField) tabComp[i]).setText(data.get(i));
                    if (model.getId() != -1) {
                        ((LimitedTextField) tabComp[i]).setEditable(tools.Tools.convertToBoolean(map.get(i).get(6).toString()));
                        ((LimitedTextField) tabComp[i]).setEnabled(tools.Tools.convertToBoolean(showType));
                    }
                } else if (tabComp[i] instanceof JComboboxXML) {
                    int n = tools.Tools.convertToInt(data.get(i).toString().trim());
                    ((JComboboxXML) tabComp[i]).setId(n);
                    ((JComboboxXML) tabComp[i]).setEnabled(tools.Tools.convertToBoolean(showType));
                } else if (tabComp[i] instanceof JTextFieldFileChooser) {
                    ((JTextFieldFileChooser) tabComp[i]).setText(data.get(i).toString());
                    ((JTextFieldFileChooser) tabComp[i]).setEnabled(tools.Tools.convertToBoolean(showType));
                } else if (tabComp[i] instanceof JCheckBox) {
                    ((JCheckBox) tabComp[i]).setSelected(tools.Tools.convertToBoolean(data.get(i).toString()));
                }
            }
        } else {
            for (JComponent tabComp1 : tabComp) {
                if (tabComp1 instanceof LimitedTextField) {
                    ((LimitedTextField) tabComp1).setText("");
                } else if (tabComp1 instanceof JComboboxXML) {
                    ((JComboboxXML) tabComp1).setId(-1);
                } else if (tabComp1 instanceof JTextFieldFileChooser) {
                    ((JTextFieldFileChooser) tabComp1).setText("");
                } else if (tabComp1 instanceof JTextFieldFileChooser) {
                    ((JCheckBox) tabComp1).setSelected(false);
                }
            }
        }
    }

    public void removeButton() {
        this.remove(pButton);
        this.validate();
        this.repaint();
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
        if (containerType == XMLUIEntity.FRAME) {
            showInFrame();
        } else if (containerType == XMLUIEntity.DIALOG) {
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
    public void update(Observable o, Object arg) {
        System.out.println("Notification du modele");
        loadData();
    }

    @Override
    public ArrayList<String> getData() {
        ArrayList<String> a = new ArrayList<String>();
        for (int i = 0; i < tabComp.length; i++) {
            if (tabComp[i] instanceof LimitedTextField) {
                a.add(((LimitedTextField) tabComp[i]).getText());
            } else if (tabComp[i] instanceof JComboboxXML) {
                a.add("" + ((JComboboxXML) tabComp[i]).getId());
            } else if (tabComp[i] instanceof JTextFieldFileChooser) {
                a.add("" + ((JTextFieldFileChooser) tabComp[i]).getText());
            }else if (tabComp[i] instanceof JCheckBox) {
                a.add("" + tools.Tools.convertBooleanToInt(((JCheckBox) tabComp[i]).isSelected()));
            }
        }
        return a;
    }

    @Override
    public IEntityModel getModelEntity() {
        return model;
    }

    @Override
    public void setIdLink(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getIdLink() {
        if (cLink != null) {
            if (cLink.getSelectedIndex() != 0) {
                for (int i = 0; i < model.getDataLink().length; i++) {
                    if (model.getDataLink()[i][0].toString().equals(cLink.getSelectedItem().toString())) {
                        return tools.Tools.convertToInt(model.getDataLink()[i][model.getDataLink()[i].length - 1].toString().trim());
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void setShowType(int type) {
        showType = type;
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

    public boolean isActiveButton() {
        return activeButton;
    }

    public void setActiveButton(boolean activeButton) {
        this.activeButton = activeButton;
    }
}
