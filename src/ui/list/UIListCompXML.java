package ui.list;

import controler.AbstractControler;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import main.Ressource;
import model.entity.IEntityModel;
import model.entity.XMLEntityModel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ui.entity.IUIEntity;

/**
 *
 * @author chautj
 */
public class UIListCompXML extends JPanel implements IUIEntity, ActionListener {

    private JComboBox c;
    protected static final int CURRENTDATA = Integer.parseInt(Ressource.conf.getConfig().getProperty(Ressource.DATA_TYPE_PROP));
    private JXButton bAdd = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgAdd))));
    private GridBagConstraints gbc;
    private int[] n = {1, 2};
    private String type;
    private IUIEntity panel;
    public boolean listener = true;
    public XMLEntityModel model;

    public UIListCompXML(XMLEntityModel m) {
        super(new GridBagLayout(), true);
        model = m;
    }

    public UIListCompXML(String m) {
        super(new GridBagLayout(), true);
        model = new XMLEntityModel(m);
    }

    @Override
    public void load() {
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
        bAdd.addActionListener(this);
    }

    @Override
    public void control(AbstractControler controller) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadData() {
        if (CURRENTDATA == Ressource.XMLDATA) {
            model.getXml().loadData(model.getName() + ".xml");
            String[] data = new String[model.getXml().getAllData().length];
            for (int i = 0; i < model.getXml().getAllData().length; i++) {
            }
            if (model.getXml().getAllData() != null) {
                if (c == null) {
                    //c = new JComboBox(oElmt);
                    AutoCompleteDecorator.decorate(c);
                } else {
                    this.listener = false;
                    c.removeAllItems();
//                    for (int i = 0; i < oElmt.length; i++) {
//                        c.addItem(oElmt[i]);
//                    }
                }
            } else {
                if (c == null) {
                    c = new JComboBox();
                    AutoCompleteDecorator.decorate(c);
                } else {
                    c.removeAllItems();
                }
            }
        }
        this.listener = true;
        this.revalidate();
        this.repaint();
        this.validate();
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

    public void removeButton() {
        this.remove(bAdd);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.load();
        panel.showInDialog();
    }

    public JComboBox getCombobox() {
        return c;
    }

    public void setCombobox(JComboBox c) {
        this.c = c;
    }

    public JXButton getButton() {
        return bAdd;
    }

    public void setButton(JXButton bAj) {
        this.bAdd = bAj;
    }

    public void setEnabled(boolean b) {
        bAdd.setEnabled(b);
        c.setEnabled(b);
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
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getId() {
        throw new UnsupportedOperationException("Not supported yet.");
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
