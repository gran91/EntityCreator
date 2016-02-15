/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.checklist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Ressource;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Jeremy.CHAUT
 */
public class CheckListPanel extends JPanel implements ListSelectionListener, ActionListener {
    
    protected JXLabel lname, llist, lmsg;
    protected JTextField tname;
    protected JList list;
    private JScrollPane scrollList;
    private CheckListManager checklistmanager;
    private GridBagConstraints gbc;
    private JXButton bAj, bSup;
    protected Object[] data;
    protected String msg, name;
    
    public CheckListPanel(int id) {
        super(new BorderLayout(), true);
        name = i18n.Language.getLabel(id);
        init(name + ":");
        load();
    }
    
    public CheckListPanel(String title) {
        super(new BorderLayout(), true);
        name = title;
        init(name + ":");
        load();
    }
    
    public CheckListPanel(int id, String[] d) {
        super(new BorderLayout(), true);
        name = i18n.Language.getLabel(id);
        data = d;
        init(name + ":");
        load();
    }
    
    public CheckListPanel(String title, String[] d) {
        super(new BorderLayout(), true);
        name = title;
        data = d;
        init(name + ":");
        load();
    }
    
    private void init(String name) {
        lname = new JXLabel(name);
        lmsg = new JXLabel("");
        lmsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lmsg.setForeground(Color.red);
        llist = new JXLabel(i18n.Language.getLabel(189) + " " + name);
        tname = new JTextField();
        list = (data == null) ? new JList() : new JList(data);
        checklistmanager = new CheckListManager(list);
        scrollList = new JScrollPane(list);
        bAj = new JXButton(new ImageIcon(getClass().getClassLoader().getResource(Ressource.imgAdd)));
        bAj.setToolTipText(i18n.Language.getLabel(1));
        bAj.addActionListener(this);
        bSup = new JXButton(new ImageIcon(getClass().getClassLoader().getResource(Ressource.imgDel)));
        bSup.setToolTipText(i18n.Language.getLabel(2));
        bSup.addActionListener(this);
    }
    
    private void load() {
        JXPanel pb = new JXPanel(new FlowLayout(), true);
        pb.add(bAj);
        pb.add(bSup);
        
        JXPanel pt = new JXPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        pt.add(lname, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        pt.add(tname, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        pt.add(lmsg, gbc);
        
        JXPanel pscroll = new JXPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        pscroll.add(llist, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.9;
        pscroll.add(scrollList, gbc);
        
        this.add(pt, BorderLayout.NORTH);
        this.add(pb, BorderLayout.CENTER);
        this.add(pscroll, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object act = e.getSource();
        if (act == bAj) {
            add();
        } else if (act == bSup) {
            del();
        }
    }
    
    public boolean checkAdd() {
        if (!checkNotExist()) {
            msg = name + " " + i18n.Language.getLabel(14);
            return false;
        } else if (tname.getText().isEmpty()) {
            msg = i18n.Language.getLabel(189) + " " + name + " " + i18n.Language.getLabel(188);
            return false;
        }
        return true;
    }
    
    public boolean checkNotExist() {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (data[i].toString().trim().equals(tname.getText().trim())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void add() {
        if (checkAdd()) {
            lmsg.setText("");
            Object[] temp = null;
            if (data == null || data.length == 0) {
                temp = new Object[1];
            } else {
                temp = new Object[data.length + 1];
                for (int i = 0; i < data.length; i++) {
                    temp[i] = data[i];
                }
            }
            temp[temp.length - 1] = tname.getText();
            data = temp;
            list.setListData(data);
        } else {
            lmsg.setText(msg);
        }
    }
    
    public void del() {
        msg = "";
        int[] ids = list.getSelectedIndices();
        if (ids.length <= 0) {
            msg = i18n.Language.getLabel(13);
        } else {
            int reponse = JOptionPane.showOptionDialog(
                    this,
                    i18n.Language.getLabel(41),
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    new ImageIcon("img/warning.gif"),
                    null,
                    null);
            //si oui
            Object[] temp = new Object[data.length - ids.length];
            if (reponse == JOptionPane.OK_OPTION) {
                int cpt = 0;
                for (int i = 0; i < data.length; i++) {
                    if (Arrays.binarySearch(ids, i) < 0) {
                        temp[cpt] = data[i];
                        cpt++;
                    }
                }
                data = temp;
                list.setListData(data);
            }
        }
    }
    
    public CheckListManager getChecklistmanager() {
        return checklistmanager;
    }
    
    public void setChecklistmanager(CheckListManager checklistmanager) {
        this.checklistmanager = checklistmanager;
    }
    
    public Object[] getData() {
        return data;
    }
    
    public void setData(Object[] data) {
        this.data = data;
        if (data != null) {
            list.setListData(data);
        } else {
            list.setListData(new Object[0]);
        }
    }
    
    public JList getList() {
        return list;
    }
    
    public void setList(JList list) {
        this.list = list;
    }
    
    public JXLabel getLname() {
        return lname;
    }
    
    public void setLname(JXLabel lname) {
        this.lname = lname;
    }

    public JTextField getTname() {
        return tname;
    }

    public void setTname(JTextField tname) {
        this.tname = tname;
    }

    public JXLabel getLmsg() {
        return lmsg;
    }

    public void setLmsg(JXLabel lmsg) {
        this.lmsg = lmsg;
    }

    public JScrollPane getScrollList() {
        return scrollList;
    }
    
    public void setScrollList(JScrollPane scrollList) {
        this.scrollList = scrollList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
