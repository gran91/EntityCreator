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
import java.util.Vector;
import javax.swing.*;
import ui.panel.model.PanelButton;

/**
 *
 * @author JCHAUT
 */
public class UICreateRibbonTask extends JPanel implements ActionListener {

    private JLabel lname,llng, lshortcut;
    private JTextField tname,tlng;
    private JComboBox clng, cshorcut;
    private ButtonGroup group;
    private JRadioButton radioString, radioLng;
    private GridBagConstraints gbc;
    protected static int spaceTop = 5;
    protected static int spaceBottom = 0;
    protected static int spaceLeft = 0;
    protected static int spaceRight = 5;
    private JPanel p;
    private PanelButton pButton = new PanelButton(PanelButton.BT_CUSTOM);
    private int y = 0;

    public UICreateRibbonTask() {
        this.setLayout(new BorderLayout());
        p = new JPanel(new GridBagLayout(), true);
        lname = new JLabel(i18n.Language.getLabel(11));
        llng = new JLabel(i18n.Language.getLabel(18));
        lshortcut = new JLabel(i18n.Language.getLabel(15));
        tname = new JTextField();
        tlng = new JTextField();
        clng = new JComboBox(tools.Tools.extractColumn(i18n.Language.getAllLabel(), 0));
        
        Vector vChar=new Vector();
        for(char c='A';c<='Z';c++){
            vChar.add(c);
        }
        cshorcut = new JComboBox(vChar);
        
        radioString = new JRadioButton("Text");
        radioString.setActionCommand("Text");
        radioString.setSelected(true);

        radioLng = new JRadioButton("ID Language");
        radioLng.setActionCommand("ID Language");

        group = new ButtonGroup();
        group.add(radioString);
        group.add(radioLng);

        radioLng.setSelected(true);
        radioString.addActionListener(this);
        radioLng.addActionListener(this);

        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.gridheight = 2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(llng, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(radioString, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        gbc.gridheight = 1;
        p.add(radioLng, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.gridheight = 2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(clng, gbc);
        
        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lname, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(tname, gbc);
        
        gbc = new GridBagConstraints();
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft + 10, spaceBottom, spaceRight);
        p.add(lshortcut, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        p.add(cshorcut, gbc);

        this.add(p, BorderLayout.CENTER);
        JButton bGener = new JButton("Générer");
        bGener.addActionListener(this);
        pButton.addButton(bGener, "generate");
        this.add(pButton, BorderLayout.SOUTH);
    }

    private boolean checkData() {
        String s = "";
        if (tname.getText().isEmpty()) {
            s += i18n.Language.getLabel(11) + " " + i18n.Language.getLabel(209) + "\n";
        }

        if (s.isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == radioString || o == radioLng) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    gbc = new GridBagConstraints();
                    gbc.anchor = java.awt.GridBagConstraints.WEST;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.gridx = 2;
                    gbc.gridy = 1;
                    gbc.weightx = 0.6;
                    gbc.gridheight = 2;
                    gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
                    if (radioString.isSelected()) {
                        p.remove(clng);
                        p.add(tlng, gbc);
                    } else {
                        p.remove(tlng);
                        p.add(clng, gbc);
                    }
                    p.validate();
                    p.repaint();
                }
            });
        } else if (checkData()) {
            ArrayList h = new ArrayList();
            h.add(tname.getText());
            h.add(i18n.Language.getIdLabel(clng.getSelectedItem().toString()));
            //    new CreateEntity(h, table.transformData());
        }
    }
}
