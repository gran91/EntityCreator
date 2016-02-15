/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jeremy.CHAUT
 */
public class About extends JPanel {

    private String text = "M3ReportDesigner v1.0.0\n\nJérémy Chaut";
    private About about;

    public About() {
        super(new BorderLayout(), true);
        about = this;
        this.add(new JTextArea(text), BorderLayout.CENTER);
        JButton bok = new JButton("OK");
        bok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Window w = (Window) SwingUtilities.getRoot(about);
                w.dispose();
            }
        });

        this.add(bok, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
