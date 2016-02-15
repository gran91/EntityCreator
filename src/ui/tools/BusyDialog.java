/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Ressource;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Jeremy.CHAUT
 */
public class BusyDialog extends JDialog implements ActionListener {

    private JXBusyLabel busy;
    private JTextArea label;
    private GridBagConstraints gbc;
    private final Window parent;
    private Thread proc;
    private JXPanel p, pbutton;
    private final JButton bStop = new JButton("Stop");
    private final JButton bPause = new JButton("Pause");
    private int status = -1;
    public final int START = 1;
    public final int STOP = 0;
    public final int PAUSE = 2;

    public BusyDialog(Window f) {
        super(f);
        parent = f;
        parent.setEnabled(false);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        busy = ui.tools.UITools.createComplexBusyLabel();
        label = new JTextArea();
        label.setOpaque(false);
        label.setPreferredSize(new Dimension(200, 100));
        label.setEditable(false);
        new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        label.setLineWrap(true);
        label.setWrapStyleWord(true);
    }

    public void load() {
        p = new JXPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        p.add(busy, gbc);

        this.getContentPane().add(p, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }

    private void loadProcessButton() {
        if (pbutton != null) {
            p.remove(pbutton);
        }
        pbutton = new JXPanel(new GridBagLayout(), true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        pbutton.add(bPause, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        pbutton.add(bStop, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        p.add(pbutton, gbc);

        bPause.addActionListener(this);
        bStop.addActionListener(this);
        rebuild();
    }

    private void addLabel() {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        p.add(label, gbc);
    }

    public void start() {
        busy.setBusy(true);
        if (proc != null) {
            proc.start();
            status = START;
        }
    }

    public void pause() {
        if (status == START) {
            busy.setBusy(false);
            status = PAUSE;
            bPause.setText("Resume");
        } else {
            busy.setBusy(true);
            status = START;
            bPause.setText("Pause");
        }
    }

    public void stop() {
        status = STOP;
        busy.setBusy(false);
        parent.setEnabled(true);
        this.dispose();
    }

    public void dialStatus() {
        if (this.getStatus() == this.STOP) {
            return;
        } else if (this.getStatus() == this.PAUSE) {
            while (this.getStatus() == this.PAUSE) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Ressource.logger.error(ex.getLocalizedMessage(), ex);
                    setError(ex.getLocalizedMessage());
                }
            }
        }
    }

    public JXBusyLabel getBusy() {
        return busy;
    }

    public void setBusy(JXBusyLabel busy) {
        this.busy = busy;
        rebuild();
    }

    public JTextArea getLabel() {
        return label;
    }

    public void setLabel(JTextArea label) {
        this.label = label;
        rebuild();
    }

    public void setText(String s) {
        this.label.setText(s);
        rebuild();
    }

    public void setError(String s) {
        try {
            label.setForeground(Color.red);
            label.setText(s);
            Thread.sleep(5000);
            label.setForeground(Color.black);
        } catch (InterruptedException ex) {
            Ressource.logger.error(ex.getLocalizedMessage(), ex);
        }
        stop();
    }

    public void clear() {
        setText("");
    }

    public Thread getP() {
        return proc;
    }

    public void setProc(Thread p) {
        this.proc = p;
        loadProcessButton();
    }

    public void rebuild() {
        label.setVisible(!label.getText().isEmpty());
        if (!label.getText().isEmpty()) {
            addLabel();
        } else {
            if (p != null) {
                p.remove(label);
            }
        }
        this.pack();
        this.repaint();
        this.validate();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object act = e.getSource();
        if (act == bStop) {
//            while (proc != null && !proc.isInterrupted()) {
//                proc.stop();
//            }
            stop();
        } else if (act == bPause) {
//            if (proc != null && proc.isAlive()) {
//                proc.suspend();
//            }
            pause();
        }
    }
}
