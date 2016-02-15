package ui.utils;

import controler.utils.ControlerList;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.entities.IEntitiesModel;
import model.entity.IEntityModel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ui.entity.IUIEntity;

/**
 *
 * @author chautj
 */
public class JComboboxAdd extends JPanel implements IUIList {

    private JComboBox c;
    private Object[] oElmt;
    private JXButton bAdd = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgAdd))));
    private GridBagConstraints gbc;
    private IUIEntity panel;
    public boolean listener = true;
    private IEntitiesModel model;

    public JComboboxAdd(IEntitiesModel m, IUIEntity p) {
        super(new GridBagLayout(), true);
        model = m;
        panel = p;
    }

    @Override
    public void load() {
        c = new JComboBox();
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
        bAdd.setActionCommand("add");
    }

    @Override
    public void loadData() {
        loadData(0);
    }

    @Override
    public void loadData(int col) {
        final int column = col;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (model.getEntities().isEmpty()) {
                    if (c == null) {
                        c = new JComboBox();
                        AutoCompleteDecorator.decorate(c);
                    } else {
                        c.removeAllItems();
                    }
                } else {
                    oElmt = new Object[model.getEntities().size()];
                    if (c != null) {
                        listener = false;
                        c.removeAllItems();
                    }
                    for (int i = 0; i < model.getEntities().size(); i++) {
                        IEntityModel mEntity = model.getEntities().get(i);
                        oElmt[i] = mEntity.getData().get(column);
                        if (c != null) {
                            c.addItem(oElmt[i]);
                        }

                    }
                    if (c == null) {
                        c = new JComboBox(oElmt);
                        AutoCompleteDecorator.decorate(c);
                    }
                }
                listener = true;
                revalidate();
                repaint();
                validate();
            }
        });
    }

    @Override
    public void removeButton() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                remove(bAdd);
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void setEnabled(boolean b) {
        bAdd.setEnabled(b);
        c.setEnabled(b);
    }

    @Override
    public void control(ControlerList controller) {
        bAdd.addActionListener(controller);
    }

    @Override
    public IUIEntity getViewEntity() {
        return panel;
    }

    @Override
    public IEntitiesModel getModel() {
        return model;
    }

    public JComboBox getCombobox() {
        return c;
    }

    public void setCombobox(JComboBox c) {
        this.c = c;
    }
}
