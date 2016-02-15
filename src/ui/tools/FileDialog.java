/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tools;

import java.awt.Window;
import javax.swing.*;

/**
 *
 * @author Jeremy.CHAUT
 */
public class FileDialog extends JOptionPane{

    private JTextFieldFileChooser chooser;
    private JDialog dialog;
    
    public FileDialog(Window w, String title, int type) {
        super();
        setMessageType(JOptionPane.QUESTION_MESSAGE);
        setOptionType(JOptionPane.OK_CANCEL_OPTION);
        chooser = new JTextFieldFileChooser(type);
        setMessage(new Object[]{chooser});
        dialog = createDialog(w, title);
        dialog.setVisible(true);
    }

    public String getPathFile() {
        return chooser.getText();
    }

    public void setPathFile(String path) {
        this.chooser.getTextfield().setText(path);
    }

    public JTextFieldFileChooser getChooser() {
        return chooser;
    }

    public void setChooser(JTextFieldFileChooser chooser) {
        this.chooser = chooser;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }
    
}
