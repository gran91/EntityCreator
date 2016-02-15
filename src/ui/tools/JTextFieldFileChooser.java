package ui.tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.Ressource;
import org.jdesktop.swingx.JXButton;

/**
 *
 * @author chautj
 */
public class JTextFieldFileChooser extends JPanel implements ActionListener {

    private JTextField textfield;
    private JXButton bPath;
    private GridBagConstraints gbc;
    private int type;
    private ArrayList<String> filter;

    public JTextFieldFileChooser(int t) {
        super(new GridBagLayout(), true);
        init(t, "");
    }

    public JTextFieldFileChooser(int t, String s) {
        super(new GridBagLayout(), true);
        init(t, s);
    }

    private void init(int t, String s) {
        type = t;
        textfield = new JTextField();
        textfield.setColumns(50);
        bPath = new JXButton("...");

        int x = 0;
        if (!s.isEmpty()) {
            gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = java.awt.GridBagConstraints.WEST;
            gbc.insets = new java.awt.Insets(0, 0, 0, 0);
            this.add(new JLabel(s), gbc);
            x++;
        }

        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(0, 0, 0, 0);
        this.add(textfield, gbc);
        x++;
        gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(0, 5, 0, 0);
        this.add(bPath, gbc);

        bPath.addActionListener(this);
        bPath.setActionCommand("file");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (type == Ressource.DIRECTORY) {
            zio.ToolsFile.directoryChoice(null, textfield);
        } else {
            File[] f = zio.ToolsFile.fileChoice(this, null, filter);
            if (f != null) {
                textfield.setText(f[0].getAbsolutePath());
            }
        }

    }

    public void setEnabled(boolean b){
        textfield.setEnabled(b);
        bPath.setEnabled(b);
        
    }
    public JXButton getbPath() {
        return bPath;
    }

    public void setbPath(JXButton bPath) {
        this.bPath = bPath;
    }

    public JTextField getTextfield() {
        return textfield;
    }

    public void setTextfield(JTextField textfield) {
        this.textfield = textfield;
    }

    public String getText() {
        return textfield.getText();
    }

    public void setText(String s) {
        textfield.setText(s);
    }
}
