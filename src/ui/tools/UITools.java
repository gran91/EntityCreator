package ui.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.jdesktop.swingx.painter.BusyPainter;

/**
 *
 * @author Jérémy Chaut
 */
public class UITools {

    public static JFrame createFrame(String title, JComponent p) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(p);
        frame.pack();
        frame.setVisible(true);
        centerOnScreen(frame);
        return frame;
    }

    public static JDialog createDialog(Window parent, String title, JComponent p) {
        JDialog frame = new JDialog(parent, title);
        frame.getContentPane().add(p);
        frame.pack();
        frame.setVisible(true);
        centerOnScreen(frame);
        return frame;
    }

    public static JComboBox loadElementCombobox(JComboBox c, Object[] o) {
        c.removeAllItems();
        for (int i = 0; i < o.length; i++) {
            c.addItem(o[i]);
        }
        return c;
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    public static JButton createButton(String text, String icon) {
        return createButton(text, icon, false);
    }

    public static JButton createButton(String text, String icon, boolean flat) {
        ImageIcon iconNormal = readImageIcon(icon + ".png");
        ImageIcon iconHighlight = readImageIcon(icon + "_highlight.png");
        ImageIcon iconPressed = readImageIcon(icon + "_pressed.png");

        JButton button = new JButton(text, iconNormal);
        button.setFocusPainted(!flat);
        button.setBorderPainted(!flat);
        button.setContentAreaFilled(!flat);
        if (iconHighlight != null) {
            button.setRolloverEnabled(true);
            button.setRolloverIcon(iconHighlight);
        }
        if (iconPressed != null) {
            button.setPressedIcon(iconPressed);
        }
        return button;
    }

    public static JXBusyLabel createComplexBusyLabel() {
        return createComplexBusyLabel(25);
    }

    public static JXBusyLabel createComplexBusyLabel(int dim) {
        JXBusyLabel label = new JXBusyLabel(new Dimension(dim, dim));
        //BusyPainter painter = new BusyPainter(new Rectangle2D.Float(0, 0, 8.0f, 8.0f), new Rectangle2D.Float(5.5f, 5.5f, 27.0f, 27.0f));
        BusyPainter painter = new BusyPainter(new Rectangle2D.Float(0, 0, (dim * 0.13f), (dim * 0.13f)), new Rectangle2D.Float((dim * 0.12f), (dim * 0.12f), (dim * 0.52f), (dim * 0.52f)));
        /*
         * BusyPainter painter = new BusyPainter( new Rectangle2D.Float(0,
         * 0,13.500001f,1), new
         * RoundRectangle2D.Float(12.5f,12.5f,59.0f,59.0f,10,10));
         */
        painter.setTrailLength(4);
        painter.setPoints(8);
        painter.setFrame(-1);
        painter.setBaseColor(Color.BLUE);
        painter.setHighlightColor(Color.LIGHT_GRAY);
        label.setPreferredSize(new Dimension(dim, dim));
        label.setIcon(new EmptyIcon(dim, dim));
        label.setBusyPainter(painter);
        label.setToolTipText("Loading");
        return label;
    }

    public static void closeConteneur(Component c) {
        for (Container p = c.getParent(); p != null; p = p.getParent()) {
            if (p instanceof Window) {
                JFrame frame = (JFrame) p;
                frame.setVisible(false);
                frame.dispose();
                break;
            } else if (p instanceof JInternalFrame) {
                JInternalFrame frame = (JInternalFrame) p;
                frame.setVisible(false);
                frame.dispose();
                break;
            }
        }
    }

    public static void centerOnScreen(Component component) {
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation(
                (screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
    }

    public static ImageIcon readImageIcon(String filename) {
        URL url = UITools.class.getResource("img/" + filename);
        if (url == null) {
            return null;
        }
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
    }

    public static BufferedImage scale(BufferedImage bImage, double factor) {
        int destWidth = (int) (bImage.getWidth() * factor);
        int destHeight = (int) (bImage.getHeight() * factor);
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();

        return bImageNew;
    }

    public static BufferedImage scale(BufferedImage bImage, int maxWidth, int maxHeight) {
        double factor = new Double(maxWidth) / new Double(bImage.getWidth());
        factor = (new Double(maxHeight) / new Double(bImage.getHeight()) < factor) ? new Double(maxHeight) / new Double(bImage.getHeight()) : factor;
        int destWidth = (int) (bImage.getWidth() * factor);
        int destHeight = (int) (bImage.getHeight() * factor);
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();

        return bImageNew;
    }


    public static JPanel createTablePanel(String name, int n) throws ClassNotFoundException {
        JPanel panel = null;
        try {
            Class cl = Class.forName(name);
            Constructor ct = null;
            Object o2;
            if (n == -1) {
                ct = cl.getConstructor();
                o2 = ct.newInstance();
            } else {
                ct = cl.getConstructor(Integer.TYPE);
                o2 = ct.newInstance(new Integer(0));
            }
            //       Method m;
            //     m = cl.getMethod("setGui", Gui.class);
            //   m.invoke(o2, gui);
            panel = (JPanel) o2;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return panel;
    }
    
    public static JSpinner createTimeSpinner() {
        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        spinner.setEditor(de);
        return spinner;
    }
}
