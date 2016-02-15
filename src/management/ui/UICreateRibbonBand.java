/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package management.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import management.process.CreateEntity;
import ui.panel.model.PanelButton;

/**
 *
 * @author JCHAUT
 */
public class UICreateRibbonBand extends JPanel implements ActionListener {

    private JLabel lname, llng, llink;
    private JTextField tname;
    private JComboBox clng, clink;
    private GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    protected UITableEntity table;
    private PanelButton pButton = new PanelButton(PanelButton.BT_CUSTOM);
    private int y = 0;

    public UICreateRibbonBand() {
        this.setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridBagLayout(), true);
        table = new UITableEntity();
        lname = new JLabel(i18n.Language.getLabel(11));
        llng = new JLabel(i18n.Language.getLabel(206));
        llink = new JLabel(i18n.Language.getLabel(208));
        tname = new JTextField();
        clng = new JComboBox(tools.Tools.extractColumn(table.getAllLng(), 0));
        clink = new JComboBox(table.getEntities());

        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lname, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(tname, gbc);
        y++;

        gbc.gridx = 0;
        gbc.weightx = 0.2;
        gbc.gridy = y;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(llng, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(clng, gbc);
        y++;

        gbc.gridx = 0;
        gbc.weightx = 0.2;
        gbc.gridy = y;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(llink, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(clink, gbc);

        this.add(p, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
        JButton bGener = new JButton("Générer");
        bGener.addActionListener(this);
        pButton.addButton(bGener, "generate");
        this.add(pButton, BorderLayout.SOUTH);
    }

    private boolean checkData() {
        String s = "";
        if (tname.getText().isEmpty()) {
            s += i18n.Language.getLabel(11) + " " + i18n.Language.getLabel(209) + "\n";
        } else if (tools.Tools.convertArrayToArrayList(table.getEntities(), null).contains(tname.getText())) {
            s += i18n.Language.getLabel(11) + " " + i18n.Language.getLabel(14) + "\n";
        }

        Object[] t = tools.Tools.extractColumn(table.getModel().getData(), 0);

        if (s.isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkData()) {
            ArrayList h = new ArrayList();
            h.add(tname.getText());
            h.add(i18n.Language.getIdLabel(clng.getSelectedItem().toString()));
            h.add(clink.getSelectedItem());
            new CreateEntity(h, table.transformData());
        }
    }
}
