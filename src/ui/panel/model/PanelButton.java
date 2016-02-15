/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.panel.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import ui.tools.*;

/**
 *
 * @author jchaut
 */
public class PanelButton extends JPanel {

    private GridBagConstraints gbc;
    public static JXButton bApply, bOk, bCancel, bNext, bPrev;
    public JXButton bAdd = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgAdd))));
    public JXButton bDel = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgDel))));
    public JXButton bSelect = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgSelect))));
    public JXButton bUnselect = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgUnselect))));
    public JXButton bMaj = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgRefresh))));
    public JXButton bExcel = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgExcel))));
    public JXButton bSave = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgSave))));
    public JXButton bProc = new JXButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(main.Ressource.imgProcess))));
    public JXBusyLabel busy = UITools.createComplexBusyLabel();
    public JXLabel linfo = new JXLabel();
    public final int spaceTop = 5;
    public final int spaceBottom = 5;
    public final int spaceLeft = 5;
    public final int spaceRight = 5;
    public int x = 1;
    public static final int BT_CUSTOM = 0;
    public static final int BT_OK = 1;
    public static final int BT_OK_CANCEL = 2;
    public static final int BT_OK_CANCEL_APPLY = 3;
    public static final int BT_CANCEL = 4;
    public static final int BT_CANCEL_APPLY = 5;
    public static final int BT_APPLY = 6;
    public static final int BT_NEXT_PREV = 7;
    public static final int BT_ADD_DEL = 8;
    public static final int BT_TABLE = 9;

    public PanelButton() {
        super(new GridBagLayout(), true);
        build(3);
    }

    public PanelButton(int cas) {
        build(cas);
    }

    private void build(int cas) {
        if (cas == 1 || cas == 2 || cas == 3) {
            bOk = new JXButton("OK");
            bOk.setActionCommand("OK");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bOk, gbc);
            x++;
        }
        if (cas == 2 || cas == 3 || cas == 4 || cas == 5) {
            bCancel = new JXButton(i18n.Language.getLabel(130));
            bCancel.setActionCommand("Cancel");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bCancel, gbc);
            x++;
        }
        if (cas == 3 || cas == 5 || cas == 6) {
            bApply = new JXButton(i18n.Language.getLabel(131));
            bApply.setActionCommand("Apply");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bApply, gbc);
            x++;
        }

        if (cas == 7) {
            bPrev = new JXButton(i18n.Language.getLabel(162));
            bPrev.setActionCommand("previous");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bPrev, gbc);
            x++;
            bNext = new JXButton(i18n.Language.getLabel(161));
            bNext.setActionCommand("next");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bNext, gbc);
            x++;
        }
        if (cas == 8) {
            bAdd.setActionCommand("add");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bAdd, gbc);
            x++;
            bDel.setActionCommand("del");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bDel, gbc);
            x++;
        }
        if (cas == 9) {
            bAdd.setActionCommand("add");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bAdd, gbc);
            x++;
            bDel.setActionCommand("del");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bDel, gbc);
            x++;
            bMaj.setActionCommand("update");
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 1;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
            this.add(bMaj, gbc);
            x++;
        }
    }

    public void addButton(JButton b) {
        addButton(b, "");
    }

    public void addButton(JButton b, String command) {
        if (!command.equals("")) {
            b.setActionCommand(command);
        }
        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 1;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        this.add(b, gbc);
        x++;
    }

    public void addBusy() {
        busy.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 1;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        this.add(busy, gbc);
        x++;
    }

    public void addInfo() {
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(spaceTop, spaceLeft, spaceBottom, spaceRight);
        this.add(linfo, gbc);
    }
}
