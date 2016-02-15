/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tools;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jeremy.CHAUT
 */
public class NotificationManager {

    List<NotificationPopup> list = new ArrayList<>();

    public void addNotification(String message) {
        final NotificationPopup f = new NotificationPopup();
        Container c = f.getContentPane();
        c.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        final JLabel l = new JLabel(message);
        l.setOpaque(false);
        c.add(l, constraints);
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        f.setVisible(true);
        final JButton b = new JButton(new AbstractAction("x") {

            @Override
            public void actionPerformed(final ActionEvent e) {
                list.remove(f);
                f.dispose();
                reorderNotification();
            }
        });
        b.setOpaque(false);
        b.setMargin(new Insets(1, 4, 1, 4));
        b.setFocusable(false);
        c.add(b, constraints);
        f.setVisible(true);
        list.add(f);
    }

    private void reorderNotification() {
        int cpt = 1;
        for (NotificationPopup p : list) {
            p.setLocation(p.screenSize.width - p.getWidth(), p.screenSize.height - p.taskBarSize - (p.getHeight() * cpt));
            cpt++;
        }
    }

    public class NotificationPopup extends JDialog {

        private final LinearGradientPaint lpg;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        final int taskBarSize = scnMax.bottom;

        public NotificationPopup() {
            setUndecorated(true);
            setSize(300, 50);
            setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - (getHeight() * (list.size() + 1)));
            lpg = new LinearGradientPaint(0, 0, 0, getHeight() / 2, new float[]{0f, 0.3f, 1f}, new Color[]{new Color(1f, 1f, 1f), new Color(1f, 1f, 1f), new Color(1f, 1f, 1f)});
            //lpg = new LinearGradientPaint(0, 0, 0, getHeight() / 2, new float[]{0f, 0.3f, 1f}, new Color[]{new Color(0.1f, 0.1f, 1f), new Color(1f, 1f, 1f), new Color(1f, 1f, 1f)});
            setContentPane(new BackgroundPanel());
        }

        private class BackgroundPanel extends JPanel {

            public BackgroundPanel() {
                setOpaque(true);
            }

            @Override
            protected void paintComponent(final Graphics g) {
                final Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(lpg);
                g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
                g2d.setColor(Color.BLUE);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        }
    }
}
