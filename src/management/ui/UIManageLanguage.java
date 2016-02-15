/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package management.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import ui.tools.UITools;

/**
 *
 * @author JCHAUT
 */
public class UIManageLanguage extends JPanel implements ActionListener {

    private JLabel lname;
    private JComboBox cname;
    public JXButton bAdd = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgAdd))));
    private GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected UITableLanguage table;
    private JPanel pInfo = new JPanel(new FlowLayout(), true);
    private JXBusyLabel busy = UITools.createComplexBusyLabel();
    private JLabel lbusy = new JLabel();
    private int y = 0;

    public UIManageLanguage() {
        this.setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridBagLayout(), true);
        table = new UITableLanguage(this);
        lname = new JLabel(i18n.Language.getLabel(38));
        cname = new JComboBox(new DefaultComboBoxModel(table.getModel().getAllLng().toArray(new String[table.getModel().getAllLng().size()])));

        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lname, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.79;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(cname, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.01;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(bAdd, gbc);
        bAdd.setActionCommand("ADD_LNG_FILE");
        bAdd.addActionListener(this);

        this.add(p, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);

        busy.setEnabled(false);
        pInfo.add(busy);
        pInfo.add(lbusy);
        this.add(pInfo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ADD_LNG_FILE") && cname.getSelectedIndex() != 0) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    busy.setEnabled(true);
                    busy.setBusy(true);
                }
            });
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    table.getModel().addColumn(cname.getSelectedItem().toString());
                    table.getModel().getAllLng().remove(cname.getSelectedItem().toString());
                    table.getModel().getAllCodeLng().remove(cname.getSelectedItem().toString().substring(0, 2));
                    cname.removeItemAt(cname.getSelectedIndex());
                    cname.setSelectedIndex(0);
                }
            });
            t.start();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    busy.setEnabled(false);
                    busy.setBusy(false);
                }
            });
        }
        if (e.getActionCommand().equals("ADD_LABEL")) {
            table.getModel().addRow();
        }
        if (e.getActionCommand().equals("DEL_LABEL")) {
            Integer[] n = new Integer[table.getTable().getSelectedRows().length];
            for (int i = 0; i < n.length; i++) {
                n[i] = table.getTable().getSelectedRows()[i];
            }
            table.getModel().removeRows(n);
            table.getModel().fireTableDataChanged();
        }
    }
}
